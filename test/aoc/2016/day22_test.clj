(ns aoc.2016.day22-test
  (:require [aoc.2016.day22 :as day22]
            [clojure.test :refer [deftest is testing]]))

(deftest part01
  (testing "inputs"
    (is (= 1043 (day22/part01 day22/input)))))

(deftest part02
  (testing "inputs"
    (is (= 7 (day22/part02 day22/example)))
    (is (= 185 (day22/part02 day22/input)))))
