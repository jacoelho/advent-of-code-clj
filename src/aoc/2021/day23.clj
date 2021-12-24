(ns aoc.2021.day23
  (:require [aoc.geometry :as geometry]
            [aoc.collections :as collections]))

(def energy-required {\A 1 \B 10 \C 100 \D 1000})

(def amphipod-room {\A 2 \B 4 \C 6 \D 8})

(def state
  {[0 0] \. [1 0] \. [3 0] \. [5 0] \. [7 0] \. [9 0] \. [10 0] \.
   [2 0] \. [2 1] \D [2 2] \B
   [4 0] \. [4 1] \D [4 2] \A
   [6 0] \. [6 1] \C [6 2] \A
   [8 0] \. [8 1] \B [8 2] \C
   })

(defn success?
  [state]
  (and (every? #(= \A (get state %)) [[2 1] [2 2]])
       (every? #(= \B (get state %)) [[4 1] [4 2]])
       (every? #(= \C (get state %)) [[6 1] [6 2]])
       (every? #(= \D (get state %)) [[8 1] [8 2]])))

(defn moves'
  [[x y] [x' y']]
  (let [[x-min x-max] (if (< x x') [x x'] [x' x])
        y-max      (if (zero? y) y' y)
        vertical-x (if (< y y') x' x)]
    (->> (into (mapv vector (range x-min x-max) (repeat 0))
               (mapv vector (repeat vertical-x) (range 0 (inc y-max))))
         (remove #{[x y]}))))

(def moves (memoize moves'))

(defn move-allowed?
  [state [x' y' :as src] [x y :as dst]]
  (let [amphipod (get state src)]
    (cond (#{[2 0] [4 0] [6 0] [8 0]} dst) false
          (= y y' 0) false
          (= x' x) false
          (and (not= y 0) (not= (amphipod-room amphipod) x)) false
          (and (not= y 0) (= (get state [x (inc y)]) \.)) false
          (some #(not= (get state %) \.) (moves src dst)) false
          (and (= (amphipod-room amphipod) x')
               (every? #(= amphipod (get state %)) [[x' 1] [x' 2]])) false
          (and (= (amphipod-room amphipod) x')
               (= y' 2)) false
          :else true)))

(defn available-moves
  [{:keys [grid] :as state}]
  (let [amphipods (collections/remove-vals #{\.} grid)
        empty     (keys (collections/select-vals #{\.} grid))]
    (->> amphipods
         (reduce (fn [acc [pos amphipod]]
              (->> empty
                   (filter (partial move-allowed? grid pos))
                   (map (fn [dst]
                          (-> state
                              (update :energy (fnil + 0) (* (energy-required amphipod) (count (moves pos dst))))
                              (assoc-in [:grid pos] \.)
                              (assoc-in [:grid dst] amphipod))))
                   (into acc)))
            [])
         (remove (fn [{grid' :grid}]
                      (= grid grid'))))))


;; not 2819 (too low)
;; not 18119 (too high)

(def example {[0 0] \. [1 0] \. [3 0] \. [5 0] \. [7 0] \. [9 0] \. [10 0] \.
              [2 0] \. [2 1] \B [2 2] \A
              [4 0] \. [4 1] \C [4 2] \D
              [6 0] \. [6 1] \B [6 2] \C
              [8 0] \. [8 1] \D [8 2] \A
              })

(defn search
  [state]
  (loop [states  (available-moves state)
         best    Integer/MAX_VALUE
         visited {}]
    (if (empty? states)
      best
      (let [[{:keys [energy grid] :as state'} & xs] states]
        (cond
          (success? grid)
          (recur xs (min energy best) (assoc visited grid energy))

          (< best energy)
          (recur xs best visited)

          (< (get visited grid Integer/MAX_VALUE) energy)
          (recur xs best visited)

          :else (recur (reduce conj
                         ;(fn [acc v]
                         ;        (geometry/print-map-grid grid)
                         ;        (println "to")
                         ;        (geometry/print-map-grid (:grid v))
                         ;        (println "")
                         ;        (conj acc v))
                               xs (available-moves state')) best (assoc visited grid energy)))))))

(comment (search {:grid state}))

(seq (available-moves {:grid example}))

(def example' {[0 0] \B [1 0] \. [3 0] \. [5 0] \. [7 0] \. [9 0] \C [10 0] \D
               [2 0] \.  [2 1] \A [2 2] \A
               [4 0] \.  [4 1] \. [4 2] \B
               [6 0] \.  [6 1] \. [6 2] \C
               [8 0] \.  [8 1] \. [8 2] \D
               })

(def example'' {[0 0] \. [1 0] \B [3 0] \B [5 0] \. [7 0] \. [9 0] \. [10 0] \.
               [2 0] \.  [2 1] \. [2 2] \A
               [4 0] \.  [4 1] \. [4 2] \D
               [6 0] \.  [6 1] \C [6 2] \C
               [8 0] \.  [8 1] \D [8 2] \A
               })

(doseq [x (available-moves {:grid example''})]
  (geometry/print-map-grid (:grid x))
  (println ""))

(move-allowed? example' [2 1] [3 0])