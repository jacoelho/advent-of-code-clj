(ns aoc.2015.day23-test
  (:require [aoc.2015.day23 :as day23]
            [clojure.test :refer [deftest is testing]]))

(deftest part01
  (testing "inputs"
    (is (= 492982 (day23/part01 day23/input)))))

(deftest part02
  (testing "inputs"
     (is (= 6989950 (day23/part01 day23/input)))))
