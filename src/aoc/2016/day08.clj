(ns aoc.2016.day08
  (:require [aoc.parse :as parse]
            [aoc.file :as file]
            [aoc.geometry :as geometry]))

(defn parse-line
  [line]
  (let [[_ a b axis coord idx value] (re-find #"(?:rect (\d+)x(\d+))|(?:rotate ([a-z]+) ([xy])=(\d+) by (\d+))" line)]
    (if (nil? a)
      [:rotate
       (if (= axis "row") :row :column)
       (if (= coord "x") :x :y)
       (parse/->int idx)
       (parse/->int value)]
      [:rect
       (parse/->int a)
       (parse/->int b)])))

(def input
  (file/read-lines parse-line "2016/day08.txt"))

(def board
  (geometry/grid 50 10 \_))

(defn rectangle
  [board wide tall]
  (reduce #(assoc-in %1 %2 \#)
          board
          (for [x (range wide)
                y (range tall)]
            [y x])))

(defn rotate
  [n coll]
  (if (empty? coll)
    coll
    (let [pos (mod n (count coll))
          [tail head] (split-at pos coll)]
      (concat head tail))))

(defn rotate-row
  [board column rotation]
  (if (<= (count board) column)
    board
    (update board column #(vec (rotate (- rotation) %)))))

(defn rotate-column
  [board row rotation]
  (-> board
      (geometry/transpose)
      (rotate-row row rotation)
      (geometry/transpose)))

(defn swipe-card
  [board input]
  (reduce (fn [board [instruction a b c d]]
            (case instruction
              :rect
              (rectangle board a b)

              :rotate
              (if (= a :row)
                (rotate-row board c d)
                (rotate-column board c d))))
          board
          input))

(defn part01
  [input]
  (->> input
       (swipe-card board)
       (map (partial filter #(= % \#)))
       (map count)
       (reduce +)))

(def small-board
  (geometry/grid 50 6 \_))

(defn part02
  [input]
  (->> input
       (swipe-card small-board)))

;; checking printed grid: ZFHFSFOGPO
(geometry/print-grid (part02 input))
