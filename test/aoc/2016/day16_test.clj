(ns aoc.2016.day16-test
  (:require [aoc.2016.day16 :as day16]
            [clojure.test :refer [deftest is testing]]))

(deftest part01
  (testing "inputs"
    (is (= "00000100100001100" (day16/part01 day16/input)))))

(deftest part02
  (testing "inputs"
    (is (= "00011010100010010" (day16/part02 day16/input)))))
