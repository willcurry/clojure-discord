(ns clojure-discord.requests-spec
  (:require [speclj.core :refer :all]
            [clojure-discord.discord :as discord]
            [clojure-discord.requests :as request]))

(describe "requests"
          (it "prepare replaces question marks with args"
              (should= (discord/add-base-url "fake/id/url/id2") (request/prepare "fake/?/url/?" ["id" "id2"]))))
