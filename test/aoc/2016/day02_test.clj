(ns aoc.2016.day02-test
  (:require [aoc.2016.day02 :as day02]
            [clojure.test :refer [deftest is testing]]))

(deftest part01
  (testing "inputs"
    (is (= 1985 (day02/part01 day02/example)))
    (is (= 74921 (day02/part01 day02/input)))))

(deftest part02
  (testing "inputs"
    (is (= "5DB3" (day02/part02 day02/example)))
    (is (= "A6B35" (day02/part02 day02/input)))))
