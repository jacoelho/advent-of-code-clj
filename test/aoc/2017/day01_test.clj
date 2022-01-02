(ns aoc.2017.day01-test
  (:require [aoc.2017.day01 :as day01]
            [clojure.test :refer [deftest is testing]]))

(deftest part01
  (testing "inputs"
    (is (= 1144 (day01/part01 day01/input)))))

(deftest part02
  (testing "inputs"
    (is (= 1194 (day01/part02 day01/input)))))
