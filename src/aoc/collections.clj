(ns aoc.collections)

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

(defn first-duplicate [coll]
  (reduce (fn [acc [idx x]]
            (if-let [v (get acc x)]
              (reduced [(conj v idx) x])
              (assoc! acc x [idx])))
          (transient {})
          (map-indexed vector coll)))

(defn rotate
  [n coll]
  (if (empty? coll)
    coll
    (let [pos (mod n (count coll))
          [tail head] (split-at pos coll)]
      (concat head tail))))

(defn index-highest-value
  [coll]
  (->> (map-indexed vector coll)
       (reduce (fn [[_ m :as acc] [_ v :as pair]]
                 (if (< m v) pair acc)))
       (first)))

(defn sum-by
  ([f coll]
   (sum-by f 0 coll))
  ([f val coll]
   (transduce (map f) + val coll)))

(defn count-by
  [f s]
  (reduce (fn [acc x]
            (if (f x)
              (inc acc)
              acc))
          0 s))