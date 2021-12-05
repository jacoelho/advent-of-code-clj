(ns aoc.2021.day05
  (:require [aoc.parse :as parse]
            [aoc.file :as file]
            [aoc.math :as math]))

(defn parse-line
  [line]
  (let [[_ & rest] (re-find #"(\d+),(\d+) -> (\d+),(\d+)" line)]
    (mapv parse/->int rest)))

(def input
  (file/read-lines parse-line "2021/day05.txt"))

(defn parallel-to-axis?
  [[x0 y0 x1 y1]]
  (or (= x0 x1)
      (= y0 y1)))

(defn points-between
  [[x0 y0 x1 y1]]
  (let [dx (math/signum (- x1 x0))
        dy (math/signum (- y1 y0))
        steps (range (inc (max (* dx (- x1 x0))
                               (* dy (- y1 y0)))))]
    (mapv (fn [step]
            [(+ x0 (* dx step))
             (+ y0 (* dy step))])
          steps)))

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
