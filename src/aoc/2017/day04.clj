(ns aoc.2017.day04
  (:require [aoc.file :as file]
            [clojure.math.combinatorics :as combo]))

(def input (file/read-lines #(re-seq #"\w+" %) "2017/day04.txt"))

(defn no-duplicates?
  [input]
  (= (count input)
     (count (set input))))

(defn part01
  [input]
  (->> input
       (filter no-duplicates?)
       (count)))

(defn anagram?
  [[a b]]
  (when (= (count a) (count b))
    (let [a' (sort a)
          b' (sort b)]
      (= a' b'))))

(defn passphrase-valid?
  [input]
  (->> (combo/combinations input 2)
       (every? (complement anagram?))))

(defn part02
  [input]
  (->> input
       (filter passphrase-valid?)
       (count)))
