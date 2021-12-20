(ns aoc.2021.day20
  (:require [aoc.file :as file]
            [aoc.geometry :as geometry]
            [aoc.collections :as collections]))

(def pixel->bit
  {\. 0
   \# 1})

(def input
  (let [[algorithm _ & image] (file/read-lines "2021/day20.txt")]
    {:algorithm (mapv pixel->bit algorithm)
     :image     (->> image
                     (geometry/map-grid-2d)
                     (collections/map-vals pixel->bit)
                     (collections/remove-vals #{0}))}))

(defn focus-point
  [[x y]]
  (for [dy [-1 0 1]
        dx [-1 0 1]]
    [(+ x dx) (+ y dy)]))

(defn corners
  [grid]
  (let [keys' (keys grid)
        [x-min x-max] (apply (juxt min max) (map first keys'))
        [y-min y-max] (apply (juxt min max) (map second keys'))]
    [[x-min y-min] [x-max y-max]]))

(defn padding
  [[[x-min y-min] [x-max y-max]]]
  [[(dec x-min) (dec y-min)]
   [(inc x-max) (inc y-max)]])

(defn count-on-pixels
  [image]
  (->> image
       (vals)
       (filter #{1})
       (count)))

(defn image-area
  [grid point empty-value]
  (->> point
       (focus-point)
       (map #(get grid % empty-value))
       (reduce #(+ (* 2 %) %2) (long 0))))

(defn enhance
  [algorithm image empty-value]
  (let [[[x-min y-min] [x-max y-max]] (padding (corners image))]
    (persistent!
      (reduce (fn [output pixel]
                (let [idx (image-area image pixel empty-value)
                      res (nth algorithm idx)]
                  (assoc! output pixel res)))
              (transient {})
              (for [x (range x-min (inc x-max))
                    y (range y-min (inc y-max))]
                [x y])))))

(defn enhance-n
  [n {:keys [algorithm image]}]
  (let [empty-values [0 1]]
    (loop [image image
           count 0]
      (if (= count n)
        image
        (recur (enhance algorithm image (nth empty-values (rem count 2)))
               (inc count))))))

(defn part01
  [input]
  (->> input
       (enhance-n 2)
       (count-on-pixels)))

(defn part02
  [input]
  (->> input
       (enhance-n 50)
       (count-on-pixels)))