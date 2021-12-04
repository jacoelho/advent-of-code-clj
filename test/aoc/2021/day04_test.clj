(ns aoc.2021.day04-test
  (:require [aoc.2021.day04 :as day04]
            [clojure.test :refer [deftest is testing]]))

(deftest part01
  (testing "inputs"
    (is (= 21607 (day04/part01 day04/cards day04/numbers)))))

(deftest part02
  (testing "inputs"
    (is (= 19012 (day04/part02 day04/cards day04/numbers)))))
