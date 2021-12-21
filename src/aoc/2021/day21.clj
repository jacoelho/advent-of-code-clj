(ns aoc.2021.day21
  (:require [aoc.collections :as collections]))

(def input
  [{:name :p1 :position 5}
   {:name :p2 :position 10}])

(defn mod-1
  [^Long base ^Long n]
  (-> (dec n)
      (mod base)
      (inc)))

(defn play-with-deterministic-dice
  [input]
  (loop [[current another] input
         turns 1
         [roll & dice] (->> (cycle (range 1 101))
                            (partition 3)
                            (map #(reduce + %)))]
    (let [new-position (mod-1 10 (+ (:position current) roll))
          current'     (-> current
                           (assoc :position new-position)
                           (update :score (fnil + 0) new-position))]
      (if (<= 1000 (:score current'))
        (* (:score another) 3 turns)
        (recur [another current']
               (inc turns)
               dice)))))

(defn part01
  [input]
  (play-with-deterministic-dice input))

(def dice->freq
  {3 1,
   4 3,
   5 6,
   6 7,
   7 6,
   8 3,
   9 1})

(def play-with-dirac-dice
  (memoize
    (fn [[current another]]
      (->> (for [[steps freq] dice->freq]
             (let [position' (mod-1 10 (+ (current :position) steps))
                   current'  (-> current
                                 (assoc :position position')
                                 (update :score (fnil + 0) position'))]
               (if (<= 21 (:score current'))
                 {(:name current) freq}
                 (->> (play-with-dirac-dice [another current'])
                      (collections/map-vals #(* % freq))))))
           (apply merge-with +)))))

(defn part02
  [input]
  (->> (play-with-dirac-dice input)
       vals
       (reduce max)))