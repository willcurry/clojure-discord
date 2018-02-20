(ns example-bot.core
  (:require [clojure-discord.core :as discord]
            [clojure-discord.channel :as channel]))

(defn -main
  [& args]
  (discord/connect))
