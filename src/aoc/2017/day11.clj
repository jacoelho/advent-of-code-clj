(ns aoc.2017.day11
  (:require [aoc.file :as file]
            [aoc.geometry :as geometry]))

;; https://www.redblobgames.com/grids/hexagons/#neighbors-offset
;; odd-q grid type
(def neighbours
  {"n"  [0 -1]
   "ne" [1 0]
   "se" [1 1]
   "s"  [0 1]
   "sw" [-1 0]
   "nw" [-1 -1]})

(def input (->> "2017/day11.txt"
                (file/read-lines #(re-seq #"\w+" %))
                (first)))

(defn move
  [pos steps]
  (lazy-seq
    (if (seq steps)
      (cons pos
            (move (mapv + pos (neighbours (first steps)))
                  (rest steps)))
      [pos])))

(defn part01
  [input]
  (->> input
       (move [0 0])
       (last)
       (geometry/chebyshev-distance)))

(defn part02
  [input]
  (->> input
       (move [0 0])
       (map (partial geometry/chebyshev-distance))
       (apply max)))
