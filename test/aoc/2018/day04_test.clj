(ns aoc.2018.day04-test
  (:require [aoc.2018.day04 :as day04]
            [clojure.test :refer [deftest is testing]]))

(deftest part01
  (testing "inputs"
    (is (= 48680 (day04/part01 day04/input)))))

(deftest part02
  (testing "inputs"
    (is (= 94826 (day04/part02 day04/input)))))
