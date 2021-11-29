(ns aoc.2015.day11
  (:require
   [aoc.file :as aoc]
   [clojure.test :refer [testing is]]))

(defn char->numeric
  [c]
  (- (int c) (int \a)))

(defn string->numeric
  [input]
  (->> input
       (map char->numeric)
       (apply vector)))

(defn numeric->char
  [c]
  (char (+ c (int \a))))

(defn numeric->string
  [input]
  (->> input
       (map numeric->char)
       (apply str)))

(defn consecutive?
  [input]
  (->> input
       (partition 3 1)
       (some (fn [[a b c]]
               (and (= (- b a) 1)
                    (= (- c b) 1))))
       (boolean)))

(def forbidden-chars
  #{(char->numeric \i)
    (char->numeric \o)
    (char->numeric \l)})

(defn without-forbidden?
  [input]
  (->> input
       (some #(forbidden-chars %))
       (not)))

(defn two-pairs?
  [input]
  (let [pairs (->> input
                   (partition-by identity)
                   (map #(count %))
                   (filter #(> % 1)))]
    (or (> (count pairs) 1)
        (some #(> % 3) pairs))))

(defn valid?
  [input]
  (and (consecutive? input)
       (without-forbidden? input)
       (two-pairs? input)))

(defn a++
  [col]
  (loop [pos (dec (count col))
         res col]
    (if (neg? pos)
      res
      (case (get res pos)
        nil res
        25 (recur (dec pos) (assoc res pos 0))
        (update res pos inc)))))

(defn part01
  [input]
  (->> (string->numeric input)
       (iterate a++)
       (drop 1)
       (filter valid?)
       (first)
       (numeric->string)))

(testing "Part 01"
  (is (= "cqjxxyzz" (part01 "cqjxjnds"))))

(testing "Part 02"
  (is (= "cqkaabcc" (part01 "cqjxxyzz"))))
