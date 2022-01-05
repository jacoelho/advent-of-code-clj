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
  (some #(when (grid (add-position position %)) %)
        (disj #{[1 0] [-1 0] [0 -1] [0 1]} direction [(- x) (- y)])))

(defn traverse
  [grid position direction]
  (lazy-seq
    (when-let [current-element (grid position)]
      (let [direction (if (= current-element \+)
                        (turn grid position direction)
                        direction)]
        (cons current-element
              (traverse grid (add-position position direction) direction))))))

(defn part01
  [input]
  (->> (traverse input (starting-point input) [0 1])
       (remove #{\| \- \+})
       (apply str)))

(defn part02
  [input]
  (->> (traverse input (starting-point input) [0 1])
       (count)))
