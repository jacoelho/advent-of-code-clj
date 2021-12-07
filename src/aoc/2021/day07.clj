(ns aoc.2021.day07
  (:require [aoc.file :as file]
            [aoc.parse :as parse]
            [clojure.string :as str]
            [aoc.math :as math]))

(defn parse-line
  [line]
  (map parse/->int (str/split line #",")))

(def input
  (vec (first (file/read-lines parse-line "2021/day07.txt"))))

(defn fuel-constant-rate
  [input point]
  (->> input
       (map #(math/abs (- point %)))
       (reduce +)))

(defn sum-to-n
  [n]
  (quot (* n (+ n 1)) 2))

(defn fuel-linear-rate
  [input point]
  (->> input
       (map #(sum-to-n (math/abs (- point %))))
       (reduce +)))

(defn least-fuel
  [fuel-calc-fn input]
  (->> input
       (map (partial fuel-calc-fn input))
       (apply min)))

(defn part01
  [input]
  (least-fuel fuel-constant-rate input))

(defn part02
  [input]
  (least-fuel fuel-linear-rate input))
