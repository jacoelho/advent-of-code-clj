(ns aoc.2018.day08-test
  (:require [aoc.2018.day08 :as day08]
            [clojure.test :refer [deftest is testing]]))

(deftest part01
  (testing "inputs"
    (is (= 38567 (day08/part01 day08/input)))))

(deftest part02
  (testing "inputs"
    (is (= 24453 (day08/part02 day08/input)))))
