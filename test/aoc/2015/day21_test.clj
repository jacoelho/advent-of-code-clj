(ns aoc.2015.day21-test
  (:require [aoc.2015.day21 :as day21]
            [clojure.test :refer [deftest is testing]]))

(deftest part01
  (testing "inputs"
    (is (= 111 (day21/part01 day21/player day21/boss)))))

(deftest part02
  (testing "inputs"
    (is (= 188 (day21/part02 day21/player day21/boss)))))
