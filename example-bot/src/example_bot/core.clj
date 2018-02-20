(ns example-bot.core
  (:require [clojure-discord.core :as discord]))

(defn -main
  [& args]
  (discord/trigger-typing-indicator "415199853845544971")
  (discord/create-message "415199853845544971" "hello!"))
