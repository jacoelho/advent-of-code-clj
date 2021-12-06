(ns aoc.2021.day06
  (:require [aoc.file :as file]
            [clojure.string :as str]
            [aoc.parse :as parse]))

(defn parse-line
  [line]
  (map parse/->int (str/split line #",")))

(def input
   (vec (first (file/read-lines parse-line "2021/day06.txt"))))

(defn simulate
  [state]
  (reduce (fn [state' [age qty]]
            (if (zero? age)
              (-> state'
                  (update 0 (fnil - 0) qty)
                  (update 6 (fnil + 0) qty)
                  (update 8 (fnil + 0) qty))
              (-> state'
                  (update age (fnil - 0) qty)
                  (update (dec age) (fnil + 0) qty))))
          state
          state))

(defn lanternfish-after
  [days input]
  (->> input
       (frequencies)
       (iterate simulate)
       (drop days)
       (first)
       (vals)
       (reduce +)))

(defn part01
  [input]
  (lanternfish-after 80 input))

(defn part02
  [input]
  (lanternfish-after 256 input))
