(ns aoc.2018.day20
  (:require [aoc.file :as file]
            [aoc.collections :as collections]))

(def input (->> (file/read-lines "2018/day20.txt")
                (first)
                (rest)
                (drop-last 1)))

(def directions {\N [0 -1]
                 \S [0 1]
                 \W [1 0]
                 \E [-1 0]})

(defn move
  [[x y] [dx dy]]
  [(+ x dx) (+ y dy)])

(defn step
  [{:keys [grid stack current]
    :as   state} element]
  (case element
    \( (update state :stack conj current)
    \) (-> state
           (assoc :current (peek stack))
           (update :stack pop))
    \| (assoc state :current (peek stack))
    (let [position      (move current (directions element))
          next-distance (inc (get grid current 0))]
      (-> state
          (assoc-in [:grid position] (min (get grid position Integer/MAX_VALUE)
                                          next-distance))
          (assoc :current position)))))

(defn part01
  [input]
  (->> (reduce step {:grid    {}
                     :stack   []
                     :current [0 0]} input)
       (:grid)
       (vals)
       (apply max)))

(defn part02
  [input]
  (->> (reduce step {:grid    {}
                     :stack   []
                     :current [0 0]} input)
       (:grid)
       (vals)
       (collections/count-by #(<= 1000 %))))
