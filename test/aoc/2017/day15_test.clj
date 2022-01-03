(ns aoc.2017.day15-test
  (:require [aoc.2017.day15 :as day15]
            [clojure.test :refer [deftest is testing]]))

(deftest part01
  (testing "inputs"
    (is (= 594 (day15/part01 day15/input)))))

(deftest part02
  (testing "inputs"
    (is (= 328 (day15/part02 day15/input)))))
