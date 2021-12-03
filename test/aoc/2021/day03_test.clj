(ns aoc.2021.day03-test
  (:require [aoc.2021.day03 :as day03]
            [clojure.test :refer [deftest is testing]]))

(deftest part01
  (testing "inputs"
    (is (= 198 (day03/part01 day03/example)))
    (is (= 3959450 (day03/part01 day03/input)))))

(deftest part02
  (testing "inputs"
    (is (= 230 (day03/part02 day03/example)))
    (is (= 7440311 (day03/part02 day03/input)))))
