(ns aoc.2016.day03
  (:require [aoc.parse :as parse]
            [aoc.file :as file]))

(defn parse-line
  [line]
  (let [[_ a b c] (re-find #"(\d+)\s+(\d+)\s+(\d+)" line)]
    (mapv parse/string->int [a b c])))

(def input
  (file/read-lines parse-line "2016/day03.txt"))

(defn valid-triangle?
  [[a b c]]
  (and (< c (+ a b))
       (< b (+ a c))
       (< a (+ c b))))

(defn count-valid-triangles
  [input]
  (->> input
       (reduce (fn [acc el]
                 (if (valid-triangle? el)
                   (inc acc)
                   acc))
               0)))

(defn part01
  [input]
  (count-valid-triangles input))

(defn part02
  [input]
  (->> input
       (apply mapcat vector)
       (partition 3)
       (count-valid-triangles)))
