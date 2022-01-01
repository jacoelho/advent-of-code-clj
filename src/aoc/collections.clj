(ns aoc.collections)

(defn map-invert
  [m]
  (reduce (fn [m [k v]] (assoc m v k)) {} m))

(defn map-invert
  [m]
  (persistent!
    (reduce-kv (fn [m k v] (assoc! m v k))
               (transient (empty m)) m)))

(defn map-vals
  [f m]
  (persistent!
    (reduce-kv (fn [m k v] (assoc! m k (f v)))
               (transient (empty m)) m)))

(defn map-keys
  [f m]
  (persistent!
    (reduce-kv (fn [m k v] (assoc! m (f k) v))
               (transient (empty m)) m)))

(defn remove-keys
  [pred m]
  (persistent!
    (reduce-kv (fn [m k v]
                 (if (pred k)
                   m
                   (assoc! m k v)))
               (transient (empty m)) m)))

(defn remove-vals
  [pred m]
  (persistent!
    (reduce-kv (fn [m k v]
                 (if (pred v)
                   m
                   (assoc! m k v)))
               (transient (empty m)) m)))

(defn select-vals
  [pred m]
  (persistent!
    (reduce-kv (fn [m k v]
                 (if (pred v)
                   (assoc! m k v)
                   m))
               (transient (empty m)) m)))

(defn first-duplicate
  [coll]
  (loop [seen #{}
         coll coll]
    (when-let [[x & xs] (seq coll)]
      (if-let [j (seen x)]
        j
        (recur (conj seen x) xs)))))

(defn rotate
  [n coll]
  (if (empty? coll)
    coll
    (let [pos (mod n (count coll))
          [tail head] (split-at pos coll)]
      (concat head tail))))