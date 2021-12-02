(ns aoc.2016.day01
  (:require [aoc.file :as file]
            [aoc.parse :as parse]
            [aoc.math :as math]
            [aoc.collections :as collections]
            [clojure.string :as str]))

(def input
  (->> "2016/day01.txt"
       (file/read-lines #(re-seq #"([LR]+)(\d+)" %))
       (first)
       (map (fn [[_ direction blocks]]
              [direction
               (parse/->int blocks)]))))

(def example
  [["R" 5]
   ["L" 5]
   ["R" 5]
   ["R" 3]])

(defn turn
  [current direction]
  (case [current direction]
    [:north "L"] :west
    [:north "R"] :east
    [:south "L"] :east
    [:south "R"] :west
    [:east "L"] :north
    [:east "R"] :south
    [:west "L"] :south
    [:west "R"] :north))

(defn steps-y-axis
  [[x y] dy]
  (let [op (if (pos? dy) inc dec)]
    (drop 1 (map vector (repeat x) (range y (op (+ y dy)) (op 0))))))

(defn steps-x-axis
  [[x y] dx]
  (let [op (if (pos? dx) inc dec)]
    (drop 1 (map vector (range x (op (+ x dx)) (op 0)) (repeat y)))))

(defn walk
  [instructions]
  (reduce (fn [agent [direction blocks]]
            (let [x (get agent :x)
                  y (get agent :y)
                  headed (turn (:headed agent) direction)
                  [dx dy] (case headed
                            :north [0 blocks]
                            :south [0 (- blocks)]
                            :east [blocks 0]
                            :west [(- blocks) 0])
                  [x' y'] [(+ x dx) (+ y dy)]]
              (-> agent
                  (assoc :x x')
                  (assoc :y y')
                  (update :visited (fn [visited]
                                     (into visited (if (zero? dx)
                                                     (steps-y-axis [x y] dy)
                                                     (steps-x-axis [x y] dx)))))
                  (assoc :headed headed))))
          {:x       0
           :y       0
           :visited [[0 0]]
           :headed  :north}
          instructions))

(defn part01
  [input]
  (let [{:keys [x y]} (walk input)]
    (math/manhattan-distance [0 0] [x y])))

(defn part02
  [input]
  (let [{:keys [visited]} (walk input)]
    (math/manhattan-distance [0 0] (collections/first-duplicate visited))))
