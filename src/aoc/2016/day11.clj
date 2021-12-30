(ns aoc.2016.day11
  (:require [clojure.math.combinatorics :as combo]
            [clojure.set :as set]
            [aoc.search :as search]))

(def example
  {:elevator 0
   :floors   {0 #{[:chip "hydrogen"] [:chip "lithium"]}
              1 #{[:generator "hydrogen"]}
              2 #{[:generator "lithium"]}
              3 #{}}})

(def input
  {:elevator 0
   :floors   {0 #{[:generator "promethium"] [:chip "promethium"]}
              1 #{[:generator "cobalt"] [:generator "curium"] [:generator "ruthenium"] [:generator "plutonium"]}
              2 #{[:chip "cobalt"] [:chip "curium"] [:chip "ruthenium"] [:chip "plutonium"]}
              3 #{}}})

(defn chip? [[t]] (= t :chip))
(def valid-floor? #{0 1 2 3})

(defn arrangement-valid?
  [floor]
  (let [chips (filter chip? floor)]
    (or (= (count floor) (count chips))
        (every? (fn [[_ el]] (contains? floor [:generator el])) chips))))

(defn combinations
  "it can carry at most yourself and two RTGs or microchips in any combination"
  [floor]
  (let [floor (seq floor)]
    (into (combo/combinations floor 2)
          (combo/combinations floor 1))))

(defn move-elements
  [state from to elements]
  (-> state
      (assoc :elevator to)
      (update-in [:floors from] set/difference elements)
      (update-in [:floors to] set/union elements)))

(defn moves
  [{:keys [elevator floors] :as state}]
  (let [possible-floors (keep valid-floor? (map + [elevator elevator] [-1 1]))
        possible-moves  (map set (combinations (floors elevator)))]
    (for [to possible-floors
          m  possible-moves
          :let [state' (move-elements state elevator to m)]
          :when (and (arrangement-valid? (get-in state' [:floors elevator]))
                     (arrangement-valid? (get-in state' [:floors to])))]
      state')))

(defn div-by-2
  [num]
  (let [q (quot num 2)
        r (rem num 2)]
    (if (zero? r)
      q
      (+ 1 q))))

(defn heuristic
  [{:keys [floors]}]
  (->> floors
       (vals)
       (map count)
       (map * [3 2 1 0])
       (reduce +)
       (div-by-2)))

(defn part01
  [input]
  (first (search/astar input moves heuristic (constantly 1))))

;; 33
(comment (time (part01 input)))

(def input-part-2
  (update-in input [:floors 0] conj [:generator "elerium"] [:chip "elerium"][:generator "dilithium"] [:chip "dilithium"]))

;; 57
;; "Elapsed time: 844425.739294 msecs"
(comment (time (part01 input-part-2)))