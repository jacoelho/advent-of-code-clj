(ns aoc.2015.day16
  (:require
   [aoc.file :as file]
   [aoc.parse :as parse]))

(def re #"^Sue (\d+): (\w+): (\d+), (\w+): (\d+), (\w+): (\d+)$")

(defn parse-line
  [line]
  (let [[_ number a aa b bb c cc] (re-find re line)
        [number' aa' bb' cc']     (map parse/string->int [number aa bb cc])]
    {number' {a aa'
              b bb'
              c cc'}}))

(def input
  (file/read-lines parse-line "2015/day16.txt"))

(def memory {"children"    3
             "cats"        7
             "samoyeds"    2
             "pomeranians" 3
             "akitas"      0
             "vizslas"     0
             "goldfish"    5
             "trees"       3
             "cars"        2
             "perfumes"    1})

(defn matches
  [f memory entry]
  (->> entry
       (some (fn [[n vals]]
               (when (f memory vals) n)))))

(defn same-count?
  [memory entry]
  (every? (fn [[k v]]
            (let [e (get entry k 0)]
              (or (= v e)
                  (= e 0)))) memory))

(defn part01
  [memory input]
  (->> input
       (some (partial matches same-count? memory))))

(defn updated-instructions
  [memory entry]
  (every? (fn [[k v]]
            (if (contains? entry k)
              (let [e (get entry k 0)]
                (case k
                  ("cats" "trees") (> e v)
                  ("pomeranians" "goldfish") (> v e)
                  (= e v)))
              true)) memory))

(defn part02
  [memory input]
  (->> input
       (some (partial matches updated-instructions memory))))
