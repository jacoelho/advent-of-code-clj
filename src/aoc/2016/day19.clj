(ns aoc.2016.day19)

(def input 3004953)

;; analytical solutions
;; found in https://www.reddit.com/r/adventofcode/comments/5j4lp1/2016_day_19_solutions/

(defn closest-exponent
  "closest base^v to n"
  [base n]
  (->> (quot (Math/log n) (Math/log base))
       (Math/floor)
       (int)))

(defn int-pow
  [base n]
  (int (Math/pow base n)))

(defn part01
  [input]
  (let [pow (closest-exponent 2 input)
        b   (int-pow 2 pow)]
    (+ (quot input b)
       (* 2 (rem input b)))))

(defn part02
  [input]
  (let [pow (closest-exponent 3 input)
        b   (int-pow 3 pow)]
    (cond (= input b) input
          (<= (- input b) b) (- input b)
          :else (- (* 2 input) (* 3 b)))))
