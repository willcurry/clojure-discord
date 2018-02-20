(ns clojure-discord.core
  (:require [clj-http.client :as client]
            [aero.core :refer (read-config)]
            [clojure.data.json :as json]
            [clojure-discord.socket :as socket]))

(def ^:private ^:const base-url "https://discordapp.com/api/v6/")
(def ^:private ^:const token (:token (read-config "config.edn")))

(defn current-time []
  (str (System/currentTimeMillis)))

(defn- add-base-url [end-url]
  (str base-url end-url))

(defn create-request [request args]
  (cond
    (not (clojure.string/includes? request "?"))
      (add-base-url request)
    :else
      (recur (clojure.string/replace-first request "?" (first args)) (rest args))))

(defn get-request [url]
  (json/read-str
    (:body (client/get url {:headers {"Authorization" (str "Bot " token)}}))))

(defn put-request [url]
  (:body (client/put url {:headers {"Authorization" (str "Bot " token)}})))

(defn post-request [url json]
  (:body (client/post url {:body json
                           :headers {"Authorization" (str "Bot " token)}
                           :content-type :json
                           :accept :json})))

(defn- get-gateway []
  (get-request (add-base-url "gateway/bot")))

(defn- create-gateway-url []
  (str (get (get-gateway) "url") "?v=6&encoding=json"))

(defn connect []
  (let [connection (socket/create-connection (create-gateway-url))]
    (socket/identify-with-discord connection token)))
