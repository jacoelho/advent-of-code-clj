(ns aoc.2016.day06-test
  (:require [aoc.2016.day06 :as day06]
            [clojure.test :refer [deftest is testing]]))

(deftest part01
  (testing "inputs"
    (is (= "easter" (day06/part01 day06/example)))
    (is (= "qqqluigu" (day06/part01 day06/input)))))

(deftest part02
  (testing "inputs"
    (is (= "advent" (day06/part02 day06/example)))
    (is (= "lsoypmia" (day06/part02 day06/input)))))
