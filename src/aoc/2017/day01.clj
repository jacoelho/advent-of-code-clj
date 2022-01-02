(ns aoc.2017.day01
  (:require [aoc.file :as file]))

(defn char->int
  [ch]
  (Character/digit ^char ch 10))

(def input (first (file/read-lines #(mapv char->int %) "2017/day01.txt")))

(defn captcha-sum
  [pairs]
  (->> pairs
       (filter (partial apply =))
       (map first)
       (reduce +)))

(defn part01
  [input]
  (->> (conj input (first input))
       (partition 2 1)
       (captcha-sum)))

(defn part02
  [input]
  (let [mid (quot (count input) 2)]
    (->> input
         (split-at mid)
         (apply map vector)
         (captcha-sum)
         (* 2))))
