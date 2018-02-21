(ns clojure-discord.core
  (:require [clojure.data.json :as json]
            [clojure-discord.requests :as request]
            [clojure-discord.discord :as discord]
            [clojure-discord.parser :as parser]
            [clojure-discord.socket :as socket]))

(def ^:private connection (atom nil))
(def ^:private last-sequence-number (atom nil))
(def ^:private session-id (atom nil))

(defn- get-gateway []
  (request/get (discord/add-base-url "gateway/bot")))

(defn- keep-alive [time-between]
  (.start (Thread. (fn []
             (socket/heartbeat @connection @last-sequence-number)
             (Thread/sleep time-between)
             (recur)))))

(defn- create-gateway-url []
  (str (get (get-gateway) "url") "?v=6&encoding=json"))

(defn- handle-event [parsed-payload]
  (let [event (:event parsed-payload)
        data (:data parsed-payload)]
  (cond (= event "READY") (reset! session-id (get data "session_id")))))

(defn- handle-incoming-request [json-payload]
  (let [parsed-payload (parser/parse json-payload)]
    (reset! last-sequence-number (:sequence-number parsed-payload))
    (cond (= (:op parsed-payload) 10) (keep-alive (get (:data parsed-payload) "heartbeat_interval"))
          (= (:op parsed-payload) 0) (handle-event parsed-payload))))

(defn connect []
  (socket/set-handler-function handle-incoming-request)
  (let [new-connection (socket/create-connection (create-gateway-url))]
    (reset! connection new-connection)
    (socket/identify-with-discord new-connection discord/token)))
