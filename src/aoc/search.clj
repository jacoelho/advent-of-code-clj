(ns aoc.search
  (:require [clojure.data.priority-map :as pm]))

(defn astar
  "Finds a [cost, path] for the lowest-cost path from start to a goal.
  A goal is any state such that heuristic returns zero."
  [start neighbours heuristic step-cost]
  (loop [q         (pm/priority-map start (heuristic start))
         previous  {}
         path-cost {start 0}]
    (when-let [[current _] (peek q)]
      (if (zero? (heuristic current))
        [(path-cost current) (reverse (take-while identity (iterate previous current)))]
        (let [neighbours-costs (into [] (comp (map (fn [s] [s (+ (path-cost current) (step-cost current s))]))
                                              (filter (fn [[s cost]]
                                                        (or (not (contains? path-cost s))
                                                            (< cost (path-cost s))))))
                                     (neighbours current))]
          (recur (into (pop q) (map (fn [[s cost]] [s (+ cost (heuristic s))])) neighbours-costs)
                 (into previous (map (fn [[s]] [s current])) neighbours-costs)
                 (into path-cost neighbours-costs)))))))

(defn bfs
  [start neighbours goal?]
  (astar start
         neighbours
         (fn [el] (if (goal? el) 0 1))
         (constantly 1)))