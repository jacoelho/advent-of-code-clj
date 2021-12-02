(ns aoc.2021.day02
  (:require [aoc.parse :as parse]
            [clojure.string :as str]
            [aoc.file :as file]))

(def directions
  {"forward" :forward
   "down"    :down
   "up"      :up})

(defn parse-line
  [line]
  (let [[_ direction units] (re-find #"^(\w+) (\d+)$" line)]
    [(directions direction)
     (parse/->int units)]))

(def example
  (->> "forward 5\ndown 5\nforward 8\nup 3\ndown 8\nforward 2"
       (str/split-lines)
       (map parse-line)))

(def input
  (file/read-lines parse-line "2021/day02.txt"))

(defn navigate
  [instructions]
  (reduce (fn [submarine [direction units]]
            (case direction
              :forward (update submarine :horizontal + units)
              :up (update submarine :depth - units)
              :down (update submarine :depth + units)))
          {:horizontal 0
           :depth      0}
          instructions))

(defn part01
  [input]
  (let [{:keys [horizontal depth]} (navigate input)]
    (* horizontal depth)))

(defn navigate-with-aim
  [instructions]
  (reduce (fn [submarine [direction units]]
            (case direction
              :forward (-> submarine
                           (update :horizontal + units)
                           (update :depth #(+ % (* units (:aim submarine)))))
              :up (update submarine :aim - units)
              :down (update submarine :aim + units)))
          {:horizontal 0
           :depth      0
           :aim        0}
          instructions))

(defn part02
  [input]
  (let [{:keys [horizontal depth]} (navigate-with-aim input)]
    (* horizontal depth)))
