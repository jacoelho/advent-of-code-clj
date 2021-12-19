(ns aoc.2021.day19
  (:require [aoc.file :as file]
            [aoc.parse :as parse]
            [clojure.math.combinatorics :as comb]
            [aoc.geometry :as geometry]
            [clojure.set :as set])
  (:import (clojure.lang PersistentQueue)))

(set! *warn-on-reflection* true)

(defrecord point [^long x ^long y ^long z])

(defn seq->point
  [[x y z]]
  (->point x y z))

(defn plus
  [^point a ^point b]
  (->point (+ (:x a) (:x b))
           (+ (:y a) (:y b))
           (+ (:z a) (:z b))))

(defn minus
  [^point a ^point b]
  (->point (- (:x a) (:x b))
           (- (:y a) (:y b))
           (- (:z a) (:z b))))

(defn manhattan-distance
  [^point a ^point b]
  (let [p' (minus a b)]
    (+ (Math/abs ^long (:x p'))
       (Math/abs ^long (:y p'))
       (Math/abs ^long (:z p')))))

(defn rotation
  [^long rot ^point p]
  (case (long rot)
    0 (->point (:x p) (:y p) (:z p))
    1 (->point (:y p) (- (:x p)) (:z p))
    2 (->point (- (:x p)) (- (:y p)) (:z p))
    3 (->point (- (:y p)) (:x p) (:z p))
    4 (->point (:z p) (:y p) (- (:x p)))
    5 (->point (:y p) (- (:z p)) (- (:x p)))
    6 (->point (- (:z p)) (- (:y p)) (- (:x p)))
    7 (->point (- (:y p)) (:z p) (- (:x p)))
    8 (->point (:z p) (- (:x p)) (- (:y p)))
    9 (->point (- (:x p)) (- (:z p)) (- (:y p)))
    10 (->point (- (:z p)) (:x p) (- (:y p)))
    11 (->point (:x p) (:z p) (- (:y p)))
    12 (->point (:z p) (- (:y p)) (:x p))
    13 (->point (- (:y p)) (- (:z p)) (:x p))
    14 (->point (- (:z p)) (:y p) (:x p))
    15 (->point (:y p) (:z p) (:x p))
    16 (->point (:z p) (:x p) (:y p))
    17 (->point (:x p) (- (:z p)) (:y p))
    18 (->point (- (:z p)) (- (:x p)) (:y p))
    19 (->point (- (:x p)) (:z p) (:y p))
    20 (->point (- (:x p)) (:y p) (- (:z p)))
    21 (->point (:y p) (:x p) (- (:z p)))
    22 (->point (:x p) (- (:y p)) (- (:z p)))
    23 (->point (- (:y p)) (- (:x p)) (- (:z p)))))

(defn parse-positions
  [lines]
  (let [[match & rest] (re-find #"(-?\d+),(-?\d+),(-?\d+)" lines)]
    (when match
      (seq->point (mapv parse/->int rest)))))

(defn parse-scanner
  [lines]
  (->> lines
       (mapv parse-positions)
       (drop 1)
       (into #{})))

(defn parse-file
  [input]
  (->> input
       (file/read-lines)
       (partition-by #(= "" %))
       (mapv parse-scanner)
       (remove empty?)))

(def input
  (parse-file "2021/day19.txt"))

(def example
  (parse-file "2021/day19-example.txt"))

(defn find-scanner
  [scanner-left scanner-right]
  (->> (range 24)
       (map #(map (partial rotation %) scanner-right))
       (some (fn [rotations]
               (let [candidate (set rotations)
                     product   (comb/cartesian-product scanner-left candidate)]
                 (some (fn [difference]
                         (let [moved (set (map (partial plus difference) candidate))]
                           (when (<= 12 (count (set/intersection scanner-left moved)))
                             [difference moved])))
                       (map #(apply minus %) product)))))))

(defn align
  [scanners]
  (loop [data {:scanners [(->point 0 0 0)]
                 :beacons  (first scanners)}
         queue  (into PersistentQueue/EMPTY (rest scanners))]
    (if (seq queue)
      (if-let [[scanner found] (find-scanner (:beacons data) (peek queue))]
        (recur (-> data
                   (update :scanners conj scanner)
                   (update :beacons into found)) (pop queue))
        (recur data (pop (conj queue (peek queue)))))
      data)))

(defn part01
  [input]
  (count (:beacons (align input))))

(defn part02
  [input]
  (let [result (:scanners (align input))]
    (reduce max (for [a result
                      b result
                      :when (not= a b)]
                  (manhattan-distance a b)))))

