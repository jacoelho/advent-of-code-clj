(ns aoc.2018.day23-test
  (:require [aoc.2018.day23 :as day23]
            [clojure.test :refer [deftest is testing]]))

(deftest part01
  (testing "inputs"
    (is (= 491 (day23/part01 day23/input)))))

(deftest part02
  (testing "inputs"
    (is (= 60474080 (day23/part02 day23/input)))))