(ns aoc.2018.day13-test
  (:require [aoc.2018.day13 :as day13]
            [clojure.test :refer [deftest is testing]]))

(deftest part01
  (testing "inputs"
    (is (= "57,104" (day13/part01 day13/input)))))

(deftest part02
  (testing "inputs"
    (is (= "67,74" (day13/part02 day13/input)))))
