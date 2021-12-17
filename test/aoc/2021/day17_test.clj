(ns aoc.2021.day17-test
  (:require [aoc.2021.day17 :as day17]
            [clojure.test :refer [deftest is testing]]))

(deftest part01
  (testing "inputs"
    (is (= 45 (day17/part01 day17/example)))
    (is (= 10878 (day17/part01 day17/input)))))

(deftest part02
  (testing "inputs"
    (is (= 112 (day17/part02 day17/example)))
    (is (= 4716 (day17/part02 day17/input)))))
