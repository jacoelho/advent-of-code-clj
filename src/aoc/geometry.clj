(ns aoc.geometry
  (:require [aoc.math :as math]))

(defn manhattan-distance
  ([a]
   (manhattan-distance (vec (repeat (count a) 0)) a))
  ([a b]
   (reduce + (map (comp #(Math/abs %) -) a b))))

(defn points-between
  ([[x0 x1 :as points]]
   (when (seq points)
     (let [dx    (math/signum (- x1 x0))
           steps (range (inc (* dx (- x1 x0))))]
       (mapv (fn [step]
               (+ x0 (* dx step)))
             steps))))
  ([[x0 y0] [x1 y1]]
   (let [dx    (math/signum (- x1 x0))
         dy    (math/signum (- y1 y0))
         steps (range (inc (max (* dx (- x1 x0))
                                (* dy (- y1 y0)))))]
     (mapv (fn [step]
             [(+ x0 (* dx step))
              (+ y0 (* dy step))])
           steps))))

(defn transpose
  [coll]
  (apply mapv vector coll))

(defn grid
  [rows columns value]
  (vec (repeat columns (vec (repeat rows value)))))

(def neighbour-offsets
  [[-1 -1] [0 -1] [1 -1]
   [-1 0] [1 0]
   [-1 1] [0 1] [1 1]])

(defn neighbours
  [point]
  (mapv #(mapv + point %) neighbour-offsets))

(defn print-grid
  [grid]
  (dorun (for [row grid]
           (println (apply str row)))))

(defn map-grid-2d
  [lines]
  (into {} (for [[y row] (map-indexed vector lines)
                 [x v] (map-indexed vector row)]
             [[x y] v])))


(defn print-map-grid
  [grid]
  (let [keys' (keys grid)
        max-x (inc (apply max (map first keys')))
        max-y (inc (apply max (map second keys')))]
    (doseq [y (range max-y)]
      (doseq [x (range max-x)]
        (print (get grid [x y] \_)))
      (print "\n"))))
