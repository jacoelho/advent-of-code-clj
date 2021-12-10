(ns aoc.2021.day10-test
  (:require [aoc.2021.day10 :as day10]
            [clojure.test :refer [deftest is testing]]))

(deftest part01
  (testing "inputs"
    (is (= 370407 (day10/part01 day10/input)))))

(deftest part02
  (testing "inputs"
    (is (= 3249889609 (day10/part02 day10/input)))))