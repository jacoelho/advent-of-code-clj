(ns aoc.2021.day14-test
  (:require [aoc.2021.day14 :as day14]
            [clojure.test :refer [deftest is testing]]))

(deftest part01
  (testing "inputs"
    (is (= 1588 (day14/part01 day14/example)))
    (is (= 3230 (day14/part01 day14/input)))))

(deftest part02
  (testing "inputs"
    (is (= 2188189693529 (day14/part02 day14/example)))
    (is (= 3542388214529 (day14/part02 day14/input)))))