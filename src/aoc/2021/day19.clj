(ns aoc.2021.day19
  (:require [aoc.file :as file]
            [aoc.parse :as parse]
            [clojure.math.combinatorics :as comb]
            [aoc.geometry :as geometry]
            [clojure.set :as set]))

(defn parse-header
  [line]
  (->> line
       (re-find #"\d+")
       (parse/->int)))

(defn parse-positions
  [line]
  (let [[_ & rest] (re-find #"(-?\d+),(-?\d+),(-?\d+)" line)]
    (mapv parse/->int rest)))

(defn parse-scanner
  [[h & pos]]
  {:scanner   (parse-header h)
   :positions (into #{} (mapv parse-positions pos))})

(defn parse-file
  [input]
  (->> input
       (file/read-lines)
       (partition-by #(= "" %))
       (remove #(= 1 (count %)))
       (mapv parse-scanner)))

(def input
  (parse-file "2021/day19.txt"))

(def example
  (parse-file "2021/day19-example.txt"))

(defn rotation
  [[x y z] rot]
  (case rot
    0 [x y z]
    1 [y (- x) z]
    2 [(- x) (- y) z]
    3 [(- y) x z]
    4 [z y (- x)]
    5 [y (- z) (- x)]
    6 [(- z) (- y) (- x)]
    7 [(- y) z (- x)]
    8 [z (- x) (- y)]
    9 [(- x) (- z) (- y)]
    10 [(- z) x (- y)]
    11 [x z (- y)]
    12 [z (- y) x]
    13 [(- y) (- z) x]
    14 [(- z) y x]
    15 [y z x]
    16 [z x y]
    17 [x (- z) y]
    18 [(- z) (- x) y]
    19 [(- x) z y]
    20 [(- x) y (- z)]
    21 [y x (- z)]
    22 [x (- y) (- z)]
    23 [(- y) (- x) (- z)]))

(defn beacon-distances
  [{:keys [scanner positions]}]
  (reduce (fn [acc [a b]]
            (update-in acc [:beacon a] (fnil conj #{}) (geometry/manhattan-distance a b)))
          {:scanner scanner}
          (comb/combinations positions 2)))

(defn overlaps?
  [{beacons-1 :beacon} {beacons-2 :beacon}]
  (<= 66
      (->> beacons-1
           (map (fn [[_ distances]]
                  (reduce (fn [acc [_ distances']]
                            (let [similar (set/intersection distances distances')]
                              (+ acc (count similar))))
                          0
                          beacons-2)))
           (reduce +))))

(defn align-one
  [{pos-1 :positions} {pos-2 :positions}]
  (let [rotations (->> (range 24)
                       (map (fn [rot]
                              (map #(rotation % rot) pos-2))))
        distances (->> rotations
                       (mapv #(comb/cartesian-product pos-1 %))
                       (mapv (fn [el] (mapv #(apply mapv - %) el))))]
    (some (fn [deltas]
            (some (fn [delta]
                    (some (fn [rotation]
                            (let [candidate (mapv #(mapv + delta %) rotation)]
                              (when (<= 12 (count (filter (partial contains? pos-1) candidate)))
                                candidate)))
                          rotations))
                  deltas))
          distances)))

(defn find-overlap
  [scanner scanners]
  (let [scanner' (beacon-distances scanner)]
    (loop [[x & xs] scanners
           skipped []]
      (if (overlaps? scanner' (beacon-distances x))
        [x (into [] (concat skipped xs))]
        (recur (conj skipped x) xs)))))

(defn align-scanners
  [scanners]
  (loop [reference (first scanners)
         scanners  (rest scanners)]
    (if (seq scanners)
      (let [[match remaining] (find-overlap reference scanners)]
        (recur (reduce (fn [acc pos]
                         (update acc :positions conj pos))
                       reference
                       (align-one reference match))
               remaining))
      reference)))

(align-one (first example) (second example))

(count (:positions (align-scanners input)))




