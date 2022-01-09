(ns aoc.2018.day03
  (:require [aoc.parse :as parse]
            [aoc.file :as file]))

(defn parse-line
  [line]
  (let [[id x y w h] (mapv parse/string->int (re-seq #"\d+" line))]
    [id [x y] [w h]]))

(defn claim-area
  [[_ [x y] [w h]]]
  (into #{} (for [y (range y (+ y h))
                  x (range x (+ x w))]
              [x y])))

(def input (->> (file/read-lines parse-line "2018/day03.txt")
                (map (fn [[id :as claim]]
                       [id (claim-area claim)]))
                (into {})))

(defn stitch
  [coll]
  (reduce (fn [m [_ v]]
            (reduce #(update %1 %2 (fnil inc 0)) m v)) {} coll))

(defn part01
  [input]
  (->> input
       (stitch)
       (filter (fn [[_ v]] (<= 2 v)))
       (count)))

(defn part02
  [input]
  (let [s (stitch input)]
    (->> input
         (some (fn [[k v]]
                 (when (every? #(= 1 (s %)) v)
                   k))))))
