(ns aoc.2021.day01-test
  (:require [aoc.2021.day01 :as day01]
            [clojure.test :refer [deftest is testing]]))

(deftest part01
  (testing "inputs"
    (is (= 7 (day01/part01 day01/example)))
    (is (= 1527 (day01/part01 day01/input)))))

(deftest part01
  (testing "inputs"
    (is (= 5 (day01/part02 day01/example)))
    (is (= 1575 (day01/part02 day01/input)))))