(ns aoc.2016.day09-test
  (:require [aoc.2016.day09 :as day09]
            [clojure.test :refer [deftest is testing]]))

(deftest part01
  (testing "inputs"
    (is (= 150914 (day09/part01 day09/input)))))

(deftest part02
  (testing "inputs"
    (is (= 11052855125 (day09/part02 day09/input)))))
