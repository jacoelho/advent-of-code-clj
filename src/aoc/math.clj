(ns aoc.math)

(defn abs [n] (max n (- n)))

(defn signum
  [v]
  (cond (pos? v) 1
        (neg? v) -1
        :else 0))
