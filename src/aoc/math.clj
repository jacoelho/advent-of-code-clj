(ns aoc.math)

(defn abs [n] (max n (- n)))

(defn long->digits [^long num]
  (loop [n num
         res '()]
    (if (= n 0)
      res
      (recur (quot n 10) (conj res (rem n 10))))))

(defn digits->long [col]
  (reduce #(+ (* 10 %) %2) (long 0) col))

(defn manhattan-distance
  [[x1 y1] [x2 y2]]
  (+ (Math/abs (- x2 x1))
     (Math/abs (- y2 y1))))
