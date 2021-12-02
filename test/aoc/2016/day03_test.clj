(ns aoc.2016.day03-test
  (:require [aoc.2016.day03 :as day03]
            [clojure.test :refer [deftest is testing]]))

(deftest part01
  (testing "inputs"
    (is (= 1050 (day03/part01 day03/input)))))

(deftest part02
  (testing "inputs"
    (is (= 1921 (day03/part02 day03/input)))))
