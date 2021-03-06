(ns aoc.2016.day13
  (:require [aoc.convert :as convert]
            [aoc.geometry :as geometry]
            [aoc.search :as search])
  (:import (clojure.lang PersistentQueue)))

(defn open-space?
  [favorite-number [x y]]
  (->> (+ favorite-number (* x x) (* 3 x) (* 2 x y) y (* y y))
       (convert/long->binary)
       (filter #{1})
       (count)
       (even?)))

(defn neighbours
  [favorite-number point]
  (->> point
       (geometry/neighbours4)
       (filter (fn [[x y :as p]]
                 (and (<= 0 x)
                      (<= 0 y)
                      (open-space? favorite-number p))))))

(def input 1362)

(defn bfs-with-max-distance
  [start max-distance neighbours goal?]
  (loop [q       (conj (PersistentQueue/EMPTY) start)
         visited {start 0}]
    (when-let [current (peek q)]
      (if (or (<= max-distance (visited current))
              (goal? current))
        visited
        (let [next-distance (inc (visited current))
              n          (->> current
                              (neighbours)
                              (remove visited))]
          (recur (into (pop q) n)
                 (into visited (map (fn [n] [n next-distance])) n)))))))

(defn part01
  [input]
  (let [goal [31 39]]
    ((bfs-with-max-distance [1 1]
                            Integer/MAX_VALUE
                            (partial neighbours input)
                            #{goal}) goal)))

(defn part02
  [input]
  (count (bfs-with-max-distance [1 1]
                                50
                                (partial neighbours input)
                                (constantly false))))
