(ns aoc.2015.day13-test
  (:require [aoc.2015.day13 :as day13]
            [clojure.test :refer [deftest is testing]]))

(deftest part01
  (testing "inputs"
    (is (= 330 (day13/part01 day13/example)))
    (is (= 664 (day13/part01 day13/input)))))

(deftest part02
  (testing "inputs"
    (is (= 640 (day13/part01 (conj day13/input [["you" "you"] 0]))))))
