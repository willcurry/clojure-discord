(ns clojure-discord.core
  (:require [clojure.data.json :as json]
            [clojure-discord.requests :as request]
            [clojure-discord.discord :as discord]
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

(defn- handle-event [payload data]
  (let [event (get payload "t")]
    (cond (= event "READY") (reset! session-id (get data "session_id")))))


(defn- create-gateway-url []
  (str (get (get-gateway) "url") "?v=6&encoding=json"))

(defn- handle-incoming-request [json-payload]
  (let [payload (json/read-str json-payload)
        op (get payload "op")
        data (get payload "d")
        sequence-number (get payload "s")]
    (reset! last-sequence-number sequence-number)
    (cond (= op 10) (keep-alive (get data "heartbeat_interval"))
          (= op 0) (handle-event payload data))))

(defn connect []
  (socket/set-handler-function handle-incoming-request)
  (let [new-connection (socket/create-connection (create-gateway-url))]
    (reset! connection new-connection)
    (socket/identify-with-discord new-connection discord/token)))
