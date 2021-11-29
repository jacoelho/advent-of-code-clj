(ns aoc.2015.day08
  (:require
   [aoc.file :as file]))

(def input
  (file/read-lines "2015/day08.txt"))

(defn count-memory
  [input]
  (count input))

(defn count-code
  [input]
  (loop [count         -2
         [x y :as all] input]
    (if (nil? x)
      count
      (recur (inc count)
             (case [x y]
               [\\ \\] (drop 2 all)
               [\\ \"] (drop 2 all)
               [\\ \x] (drop 4 all)
               (rest all))))))

(defn part01
  [input]
  (apply + (map #(- (count-memory %)
                    (count-code %)) 
                input)))

(defn count-encoded
  [input]
  (reduce + 2 (map #(case % (\\ \") 2 1) input)))

(defn part02
  [input]
  (apply + (map #(- (count-encoded %)
                    (count-memory %))
                input)))
