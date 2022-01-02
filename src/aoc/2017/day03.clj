(ns aoc.2017.day03
  (:require [aoc.geometry :as geometry]))

(def input 265149)

;; https://github.com/tschady/advent-of-code/blob/main/src/aoc/2017/d03.clj
(def spiral-steps
  (let [step-len (mapcat #(repeat 2 %) (map inc (range)))
        step-dir (cycle [[1 0] [0 1] [-1 0] [0 -1]])]
    (mapcat repeat step-len step-dir)))

(defn spiral
  ([start]
   (cons start (spiral start spiral-steps)))
  ([pos [step & xs]]
   (let [pos' (mapv + pos step)]
     (lazy-seq
       (cons pos' (spiral pos' xs))))))

(defn part01
  [input]
  (let [pos (nth (spiral [0 0]) (dec input))]
    (geometry/manhattan-distance [0 0] pos)))

(defn spiral-sum
  [acc pos]
  (let [n (->> (geometry/neighbours8 pos)
               (map (get acc :spiral {}))
               (remove nil?)
               (reduce +)
               (max 1))]
    (-> acc
        (assoc-in [:spiral pos] n)
        (update :max (fnil max 0) n))))

(defn part02
  [input]
  (->> (spiral [0 0])
       (reductions spiral-sum {})
       (some #(when (<= input (get % :max 0))
                (:max %)))))