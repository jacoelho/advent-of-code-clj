(ns aoc.2017.day10-test
  (:require [aoc.2017.day10 :as day10]
            [clojure.test :refer [deftest is testing]]))

(deftest part01
  (testing "inputs"
    (is (= 52070 (day10/part01 day10/input)))))

(deftest part02
  (testing "inputs"
    (is (= "7f94112db4e32e19cf6502073c66f9bb" (day10/part02 day10/input-string)))))
