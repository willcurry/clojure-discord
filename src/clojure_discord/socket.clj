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

(defn heartbeat [connection last-sequence-number]
  (cond
    (true? @ACK) (do (reset! ACK false) (web-socket/send-msg connection (json/write-str {:op 1
                                                                                    :d last-sequence-number})))
    :else (web-socket/close connection)))
