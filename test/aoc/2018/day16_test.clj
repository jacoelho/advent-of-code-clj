(ns aoc.2018.day16-test
  (:require [aoc.2018.day16 :as day16]
            [clojure.test :refer [deftest is testing]]))

(deftest part01
  (testing "inputs"
    (is (= 624 (day16/part01 day16/input-section1)))))

(deftest part02
  (testing "inputs"
    (is (= 584 (day16/part02 day16/input-section1 day16/input-section2)))))
