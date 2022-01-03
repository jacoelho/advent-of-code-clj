(ns aoc.2017.day14-test
  (:require [aoc.2017.day14 :as day14]
            [clojure.test :refer [deftest is testing]]))

(deftest part01
  (testing "inputs"
    (is (= 8190 (day14/part01 day14/input)))))

(deftest part02
  (testing "inputs"
    (is (= 1134 (day14/part02 day14/input)))))
