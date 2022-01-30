(ns aoc.2018.day17-test
  (:require [aoc.2018.day17 :as day17]
            [clojure.test :refer [deftest is testing]]))

(deftest part01
  (testing "inputs"
    (is (= 39367 (day17/part01 day17/input)))))

(deftest part02
  (testing "inputs"
    (is (= 33061 (day17/part02 day17/input)))))
