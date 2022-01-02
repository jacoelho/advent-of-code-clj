(ns aoc.2017.day06
  (:require [aoc.file :as file]
            [aoc.parse :as parse]
            [aoc.collections :as collections]))

(def input (->> (file/read-lines #(re-seq #"\d+" %) "2017/day06.txt")
                (first)
                (mapv parse/->int)))

(def example [0 2 7 0])

(defn index-highest-value
  [coll]
  (->> (map-indexed vector coll)
       (reduce (fn [[_ m :as acc] [_ v :as pair]]
                 (if (< m v) pair acc)))
       (first)))

(defn spread-max-block
  [coll]
  (let [max-idx (index-highest-value coll)]
    (->> (range (count coll))
         (cycle)
         (drop (inc max-idx))
         (take (get coll max-idx))
         (reduce (fn [acc idx]
                   (update acc idx inc))
                 (assoc coll max-idx 0)))))

(defn part01
  [input]
  (->> (iterate spread-max-block input)
       (collections/first-duplicate)
       (first)
       (second)))

(defn part02
  [input]
  (->> (iterate spread-max-block input)
       (collections/first-duplicate)
       (first)
       (reverse)
       (apply -)))
