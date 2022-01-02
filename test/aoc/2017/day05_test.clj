(ns aoc.2017.day05-test
  (:require [aoc.2017.day05 :as day05]
            [clojure.test :refer [deftest is testing]]))

(deftest part01
  (testing "inputs"
    (is (= 394829 (day05/part01 day05/input)))))

(deftest part02
  (testing "inputs"
    (is (= 31150702 (day05/part02 day05/input)))))
