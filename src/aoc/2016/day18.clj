(ns aoc.2016.day18
  (:require [aoc.file :as file]))

(def input (apply vec (file/read-lines "2016/day18.txt")))

(def trap-combinations #{[\^ \^ \.] [\. \^ \^] [\^ \. \.] [\. \. \^]})

(defn next-row
  [row]
  (->> (conj (into [\.] row) \.)
       (partition 3 1)
       (mapv #(if (trap-combinations %) \^ \.))))

(defn count-safe-tiles
  [input n-rows]
  (->> input
       (iterate next-row)
       (take n-rows)
       (map #(count (filter #{\.} %)))
       (reduce +)))

(defn part01
  [input]
  (count-safe-tiles input 40))

(defn part02
  [input]
  (count-safe-tiles input 400000))
