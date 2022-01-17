(ns aoc.2018.day14-test
  (:require [aoc.2018.day14 :as day14]
            [clojure.test :refer [deftest is testing]]))

(deftest part01
  (testing "inputs"
    (is (= "1631191756" (day14/part01 day14/input)))))

(deftest part02
  (testing "inputs"
    (is (= 20219475 (day14/part02 day14/input)))))
