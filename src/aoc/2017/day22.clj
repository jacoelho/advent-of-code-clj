(ns aoc.2017.day22
  (:require [aoc.file :as file]
            [aoc.geometry :as geometry]))

(def input (->> "2017/day22.txt"
                (file/read-lines #(apply vec %&))
                (geometry/map-grid-2d)))

(def example (->> ["..#" "#.." "..."]
                  (geometry/map-grid-2d)))

(def direction-step
  {:north [0 -1]
   :south [0 1]
   :west  [-1 0]
   :east  [1 0]})

(defn turn
  [current direction]
  (case [current direction]
    [:north :left] :west
    [:north :right] :east
    [:north :reverse] :south
    [:south :left] :east
    [:south :right] :west
    [:south :reverse] :north
    [:east :left] :north
    [:east :right] :south
    [:east :reverse] :west
    [:west :left] :south
    [:west :right] :north
    [:west :reverse] :east))

(defn virus-behaviour
  [node direction]
  (if (= node \.)
    [\# (turn direction :left)]
    [\. (turn direction :right)]))

(defn virus-seq
  [behaviour-fn {:keys [position direction grid] :as state}]
  (lazy-seq
    (let [node     (get grid position \.)
          [node' direction'] (behaviour-fn node direction)
          infected (if (= \# node') 1 0)]
      (cons state
            (virus-seq behaviour-fn (-> state
                                        (update :infected (fnil + 0) infected)
                                        (assoc :position (mapv + position (direction-step direction')))
                                        (assoc :direction direction')
                                        (assoc-in [:grid position] node')))))))

(defn part01
  [input]
  (let [[_ max-pos] (geometry/map-grid-corners input)
        position (mapv #(quot % 2) max-pos)]
    (->> (virus-seq virus-behaviour {:grid input :direction :north :position position})
         (drop 10000)
         (first)
         (:infected))))

(defn virus-behaviour-4
  [node direction]
  (case node
    \. [\W (turn direction :left)]
    \W [\# direction]
    \# [\F (turn direction :right)]
    \F [\. (turn direction :reverse)]))

(defn part02
  [input]
  (let [[_ max-pos] (geometry/map-grid-corners input)
        position (mapv #(quot % 2) max-pos)]
    (->> (virus-seq virus-behaviour-4 {:grid input :direction :north :position position})
         (drop 10000000)
         (first)
         (:infected))))
