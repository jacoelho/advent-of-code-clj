(ns aoc.geometry)

(defn manhattan-distance
  ([a]
   (manhattan-distance (vec (repeat (count a) 0)) a))
  ([a b]
   (reduce + (map (comp #(Math/abs %) -) a b))))

(defn transpose
  [coll]
  (apply mapv vector coll))

(defn grid
  [rows columns value]
  (vec (repeat columns (vec (repeat rows value)))))

(defn print-grid
  [grid]
  (dorun (for [row grid]
           (println (apply str row)))))
