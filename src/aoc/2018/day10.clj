(ns aoc.2018.day10
  (:require [aoc.parse :as parse]
            [aoc.file :as file]
            [aoc.geometry :as geometry]))

(defn parse-line
  [line]
  (let [[px py dx dy] (mapv parse/string->int (re-seq #"-?\d+" line))]
    [[px py] [dx dy]]))

(def input (file/read-lines parse-line "2018/day10.txt"))

(defn step-n
  [n [[x y] [dx dy]]]
  [(+ x (* n dx))
   (+ y (* n dy))])

(defn corners
  [coll]
  (let [[x-min x-max] (apply (juxt min max) (map first coll))
        [y-min y-max] (apply (juxt min max) (map second coll))]
    [[x-max y-max] [x-min y-min]]))

(defn area
  [coll]
  (->> (corners coll)
       (apply mapv -)
       (reduce *)))

(defn to-grid
  [coll]
  (into {} (map vector coll (repeat \#))))

(defn part01
  [input]
  (->> (range)
       (map #(map (partial step-n %) input))
       (partition 2 1)
       (drop-while (fn [[a b]] (< (area b) (area a))))
       (ffirst)
       (to-grid)
       (geometry/print-map-grid)))

;; JJXZHKFP
(comment (part01 input))

(defn part02
  [input]
  (->> (range)
       (map #(map (partial step-n %) input))
       (partition 2 1)
       (map-indexed vector)
       (drop-while (fn [[_ [a b]]] (< (area b) (area a))))
       (ffirst)))
