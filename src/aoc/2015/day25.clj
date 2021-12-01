(ns aoc.2015.day25)

(def input
  {:row    2981
   :column 3075})

(defn next-code
  ^long [^long code]
  (rem (* code 252533) 33554393))

(defn triangle-number
  [[row column]]
  (+ 1 (- (+ (quot (* (+ row (dec column)) (+ row column)) 2)) row)))

(defn part01
  [{:keys [row column]}]
  (nth (iterate next-code 20151125) (dec (triangle-number [row column]))))
