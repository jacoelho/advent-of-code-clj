(ns aoc.2016.day01-test
  (:require [aoc.2016.day01 :as day01]
            [clojure.test :refer [deftest is testing]]))

(deftest part01
  (testing "inputs"
    (is (= 12 (day01/part01 day01/example)))
    (is (= 242 (day01/part01 day01/input)))))

(deftest part02
  (testing "inputs"
    (is (= 150 (day01/part02 day01/input)))))
