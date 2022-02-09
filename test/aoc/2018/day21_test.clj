(ns aoc.2018.day21-test
  (:require [aoc.2018.day21 :as day21]
            [clojure.test :refer [deftest is testing]]))

(deftest part01
  (testing "inputs"
    (is (= 13443200 (day21/part01 day21/input)))))

(deftest part02
  (testing "inputs"
    (is (= 7717135 (day21/part02 day21/input)))))