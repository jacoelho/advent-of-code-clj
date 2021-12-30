(ns aoc.2016.day13-test
  (:require [aoc.2016.day13 :as day13]
            [clojure.test :refer [deftest is testing]]))

(deftest part01
  (testing "inputs"
    (is (= 82 (day13/part01 day13/input)))))

(deftest part02
  (testing "inputs"
    (is (= 138 (day13/part02 day13/input)))))
