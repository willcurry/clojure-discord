(ns clojure-discord.core-spec
  (:require [speclj.core :refer :all]
            [clojure-discord.core :refer :all]))

(def ^:private ^:const base-url "https://discordapp.com/api/v6/")

(describe "core"
          (it "create-request replaces question marks with args"
              (should= (str base-url "fake/id/url/id2") (create-request "fake/?/url/?" ["id" "id2"]))))
