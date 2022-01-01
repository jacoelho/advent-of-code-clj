(ns aoc.2016.day17
  (:require [aoc.digest :as digest]
            [aoc.search :as search]
            [aoc.geometry :as geometry]))

(def input "mmsxrhfx")

(defn directions
  [point]
  (mapv (fn [[direction d]]
          [direction (mapv + point d)])
        [["U" [0 -1]] ["D" [0 1]] ["L" [-1 0]] ["R" [1 0]]]))

(defn open-doors
  [passcode path]
  (->> (str passcode path)
       (digest/md5)
       (take 4)
       (map #{\b \c \d \e \f})))

(defn neighbours
  [passcode [path point]]
  (->> (map (fn [open point] (when open point))
            (open-doors passcode path)
            (directions point))
       (filter (fn [[_ [x y :as p]]]
                 (and p (<= 0 x 3) (<= 0 y 3))))
       (map (fn [[d p]] [(str path d) p]))))

(defn part01
  [input]
  (->> (search/astar
         ["" [0 0]]
         #(neighbours input %)
         (fn [[_ point]]
           (geometry/manhattan-distance [3 3] point))
         (constantly 1))
       (second)
       (last)
       (first)))

(defn longest-dfs [start neighbours-fn goal?]
  (loop [frontier [start]
         longest 0]
    (if (seq frontier)
      (let [[path :as v] (peek frontier)]
        (if (goal? v)
          (recur (pop frontier) (max longest (count path)))
          (recur (into (pop frontier) (neighbours-fn v)) longest)))
      longest)))

(defn part02
  [input]
  (longest-dfs ["" [0 0]]
               (partial neighbours input)
               (fn [[_ point]] (= [3 3] point))))
