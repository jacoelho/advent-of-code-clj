(ns aoc.2016.day10-test
  (:require [aoc.2016.day10 :as day10]
            [clojure.test :refer [deftest is testing]]))

(deftest part01
  (testing "inputs"
    (is (= 116 (day10/part01 day10/input)))))

(deftest part02
  (testing "inputs"
    (is (= 23903 (day10/part02 day10/input)))))
