(ns aoc.2018.day02
  (:require [aoc.file :as file]
            [aoc.collections :as collections]
            [clojure.math.combinatorics :as comb]))

(def input (file/read-lines "2018/day02.txt"))

(defn part01
  [input]
  (->> (map (comp vals frequencies) input)
       (reduce (fn [[two three] line-freq]
                 [((if (some #{2} line-freq) inc identity) two)
                  ((if (some #{3} line-freq) inc identity) three)])
               [0 0])
       (apply *)))

(defn off-by-one
  [[a b]]
  (let [coll (map vector a b)]
    (when (= 1 (collections/count-by (partial apply not=) coll))
      (->> (keep (fn [[a b]]
                   (when (= a b)
                     a)) coll)
           (apply str)))))

(defn part02
  [input]
  (->> (comb/combinations input 2)
       (some off-by-one)))

