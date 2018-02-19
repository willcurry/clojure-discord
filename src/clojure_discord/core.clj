(ns clojure-discord.core
  (:require [clj-http.client :as client]
            [aero.core :refer (read-config)]
            [clojure.data.json :as json]))

(def ^:private ^:const base-url "https://discordapp.com/api/v6/")
(def ^:private ^:const token (:token (read-config "config.edn")))

(defn -main
  [& args])

(defn- add-base-url [end-url]
  (str base-url end-url))

(defn get-request [url]
  (json/read-str
    (:body (client/get url {:headers {"Authorization" (str "Bot " token)}}))))

(defn get-pinned-messages [channel-id]
  (let [end-url (str "channels/" channel-id "/pins")]
    (get-request (add-base-url end-url))))
