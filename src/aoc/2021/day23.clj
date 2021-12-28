(ns aoc.2021.day23
  (:require [aoc.geometry :as geometry]
            [aoc.collections :as collections]))

(def example
  {[0 0]  \.
   [1 0]  \.
   [2 0]  \. [2 1] \B [2 2] \A
   [3 0]  \.
   [4 0]  \. [4 1] \C [4 2] \D
   [5 0]  \.
   [6 0]  \. [6 1] \B [6 2] \C
   [7 0]  \.
   [8 0]  \. [8 1] \D [8 2] \A
   [9 0]  \.
   [10 0] \.})

(def input
  {[0 0]  \.
   [1 0]  \.
   [2 0]  \. [2 1] \D [2 2] \B
   [3 0]  \.
   [4 0]  \. [4 1] \D [4 2] \A
   [5 0]  \.
   [6 0]  \. [6 1] \C [6 2] \A
   [7 0]  \.
   [8 0]  \. [8 1] \B [8 2] \C
   [9 0]  \.
   [10 0] \.})

(def amphipod-room {\A 2 \B 4 \C 6 \D 8})

(defn amphipod-move-cost
  [amphipod moves]
  (* ({\A 1 \B 10 \C 100 \D 1000} amphipod) moves))

(defn path-between
  [[x y] [x' y']]
  (let [[x-min x-max] (if (< x x') [x x'] [x' x])
        y-max (if (zero? y') 0 (inc y'))]
    (-> (concat (mapv vector (repeat x) (range 0 (inc y)))
                (mapv vector (repeat x') (range 0 y-max))
                (mapv vector (range x-min (inc x-max)) (repeat 0)))
        (set)
        (disj [x y])
        (sort))))

(defn paths
  [input]
  (->> (for [src (keys input)
             dst (keys input)
             :when (not= src dst)]
         [src dst (path-between src dst)])
       (reduce (fn [acc [src dst p]]
                 (assoc-in acc [src dst] p))
               {})))

(defn get-room
  [state room]
  (->> (map vector (repeat room) (iterate inc 1))
       (map state)
       (take-while (completing nil?))))

(defn success?
  [{:keys [grid]}]
  (and (every? #(= \A %) (get-room grid 2))
       (every? #(= \B %) (get-room grid 4))
       (every? #(= \C %) (get-room grid 6))
       (every? #(= \D %) (get-room grid 8))))

(defn outside-room?
  [destination]
  (#{[2 0] [4 0] [6 0] [8 0]} destination))

(defn hallway-move?
  [[_ y] [_ y']]
  (= y y' 0))

(defn room-move?
  [[x _] [x' _]]
  (= x x'))

(defn path-occupied?
  [paths state src dst]
  (some #(not= (get state %) \.) (get-in paths [src dst])))

(defn destination-room?
  [state src [x y]]
  (let [amphipod (get state src)
        room     (amphipod-room amphipod)]
    #_(println [state src [x y]])
    (if (= y 0)
      true
      (and (= x room)
           (->> room
                (get-room state)
                (map-indexed (fn [idx item] [(inc idx) item]))
                (filter (fn [[idx]] (< y idx)))
                (every? (fn [[_ v]] (= v amphipod))))))))

(defn in-correct-room?
  [state [x _ :as src] _]
  (let [amphipod    (get state src)
        room-number (amphipod-room amphipod)]
    (and (= x room-number)
         (every? #(= amphipod %) (get-room state room-number)))))

(defn move-allowed?
  [paths state src dst]
  (cond (outside-room? dst) false
        (hallway-move? src dst) false
        (room-move? src dst) false
        (path-occupied? paths state src dst) false
        (in-correct-room? state src dst) false
        (not (destination-room? state src dst)) false
        :else true))

(defn available-moves
  [paths {:keys [grid] :as state}]
  (let [amphipods (collections/remove-vals #{\.} grid)
        empty     (keys (collections/select-vals #{\.} grid))]
    (reduce (fn [acc [pos amphipod]]
              (->> empty
                   (filter (partial move-allowed? paths grid pos))
                   (map (fn [dst]
                          (-> state
                              (update :energy (fnil + 0) (amphipod-move-cost amphipod (count (get-in paths [pos dst]))))
                              (assoc-in [:grid pos] \.)
                              (assoc-in [:grid dst] amphipod))))
                   (into acc)))
            []
            amphipods)))

(defn solve
  [paths visited best {:keys [grid energy] :as state}]
  (cond
    (success? state)
    (do
      (swap! best min energy)
      energy)

    (<= @best energy)
    Long/MAX_VALUE

    (<= (@visited grid Integer/MAX_VALUE) energy)
    Integer/MAX_VALUE

    :else
    (do
      (swap! visited assoc grid energy)
      (transduce
        (map #(solve paths visited best %))
        min
        Integer/MAX_VALUE
        (available-moves paths state)))))

(defn part01
  [input]
  (solve (paths input) (atom {}) (atom Integer/MAX_VALUE) {:grid input :energy 0}))

(def input-part2
  {[0 0]  \.
   [1 0]  \.
   [2 0]  \. [2 1] \D [2 2] \D [2 3] \D [2 4] \B
   [3 0]  \.
   [4 0]  \. [4 1] \D [4 2] \C [4 3] \B [4 4] \A
   [5 0]  \.
   [6 0]  \. [6 1] \C [6 2] \B [6 3] \A [6 4] \A
   [7 0]  \.
   [8 0]  \. [8 1] \B [8 2] \A [8 3] \C [8 4] \C
   [9 0]  \.
   [10 0] \.})
