(ns aoc.2021.day15-test
  (:require [aoc.2021.day15 :as day15]
            [clojure.test :refer [deftest is testing]]))

(deftest part01
  (testing "inputs"
    (is (= 40 (day15/part01 day15/example)))
    (is (= 386 (day15/part01 day15/input)))))

(deftest part02
  (testing "inputs"
    (is (= 315 (day15/part02 day15/example)))
    (is (= 2806 (day15/part02 day15/input)))))