(ns aoc.2015.day10-test
  (:require [aoc.2015.day10 :as day10]
            [clojure.test :refer [deftest is testing]]))

(deftest part01
  (testing "inputs"
    (is (= 492982 (day10/part01 40 day10/input)))))

(deftest part02
  (testing "inputs"
     (is (= 6989950 (day10/part01 50 day10/input)))))