(ns aoc.2016.day06
  (:require [aoc.file :as file]))

(def example
  ["eedadn"
   "drvtee"
   "eandsr"
   "raavrd"
   "atevrs"
   "tsrnev"
   "sdttsa"
   "rasrtv"
   "nssdts"
   "ntnada"
   "svetve"
   "tesnvt"
   "vntsnd"
   "vrdear"
   "dvrsen"
   "enarar"])

(def input
  (file/read-lines "2016/day06.txt"))

(defn repetition-code
  [compare input]
  (->> input
       (apply mapcat vector)
       (partition (count input))
       (map (comp
              ffirst
              (partial sort-by val compare)
              frequencies))
       (apply str)))

(defn part01
  [input]
  (repetition-code > input))

(defn part02
  [input]
  (repetition-code < input))
