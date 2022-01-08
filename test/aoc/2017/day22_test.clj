(ns aoc.2017.day22-test
  (:require [aoc.2017.day22 :as day22]
            [clojure.test :refer [deftest is testing]]))

(deftest part01
  (testing "inputs"
    (is (= 5570 (day22/part01 day22/input)))))

(deftest part02
  (testing "inputs"
    (is (= 2512022 (day22/part02 day22/input)))))
