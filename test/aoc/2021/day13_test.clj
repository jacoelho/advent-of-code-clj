(ns aoc.2021.day13-test
  (:require [aoc.2021.day13 :as day13]
            [clojure.test :refer [deftest is testing]]))

(deftest part01
  (testing "inputs"
    (is (= 695 (day13/part01 day13/input)))))

(deftest part02
  (testing "inputs"
    ;; prints GJZGLUPJ
    (is (= nil (day13/part02 day13/input)))))