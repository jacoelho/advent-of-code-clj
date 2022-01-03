(ns aoc.2021.day01
  (:require [aoc.file :as file]
            [aoc.parse :as parse]))

(def example
  [199
   200
   208
   210
   200
   207
   240
   269
   260
   263])

(def input
  (file/read-lines parse/string->int "2021/day01.txt"))

(defn part01
  [input]
  (->> input
       (partition 2 1)
       (reduce (fn [acc [a b]]
                 (if (< a b)
                   (inc acc)
                   acc))
               0)))

(defn part02
  [input]
  (->> input
       (partition 3 1)
       (map (partial apply +))
       (part01)))
