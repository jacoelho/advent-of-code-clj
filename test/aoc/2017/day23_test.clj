(ns aoc.2017.day23-test
  (:require [aoc.2017.day23 :as day23]
            [clojure.test :refer [deftest is testing]]))

(deftest part01
  (testing "inputs"
    (is (= 4225 (day23/part01 day23/input)))))

(deftest part02
  (testing "inputs"
    (is (= 905 (day23/part02 day23/input)))))
