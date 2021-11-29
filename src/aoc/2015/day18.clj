(ns aoc.2015.day18
  (:require
   [aoc.file :as file]
   [clojure.test :refer [testing is]]
   [clojure.string :as str]))

(defn parse-line
  [line]
  (->> line
       (mapv #(case %
               \# 1
               \. 0))))

(def input
  (vec (file/read-lines parse-line "2015/day18.txt")))

(def example
  (mapv parse-line
        (str/split-lines ".#.#.#
...##.
#....#
..#...
#.#..#
####..")))

(defn neighbours-coords
  [x y max-x max-y]
  (for [x'    (range (max 0 (dec x)) (inc (min (inc x) max-x)))
        y'    (range (max 0 (dec y)) (inc (min (inc y) max-y)))
        :when (not (and (= x x')
                        (= y y')))]
    [y' x']))

(defn neighbours-on
  [layout x y]
  (->> (neighbours-coords x y (dec (count (first layout))) (dec (count layout)))
       (map #(get-in layout %))
       (apply +)))

(defn count-lights-on
  [layout]
  (->> layout 
       (map (partial apply +))
       (apply +)))

(defn next-value
  [layout x y]
  (case [(get-in layout [y x]) (neighbours-on layout x y)]
    ([1 2] [1 3] [0 3]) 1
    0))

(defn next-layout
  [layout]
  (mapv (fn [y]
          (mapv #(next-value layout % y)
                (range (count (first layout)))))
        (range (count layout))))

(defn part01
  [input steps]
  (->> input
       (iterate next-layout) 
       (drop steps)
       (first)
       (count-lights-on)))

(testing "Part 01"
  (is (= 814 (part01 input 100))))

(defn corners-on
  [layout]
  (let [max-x (dec (count (first layout)))
        max-y (dec (count layout))]
    (reduce #(assoc-in % %2 1) layout [[0 0] [0 max-x] [max-y 0] [max-y max-x]])))

(defn part02
  [input steps]
  (->> input
       (corners-on)
       (iterate (comp corners-on next-layout))
       (drop steps)
       (first)
       (count-lights-on)))

(testing "Part 02"
  (is (= 924 (part02 input 100))))
