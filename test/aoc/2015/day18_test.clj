(ns aoc.2015.day18-test
  (:require [aoc.2015.day18 :as day18]
            [clojure.test :refer [deftest is testing]]))

(deftest part01
  (testing "inputs"
    (is (= 814 (day18/part01 day18/input 100)))))

(deftest part02
  (testing "inputs"
    (is (= 924 (day18/part02 day18/input 100)))))
