(ns aoc.2021.day17
  (:require [aoc.parse :as parse]
            [aoc.file :as file]))

(defn parse-line
  [line]
  (let [[_ & rest] (re-find #"target area: x=(-?\d+)..(-?\d+), y=(-?\d+)..(-?\d+)" line)
        [x0 x1 y0 y1] (mapv parse/string->int rest)]
    [[x0 y0]
     [x1 y1]]))

(def example
  [[20 -10] [30 -5]])

(def input
  (first (file/read-lines parse-line "2021/day17.txt")))

(defn projectile-step
  [[[x y] [dx dy]]]
  [[(+ x dx) (+ y dy)]
   [(max 0 (dec dx)) (dec dy)]])

(defn passed?
  [[[_ y0] [x1 _]] [px py]]
  (or (< x1 px)
      (< py y0)))

(defn trajectory
  [area speed]
  (->> [[0 0] speed]
       (iterate projectile-step)
       (take-while (fn [[pos]]
                     (not (passed? area pos))))))

(defn hit?
  [[[x0 y0] [x1 y1]] [px py]]
  (and (<= x0 px x1)
       (<= y0 py y1)))

(defn trajectory-hits?
  [area trajectory-points]
  (some (fn [[pos]]
          (hit? area pos))
        trajectory-points))

(defn starting-velocities
  [[[_ y0] [x1 _]]]
  (for [x (range (inc x1))
        y (range y0 (* y0 -1))]
    [x y]))

(defn part01
  [input]
  (->> input
       (starting-velocities)
       (map (partial trajectory input))
       (filter (partial trajectory-hits? input))
       (mapcat (partial map (fn [[[_ y]]] y)))
       (apply max)))

(defn part02
  [input]
  (->> input
       (starting-velocities)
       (map (partial trajectory input))
       (filter (partial trajectory-hits? input))
       (count)))
