(ns aoc.2016.day15-test
  (:require [aoc.2016.day15 :as day15]
            [clojure.test :refer [deftest is testing]]))

(deftest part01
  (testing "inputs"
    (is (= 376777 (day15/part01 day15/input)))))

(deftest part02
  (testing "inputs"
    (is (= 3903937 (day15/part01 day15/input-part2)))))
