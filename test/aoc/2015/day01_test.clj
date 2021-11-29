(ns aoc.2015.day01-test
  (:require [aoc.2015.day01 :as day01]
            [clojure.test :refer [deftest is testing]]))

(deftest part01
  (testing "inputs"
    (is (= 0 (day01/part01 "()()")))
    (is (= 3 (day01/part01 "))(((((")))
    (is (= 280 (day01/part01 day01/input)))))

(deftest part02
  (testing "inputs"
    (is (= 5 (day01/part02 "()())")))
    (is (= 1 (day01/part02 ")")))
    (is (= 1797 (day01/part02 day01/input)))))
