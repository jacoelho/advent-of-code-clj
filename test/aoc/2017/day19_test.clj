(ns aoc.2017.day19-test
  (:require [aoc.2017.day19 :as day19]
            [clojure.test :refer [deftest is testing]]))

(deftest part01
  (testing "inputs"
    (is (= "LOHMDQATP" (day19/part01 day19/input)))))

(deftest part02
  (testing "inputs"
    (is (= 16492 (day19/part02 day19/input)))))
