(ns aoc.2015.day02
  (:require
   [aoc.file :as file]
   [aoc.parse :as parse]
   [clojure.string :as str]))

(defn parse
  [line]
  (->> (str/split line #"x")
       (map parse/->int)))

(def input
  (file/read-lines parse "2015/day02.txt"))

(defn side-areas
  [[length width height]]
  [(* length width) (* width height) (* height length)])

(defn wrap-smallest-side
  [areas]
  (apply min areas))

(defn wrap-box
  [areas]
  (reduce #(+ %1 (* 2 %2)) 0 areas))

(defn wrap
  [input]
  (let [areas (side-areas input)]
    (+ (wrap-box areas) 
       (wrap-smallest-side areas))))

(defn part01
  [input]
  (->> input
       (map wrap)
       (reduce +)))

(defn perimeter
  [l w]
  (* 2 (+ l w)))

(defn ribbon
  [[length width height]]
  (let [smallest-perimeter (min (perimeter length width)
                                (perimeter width height)
                                (perimeter height length))
        volume             (* length width height)]
    (+ smallest-perimeter volume)))

(defn part02
  [input]
  (->> input
       (map ribbon)
       (reduce +)))
