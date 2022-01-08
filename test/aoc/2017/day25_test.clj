(ns aoc.2017.day25-test
  (:require [aoc.2017.day25 :as day25]
            [clojure.test :refer [deftest is testing]]))

(deftest part01
  (testing "inputs"
    (is (= 3578 (day25/part01 day25/input)))))
