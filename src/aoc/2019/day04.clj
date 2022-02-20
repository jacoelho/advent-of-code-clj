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

(defn password?
  [password]
  (let [digits (convert/int->digits password)]
    (and (increase? digits)
         (adjacent-equal? digits))))

(defn part01
  [[a b]]
  (->> (range a (inc b))
       (collections/count-by password?)))

(defn exactly-two-adjacent?
  [password]
  (->> (frequencies password)
       (some #(= 2 (second %)))))

(defn password-improved?
  [password]
  (let [digits (convert/int->digits password)]
    (and (increase? digits)
         (exactly-two-adjacent? digits))))

(defn part02
  [[a b]]
  (->> (range a (inc b))
       (collections/count-by password-improved?)))
