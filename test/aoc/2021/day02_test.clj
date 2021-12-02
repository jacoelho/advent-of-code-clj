(ns aoc.2021.day02-test
  (:require [aoc.2021.day02 :as day02]
            [clojure.test :refer [deftest is testing]]))

(deftest part01
  (testing "inputs"
    (is (= 150 (day02/part01 day02/example)))
    (is (= 1580000 (day02/part01 day02/input)))))

(deftest part02
  (testing "inputs"
    (is (= 900 (day02/part02 day02/example)))
    (is (= 1251263225 (day02/part02 day02/input)))))
