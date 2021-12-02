(ns aoc.collections)

(defn first-duplicate
  [coll]
  (loop [seen #{}
         coll coll]
    (when-let [[x & xs] (seq coll)]
      (if-let [j (seen x)]
        j
        (recur (conj seen x) xs)))))
