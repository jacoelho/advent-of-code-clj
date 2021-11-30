(ns aoc.2015.day09
  (:require
   [aoc.file :as file]
   [aoc.parse :as parse]))

(defrecord Pair [start end ^int distance])

(defn parse-line
  [line]
  (let [[_ start end d] (re-find #"^(\w+) to (\w+) = (\d+)$" line)
        distance (parse/->int d)]
    [(->Pair start end distance) (->Pair end start distance)]))

(def input
  (->>
   (file/read-lines parse-line "2015/day09.txt")
   (apply concat)
   (group-by :start)))

(defn destinations-available
  [visited cur locations]
  (filter #(nil? (visited (:end %))) (get locations cur)))

(defn visit
  [locations visited distance current-location]
  (let [destinations (destinations-available visited current-location locations)]
    (if (empty? destinations)
      distance
      (map #(visit locations
                   (conj visited current-location)
                   (+ distance (:distance %))
                   (:end %))
           destinations))))

(defn visit-all
  [locations]
  (let [start (keys locations)]
    (flatten (map #(visit locations #{} 0 %) start))))

(defn part01
  [input]
  (apply min (visit-all input)))

(defn part02
  [input]
  (apply max (visit-all input)))