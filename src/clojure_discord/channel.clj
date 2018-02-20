(ns clojure-discord.channel
  (:require [clojure-discord.core :refer :all]
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

(defn get-pinned-messages [channel-id]
  (get-request (create-request (:pinned-messages get-requests) [channel-id])))

(defn get-channel [channel-id]
  (get-request (create-request (:channel get-requests) [channel-id])))

(defn get-channel-invites [channel-id]
  (get-request (create-request (:channel-invites get-requests) [channel-id])))

(defn pin-message [channel-id message-id]
  (put-request (create-request (:pin-message put-requests) [channel-id message-id])))

(defn trigger-typing-indicator [channel-id]
  (post-request (create-request (:trigger-typing post-requests) [channel-id]) ""))

(defn create-message [channel-id text]
  (let [json (json/write-str {:content text
                              :nonce (current-time)
                              :tts false})]
  (post-request (create-request (:create-message post-requests) [channel-id]) json)))
