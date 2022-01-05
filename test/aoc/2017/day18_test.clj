(ns aoc.2017.day18-test
  (:require [aoc.2017.day18 :as day18]
            [clojure.test :refer [deftest is testing]]))

(deftest part01
  (testing "inputs"
    (is (= 3188 (day18/part01 day18/input)))))

(deftest part02
  (testing "inputs"
    (is (= 7112 (day18/part02 day18/input)))))
