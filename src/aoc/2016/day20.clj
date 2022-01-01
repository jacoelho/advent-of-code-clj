(ns aoc.2016.day20
  (:require [clojure.string :as string]
            [aoc.parse :as parse]
            [aoc.file :as file]))

(defn parse-line
  [line]
  (let [v (string/split line #"\-")]
    (mapv parse/->long v)))

(def input (file/read-lines parse-line "2016/day20.txt"))

(defn find-unblocked
  [ip-ranges current]
  (lazy-seq
    (when-let [[low high] (first ip-ranges)]
      (concat (range current low)
              (find-unblocked (rest ip-ranges)
                              (max current (inc high)))))))

(defn part01
  [input]
  (first (find-unblocked (sort-by first input) 0)))

(defn part02
  [input]
  (count (find-unblocked (sort-by first input) 0)))