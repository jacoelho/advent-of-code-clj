(ns aoc.2019.day03
  (:require [aoc.file :as file]
            [aoc.parse :as parse]
            [clojure.set :as set]
            [aoc.geometry :as geometry]))

(defn parse-line
  [line]
  (mapv (fn [[_ direction length]]
          [direction (parse/string->int length)])
        (re-seq #"([UDRL])(\d+)" line)))

(def input (file/read-lines parse-line "2019/day03.txt"))

(def directions {"U" [0 1]
                 "D" [0 -1]
                 "L" [-1 0]
                 "R" [1 0]})

(def central-port [0 0])

(defn expand-path
  [start path]
  (->> path
       (mapcat (fn [[direction steps]]
                 (repeat steps (directions direction))))
       (reductions (partial mapv +) start)))

(defn part01
  [input]
  (let [wires (map (comp set (partial expand-path central-port)) input)]
    (->> (disj (apply set/intersection wires) central-port)
         (map geometry/manhattan-distance)
         (apply min))))

(defn part02
  [input]
  (->> input
       (map #(->> (expand-path central-port %)
                  (map-indexed (fn [steps location]
                                 [location [steps]]))
                  (into {})))
       (apply merge-with into)
       (keep (fn [[_ steps]]
               (when (= 2 (count steps))
                 (let [res (apply + steps)]
                   (when (pos? res)
                     res)))))
       (apply min)))
