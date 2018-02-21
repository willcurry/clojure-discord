(ns clojure-discord.gateway
  (:require [clojure.data.json :as json]
            [clojure-discord.requests :as request]
            [clojure-discord.discord :as discord]
            [clojure-discord.parser :as parser]
            [clojure-discord.events :as events]
            [clojure-discord.socket :as socket]))

(def ^:private connection (atom nil))
(def ^:private last-sequence-number (atom nil))
(def ^:private session-id (atom nil))

(defn- get-gateway []
  (request/get (discord/add-base-url "gateway/bot")))

(defn- create-gateway-url []
  (str (get (get-gateway) "url") "?v=6&encoding=json"))

(defn- keep-alive [time-between]
  (.start (Thread. (fn []
             (socket/heartbeat @connection @last-sequence-number)
             (Thread/sleep time-between)
             (recur)))))

(defn- handle-incoming-request [json-payload]
  (let [parsed-payload (parser/parse json-payload)]
    (reset! last-sequence-number (:sequence-number parsed-payload))
    (cond (= (:op parsed-payload) 10) (keep-alive (get (:data parsed-payload) "heartbeat_interval"))
          (= (:op parsed-payload) 0) (events/handle parsed-payload))))

(defn connect []
  (socket/set-handler-function handle-incoming-request)
    (reset! connection (socket/create-connection (create-gateway-url)))
    (socket/identify-with-discord @connection discord/token))