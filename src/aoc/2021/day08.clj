(ns aoc.2021.day08
  (:require [aoc.file :as file]
            [clojure.set :as set]
            [aoc.math :as math]))

(defn parse-line
  [line]
  (->> line
       (re-seq #"[a-z]+")
       (mapv set)))

(def example
  (parse-line "acedgfb cdfbe gcdfa fbcad dab cefabd cdfgeb eafb cagedb ab | cdfeb fcadb cdfeb cdbaf"))

(def input
  (file/read-lines parse-line "2021/day08.txt"))

(defn part01
  [input]
  (->> input
       (mapcat (partial take-last 4))
       (filter #(#{2 4 3 7} (count %)))
       (count)))
      
;;     digits 0 1 2 3 4 5 6 7 8 9
;; # segments 6 2 5 5 4 5 6 3 7 6

;; "easy" digits:
;; digit 1 only one with 2 segments
;; digit 4 only one with 4 segments
;; digit 7 only one with 3 segments
;; digit 8 only one with 7 segments

;; remaining
;;     digits 0 X 2 3 X 5 6 X X 9
;; # segments 6 2 5 5 4 5 6 3 7 6

;; 2, 3 and 5: 5 segments
;; 0, 6 and 9: 6 segments

;; numbers with 6 segments:
;; 6 only one not overlapping 1 c segment
;; 0 only one not overlapping 4 d segment
;; 9 is the remaining one

;; remaining
;;     digits X X 2 3 X 5 X X X X
;; # segments 6 2 5 5 4 5 6 3 7 6

;; numbers with 5 segments
;; 3 only overlaping 1
;; 5 differs for 6 by 1 (2 by 2)
;; 2 is the remaining one

(defn separate
  "Returns a vector:
   [ (filter f s), (filter (complement f) s) ]"
  [f s]
  [(filter f s) (filter (complement f) s)])

(defn find-easy-digits
  [mapping raw]
  (-> mapping
      (assoc 1 (first (get raw 2)))
      (assoc 4 (first (get raw 4)))
      (assoc 7 (first (get raw 3)))
      (assoc 8 (first (get raw 7)))))

(defn find-digits-6-segments
  [mapping raw]
  (let [elements (get raw 6)
        [[six] xs] (separate #(not (set/subset? (get mapping 1) %)) elements)
        [[zero] [nine]] (separate #(not (set/subset? (get mapping 4) %)) xs)]
    (-> mapping
        (assoc 6 six)
        (assoc 0 zero)
        (assoc 9 nine))))

(defn find-digits-5-segments
  [mapping raw]
  (let [elements (get raw 5)
        [[three] xs] (separate #(set/subset? (get mapping 1) %) elements)
        [[five] [two]] (separate #(= 1 (count (set/difference (get mapping 6) %))) xs)]
    (-> mapping
        (assoc 3 three)
        (assoc 5 five)
        (assoc 2 two))))

(defn find-digits
  [input]
  (let [input' (->> input
                    (drop-last 4)
                    (group-by count))]
    (-> {}
        (find-easy-digits input')
        (find-digits-6-segments input')
        (find-digits-5-segments input'))))

(defn swap
  [m]
  (reduce-kv #(assoc %1 %3 %2) {} m))

(defn decode
  [mapping input]
  (map mapping (take-last 4 input)))

(defn decode-line
  [input]
  (let [mapping (swap (find-digits input))]
    (math/digits->long (decode mapping input))))

(defn part02
  [input]
  (->> input
       (map decode-line)
       (reduce +)))