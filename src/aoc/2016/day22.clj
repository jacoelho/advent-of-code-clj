(ns aoc.2016.day22
  (:require [aoc.parse :as parse]
            [aoc.file :as file]
            [clojure.math.combinatorics :as comb]
            [aoc.geometry :as geometry]
            [aoc.search :as search]))

(defn parse-line
  [line]
  (let [[_ & rest] (re-find #"/dev/grid/node-x(\d+)-y(\d+)\s*(\d+)T\s*(\d+)T\s*(\d+)T" line)
        [x y size used available] (mapv parse/string->int rest)]
    (when x
      [[x y] {:size      size
              :used      used
              :available available}])))

(def input (remove nil? (file/read-lines parse-line "2016/day22.txt")))
(def example (remove nil? (file/read-lines parse-line "2016/day22-example.txt")))

(defn viable?
  [[disk {:keys [used]}] [disk' {:keys [available]}]]
  (and (not= disk disk')
       (not (zero? used))
       (<= used available)))

(defn part01
  [input]
  (->> (comb/cartesian-product input input)
       (filter (partial apply viable?))
       (count)))

(defn max-x
  [grid]
  (reduce (fn [acc [[x y]]]
            (if (zero? y)
              (max x acc)
              acc))
          0 grid))

(defn empty-node
  [grid]
  (some (fn [[pos {:keys [used]}]]
          (when (zero? used)
            pos)) grid))

(defn neighbours
  [grid {:keys [data empty]}]
  (let [{:keys [size]} (get grid empty)
        n (geometry/neighbours4 empty)]
    (->> n
         (map (fn [pos]
                (when-let [node (get grid pos)]
                  (when (<= (:used node) size)
                    {:data  (if (= pos data) empty data)
                     :empty pos}))))
         (remove nil?))))

(defn part02
  [input]
  (let [grid (into {} input)]
    (first (search/astar
             {:data [(max-x grid) 0] :empty (empty-node grid)}
             (partial neighbours grid)
             (fn [{:keys [data]}] (geometry/manhattan-distance [0 0] data))
             (constantly 1)))))
