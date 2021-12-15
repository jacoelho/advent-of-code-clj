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
  (reduce (fn [acc pos]
            (if-let [v (get grid pos)]
              (conj acc [v pos])
              acc))
          []
          (neighbours pos)))

(defrecord Vertex [distance coordinate]
  Comparable
  (compareTo [this other] (.compareTo (:distance this) (:distance other))))

(defn dijkstra
  ([start neighbours goal?]
   (let [queue (PriorityQueue. [(->Vertex 0 start)])]
     (loop [distances {}]
       (let [visit (.poll queue)]
         (if (nil? visit)
           distances
           (if (contains? distances (:coordinate visit))
             (recur distances)
             (let [new-distances (assoc distances (:coordinate visit) (:distance visit))]
               (doseq [[distance coordinate] (neighbours (:coordinate visit))]
                 (when-not (contains? distances coordinate)
                   (.add queue (->Vertex (+ (:distance visit) distance) coordinate))))
               (if (goal? (:coordinate visit))
                 new-distances
                 (recur new-distances))))))))))

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

