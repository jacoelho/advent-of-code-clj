(ns aoc.2018.day25-test
  (:require [aoc.2018.day25 :as day25]
            [clojure.test :refer [deftest is testing]]))

(deftest part01
  (testing "inputs"
    (is (= 383 (day25/part01 day25/input)))))
