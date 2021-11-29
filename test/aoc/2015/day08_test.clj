(ns aoc.2015.day08-test
  (:require [aoc.2015.day08 :as day08]
            [clojure.test :refer [deftest is testing]]))

(deftest part01
  (testing "inputs"
    (is (= 1333 (day08/part01 day08/input)))))

(deftest part02
  (testing "inputs"
    (is (= 2046 (day08/part02 day08/input)))))