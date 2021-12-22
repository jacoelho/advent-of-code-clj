(ns aoc.2021.day22-test
  (:require [aoc.2021.day22 :as day22]
            [clojure.test :refer [deftest is testing]]))

(deftest part01
  (testing "inputs"
    (is (= 652209 (day22/part01 day22/input)))))

(deftest part02
  (testing "inputs"
    (is (= 1217808640648260 (day22/part02 day22/input)))))
