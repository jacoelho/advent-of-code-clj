(ns aoc.2016.day07-test
  (:require [aoc.2016.day07 :as day07]
            [clojure.test :refer [deftest is testing]]))

(deftest part01
  (testing "inputs"
    (is (= 115 (day07/part01 day07/input)))))

(deftest part02
  (testing "inputs"
    (is (= 231 (day07/part02 day07/input)))))
