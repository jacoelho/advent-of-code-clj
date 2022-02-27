(ns aoc.2019.day05-test
  (:require [aoc.2019.day05 :as day05]
            [clojure.test :refer [deftest is testing]]))

(deftest part01
  (testing "inputs"
    (is (= 8332629 (day05/part01 day05/input)))))

(deftest part02
  (testing "inputs"
    (is (= 8805067 (day05/part02 day05/input)))))
