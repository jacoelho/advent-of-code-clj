(ns aoc.2018.day09-test
  (:require [aoc.2018.day09 :as day09]
            [clojure.test :refer [deftest is testing]]))

(deftest part01
  (testing "inputs"
    (is (= 386018 (day09/part01 day09/input)))))

(deftest part02
  (testing "inputs"
    (is (= 3085518618 (day09/part02 day09/input)))))
