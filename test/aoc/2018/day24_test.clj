(ns aoc.2018.day24-test
  (:require [aoc.2018.day24 :as day24]
            [clojure.test :refer [deftest is testing]]))

(deftest part01
  (testing "inputs"
    (is (= 22859 (day24/part01 day24/input)))))

(deftest part02
  (testing "inputs"
    (is (= 2834 (day24/part02 day24/input)))))