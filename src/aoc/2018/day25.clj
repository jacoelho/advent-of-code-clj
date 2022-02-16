(ns aoc.2018.day25 
  (:require [aoc.parse :as parse]
            [aoc.file :as file]
            [aoc.geometry :as geometry]
            [aoc.search :as search]
            [clojure.set :as set]))

(defn parse-line
  [line]
  (->> (re-seq #"-?\d+" line)
       (mapv (partial parse/string->int))))

(def input (set (file/read-lines parse-line "2018/day25.txt")))

(defn neighbours
  [points point]
  (filter #(<= (geometry/manhattan-distance % point) 3) points))

(defn part01
  ([input]
   (part01 input 0))
  ([input constellations]
   (if (seq input)
     (recur (set/difference input (search/reachable-nodes (partial neighbours input) 
                                                          (first input)))
            (inc constellations))
     constellations)))
