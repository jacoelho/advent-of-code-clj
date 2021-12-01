(ns aoc.2015.day24
  (:require [aoc.file :as file]
            [aoc.parse :as parse]
            [clojure.math.combinatorics :as combo]))

(def example
  [1 2 3 4 5 7 8 9 10 11])

(def input
  (file/read-lines parse/->int "2015/day24.txt"))

(defn entanglement
  [coll]
  (apply * coll))

(defn possible-arrangements
  [packages-weights goal-weight number-packages]
  (->> number-packages
       (combo/combinations packages-weights)
       (filter #(= (apply + %) goal-weight))))

(defn split-packages
  [pockets input]
  (let [goal-weight (/ (apply + input) pockets)]
    (some (fn [number-packages]
            (let [arrangements (possible-arrangements input goal-weight number-packages)]
              (if (not-empty arrangements)
                (apply min (map entanglement arrangements)))))
          (range))))

(defn part01
  [input]
  (split-packages 3 input))

(defn part02
  [input]
  (split-packages 4 input))
