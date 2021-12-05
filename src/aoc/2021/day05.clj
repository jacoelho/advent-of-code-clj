(ns aoc.2021.day05
  (:require [aoc.parse :as parse]
            [aoc.file :as file]))

(defn parse-line
  [line]
  (let [[_ & rest] (re-find #"(\d+),(\d+) -> (\d+),(\d+)" line)]
    (mapv parse/->int rest)))

(def input
  (file/read-lines parse-line "2021/day05.txt"))

(defn parallel-to-axis?
  [[a b c d]]
  (or (= a c)
      (= b d)))

(defn points-between
  [[a b c d :as points]]
  (let [dx (if (< c a) -1 1)
        dy (if (< d b) -1 1)
        range-x (range a (+ c dx) dx)
        range-y (range b (+ d dy) dy)]
    (if (parallel-to-axis? points)
      (for [x range-x y range-y] [x y])
      (map vector range-x range-y))))

(defn raster-between
  [grid points]
  (reduce #(update %1 %2 (fnil inc 0))
          grid
          (points-between points)))

(defn draw-lines
  [input]
  (reduce raster-between {} input))

(defn count-overlaps
  [grid]
  (reduce (fn [acc [_ v]]
            (if (< 1 v)
              (inc acc)
              acc))
          0
          grid))

(defn part01
  [input]
  (->> input
       (filter parallel-to-axis?)
       (draw-lines)
       (count-overlaps)))

(defn part02
  [input]
  (->> input
       (draw-lines)
       (count-overlaps)))
