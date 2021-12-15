(ns aoc.2021.day15
  (:require [aoc.file :as file]
            [aoc.geometry :as geometry]
            [aoc.parse :as parse]
            [aoc.collections :as collections])
  (:import (java.util PriorityQueue)))

(defn parse-line
  [line]
  (mapv parse/->int (re-seq #"\d" line)))

(def example
  (->> "2021/day15-example.txt"
       (file/read-lines parse-line)
       (geometry/map-grid-2d)))

(def input
  (->> "2021/day15.txt"
       (file/read-lines parse-line)
       (geometry/map-grid-2d)))

(defn bottom-right-corner
  [grid]
  (let [keys' (keys grid)
        max-x (apply max (map first keys'))
        max-y (apply max (map second keys'))]
    [max-x max-y]))

(defn neighbours
  [point]
  (mapv #(mapv + point %) [[0 -1] [0 1] [-1 0] [1 0]]))

(defn neighbours-risk
  [grid pos]
  (persistent! (reduce (fn [acc pos]
                         (if-let [v (get grid pos)]
                           (assoc! acc pos v)
                           acc))
                       (transient {})
                       (neighbours pos))))

(def vertex-comparator
  (reify java.util.Comparator
    (compare
      [_ o1 o2]
      (compare (nth o1 1) (nth o2 1)))
    (equals
      [o1 o2]
      (= o1 o2))))

(defn dijkstra
  ([start neighbours goal?]
   (let [queue (doto
                 (PriorityQueue. vertex-comparator)
                 (.add [start 0]))]
     (loop [distances {}]
       (if-let [[coordinate distance] (.poll queue)]
         (if (contains? distances coordinate)
           (recur distances)
           (let [new-distances (assoc distances coordinate distance)]
             (doseq [vertex (->> coordinate
                                 (neighbours)
                                 (collections/remove-keys distances)
                                 (collections/map-vals #(+ distance %))
                                 (seq))]
               (.add queue vertex))
             (if (goal? coordinate)
               new-distances
               (recur new-distances))))
         distances)))))

(defn part01
  [input]
  (let [goal (bottom-right-corner input)]
    (get (dijkstra [0 0] (partial neighbours-risk input) #(= % goal)) goal)))

(defn expand-grid
  [factor grid]
  (let [[w h] (map inc (bottom-right-corner grid))]
    (->> (for [[[ox oy] ov] grid
               i (range factor)
               j (range factor)
               :let [x (+ ox (* i w))
                     y (+ oy (* j h))
                     v (-> (iterate #(inc (mod % 9)) ov)
                           (nth (+ i j)))]]
           [[x y] v])
         (into {}))))

(defn part02
  [input]
  (let [input' (expand-grid 5 input)
        goal (bottom-right-corner input')]
    (get (dijkstra [0 0] (partial neighbours-risk input') #(= % goal)) goal)))
