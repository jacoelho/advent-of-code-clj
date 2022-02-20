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
  (loop [n   (Math/abs num)
         res '()]
    (if (< n 10)
      (conj res n)
      (recur (quot n 10) (conj res (rem n 10))))))

(defn int->digits
  [num]
  (loop [n   (int num)
         res '()]
    (if (< n 10)
      (conj res n)
      (recur (unchecked-divide-int n 10)
             (conj res (rem n 10))))))

(defn digits->long [col]
  (reduce #(+ (* 10 %) %2) (long 0) col))
