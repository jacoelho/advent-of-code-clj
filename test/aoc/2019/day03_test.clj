(ns aoc.2019.day03-test
  (:require [aoc.2019.day03 :as day03]
            [clojure.test :refer [deftest is testing]]))

(deftest part01
  (testing "inputs"
    (is (= 386 (day03/part01 day03/input)))))

(deftest part02
  (testing "inputs"
    (is (= 6484 (day03/part02 day03/input)))))
