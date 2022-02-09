(ns aoc.2018.day22
  (:require [aoc.collections :as collections]))

(def input {:depth  11991
            :target [6 797]})

(defn erosion-fn
  [depth]
  (fn [x y]
    (mod (+ depth (* x y)) 20183)))

(defn erosion-level
  [cave erosion-fn target [x y :as point]]
  (apply erosion-fn
         (cond (or (= [0 0] point)
                   (= target point)) [0 0]
               (zero? x) [y 48271]
               (zero? y) [x 16807]
               :else [(cave [(dec x) y])
                      (cave [x (dec y)])])))

(defn erosion-levels
  [depth [x y :as target]]
  (let [erosion (erosion-fn depth)]
    (persistent!
     (reduce (fn [cave coords]
               (assoc! cave coords (erosion-level cave erosion target coords)))
             (transient {})
             (for [x (range (inc x))
                   y (range (inc y))]
               [x y])))))

(defn part01
  [{:keys [depth target]}]
  (->> (erosion-levels depth target)
       (collections/map-vals #(mod % 3))
       (vals)
       (reduce +)))

;; 5622
(comment (part01 input))

(defn valid-gear
  [m]
  (reduce-kv
   (fn [res point level]
     (assoc res point (case (mod level 3)
                        0 #{:climbing :torch}    ;; rocky
                        1 #{:climbing :neither}  ;; wet 
                        2 #{:torch :neither})))  ;; narrow
   {}
   m))

(valid-gear (erosion-levels 100 [10 10]))