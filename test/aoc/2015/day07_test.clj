(ns aoc.2015.day07-test
  (:require [aoc.2015.day07 :as day07]
            [clojure.test :refer [deftest is testing]]))

(deftest part01
  (testing "inputs"
    (is (= 3176 (day07/part01 day07/input)))))

(deftest part02
  (testing "inputs"
    (is (= 14710 (day07/part01 day07/input-with-override)))))