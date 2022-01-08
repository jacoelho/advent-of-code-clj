(ns aoc.2017.day24-test
  (:require [aoc.2017.day24 :as day24]
            [clojure.test :refer [deftest is testing]]))

(deftest part01
  (testing "inputs"
    (is (= 1695 (day24/part01 day24/input)))))

(deftest part02
  (testing "inputs"
    (is (= 1673 (day24/part02 day24/input)))))
