(ns aoc.2016.day15
  (:require [aoc.parse :as parse]
            [aoc.file :as file]))

(defn parse-line
  [line]
  (let [[_ & rest] (re-find #"^Disc #(\d+) has (\d+) positions; at time=0, it is at position (\d+)\.$" line)]
    (mapv parse/->int rest)))

(def input (file/read-lines parse-line "2016/day15.txt"))

(defn falls?
  [t [disc positions start-pos]]
  (zero? (rem (+ disc start-pos t) positions)))

(defn part01
  [input]
  (->> (range)
       (some #(when (every? (partial falls? %) input) %))))

(def input-part2 (conj input [(inc (count input)) 11 0]))
