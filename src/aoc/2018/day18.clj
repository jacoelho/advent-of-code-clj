(ns aoc.2018.day18
  (:require [aoc.file :as file]
            [aoc.geometry :as geometry]
            [aoc.collections :as collections]))

(def input (->> (file/read-lines "2018/day18.txt")
                (geometry/map-grid-2d)))

(defn neighbours
  [grid position]
  (->> (geometry/neighbours8 position)
       (keep grid)))

(defn next-acre
  [grid position acre]
  (let [freq       (->> (neighbours grid position)
                        (frequencies))
        trees      (get freq \| 0)
        lumberyard (get freq \# 0)]
    (case acre
      \. (if (<= 3 trees) \| \.)
      \| (if (<= 3 lumberyard) \# \|)
      \# (if (and (pos? lumberyard)
                  (pos? trees))
           \#
           \.))))

(defn minute-step
  [grid]
  (persistent!
    (reduce-kv (fn [m k v] (assoc! m k (next-acre grid k v)))
               (transient (empty grid)) grid)))

(defn after-minutes
  [input minutes]
  (let [freq (->> (iterate minute-step input)
                  (drop minutes)
                  (first)
                  (vals)
                  (frequencies))]
    (* (freq \#) (freq \|))))

(defn part01
  [input]
  (after-minutes input 10))

(defn part02
  [input]
  (let [[[first-occurrence second-occurrence] grid] (->> (iterate minute-step input)
                                                         (collections/first-duplicate))
        cycle-length (- second-occurrence first-occurrence)
        want         (mod (- 1000000000 second-occurrence) cycle-length)
        freq         (->> (iterate minute-step grid)
                          (drop want)
                          (first)
                          (vals)
                          (frequencies))]
    (* (freq \#) (freq \|))))
