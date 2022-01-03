(ns aoc.2017.day09
  (:require [aoc.file :as file]
            [clojure.string :as string]))

(def input (first (file/read-lines "2017/day09.txt")))

(defn remove-canceled
  [s]
  (string/replace s #"!." ""))

(defn remove-garbage
  [s]
  (string/replace s #"<.*?>" "<>"))

(defn score-groups
  [s]
  (loop [[x & xs] s
         level 0
         score 0]
    (case x
      nil score
      \{ (recur xs (inc level) (+ score (inc level)))
      \} (recur xs (dec level) score)
      (recur xs level score))))

(defn part01
  [input]
  (-> input
      (remove-canceled)
      (remove-garbage)
      (score-groups)))

(defn part02
  [input]
  (let [without-canceled (remove-canceled input)]
    (- (count without-canceled)
       (count (remove-garbage without-canceled)))))
