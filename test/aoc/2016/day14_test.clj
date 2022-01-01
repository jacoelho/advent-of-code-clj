(ns aoc.2016.day14-test
  (:require [aoc.2016.day14 :as day14]
            [clojure.test :refer [deftest is testing]]))

(deftest part01
  (testing "inputs"
    (is (= 15035 (day14/part01 day14/input)))))

(deftest part02
  (testing "inputs"
    (is (= 19968 (day14/part02 day14/input)))))
