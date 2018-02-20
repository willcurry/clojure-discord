(ns clojure-discord.channel
  (:require [clojure-discord.requests :as request]
            [clojure.data.json :as json]))

(def ^:private get-requests
  {:pinned-messages "channels/?/pins"
   :channel "channels/?"
   :channel-invites "channels/?/invites"})

(def ^:private put-requests
  {:pin-message "channels/?/pins/?"})

(def ^:private post-requests
  {:trigger-typing "channels/?/typing"
   :create-message "channels/?/messages"})

(defn current-time []
  (str (System/currentTimeMillis)))

(defn get-pinned-messages [channel-id]
  (request/get (request/prepare (:pinned-messages get-requests) [channel-id])))

(defn get-channel [channel-id]
  (request/get (request/prepare (:channel get-requests) [channel-id])))

(defn get-channel-invites [channel-id]
  (request/get (request/prepare (:channel-invites get-requests) [channel-id])))

(defn pin-message [channel-id message-id]
  (request/put (request/prepare (:pin-message put-requests) [channel-id message-id])))

(defn trigger-typing-indicator [channel-id]
  (request/post (request/prepare (:trigger-typing post-requests) [channel-id]) ""))

(defn create-message [channel-id text]
  (let [json (json/write-str {:content text
                              :nonce (current-time)
                              :tts false})]
  (request/post (request/prepare (:create-message post-requests) [channel-id]) json)))
