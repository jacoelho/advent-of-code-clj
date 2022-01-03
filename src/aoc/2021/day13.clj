(ns aoc.2021.day13
  (:require [aoc.parse :as parse]
            [aoc.file :as file]
            [aoc.collections :as collections]
            [aoc.geometry :as geometry]))

(defn parse-line
  [line]
  (condp re-matches line
    #"^(\d+),(\d+)$" :>> (fn [[_ & rest]]
                           [:pos (mapv parse/string->int rest)])


    #"^fold along (x|y)=(\d+)$" :>> (fn [[_ axis value]]
                                      [:fold [axis (parse/string->int value)]])

    #"$" :>> (fn [_] nil)))

(defn parse-file
  [input]
  (->> input
       (file/read-lines parse-line)
       (remove nil?)
       (reduce (fn [acc [op [a b]]]
                 (case op
                   :pos (assoc-in acc [:grid [a b]] "X")
                   :fold (update acc :instructions conj [a b])))
               {:grid {}
                :instructions []})))

(def example
  (parse-file "2021/day13-example.txt"))

(def input
  (parse-file "2021/day13.txt"))

(defn offset
  [reference value]
  (- (* 2 reference) value))

(defn fold
  [grid [axis value]]
  (collections/map-keys (fn [[x y]]
                          (case axis
                            "x" (if (< x value)
                                  [x y]
                                  [(offset value x) y])
                            "y" (if (< y value)
                                  [x y]
                                  [x (offset value y)])))
                        grid))

(defn part01
  [input]
  (count (fold (:grid input) (first (:instructions input)))))

(defn part02
  [input]
  (geometry/print-map-grid (reduce fold (:grid input) (:instructions input))))
