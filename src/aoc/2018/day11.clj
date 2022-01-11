(ns aoc.2018.day11
  (:require [clojure.string :as str]))

(def input 4151)

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

(defn summed-area-table
  [serial]
  (->> (reduce (fn [m [^long x ^long y :as p]]
                 (assoc m p (- (+ (power-level p serial)
                                  (get m [x (dec y)] 0)
                                  (get m [(dec x) y] 0))
                               (get m [(dec x) (dec y)] 0)))) {}
               (for [y (range 1 301)
                     x (range 1 301)]
                 [x y]))))

(defn square-power
  [grid [^long x ^long y] ^long size]
  (let [[x-min y-min x-max y-max] [(dec x) (dec y) (+ x (dec size)) (+ y (dec size))]]
    (-> (get grid [x-min y-min] 0)
        (+ (get grid [x-max y-max] 0))
        (- (get grid [x-max y-min] 0))
        (- (get grid [x-min y-max] 0)))))

(defn max-power-square-n
  [^long n grid]
  (->> (for [x (range 1 (- 301 n))
             y (range 1 (- 301 n))]
         [[x y] (square-power grid [x y] n)])
       (apply max-key second)))

(defn part01
  [input]
  (->> (summed-area-table input)
       (max-power-square-n 3)
       (first)
       (str/join ",")))

(defn part02
  [input]
  (let [grid (summed-area-table input)]
    (->> (range 1 300)
         (pmap (fn [size] [(max-power-square-n size grid) size]))
         (apply max-key (comp second first))
         (#(let [[[[x y] _] size] %]
             (str/join "," [x y size]))))))

