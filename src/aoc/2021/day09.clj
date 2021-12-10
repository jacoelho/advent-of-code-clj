(ns aoc.2021.day09
  (:require [aoc.parse :as parse]
            [aoc.file :as file]
            [clojure.set :as set]))

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

(defn map-grid
  [input]
  (let [coordinates (for [x (range (count (first input)))
                          y (range (count input))]
                      [x y])]
    (into {} (map (fn [[x y]]
                    [[x y] (get-in input [y x])])
                  coordinates))))

(defn neighbours
  [[x0 y0]]
  (map (fn [[x1 y1]]
         (let [y (+ y1 y0)
               x (+ x1 x0)]
           [x y]))
       [[0 1]
        [0 -1]
        [-1 0]
        [1 0]]))

(defn neighbours-values
  [grid point]
  (->> point
       (neighbours)
       (map grid)
       (remove nil?)))

(defn low-point?
  [grid point]
  (let [v (get grid point)
        adjacent (neighbours-values grid point)]
    (< v (apply min adjacent))))

(defn low-points
  [grid]
  (reduce (fn [acc [point v]]
            (if (low-point? grid point)
              (conj acc v)
              acc))
          []
          grid))

(defn remove-height-9
  [grid]
  (reduce-kv (fn [m k v]
               (if (< v 9)
                 (assoc m k v)
                 m))
             {}
             grid))

(defn part01
  [input]
  (->> input
       (map-grid)
       (low-points)
       (map inc)
       (reduce +)))

(defn basin-neighbours
  [grid point]
  (->> point
       (neighbours)
       (reduce (fn [acc point]
                 (let [v (get grid point)]
                   (if (and (number? v)
                            (< v 9))
                     (conj acc point)
                     acc)))
               [])))

(defn explore-basin
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

(defn basins
  [grid]
  (->> grid
       (reduce (fn [acc [k _]]
                 (if ((get acc :visited) k)
                   acc
                   (let [basin (explore-basin grid {:visited #{k}
                                                    :explore (basin-neighbours grid k)})]
                     (-> acc
                         (update :visited set/union basin)
                         (update :basins conj basin)))))
               {:visited #{}
                :basins  []})
       (:basins)))

(defn part02
  [input]
  (->> input
       (map-grid)
       (remove-height-9)
       (basins)
       (sort-by count >)
       (take 3)
       (map count)
       (reduce *)))
