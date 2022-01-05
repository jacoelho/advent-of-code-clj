(ns aoc.2017.day20
  (:require [aoc.parse :as parse]
            [aoc.file :as file]
            [aoc.geometry :as geometry]))

(defn parse-line
  [line]
  (let [values (re-seq #"-?\d+" line)]
    (->> (mapv parse/string->int values)
         (partition 3)
         (mapv vec))))

(def input (file/read-lines parse-line "2017/day20.txt"))

(defn add
  [a b & rest]
  (apply mapv + a b rest))

(defn update-particle
  [position velocity acceleration]
  [(add position velocity acceleration) (add velocity acceleration) acceleration])

(defn update-all-particles
  [coll]
  (map (partial apply update-particle) coll))

(defn part01
  [input]
  (->> input
       (iterate update-all-particles)
       (drop 1000)
       (first)
       (map #(geometry/manhattan-distance [0 0 0] (first %)))
       (map-indexed vector)
       (apply min-key second)
       (first)))

(defn part02
  [input]
  (->> input
       (iterate (fn [particles]
                  (->> (update-all-particles particles)
                       (group-by first)
                       (filter #(= 1 (count (second %))))
                       (mapcat second))))
       (drop 1000)
       (first)
       (count)))
