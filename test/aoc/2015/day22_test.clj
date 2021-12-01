(ns aoc.2015.day22-test
  (:require [aoc.2015.day22 :as day22]
            [clojure.test :refer [deftest is testing]]))

(deftest part01
  (testing "inputs"
    (is (= 1269 (day22/part01 day22/initial-state)))))

(deftest part02
  (testing "inputs"
    (is (= 1309 (day22/part02 day22/initial-state)))))
