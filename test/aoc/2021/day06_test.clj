(ns aoc.2021.day06-test
  (:require [aoc.2021.day06 :as day06]
            [clojure.test :refer [deftest is testing]]))

(deftest part01
  (testing "inputs"
    (is (= 361169 (day06/part01 day06/input)))))

(deftest part02
  (testing "inputs"
    (is (= 1634946868992 (day06/part02 day06/input)))))
