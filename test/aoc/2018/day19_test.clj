(ns aoc.2018.day19-test
  (:require [aoc.2018.day19 :as day19]
            [clojure.test :refer [deftest is testing]]))

(deftest part01
  (testing "inputs"
    (is (= 1056 (day19/part01 day19/input)))))

(deftest part02
  (testing "inputs"
    (is (= 10915260 (day19/part02 day19/input)))))
