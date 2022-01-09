(ns aoc.2018.day05
  (:require [aoc.file :as file]
            [aoc.character :as character]))

(def input (->> (file/read-lines "2018/day05.txt")
                (first)))

(def alphabet "abcdefghijklmnopqrstuvwxyz")

(def reacts? (set (mapcat (juxt identity reverse)
                          (map (juxt identity character/upper-case)
                               alphabet))))

(defn resolve-reactions
  [polymer]
  (reduce (fn [acc el]
            (if (reacts? [(peek acc) el])
              (pop acc)
              (conj acc el)))
          []
          polymer))

(defn part01
  [input]
  (->> (resolve-reactions input)
       (count)))

(defn remove-unit
  [polymer ch]
  (remove #(= ch (character/lower-case %)) polymer))

(defn part02
  [input]
  (->> alphabet
       (map (comp part01 (partial remove-unit input)))
       (apply min)))
