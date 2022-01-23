(ns aoc.2018.day15
  (:require [aoc.file :as file]
            [aoc.geometry :as geometry]
            [aoc.collections :as collections]
            [clojure.string :as str])
  (:import (clojure.lang PersistentQueue)))

(defn unit
  [ch]
  {:hp 200 :atk 3 :type ch})

(def enemy? {\E \G \G \E})

(defn parse-file
  [f]
  (let [grid  (->> (file/read-lines f)
                   (geometry/map-grid-2d)
                   (collections/remove-vals #{\space}))
        units (->> (collections/remove-vals #{\. \#} grid)
                   (collections/map-vals unit))]
    {:grid  grid
     :units units}))

(def input (parse-file "2018/day15.txt"))

(def reading-order (juxt second first))

(defn neighbours-reading-order
  [point]
  (mapv #(mapv + point %) [[0 -1] [-1 0] [1 0] [0 1]]))

(defn select-target
  [{:keys [units]} pos]
  (let [enemy-type       (enemy? (get-in units [pos :type]))
        enemies-adjacent (->> (neighbours-reading-order pos)
                              (filter #(when-let [v (units %)]
                                         (= (:type v) enemy-type))))]
    (when (seq enemies-adjacent)
      (let [min-hp (->> (map units enemies-adjacent)
                        (apply min-key :hp)
                        (:hp))]
        (->> (map (juxt identity units) enemies-adjacent)
             (filter #(= min-hp (:hp (second %))))
             (sort-by (comp reading-order first))
             (ffirst))))))

(defn move
  [{:keys [grid units] :as state} from to]
  (if to
    (-> state
        (assoc-in [:grid from] \.)
        (assoc-in [:grid to] (grid from))
        (update :units dissoc from)
        (assoc-in [:units to] (units from)))
    state))

(defn neighbours
  [{:keys [grid]} want pos]
  (->> (neighbours-reading-order pos)
       (filter #(#{\. want} (grid %)))))

(defn bfs-reading-order
  [start neighbours goal?]
  (loop [q        (conj (PersistentQueue/EMPTY) start)
         previous {}
         visited  #{start}
         found    #{}]
    (if-let [current (peek q)]
      (if (goal? current)
        (recur (pop q)
               previous
               visited
               (conj found current))
        (let [n (->> current
                     (neighbours)
                     (remove visited)
                     (sort-by reading-order))]
          (recur (into (pop q) n)
                 (into previous (map (fn [s] [s current])) n)
                 (into visited n)
                 found)))
      (->> found
           (map #(reverse (take-while identity (iterate previous %))))
           (sort-by count)))))

(defn best-move
  [{:keys [units] :as state} pos]
  (let [enemy (enemy? (get-in units [pos :type]))
        found (bfs-reading-order pos (partial neighbours state enemy)
                                 #(= enemy (get-in state [:units % :type])))]
    (when (seq found)
      ;1. "To move, the unit first considers the squares that are in range and determines which of those squares it could reach in the fewest steps"
      ;2. "If multiple squares are in range and tied for being reachable in the fewest steps, the square which is first in reading order is chosen. "
      ;3. "If multiple steps would put the unit equally closer to its destination, the unit chooses the step which is first in reading order."
      (let [distance (count (first found))
            found    (filter #(= (count %) distance) found)
            goal     (last (first (sort-by (comp reading-order last) found)))
            found    (filter #(= (last %) goal) found)]
        (-> (sort-by (comp reading-order second) found)
            (first)
            (second))))))

(defn deal-damage
  [{:keys [units] :as state} source target]
  (if target
    (let [units' (update-in units [target :hp] - (get-in units [source :atk]))]
      (if (pos? (get-in units' [target :hp]))
        (assoc state :units units')
        (-> state
            (assoc-in [:grid target] \.)
            (update :units dissoc target))))
    state))

(defn unit-turn
  [state pos]
  (if ((:turns state) pos)
    state
    (if-let [target (select-target state pos)]
      (-> state
          (deal-damage pos target)
          (update :turns conj pos))
      (let [new-pos (best-move state pos)
            state'  (move state pos new-pos)
            target  (select-target state' new-pos)]
        (-> (deal-damage state' new-pos target)
            (update :turns conj new-pos))))))

(defn enemy-alive?
  [{:keys [units]}]
  (->> (vals units)
       (map :type)
       (apply =)
       (not)))

(defn score
  [{:keys [units turn]}]
  (->> (vals units)
       (map :hp)
       (reduce +)
       (* turn)))

(defn turn
  [state]
  (let [units    (->> (:units state)
                      (keys)
                      (sort-by reading-order)
                      (map-indexed vector))
        last-idx (dec (count units))
        state'   (reduce (fn [state [idx unit]]
                           (let [result (unit-turn state unit)]
                             (if (or (enemy-alive? result)
                                     (= idx last-idx))
                               result
                               (reduced (assoc result :early-exit true)))))
                         (assoc state :turns #{})
                         units)]
    (if (:early-exit state')
      state'
      (update state' :turn (fnil inc 0)))))

(defn part01
  [input]
  (->> (iterate turn input)
       (drop-while enemy-alive?)
       (first)
       (score)))

(defn print-grid
  [{:keys [grid units turn]}]
  (let [[[x-min y-min] [x-max y-max]] (geometry/map-grid-corners grid)]
    (println turn)
    (doseq [h (->> (range x-min (inc x-max))
                   (map #(format "%02d" %))
                   (apply mapv vector)
                   (map (partial apply str)))]
      (println "  " h))
    (doseq [line (for [y (range y-min (inc y-max))]
                   (let [[line extra] (reduce (fn [[line extra] x']
                                                (let [line (conj line (grid [x' y]))]
                                                  (if-let [u (units [x' y])]
                                                    [line (conj extra (str (:type u) \( (:hp u) \)))]
                                                    [line extra])))
                                              [[] []]
                                              (range x-min (inc x-max)))]
                     (str (format "%02d" y) " " (apply str line) "     " (str/join " " extra))))]
      (println line))))

(defn set-elves-attack
  [{:keys [units] :as state} atk]
  (assoc state :units
               (collections/map-vals (fn [{:keys [type] :as unit}]
                                       (if (= \G type)
                                         unit
                                         (assoc unit :atk atk))) units)))

(defn count-units
  [{:keys [units]} sym]
  (count (filter #(= (:type %) sym) (vals units))))

(defn part02
  [input]
  (let [number-elves (count-units input \E)]
    (->> (iterate inc 4)
         (map (partial set-elves-attack input))
         (some (fn [scenario]
                 (let [state (->> (iterate turn scenario)
                                  (drop-while enemy-alive?)
                                  (first))]
                   (when (= number-elves (count-units state \E))
                     (score state))))))))
