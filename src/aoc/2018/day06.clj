(ns aoc.2018.day06
  (:require [aoc.file :as file]
            [aoc.parse :as parse]
            [aoc.geometry :as geometry]
            [aoc.collections :as collections]))

(defn parse-line
  [line]
  (mapv parse/string->int (re-seq #"\d+" line)))

(def input (->> (file/read-lines parse-line "2018/day06.txt")))
(def example (->> (file/read-lines parse-line "2018/day06-example.txt")))

(defn corners
  [input]
  (letfn [(min-max [f input]
            (apply (juxt min max) (map f input)))]
    [(min-max first input) (min-max second input)]))

(defn escapes?
  [[min-x max-x] [min-y max-y] [x y]]
  (or (= x min-x) (= x max-x) (= y min-y) (= y max-y)))

(defn closest-two-points
  [points point]
  (->> points
       (map (juxt identity (partial geometry/manhattan-distance point)))
       (sort-by second)
       (take 2)))

(defn count-closest
  [input]
  (let [[[x-min x-max :as x] [y-min y-max :as y]] (corners input)]
    (reduce (fn [m point]
              (let [[[a distance] [_ distance']] (closest-two-points input point)]
                (cond
                  (escapes? x y point) (update m :escapes (fnil conj #{}) a)
                  (= distance distance') m
                  :else (update-in m [:points a] (fnil inc 0)))))
            {}
            (for [x (range x-min (inc x-max))
                  y (range y-min (inc y-max))]
              [x y]))))

(defn part01
  [input]
  (let [m (count-closest input)]
    (->> (:points m)
         (collections/remove-keys (:escapes m))
         (vals)
         (apply max))))

(defn sum-distances
  [input]
  (let [[[x-min x-max] [y-min y-max]] (corners input)]
    (map (fn [point]
           (->> input
                (map #(geometry/manhattan-distance point %))
                (reduce +)))
         (for [x (range x-min (inc x-max))
               y (range y-min (inc y-max))]
           [x y]))))

(defn part02
  [input]
  (->> (sum-distances input)
       (filter #(< % 10000))
       (count)))