(ns aoc.2015.day09-test
  (:require [aoc.2015.day09 :as day09]
            [clojure.test :refer [deftest is testing]]))

(deftest part01
  (testing "inputs"
    (is (= 207 (day09/part01 day09/input)))))

(deftest part02
  (testing "inputs"
     (is (= 804 (day09/part02 day09/input)))))