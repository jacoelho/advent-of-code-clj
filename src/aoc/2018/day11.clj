(ns aoc.2018.day11
  (:require [clojure.string :as str]))

(def input 4151)

(set! *unchecked-math* :warn-on-boxed)
(set! *warn-on-reflection* true)

(defn hundreds-digit
  ^long [^long n]
  (mod (quot n 100) 10))

(defn power-level
  [[^long x ^long y] ^long serial]
  (let [rank-id (+ 10 x)]
    (-> (* rank-id y)
        (+ serial)
        (* rank-id)
        (hundreds-digit)
        (- 5))))

(defn fuel-cells-grid
  [serial]
  (into {}
        (for [y (range 1 301)
              x (range 1 301)]
          [[x y] (power-level [x y] serial)])))

(defn square
  [[^long x ^long y] ^long size]
  (for [y (range y (+ y size))
        x (range x (+ x size))]
    [x y]))

(defn power-square-n
  [^long n grid]
  (->> (for [x (range 1 (- 301 n))
             y (range 1 (- 301 n))]
         [[x y] (apply + (map #(grid %) (square [x y] n)))])
       (into {})
       (apply max-key second)))

(defn part01
  [input]
  (->> (fuel-cells-grid input)
       (power-square-n 3)
       (first)
       (str/join ",")))

;; "20,46"
(comment (part01 input))

(defn part02
  [input]
  (let [grid (fuel-cells-grid input)]
    (->> (range 3 301)
         (pmap (fn [size] [size (power-square-n size grid)]))
         (apply max-key (comp second second)))))

(comment (part02 input))
