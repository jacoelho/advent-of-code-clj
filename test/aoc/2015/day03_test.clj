(ns aoc.2015.day03-test
  (:require [aoc.2015.day03 :as day03]
            [clojure.test :refer [deftest is testing]]))

(deftest part01
  (testing "inputs"
    (is (= 2 (day03/part01 "^v^v^v^v^v")))
    (is (= 2572 (day03/part01 day03/input)))))

(deftest part02
  (testing "inputs"
    (is (= 11 (day03/part02 "^v^v^v^v^v")))
    (is (= 2631 (day03/part02 day03/input)))))