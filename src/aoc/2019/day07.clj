(ns aoc.2019.day07
  (:require [aoc.2019.intcode :as intcode]
            [clojure.math.combinatorics :as combo]))

(def input (intcode/parse-input "2019/day07.txt"))

(defn amplifiers
  [memory phases]
  (let [intcode'   (intcode/initialize memory)
        amplifiers (map #(update intcode' :input conj %) phases)]
    (reduce (fn [v amplifier]
              (->> (update amplifier :input conj v)
                   (intcode/until-halt)
                   (:output)
                   (first)))
            0
            amplifiers)))

(def example [3, 23, 3, 24, 1002, 24, 10, 24, 1002, 23, -1, 23,
              101, 5, 23, 23, 1, 24, 23, 23, 4, 23, 99, 0, 0])

(defn part01
  [input]
  (->> (combo/permutations [0 1 2 3 4])
       (map #(amplifiers input %))))


(comment (part01 example))
(comment (part01 [3,15,3,16,1002,16,10,16,1,16,15,15,4,15,99,0,0]))