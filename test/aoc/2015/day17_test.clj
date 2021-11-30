(ns aoc.2015.day17-test
  (:require [aoc.2015.day17 :as day17]
            [clojure.test :refer [deftest is testing]]))

(deftest part01
  (testing "inputs"
    (is (= 4372 (day17/part01 day17/input)))))

(deftest part02
  (testing "inputs"
    (is (= 4 (day17/part02 day17/input)))))
