(ns aoc.2017.day16-test
  (:require [aoc.2017.day16 :as day16]
            [clojure.test :refer [deftest is testing]]))

(deftest part01
  (testing "inputs"
    (is (= "bijankplfgmeodhc" (day16/part01 day16/input)))))

(deftest part02
  (testing "inputs"
    (is (= "bpjahknliomefdgc" (day16/part02 day16/input)))))
