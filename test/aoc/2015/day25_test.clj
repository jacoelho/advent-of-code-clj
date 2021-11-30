(ns aoc.2015.day25-test
  (:require [aoc.2015.day25 :as day25]
            [clojure.test :refer [deftest is testing]]))

(deftest part01
  (testing "inputs"
    (is (= 492982 (day25/part01 day25/input)))))

(deftest part02
  (testing "inputs"
    (is (= 6989950 (day25/part01 day25/input)))))
