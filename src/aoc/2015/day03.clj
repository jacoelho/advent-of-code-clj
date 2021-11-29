(ns aoc.2015.day03
  (:require [aoc.file :as file]))

(def input
  (file/read-input "2015/day03.txt"))

(defn offset
  [direction]
  (case direction
    \^ [0 1]
    \v [0 -1]
    \> [1 0]
    \< [-1 0]))

(defn move
  [[x y] direction]
  (let [[dx dy] (offset direction)]
    [(+ x dx)
     (+ y dy)]))

(defn move-remember-visited
  [[visited position] direction]
  (let [updated (move position direction)]
    [(conj visited updated) updated]))

(defn visited-houses
  [input]
  (->> input
       (reduce move-remember-visited [#{[0 0]} [0 0]])
       (first)))

(defn part01
  [input]
  (count (visited-houses input)))

(defn part02
  [input]
  (let [santa (visited-houses (keep-indexed #(when (odd? %1) %2) input))
        robot (visited-houses (keep-indexed #(when (even? %1) %2) input))]
    (count (clojure.set/union santa robot))))
