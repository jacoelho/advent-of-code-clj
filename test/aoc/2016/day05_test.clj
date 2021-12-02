(ns aoc.2016.day05-test
  (:require [aoc.2016.day05 :as day05]
            [clojure.test :refer [deftest is testing]]))

(deftest part01
  (testing "inputs"
    (is (= "f77a0e6e" (day05/part01 day05/input)))))

(deftest part02
  (testing "inputs"
    (is (= "999828ec" (day05/part02 day05/input)))))
