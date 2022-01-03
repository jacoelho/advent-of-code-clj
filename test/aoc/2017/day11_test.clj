(ns aoc.2017.day11-test
  (:require [aoc.2017.day11 :as day11]
            [clojure.test :refer [deftest is testing]]))

(deftest part01
  (testing "inputs"
    (is (= 705 (day11/part01 day11/input)))))

(deftest part02
  (testing "inputs"
    (is (= 1469 (day11/part02 day11/input)))))
