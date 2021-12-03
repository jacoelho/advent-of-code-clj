(ns aoc.2016.day08-test
  (:require [aoc.2016.day08 :as day08]
            [clojure.test :refer [deftest is testing]]))

(deftest part01
  (testing "inputs"
    (is (= 119 (day08/part01 day08/input)))))
