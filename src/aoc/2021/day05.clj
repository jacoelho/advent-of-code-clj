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
  [[a b c d]]
  (or (= a c)
      (= b d)))

(defn points-between
  [[a b c d]]
  (let [dx (math/signum (- c a))
        dy (math/signum (- d b))
        steps (range (inc (max (* dx (- c a))
                               (* dy (- d b)))))]
    (mapv (fn [step]
            [(+ a (* dx step))
             (+ b (* dy step))])
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
