(ns aoc.2015.day17
  (:require
   [aoc.file :as file]
   [aoc.parse :as parse]
   [clojure.math.combinatorics :refer [subsets]]))

(def input
  (file/read-lines parse/string->int "2015/day17.txt"))

(defn containers-matches
  [input]
  (->> input
       (map-indexed vector)
       (subsets)
       (filter #(= 150 (reduce + (map second %))))))

(defn part01
  [input]
  (->> input
       (containers-matches)
       (count)))

(defn part02
  [input]
  (->> input
       (containers-matches)
       (map count)
       (apply min)))
