(ns aoc.2021.day03
  (:require [aoc.file :as file]
            [aoc.parse :as parse]))

(defn parse-line
  [line]
  (let [n (re-seq #"\d" line)]
    (mapv parse/->int n)))

(def example
  (mapv parse-line ["00100"
                    "11110"
                    "10110"
                    "10111"
                    "10101"
                    "01111"
                    "00111"
                    "11100"
                    "10000"
                    "11001"
                    "00010"
                    "01010"]))

(def input
  (file/read-lines parse-line "2021/day03.txt"))

(defn transpose
  [coll]
  (apply mapv vector coll))

(defn by-frequency-most-common [[d1 f1] [d2 f2]]
  (let [c (compare f2 f1)]
    (if (not= c 0)
      c
      (compare d2 d1))))

(defn by-frequency-less-common [[d1 f1] [d2 f2]]
  (let [c (compare f1 f2)]
    (if (not= c 0)
      c
      (compare d1 d2))))

(defn find-frequency
  [comparator coll]
  (->> coll
       (frequencies)
       (sort comparator)
       (ffirst)))

(defn rate
  [sorting coll]
  (->> coll
       (map (partial find-frequency sorting))
       (apply str)
       (parse/binary-string->int)))

(defn power-consumption
  [coll]
  (let [gamma (rate by-frequency-most-common coll)
        epsilon (rate by-frequency-less-common coll)]
    (* gamma epsilon)))

(defn part01
  [input]
  (->> input
       (transpose)
       (power-consumption)))

(defn pos-rate
  [signal coll pos]
  (let [t (transpose coll)
        el (find-frequency signal (nth t pos))]
    (->> coll
         (filter #(= (nth % pos) el)))))

(defn rate-consecutive
  ([rating-fn numbers]
   (rate-consecutive rating-fn 0 numbers))

  ([rating-fn idx numbers]
   (if (= (count numbers) 1)
     (->> numbers
          (first)
          (apply str)
          (parse/binary-string->int))
     (recur rating-fn
            (inc idx)
            (pos-rate rating-fn numbers idx)))))

(defn part02
  [input]
  (let [o2 (rate-consecutive by-frequency-most-common input)
        co2 (rate-consecutive by-frequency-less-common input)]
    (* o2 co2)))
