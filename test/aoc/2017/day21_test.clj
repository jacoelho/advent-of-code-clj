(ns aoc.2017.day21-test
  (:require [aoc.2017.day21 :as day21]
            [clojure.test :refer [deftest is testing]]))

(deftest part01
  (testing "inputs"
    (is (= 179 (day21/part01 day21/input)))))

(deftest part02
  (testing "inputs"
    (is (= 2766750 (day21/part02 day21/input)))))
