(ns aoc.geometry)

(defn manhattan-distance
  ([a]
   (manhattan-distance (vec (repeat (count a) 0)) a))
  ([a b]
   (reduce + (map (comp #(Math/abs %) -) a b))))
