(ns aoc.2017.day02
  (:require [aoc.parse :as parse]
            [aoc.file :as file]
            [clojure.math.combinatorics :as combo]))

(defn parse-line
  [line]
  (->> (re-seq #"\d+" line)
       (mapv parse/string->int)))

(def input (file/read-lines parse-line "2017/day02.txt"))

(def max-min (juxt max min))

(defn part01
  [input]
  (->> input
       (map #(reduce - (apply max-min %)))
       (reduce +)))

(defn evenly-divisible
  [row]
  (->> (combo/cartesian-product row row)
       (some (fn [[a b]]
               (when (and (not= a b)
                          (zero? (mod a b)))
                 (quot a b))))))

(defn part02
  [input]
  (->> input
       (map evenly-divisible)
       (reduce +)))