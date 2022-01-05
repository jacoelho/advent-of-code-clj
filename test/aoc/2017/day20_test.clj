(ns aoc.2017.day20-test
  (:require [aoc.2017.day20 :as day20]
            [clojure.test :refer [deftest is testing]]))

(deftest part01
  (testing "inputs"
    (is (= 243 (day20/part01 day20/input)))))

(deftest part02
  (testing "inputs"
    (is (= 648 (day20/part02 day20/input)))))
