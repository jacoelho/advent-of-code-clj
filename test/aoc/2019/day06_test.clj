(ns aoc.2019.day06-test
  (:require [aoc.2019.day06 :as day06]
            [clojure.test :refer [deftest is testing]]))

(deftest part01
  (testing "inputs"
    (is (= 204521 (day06/part01 day06/input)))))

(deftest part02
  (testing "inputs"
    (is (= 307 (day06/part02 day06/input)))))
