(ns aoc.2019.day06
  (:require [aoc.file :as file]))

(def input (->> (file/read-lines #(re-seq #"\w+" %) "2019/day06.txt")
                (reduce (fn [m [a b]]
                          (-> m
                              (update a (fnil conj #{}) b)
                              (update b (fnil conj #{}) a)))
                        {})))

(defn orbits
  [neighbours start]
  (loop [visited  #{}
         frontier [[start 0]]
         result   {}]
    (if-let [[orbit cost] (peek frontier)]
      (if (contains? visited orbit)
        (recur visited (pop frontier) result)
        (recur (conj visited orbit)
               (->> (neighbours orbit)
                    (remove visited)
                    (mapv #(vector % (inc cost)))
                    (into (pop frontier)))
               (conj result [orbit cost])))
      result)))

(defn part01
  [input]
  (->> (orbits input "COM")
       (vals)
       (apply +)))

(defn part02
  [input]
  (-> (orbits input "YOU")
      (get "SAN")
      (- 2)))
