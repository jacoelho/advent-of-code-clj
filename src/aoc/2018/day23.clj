(ns aoc.2018.day23
  (:require [aoc.parse :as parse]
            [aoc.file :as file]
            [aoc.geometry :as geometry]
            [aoc.collections :as collections]))

(defn parse-line
  [line]
  (let [[x y z r] (->> (re-seq #"-?\d+" line)
                       (mapv parse/string->int))]
    [[x y z] r]))

(def input (file/read-lines parse-line "2018/day23.txt"))

(defn in-range?
  [[point radius] [other]]
  (<= (geometry/manhattan-distance point other) radius))

(defn part01
  [input]
  (let [strongest-bot (apply max-key second input)]
    (collections/count-by (partial in-range? strongest-bot) input)))

(defn part02
  "count overlapping segments as measured by distance to origin,
   positive values indicates segments starting
   negative values indicates segments ending
   sorted-map ensure we are walking by distance to origin"
  [input]
  (->> input
       (reduce (fn [m [p r]]
                 (let [d (geometry/manhattan-distance p)]
                   (-> m
                       (update (max (- d r) 0) (fnil inc 0))
                       (update (+ d r 1) (fnil dec 0)))))
               (sorted-map))
       (reduce (fn [{max-count :max-count
                     :as       state} [distance value]]
                 (let [state         (update state :current-count + value)
                       current-count (state :current-count)]
                   (if (< max-count current-count)
                     (-> state
                         (assoc :result distance)
                         (assoc :max-count current-count))
                     state)))
               {:current-count 0
                :max-count     0
                :result        0})
       (:result)))