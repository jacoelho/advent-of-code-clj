(ns aoc.2017.day12
  (:require [aoc.parse :as parse]
            [aoc.file :as file]))

(defn parse-line
  [line]
  (let [[h & rest] (mapv parse/string->int (re-seq #"\d+" line))]
    [h (set rest)]))

(def input (->> "2017/day12.txt"
                (file/read-lines parse-line)
                (into {})))

(defn reachable-nodes
  [neighbours node]
  (loop [visited  #{}
         frontier [node]]
    (if-let [current (peek frontier)]
      (recur (conj visited current)
             (->> (neighbours current)
                  (remove visited)
                  (into (pop frontier))))
      visited)))

(defn part01
  [input]
  (count (reachable-nodes input 0)))

(defn part02
  [input]
  (->> (keys input)
       (map (partial reachable-nodes input))
       (distinct)
       (count)))
