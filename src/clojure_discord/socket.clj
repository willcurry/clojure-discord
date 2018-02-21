(ns clojure-discord.socket
  (:require [gniazdo.core :as web-socket]
            [clojure.data.json :as json]))

(def handler
  (atom (fn [x] (print x))))

(def ^:private ACK (atom true))

(defn create-connection [url]
  (web-socket/connect url
                :on-receive @handler))

(defn identify-with-discord [connection token]
  (web-socket/send-msg connection (json/write-str
                                {:op 2, :d {"token" token
                                            "properties" {"$os" "linux"
                                                          "$browser" "clojure-discord"
                                                          "$device" "clojure-discord"}}})))

(defn set-handler-function [function]
  (reset! handler function))

(defn update-ack [bool]
  (reset! ACK bool))

(defn resume [connection session-id last-sequence-number token]
  (web-socket/send-msg connection (json/write-str {"token" token
                                                   "session_id" session-id
                                                   "seq" last-sequence-number})))

(defn heartbeat [connection last-sequence-number]
  (cond
    (true? @ACK) (do (update-ack false) (web-socket/send-msg connection (json/write-str {:op 1
                                                                                    :d last-sequence-number})) "alive")
    :else (do (web-socket/close connection) "died")))
