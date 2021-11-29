(ns aoc.2015.day06-test
  (:require [aoc.2015.day06 :as day06]
            [clojure.test :refer [deftest is testing]]))

(deftest part01
  (testing "inputs"
    (is (= 377891 (day06/part01 day06/input)))))

(deftest part02
  (testing "inputs"
    (is (= 14110788 (day06/part02 day06/input-brightness)))))