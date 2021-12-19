(ns aoc.2021.day19-test
  (:require [aoc.2021.day19 :as day19]
            [clojure.test :refer [deftest is testing]]))

(deftest part01
  (testing "inputs"
    (is (= 79 (day19/part01 day19/example)))))

(deftest part02
  (testing "inputs"
    (is (= 3621 (day19/part02 day19/example)))))
