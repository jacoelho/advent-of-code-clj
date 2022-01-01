(ns aoc.2016.day18-test
  (:require [aoc.2016.day18 :as day18]
            [clojure.test :refer [deftest is testing]]))

(deftest part01
  (testing "inputs"
    (is (= 2013 (day18/part01 day18/input)))))

(deftest part02
  (testing "inputs"
    (is (= 20006289 (day18/part02 day18/input)))))
