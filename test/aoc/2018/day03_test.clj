(ns aoc.2018.day03-test
  (:require [aoc.2018.day03 :as day03]
            [clojure.test :refer [deftest is testing]]))

(deftest part01
  (testing "inputs"
    (is (= 107820 (day03/part01 day03/input)))))

(deftest part02
  (testing "inputs"
    (is (= 661 (day03/part02 day03/input)))))
