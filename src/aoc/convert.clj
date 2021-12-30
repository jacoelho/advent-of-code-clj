(ns aoc.convert)

(defn long->binary
  [^long decimal]
  (loop [n   decimal
         res '()]
    (if (zero? n)
      res
      (recur (quot n 2)
             (conj res (mod n 2))))))

(defn long->digits [^long num]
  (loop [n num
         res '()]
    (if (= n 0)
      res
      (recur (quot n 10) (conj res (rem n 10))))))

(defn digits->long [col]
  (reduce #(+ (* 10 %) %2) (long 0) col))