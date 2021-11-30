(ns aoc.2015.day15-test
  (:require [aoc.2015.day15 :as day15]
            [clojure.test :refer [deftest is testing]]))

(deftest part01
  (testing "inputs"
    (is (= 13882464 (day15/part01 day15/input (day15/mixtures))))))

(deftest part02
  (testing "inputs"
    (is (= 11171160 (day15/part02 day15/input (day15/mixtures))))))
