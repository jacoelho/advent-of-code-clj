(ns aoc.2015.day04-test
  (:require [aoc.2015.day04 :as day04]
            [clojure.test :refer [deftest is testing]]))

(deftest part01
  (testing "inputs"
    (is (= 609043 (day04/part01 "abcdef")))
    (is (= 1048970 (day04/part01 "pqrstuv")))
    (is (= 254575 (day04/part01 "bgvyzdsv")))))

(deftest part02
  (testing "inputs"
    (is (= 1038736 (day04/part02 "bgvyzdsv")))))