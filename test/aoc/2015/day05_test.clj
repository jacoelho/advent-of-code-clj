(ns aoc.2015.day05-test
  (:require [aoc.2015.day05 :as day05]
            [clojure.test :refer [deftest is testing]]))

(deftest part01
  (testing "inputs"
    (is (= true (day05/is-nice-string? "ugknbfddgicrmopn")))
    (is (= true (day05/is-nice-string? "aaa")))
    (is (= false (day05/is-nice-string? "jchzalrnumimnmhp")))
    (is (= false (day05/is-nice-string? "haegwjzuvuyypxyu")))
    (is (= false (day05/is-nice-string? "dvszwmarrgswjxmb")))
    (is (= 258 (day05/part01 day05/input)))))

(deftest part02
  (testing "inputs"
    (is (= true (day05/is-nicer-string? "qjhvhtzxzqqjkmpb")))
    (is (= true (day05/is-nicer-string? "xxyxx")))
    (is (= false (day05/is-nicer-string? "aaa")))
    (is (= false (day05/is-nicer-string? "uurcxstgmygtbstg")))
    (is (= false (day05/is-nicer-string? "ieodomkazucvgmuy")))
    (is (= 53 (day05/part02 day05/input)))))