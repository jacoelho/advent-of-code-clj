(ns aoc.2018.day10-test
  (:require [aoc.2018.day10 :as day10]
            [clojure.test :refer [deftest is testing]]))

(deftest part02
  (testing "inputs"
    (is (= 10036 (day10/part02 day10/input)))))
