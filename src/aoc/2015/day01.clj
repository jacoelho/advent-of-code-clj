(ns aoc.2015.day01 
  (:require [aoc.file :as file]))

(def input
  (file/read-input "2015/day01.txt"))

(defn floor
  [direction]
  (case direction
    \( 1
    \) -1))

(defn part01
  [directions]
  (->> directions
       (map floor)
       (reduce +)))

(defn part02
  [directions]
  (->> directions
       (reductions (fn [[idx sum] elem]
                     [(inc idx) (+ sum (floor elem))]) [0 0])
       (drop-while (fn [[_ elm]] (not (neg? elm))))
       (ffirst)))

