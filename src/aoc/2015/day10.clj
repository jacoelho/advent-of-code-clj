(ns aoc.2015.day10
  (:require
   [aoc.math :as math]))

(def input 
  (math/long->digits 1321131112))

(defn look-and-say-one
  [col]
  [(count col) (first col)])

(defn look-and-say
  [col]
  (mapcat look-and-say-one (partition-by identity col)))

(defn part01
  [n input]
  (->> (iterate look-and-say input)
       (drop n)
       (first)
       (count)))
  