(ns aoc.matrix)

(defn print-matrix
  [matrix]
  (dorun (for [row matrix]
           (println (apply str row)))))

(defn transpose
  "Transpose a matrix
  [[1 2] => [[1 3]
   [3 4]     [2 4]]"
  [coll]
  (apply mapv vector coll))

(defn rotate
  "Rotate matrix 90 degrees clockwise"
  [m]
  (apply mapv (comp vec #(reverse %&)) m))

(defn vertical-flip
  "Flips a matrix vertically
  [[1 2] => [[3 4]
   [3 4]     [1 2]]"
  [m]
  (vec (reverse m)))

(defn horizontal-flip
  "Flips a matrix horizontally
  [[1 2] => [[2 1]
   [3 4]     [4 3]]"
  [m]
  (mapv (comp vec reverse) m))