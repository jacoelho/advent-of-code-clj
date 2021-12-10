(ns aoc.collections)

(defn map-invert
  [m]
  (reduce (fn [m [k v]] (assoc m v k)) {} m))

(defn first-duplicate
  [coll]
  (loop [seen #{}
         coll coll]
    (when-let [[x & xs] (seq coll)]
      (if-let [j (seen x)]
        j
        (recur (conj seen x) xs)))))
