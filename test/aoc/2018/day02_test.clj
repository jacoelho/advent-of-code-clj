(ns aoc.2018.day02-test
  (:require [aoc.2018.day02 :as day02]
            [clojure.test :refer [deftest is testing]]))

(deftest part01
  (testing "inputs"
    (is (= 6972 (day02/part01 day02/input)))))

(deftest part02
  (testing "inputs"
    (is (= "aixwcbzrmdvpsjfgllthdyoqe" (day02/part02 day02/input)))))
