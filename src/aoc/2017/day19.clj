(ns aoc.2017.day19
  (:require [aoc.file :as file]
            [aoc.geometry :as geometry]
            [aoc.collections :as collections]))

(defn parse-file
  [f]
  (->> (file/read-lines f)
       (geometry/map-grid-2d)
       (collections/remove-vals #{\space})))

(def example (parse-file "2017/day19-example.txt"))
(def input (parse-file "2017/day19.txt"))

(defn add-position
  [[x y] [dx dy]]
  [(+ x dx) (+ y dy)])

(defn starting-point
  [grid]
  (some (fn [[[_ y :as k] v]]
          (when (and (= y 0) (= v \|))
            k))
        grid))

(defn turn
  [grid position [x y :as direction]]
  (let [directions (-> #{[1 0] [-1 0] [0 -1] [0 1]}
                        (disj direction)
                        (disj [(- x) (- y)]))]
    (some #(when (grid (add-position position %))
             %)
          directions)))

(defn traverse
  [grid position direction]
  (lazy-seq
    (if-let [current-element (grid position)]
      (cons current-element
            (if (= current-element \+)
              (let [direction' (turn grid position direction)]
                (traverse grid (add-position position direction') direction'))
              (traverse grid (add-position position direction) direction)))
      nil)))

(defn part01
  [input]
  (->> (traverse input (starting-point input) [0 1])
       (remove #{\| \- \+})
       (apply str)))

(defn part02
  [input]
  (->> (traverse input (starting-point input) [0 1])
       (count)))
