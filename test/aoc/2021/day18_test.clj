(ns aoc.2021.day18-test
  (:require [aoc.2021.day18 :as day18]
            [clojure.test :refer [deftest is testing]]))

(deftest part01
  (testing "inputs"
    (is (= 4137 (day18/part01 day18/input)))))

(deftest part02
  (testing "inputs"
    (is (= 4573 (day18/part02 day18/input)))))
