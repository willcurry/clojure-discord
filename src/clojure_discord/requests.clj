(ns clojure-discord.requests
  (:require [clj-http.client :as client]
            [aero.core :refer (read-config)]
            [clojure-discord.discord :as discord]
            [clojure.data.json :as json]))

(defn prepare [request args]
  (cond
    (not (clojure.string/includes? request "?"))
      (discord/add-base-url request)
    :else
      (recur (clojure.string/replace-first request "?" (first args)) (rest args))))

(defn get [url]
  (json/read-str
    (:body (client/get url {:headers {"Authorization" (str "Bot " discord/token)}}))))

(defn put [url]
  (:body (client/put url {:headers {"Authorization" (str "Bot " discord/token)}})))

(defn post [url json]
  (:body (client/post url {:body json
                           :headers {"Authorization" (str "Bot " discord/token)}
                           :content-type :json
                           :accept :json})))
