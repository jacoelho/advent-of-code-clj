(ns aoc.2015.day02-test
  (:require [aoc.2015.day02 :as day02]
            [clojure.test :refer [deftest is testing]]))

(deftest part01
  (testing "inputs"
    (is (= 58 (day02/wrap [2 3 4])))
    (is (= 43 (day02/wrap [1 1 10])))
    (is (= 1588178 (day02/part01 day02/input)))))

(deftest part02
  (testing "inputs"
    (is (= 34 (day02/ribbon [2 3 4])))
    (is (= 14 (day02/ribbon [1 1 10])))
    (is (= 3783758 (day02/part02 day02/input)))))
