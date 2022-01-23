(ns aoc.2018.day14
  (:require [clojure.string :as str]))

(def input 409551)

(defn step
  [{:keys [recipes elf-1 elf-2]}]
  (let [recipe-1 (nth recipes elf-1)
        recipe-2 (nth recipes elf-2)
        sum      (+ recipe-1 recipe-2)
        recipes  (if (< 9 sum)
                   (conj recipes 1 (- sum 10))
                   (conj recipes sum))]
    {:recipes recipes
     :elf-1   (rem (+ elf-1 recipe-1 1) (count recipes))
     :elf-2   (rem (+ elf-2 recipe-2 1) (count recipes))}))

(defn part01
  [input]
  (->> (iterate step {:recipes [3 7] :elf-1 0 :elf-2 1})
       (drop-while #(< (count (:recipes %))
                       (+ input 10)))
       (first)
       (:recipes)
       (take-last 10)
       (apply str)))

(defn part02
  [input]
  (str/index-of
    (->> (iterate step {:recipes [3 7] :elf-1 0 :elf-2 1})
         (drop 50000000)
         (first)
         (:recipes)
         (apply str))
    (str input)))
