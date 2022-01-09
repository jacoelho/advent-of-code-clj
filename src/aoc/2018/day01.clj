(ns aoc.2018.day01
  (:require [aoc.file :as file]
            [aoc.parse :as parse]
            [aoc.collections :as collections]))

(def input (file/read-lines parse/string->int "2018/day01.txt"))

(defn part01
  [input]
  (reduce + input))

(defn part02
  [input]
  (->> (cycle input)
       (reductions +)
       (collections/first-duplicate)
       (second)))