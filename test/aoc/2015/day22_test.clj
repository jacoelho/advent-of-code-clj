(ns aoc.2015.day22-test
  (:require [aoc.2015.day22 :as day22]
            [clojure.test :refer [deftest is testing]]))

(deftest part01
  (testing "inputs"
    (is (= 492982 (day22/part01 day22/input)))))

(deftest part02
  (testing "inputs"
    (is (= 6989950 (day22/part01 day22/input)))))
