(ns aoc.2019.day04
  (:require [aoc.convert :as convert]
            [aoc.collections :as collections]))

(def input [172851 675869])

(defn adjacent-equal?
  [password]
  (->> (partition 2 1 password)
       (some #(apply = %))))

(defn increase?
  [password]
  (apply <= password))

(defn part01
  [[a b]]
  (->> (range a (inc b))
       (map convert/int->digits)
       (collections/count-by #(and (increase? %)
                                   (adjacent-equal? %)))))

(defn exactly-two-adjacent?
  [password]
  (->> (frequencies password)
       (some #(= 2 (second %)))))

(defn part02
  [[a b]]
  (->> (range a (inc b))
       (map convert/int->digits)
       (collections/count-by #(and (increase? %)
                                   (exactly-two-adjacent? %)))))
