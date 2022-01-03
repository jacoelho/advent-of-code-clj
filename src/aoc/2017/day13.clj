(ns aoc.2017.day13
  (:require [aoc.parse :as parse]
            [aoc.file :as file]))

(defn parse-line
  [line]
  (mapv parse/string->int (re-seq #"\d+" line)))

(def input (file/read-lines parse-line "2017/day13.txt"))

(defn caught?
  [delay [depth width]]
  (zero? (rem (+ delay depth)
              (* 2 (dec width)))))

(defn part01
  [input]
  (->> input
       (filter (partial caught? 0))
       (map (partial apply *))
       (reduce +)))

(defn safe?
  [input delay]
  (not (some (partial caught? delay) input)))

(defn part02
  [input]
  (->> (range)
       (filter (partial safe? input))
       (first)))
