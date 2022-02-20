(ns aoc.2019.day02-test
  (:require [aoc.2019.day02 :as day02]
            [clojure.test :refer [deftest is testing]]))

(deftest part01
  (testing "inputs"
    (is (= 3706713 (day02/part01 day02/input)))))

(deftest part02
  (testing "inputs"
    (is (= 8609 (day02/part02 day02/input)))))
