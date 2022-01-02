(ns aoc.2017.day04-test
  (:require [aoc.2017.day04 :as day04]
            [clojure.test :refer [deftest is testing]]))

(deftest part01
  (testing "inputs"
    (is (= 337 (day04/part01 day04/input)))))

(deftest part02
  (testing "inputs"
    (is (= 231 (day04/part02 day04/input)))))
