(ns aoc.2015.day17
  (:require
   [aoc.file :as file]
   [clojure.test :refer [testing is deftest]]
   [clojure.math.combinatorics :refer [subsets]]))

(def input
  (file/read-lines file/->int "2015/day17.txt"))

(defn containers-matches
  [input]
  (->> input
       (map-indexed vector)
       (subsets)
       (filter #(= 150 (reduce + (map second %))))))

(defn part01
  [input]
  (->> input
       (containers-matches)
       (count)))

(defn part02
  [input]
  (->> input
       (containers-matches)
       (map count)
       (apply min)))

(deftest day17
  (testing "Part 01"
    (is (= 4372 (part01 input))))
  (testing "Part 02"
    (is (= 4 (part02 input)))))
