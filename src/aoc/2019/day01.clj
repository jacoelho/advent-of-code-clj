(ns aoc.2019.day01 
  (:require [aoc.file :as file]
            [aoc.parse :as parse]
            [aoc.collections :as collections]))

(def input (file/read-lines parse/string->int "2019/day01.txt"))

(defn fuel-module
  [mass]
  (- (quot mass 3) 2))

(defn part01
  [input]
  (collections/sum-by fuel-module input))

(defn fuel
  [mass]
  (->> (iterate fuel-module mass)
       (drop 1)
       (take-while pos?)
       (reduce +)))

(defn part02
  [input]
  (collections/sum-by fuel input))
