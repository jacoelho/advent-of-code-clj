(ns aoc.2019.day07-test
  (:require [aoc.2019.day07 :as day07]
            [clojure.test :refer [deftest is testing]]))

(deftest part01
  (testing "inputs"
    (is (= 437860 (day07/part01 day07/input)))))

;(deftest part02
;  (testing "inputs"
;    (is (= 307 (day07/part02 day07/input)))))
