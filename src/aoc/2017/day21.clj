(ns aoc.2017.day21
  (:require [clojure.string :as string]
            [aoc.matrix :as matrix]
            [aoc.file :as file]
            [aoc.collections :as collections]))

(defn parse-row
  [row]
  (mapv (partial mapv {\. 0 \# 1}) row))

(defn parse-line
  [line]
  (->> (string/split line #" => ")
       (map #(string/split % #"/"))
       (mapv parse-row)))

(defn transformations
  [m]
  (let [r (take 4 (iterate matrix/rotate m))]
    (distinct (into r (mapv matrix/vertical-flip r)))))

(defn parse-file
  [f]
  (->> f
       (file/read-lines (fn [line]
                          (let [[from to] (parse-line line)
                                from' (transformations from)]
                            (map vector from' (repeat to)))))
       (apply concat)
       (into {})))

(def example (parse-file "2017/day21-example.txt"))
(def input (parse-file "2017/day21.txt"))

(def initial-grid [[0 1 0]
                   [0 0 1]
                   [1 1 1]])

(defn enhance
  [patterns m]
  (let [matrix-size (if (zero? (rem (count m) 2)) 2 3)]
    (->> (map (partial partition matrix-size) m)
         (partition matrix-size)
         (map matrix/transpose)
         (map #(map (partial get patterns) %)))))

(defn stitch
  [coll]
  (mapcat #(apply map concat %) coll))

(defn matrix-after-n
  [input n]
  (->> initial-grid
       (iterate (partial (comp stitch
                               (partial enhance input))))
       (drop n)
       (first)
       (collections/sum-by (partial reduce +))))

(defn part01
  [input]
  (matrix-after-n input 5))

(defn part02
  [input]
  (matrix-after-n input 18))
