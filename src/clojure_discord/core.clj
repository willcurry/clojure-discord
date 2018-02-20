(ns clojure-discord.core
  (:require [clj-http.client :as client]
            [aero.core :refer (read-config)]
            [clojure.data.json :as json]))

(def ^:private ^:const base-url "https://discordapp.com/api/v6/")
(def ^:private ^:const token (:token (read-config "config.edn")))

(def ^:private get-requests
  {:pinned-messages "channels/?/pins"
   :channel "channels/?"
   :channel-invites "channels/?/invites"})

(def ^:private put-requests
  {:pin-message "channels/?/pins/?"})

(def ^:private post-requests
  {:trigger-typing "channels/?/typing"})

(defn- add-base-url [end-url]
  (str base-url end-url))

(defn- create-request [request args]
  (cond
    (not (clojure.string/includes? request "?")) (add-base-url request)
    :else
      (recur (clojure.string/replace-first request "?" (first args)) (rest args))))

(defn get-request [url]
  (json/read-str
    (:body (client/get url {:headers {"Authorization" (str "Bot " token)}}))))

(defn put-request [url]
  (:body (client/put url {:headers {"Authorization" (str "Bot " token)}})))

(defn post-request [url]
  (:body (client/post url {:headers {"Authorization" (str "Bot " token)}})))

(defn get-pinned-messages [channel-id]
  (get-request (create-request (:pinned-messages get-requests) [channel-id])))

(defn get-channel [channel-id]
  (get-request (create-request (:channel get-requests) [channel-id])))

(defn get-channel-invites [channel-id]
  (get-request (create-request (:channel-invites get-requests) [channel-id])))

(defn pin-message [channel-id message-id]
  (put-request (create-request (:pin-message put-requests) [channel-id message-id])))

(defn trigger-typing-indicator [channel-id]
  (post-request (create-request (:trigger-typing post-requests) [channel-id])))
