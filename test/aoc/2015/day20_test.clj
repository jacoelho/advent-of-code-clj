(ns aoc.2015.day20-test
  (:require [aoc.2015.day20 :as day20]
            [clojure.test :refer [deftest is testing]]))

(deftest part01
  (testing "inputs"
    (is (= 786240 (day20/part01 34000000)))))

(deftest part02
  (testing "inputs"
    (is (= 831600 (day20/part02 34000000)))))
