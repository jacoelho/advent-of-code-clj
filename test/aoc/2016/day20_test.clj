(ns aoc.2016.day20-test
  (:require [aoc.2016.day20 :as day20]
            [clojure.test :refer [deftest is testing]]))

(deftest part01
  (testing "inputs"
    (is (= 23923783 (day20/part01 day20/input)))))

(deftest part02
  (testing "inputs"
    (is (= 125 (day20/part02 day20/input)))))
