(ns aoc.2015.day16-test
  (:require [aoc.2015.day16 :as day16]
            [clojure.test :refer [deftest is testing]]))

(deftest part01
  (testing "inputs"
    (is (= 103 (day16/part01 day16/memory day16/input)))))

(deftest part02
  (testing "inputs"
    (is (= 405 (day16/part02 day16/memory day16/input)))))