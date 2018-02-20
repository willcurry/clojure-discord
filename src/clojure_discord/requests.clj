(ns clojure-discord.requests
  (:require [clj-http.client :as client]
            [aero.core :refer (read-config)]
            [clojure.data.json :as json]))

(def ^:private ^:const token (:token (read-config "config.edn")))
(def ^:private ^:const base-url "https://discordapp.com/api/v6/")

(defn- add-base-url [end-url]
  (str base-url end-url))

(defn prepare [request args]
  (cond
    (not (clojure.string/includes? request "?"))
      (add-base-url request)
    :else
      (recur (clojure.string/replace-first request "?" (first args)) (rest args))))

(defn get [url]
  (json/read-str
    (:body (client/get url {:headers {"Authorization" (str "Bot " token)}}))))

(defn put [url]
  (:body (client/put url {:headers {"Authorization" (str "Bot " token)}})))

(defn post [url json]
  (:body (client/post url {:body json
                           :headers {"Authorization" (str "Bot " token)}
                           :content-type :json
                           :accept :json})))
