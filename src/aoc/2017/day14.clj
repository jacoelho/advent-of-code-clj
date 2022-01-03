(ns aoc.2017.day14
  (:require [aoc.2017.day10 :as day10]
            [aoc.collections :as collections]
            [aoc.geometry :as geometry]
            [aoc.search :as search]))

(def input "ffayrhll")

(def hex-bin
  {\0 [0 0 0 0] \1 [0 0 0 1] \2 [0 0 1 0] \3 [0 0 1 1]
   \4 [0 1 0 0] \5 [0 1 0 1] \6 [0 1 1 0] \7 [0 1 1 1]
   \8 [1 0 0 0] \9 [1 0 0 1] \a [1 0 1 0] \b [1 0 1 1]
   \c [1 1 0 0] \d [1 1 0 1] \e [1 1 1 0] \f [1 1 1 1]})

(defn hexstring->bits
  [s]
  (mapcat hex-bin s))

(defn bits
  [key row]
  (->> (str key "-" row)
       (day10/knot-hash-64)
       (hexstring->bits)))

(defn part01
  [input]
  (->> (range 128)
       (map (partial bits input))
       (map (partial collections/count-by #{1}))
       (reduce +)))

(defn neighbours
  [grid pos]
  (->> (geometry/neighbours4 pos)
       (filter grid)))

(defn part02
  [input]
  (let [grid (->> (range 128)
                  (map (partial bits input))
                  (geometry/map-grid-2d)
                  (collections/remove-vals #{0}))]
    (->> grid
        (search/flood-fill (partial neighbours grid))
        (vals)
        (set)
        (count))))
