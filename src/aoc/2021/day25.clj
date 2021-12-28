(ns aoc.2021.day25
  (:require [aoc.file :as file]
            [aoc.geometry :as geometry]))

(defn parse-file
  [file]
  (let [grid (->> file
                  (file/read-lines)
                  (geometry/map-grid-2d))
        [_ [x y]] (geometry/map-grid-corners grid)]
    (reduce-kv (fn [acc k v]
                 (case v
                   \> (update acc :right conj k)
                   \v (update acc :down conj k)
                   acc))
               {:max-x x
                :max-y y
                :down  #{}
                :right #{}}
               grid)))

(def example (parse-file "2021/day25-example.txt"))
(def input (parse-file "2021/day25.txt"))

(defn move
  [{:keys [max-x max-y]} direction [x y]]
  (case direction
    :right [(if (= x max-x) 0 (inc x)) y]
    :down [x (if (= y max-y) 0 (inc y))]))

(defn position-occupied?
  [{:keys [right down]} pos]
  (or (contains? right pos)
      (contains? down pos)))

(defn move-herd
  [state direction]
  (reduce (fn [acc position]
            (let [next-position (move state direction position)]
              (if (position-occupied? state next-position)
                acc
                (-> acc
                    (update direction conj next-position)
                    (update direction disj position)))))
          state
          (direction state)))

(defn turn
  [state]
  (reduce move-herd state [:right :down]))

(defn turn-seq
  [state]
  (let [next-state (turn state)]
    (lazy-seq (if (= state next-state)
                [state]
                (cons state (turn-seq next-state))))))

(defn part01
  [input]
  (->> input
       (turn-seq)
       (count)))