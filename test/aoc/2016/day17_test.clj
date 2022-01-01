(ns aoc.2016.day17-test
  (:require [aoc.2016.day17 :as day17]
            [clojure.test :refer [deftest is testing]]))

(deftest part01
  (testing "inputs"
    (is (= "RLDUDRDDRR" (day17/part01 day17/input)))))

(deftest part02
  (testing "inputs"
    (is (= 590 (day17/part02 day17/input)))))
