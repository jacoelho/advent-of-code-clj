(ns aoc.2015.day11-test
  (:require [aoc.2015.day11 :as day11]
            [clojure.test :refer [deftest is testing]]))

(deftest part01
  (testing "inputs"
    (is (= "cqjxxyzz" (day11/part01 "cqjxjnds")))))

(deftest part02
  (testing "inputs"
    (is (= "cqkaabcc" (day11/part01 "cqjxxyzz")))))