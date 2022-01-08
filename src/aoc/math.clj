(ns aoc.math)

(defn abs [n] (max n (- n)))

(defn signum
  [v]
  (cond (pos? v) 1
        (neg? v) -1
        :else 0))

(defn prime?
  [^long x]
  (or (= x 2)
      (and
        (odd? x)
        (not-any? #(zero? (rem x %))
                  (range 3 (inc (Math/sqrt x)) 2)))))