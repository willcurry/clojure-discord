(ns example-bot.core
  (:require [clojure-discord.core :as discord]))

(defn -main
  [& args]
  (print
    (discord/get-pinned-messages "415199853845544971"))
  (discord/trigger-typing-indicator "415199853845544971"))
