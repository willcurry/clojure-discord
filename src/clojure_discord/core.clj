(ns clojure-discord.core
  (:require [clj-http.client :as client]
            [aero.core :refer (read-config)]
            [clojure.data.json :as json]))

(def ^:private ^:const base-url "https://discordapp.com/api/v6/")
(def ^:private ^:const token (:token (read-config "config.edn")))

(def ^:private get-requests
  {:pinned-messages "channels/?/pins"
   :channel "channels/?"})

(defn- add-base-url [end-url]
  (str base-url end-url))

(defn- create-request [request args]
  (cond
    (not (clojure.string/includes? request "?")) (add-base-url request)
    :else
      (recur (clojure.string/replace request "?" (first args)) (rest args))))

(defn get-request [url]
  (json/read-str
    (:body (client/get url {:headers {"Authorization" (str "Bot " token)}}))))

(defn get-pinned-messages [channel-id]
  (get-request (create-request (:pinned-messages get-requests) [channel-id])))

(defn get-channel [channel-id]
  (get-request (create-request (:channel get-requests) [channel-id])))
