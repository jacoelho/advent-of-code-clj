(ns aoc.2015.day19-test
  (:require [aoc.2015.day19 :as day19]
            [clojure.test :refer [deftest is testing]]))

(deftest part01
  (testing "inputs"
    (is (= 576 (day19/part01 (second day19/input) (first day19/input))))))

(deftest part02
  (testing "inputs"
    (is (= 207 (day19/part02 (second day19/input) (first day19/input))))))
