(ns aoc.2016.day19-test
  (:require [aoc.2016.day19 :as day19]
            [clojure.test :refer [deftest is testing]]))

(deftest part01
  (testing "inputs"
    (is (= 1815603 (day19/part01 day19/input)))))

(deftest part02
  (testing "inputs"
    (is (= 1410630 (day19/part02 day19/input)))))
