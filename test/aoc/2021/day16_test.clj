(ns aoc.2021.day16-test
  (:require [aoc.2021.day16 :as day16]
            [clojure.test :refer [deftest is testing]]))

(deftest part01
  (testing "inputs"
    (is (= 949 (day16/part01 day16/input)))))

(deftest part02
  (testing "inputs"
    (is (= 1114600142730 (day16/part02 day16/input)))))
