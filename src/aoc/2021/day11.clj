(ns aoc.2021.day11
  (:require [aoc.parse :as parse]
            [aoc.file :as file]
            [aoc.geometry :as geometry]
            [aoc.collections :as collections]))

(defn parse-line
  [line]
  (mapv parse/->int (re-seq #"\d" line)))

(def input
  (->> "2021/day11.txt"
       (file/read-lines parse-line)
       (geometry/map-grid-2d)))

(defn flashed?
  [v]
  (< 9 v))

(defn update-energy
  "update grid energy. If a point is passed updates
  neighbours points energy if point flash"
  ([grid]
   (collections/map-vals inc grid))

  ([grid [pos v]]
   (if (flashed? v)
     (reduce (fn [grid neighbour]
               (if (zero? (get grid neighbour 0))
                 grid
                 (update grid neighbour inc)))
             (assoc grid pos 0)
             (geometry/neighbours8 pos))
     grid)))

(defn flashed-octopus
  [grid]
  (filter (fn [[_ v]]
            (flashed? v))
          grid))

(defn step
  [grid]
  (loop [grid (update-energy grid)]
    (if-let [revisit (seq (flashed-octopus grid))]
      (recur (reduce update-energy grid revisit))
      grid)))

(defn part01
  [input]
  (->> input
       (iterate step)
       (take 101)
       (mapcat #(filter zero? (vals %)))
       (count)))

(defn part02
  [input]
  (->> input
       (iterate step)
       (map-indexed vector)
       (drop-while #(some (fn [[_ v]] (not= v 0)) (second %)))
       (ffirst)))
