(ns aoc.2021.day09
  (:require [aoc.parse :as parse]
            [aoc.file :as file]))

(defn parse-line
  [line]
  (mapv parse/->int (re-seq #"\d" line)))

(def input
  (file/read-lines parse-line "2021/day09.txt"))

(def example
  [[2 1 9 9 9 4 3 2 1 0]
   [3 9 8 7 8 9 4 9 2 1]
   [9 8 5 6 7 8 9 8 9 2]
   [8 7 6 7 8 9 6 7 8 9]
   [9 8 9 9 9 6 5 6 7 8]])

(defn coords
  [grid]
  (for [x (range (count (first grid)))
        y (range (count grid))]
    [x y]))

(def offsets [[0 1]
              [0 -1]
              [-1 0]
              [1 0]])

(defn neighbours
  [grid [x0 y0]]
  (reduce (fn [acc [x1 y1]]
            (let [y (+ y1 y0)
                  x (+ x1 x0)
                  v (get-in grid [y x])]
              (if v
                (conj acc [[x y] v])
                acc)))
          []
          offsets))

(defn low-point?
  [grid [x y]]
  (let [v (get-in grid [y x])
        adjacent (map second (neighbours grid [x y]))]
    (< v (apply min adjacent))))

(defn low-points
  [grid]
  (reduce (fn [acc [x y]]
            (if (low-point? grid [x y])
              (conj acc (get-in grid [y x]))
              acc))
          []
          (coords grid)))

;; 550
(defn part01
  [input]
  (->> input
       (low-points)
       (map inc)
       (reduce +)))

(defn basin-neighbours
  [grid point]
  (->> point
      (neighbours grid)
      (filter #(< (second %) 9))
      (map first)))

(defn basin
  [grid {:keys [explore visited]}]
  (if (seq explore)
    (recur grid
           (reduce (fn [{:keys [explore visited]} point]
                     (let [c (->> point
                                  (basin-neighbours grid)
                                  (remove visited))]
                       {:explore (into explore c)
                        :visited (conj visited point)}))
                   {:visited visited
                    :explore []}
                   explore))
    visited))

;; 1100682
(defn part02
  [input]
  (->> input
       (coords)
       (filter (fn [[x y]] (not= (get-in input [y x]) 9)))
       (map #(basin input {:visited (set [%])
                           :explore (basin-neighbours input %)}))
       (sort-by count >)
       (distinct)
       (take 3)
       (map count)
       (reduce *)))

(time (part02 input))

(defn foo
  [grid input]
  (->> input
       (map (fn [[x y]]
              [[x y] (get-in grid [y x])]))))

(foo example (basin example {:visited (set [[9 4]])
                             :explore (basin-neighbours example [9 4])}))
