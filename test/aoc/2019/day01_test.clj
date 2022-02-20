(ns aoc.2019.day01-test
  (:require [aoc.2019.day01 :as day01]
            [clojure.test :refer [deftest is testing]]))

(deftest part01
  (testing "inputs"
    (is (= 3399394 (day01/part01 day01/input)))))

(deftest part02
  (testing "inputs"
    (is (= 5096223 (day01/part02 day01/input)))))
