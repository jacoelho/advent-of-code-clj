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
  (loop [visited  {}
         frontier [[start 0]]]
    (if-let [[orbit cost] (peek frontier)]
      (if (visited orbit)
        (recur visited (pop frontier))
        (let [neighbours-cost (inc cost)]
          (recur (assoc visited orbit cost)
                 (->> (neighbours orbit)
                      (keep #(when-not (visited %)
                               [% neighbours-cost]))
                      (into (pop frontier))))))
      visited)))

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
