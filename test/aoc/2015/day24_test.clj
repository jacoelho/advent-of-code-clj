(ns aoc.2015.day24-test
  (:require [aoc.2015.day24 :as day24]
            [clojure.test :refer [deftest is testing]]))

(deftest part01
  (testing "inputs"
    (is (= 492982 (day24/part01 day24/input)))))

(deftest part02
  (testing "inputs"
     (is (= 6989950 (day24/part01 day24/input)))))
