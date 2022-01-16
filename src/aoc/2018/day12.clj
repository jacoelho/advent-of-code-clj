(ns aoc.2018.day12
  (:require [aoc.file :as file]
            [clojure.string :as str]))

(def plant? #{1})
(def translate {\# 1 \. 0})

(defn parse-file
  [f]
  (let [[h _ & rest] (file/read-lines f)]
    {:state (->> (subs h 15)
                 (mapv translate))
     :rules (->> rest
                 (mapv (fn [s]
                         (let [[rule result] (str/split s #" => ")]
                           [(mapv translate rule) (translate (first result))])))
                 (into {}))}))

(def input (parse-file "2018/day12.txt"))

(defn mutate
  [{:keys [state rules idx] :or {idx 0}}]
  (let [s           (->> (concat [0 0 0 0] state [0 0 0 0])
                         (partition 5 1)
                         (mapv rules))
        plants-pos  (keep-indexed (fn [i e] (when (plant? e) i)) s)
        first-plant (first plants-pos)
        last-plant  (last plants-pos)
        new-idx     (+ (- idx 2) first-plant)]
    {:rules rules
     :state (subvec s first-plant (inc last-plant))
     :idx   new-idx}))

(defn count-plant-pots
  [{:keys [state idx] :or {idx 0}}]
  (->> state
       (keep-indexed (fn [i e] (when (plant? e) (+ i idx))))
       (reduce + 0)))

(defn part01
  [input]
  (->> (iterate mutate input)
       (drop 20)
       (first)
       (count-plant-pots)))

(defn part02
  [input]
  (let [[v difference] (->> (iterate mutate input)
                            (map count-plant-pots)
                            (partition 2 1)
                            (map (fn [[l r]]
                                   [l (- r l)]))
                            ;; looking at input 200 works
                            ;; input generates a couple differences 46 and 48 in a row
                            ;; where the stable difference is 50
                            (drop 200)
                            (first))]
    (+ v (* difference (- 50000000000 200)))))






