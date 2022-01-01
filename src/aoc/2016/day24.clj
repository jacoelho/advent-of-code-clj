(ns aoc.2016.day24
  (:require [aoc.file :as file]
            [aoc.geometry :as geometry]
            [aoc.collections :as collections]
            [aoc.search :as search]))

(def input (->> "2016/day24.txt"
                (file/read-lines #(apply vector %))
                (geometry/map-grid-2d)
                (collections/remove-vals #{\#})))

(defn element-position
  [grid element]
  (some (fn [[k v]]
          (when (= element v) k))
        grid))

(defn neighbours
  [grid {:keys [position] :as state}]
  (->> (geometry/neighbours4 position)
       (filter grid)
       (map #(-> state
                 (update :visited conj (grid %))
                 (assoc :position %)))))

(defn heuristic
  [{:keys [visited]}]
  ;; #{\. \0 \1 \2 \3 \4 \5 \6 \7}
  (- 9 (count visited)))

(defn part01
  [input]
  (first (search/astar
           {:visited #{\0} :position (element-position input \0)}
           (partial neighbours input)
           heuristic
           (constantly 1))))

(defn heuristic-with-return
  [zero-pos {:keys [visited position]}]
  ;; #{\. \0 \1 \2 \3 \4 \5 \6 \7}
  (+ (- 9 (count visited))
     (geometry/manhattan-distance zero-pos position)))

(defn part02
  [input]
  (let [zero (element-position input \0)]
    (first (search/astar
             {:visited #{\0} :position zero}
             (partial neighbours input)
             (partial heuristic-with-return zero)
             (constantly 1)))))
