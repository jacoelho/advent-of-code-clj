(ns aoc.2018.day22-test
  (:require [aoc.2018.day22 :as day22]
            [clojure.test :refer [deftest is testing]]))

(deftest part01
  (testing "inputs"
    (is (= 5622 (day22/part01 day22/input)))))

(deftest part02
  (testing "inputs"
    (is (= 1089 (day22/part02 day22/input)))))