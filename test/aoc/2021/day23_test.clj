(ns aoc.2021.day23-test
  (:require [aoc.2021.day23 :as day23]
            [clojure.test :refer [deftest is testing]]))

(deftest part01
  (testing "inputs"
    (is (= 16157 (day23/part01 day23/input)))))

(deftest part02
  (testing "inputs"
    (is (= 43481 (day23/part01 day23/input-part2)))))
