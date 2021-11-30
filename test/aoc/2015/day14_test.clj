(ns aoc.2015.day14-test
  (:require [aoc.2015.day14 :as day14]
            [clojure.test :refer [deftest is testing]]))

(deftest part01
  (testing "inputs"
    (is (= 2660 (day14/part01 day14/input 2503)))))

(deftest part02
  (testing "inputs"
    (is (= 1256 (day14/part02 day14/input 2503)))))
