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


(defn print-map-grid [grid]
  (let [[max-x max-y] (reduce (fn [[mx my] [[y x] _]]
                                [(max mx x) (max my y)])
                              [0 0]
                              grid)]
    (doseq [x (range (inc max-x))]
      (doseq [y (range (inc max-y))]
        (print (get grid [y x] \_)))
      (print "\n"))))