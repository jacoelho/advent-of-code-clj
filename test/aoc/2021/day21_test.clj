(ns aoc.2021.day21-test
  (:require [aoc.2021.day21 :as day21]
            [clojure.test :refer [deftest is testing]]))

(deftest part01
  (testing "inputs"
    (is (= 711480 (day21/part01 day21/input)))))

(deftest part02
  (testing "inputs"
    (is (= 265845890886828 (day21/part02 day21/input)))))
