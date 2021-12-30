(ns aoc.2016.day12-test
  (:require [aoc.2016.day12 :as day12]
            [clojure.test :refer [deftest is testing]]))

(deftest part01
  (testing "inputs"
    (is (= 318077 (day12/part01 day12/input)))))

(deftest part02
  (testing "inputs"
    (is (= 9227731 (day12/part02 day12/input)))))
