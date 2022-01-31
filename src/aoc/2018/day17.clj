(ns aoc.2018.day17
  (:require [aoc.parse :as parse]
            [aoc.file :as file]
            [aoc.collections :as collections]))

(defn parse-line
  [line]
  (let [[_ axis & xs] (re-find #"(x|y)=(\d+), [x|y]=(\d+)\.\.(\d+)" line)
        [a b c] (map parse/string->int xs)
        position (if (= axis "x") #(vector a %) #(vector % a))]
    (map #(vector (position %) \#) (range b (inc c)))))

(defn parse-file
  [f]
  (->> (file/read-lines parse-line f)
       (apply concat)
       (into {})))

(def example (parse-file "2018/day17-example.txt"))
(def input (parse-file "2018/day17.txt"))

(defn left
  [[x y]]
  [(dec x) y])

(defn right
  [[x y]]
  [(inc x) y])

(defn down
  [[x y]]
  [x (inc y)])

(def clay? #{\#})
(def clay-or-rest? #{\# \~})
(def clay-or-water? #{\# \~ \|})
(def sand? (complement clay-or-water?))

(defn holds?
  [grid direction position]
  (and (clay-or-rest? (grid (down position)))
       (or (clay? (grid (direction position)))
           (holds? grid direction (direction position)))))

(defn fill
  [grid direction position]
  (-> (cond-> grid
        (not (clay? (grid (direction position))))
        (fill direction (direction position)))
      (assoc position \~)))

(declare flow-step)

(defn spill
  [grid max-y direction position]
  (if (and (sand? (grid (direction position)))
           (clay-or-rest? (grid (down position))))
    (flow-step grid max-y (direction position))
    grid))

;; refactored on solution https://github.com/mfikes/advent-of-code/blob/master/src/advent_2018/day_17.cljc
(defn flow-step
  [grid max-y [_ y :as position]]
  (let [grid (assoc grid position \|)]
    (cond
      (= y max-y) grid

      (sand? (grid (down position)))
      (let [grid (flow-step grid max-y (down position))]
        (recur grid max-y position))

      (and (holds? grid left position)
           (holds? grid right position)) (-> grid
                                             (fill left position)
                                             (fill right position))

      :else (-> grid
                (spill max-y left position)
                (spill max-y right position)))))

(defn flow
  [input]
  (let [[min-y max-y] (->> (keys input)
                           (map second)
                           (apply (juxt min max)))]
    (flow-step input max-y [500 min-y])))

(defn part01
  [input]
  (->> (flow input)
       (collections/count-by (fn [[_ v]] (#{\~ \|} v)))))

(defn part02
  [input]
  (->> (flow input)
       (collections/count-by (fn [[_ v]] (#{\~} v)))))
