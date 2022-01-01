(ns aoc.2016.day21-test
  (:require [aoc.2016.day21 :as day21]
            [clojure.test :refer [deftest is testing]]))

(deftest part01
  (testing "inputs"
    (is (= "gbhafcde" (day21/part01 day21/input day21/alphabet)))))

(deftest part02
  (testing "inputs"
    (is (= "bcfaegdh" (day21/part02 day21/input)))))
