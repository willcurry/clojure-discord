(ns clojure-discord.discord
  (:require [aero.core :refer (read-config)]))

(def ^:const base-url "https://discordapp.com/api/v6/")
(def ^:const token (:token (read-config "config.edn")))

(defn add-base-url [end-url]
  (str base-url end-url))
