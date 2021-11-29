(ns aoc.2015.day05
  (:require 
   [aoc.file :as file]
   [aoc.math :as math]))

(def input (file/read-lines "2015/day05.txt"))

(def vowels #{\a \e \i \o \u})

(defn three-vowels?
  [input]
  (>=
   (->> input
        (filter vowels)
        (count))
   3))

(defn at-least-twice?
  [input]
  (boolean (some (fn [[a b]]
                   (= a b)) (map vector input (rest input)))))

(defn not-contains-banned-substring?
  [input]
  (not (re-find #"ab|cd|pq|xy" input)))

(defn is-nice-string?
  [input]
  (and (three-vowels? input)
       (at-least-twice? input)
       (not-contains-banned-substring? input)))

(defn part01
  [input]
  (count (filter is-nice-string? input)))

(defn contains-two-pairs?
  [input]
  (->> input
       (partition 2 1)                                     
       (map-indexed (fn [idx el] [idx el]))                
       (group-by second)
       (some (fn [[_ pairs]]
                 (let [[a b] (map first pairs)]
                   (cond (> (count pairs) 2) true
                         (= (count pairs) 2) (not= (math/abs (- a b)) 1)
                         :else false))))
       (boolean)))

(defn contains-letter-repeats-with-one-between?
  [input]
  (->> input
       (partition 3 1)
       (some (fn [[a _ c]] (= a c)))
       (boolean)))

(defn is-nicer-string?
  [input]
  (and (contains-two-pairs? input)
       (contains-letter-repeats-with-one-between? input)))

(defn part02
  [input]
  (count (filter is-nicer-string? input)))
