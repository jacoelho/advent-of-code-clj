(ns aoc.2018.day22
  (:require [aoc.collections :as collections]
            [aoc.geometry :as geometry]
            [aoc.search :as search]))

(def input {:depth  11991
            :target [6 797]})

(defn erosion
  [depth x y]
  (mod (+ depth (* x y)) 20183))

(defn erosion-level
  [cave depth target [x y :as point]]
  (apply erosion depth
         (cond (or (= [0 0] point)
                   (= target point)) [0 0]
               (zero? x) [y 48271]
               (zero? y) [x 16807]
               :else [(cave [(dec x) y])
                      (cave [x (dec y)])])))

(defn erosion-levels
  [depth [x y :as target]]
  (persistent!
   (reduce (fn [cave coords]
             (assoc! cave coords (erosion-level cave depth target coords)))
           (transient {})
           (for [x (range (inc x))
                 y (range (inc y))]
             [x y]))))

(defn region-types
  [m]
  (collections/map-vals #(mod % 3) m))

(defn part01
  [{:keys [depth target]}]
  (->> (erosion-levels depth target)
       (region-types)
       (vals)
       (reduce +)))

(defn valid-gear
  [m]
  (persistent! (reduce-kv
                (fn [res point level]
                  (assoc! res point (case level
                                      0 #{:climbing :torch}    ;; rocky
                                      1 #{:climbing :neither}  ;; wet 
                                      2 #{:torch :neither})))  ;; narrow
                (transient (empty m))
                m)))

(defn neighbours
  [m [position gear]]
  (let [suitable-gear (m position)
        switch-gear   (first (disj (m position) gear))
        neighbours'   (->> (geometry/neighbours4 position)
                           (map (fn [v] [v gear])))]
    (->> (conj neighbours' [position switch-gear])
         (filter (fn [[position gear]]
                   (when-let [required-gear (m position)]
                     (and (suitable-gear gear)
                          (required-gear gear))))))))

(defn step-cost
  [[a] [b]]
  (if (= a b) 7 1))

(defn part02
  [{:keys [depth target]}]
  (let [m (->> (erosion-levels depth (mapv * target [20 20]))
               (region-types)
               (valid-gear))]
    (first (search/astar
     [[0 0] :torch]
     (partial neighbours m)
     (fn [[position]]
       (geometry/manhattan-distance target position))
     step-cost))))