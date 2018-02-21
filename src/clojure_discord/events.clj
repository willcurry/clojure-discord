(ns clojure-discord.events
  (:require [clojure-discord.socket :as socket]))

(def ^:private session-id (atom nil))

(defn handle [parsed-payload]
  (cond (= (:event parsed-payload) "READY") (reset! session-id (get (:data parsed-payload) "session_id"))))

(defn get-session-id []
  @session-id)
