(ns aoc.2015.day09
  (:require
   [aoc.file :as aoc]
   [clojure.test :refer [testing is]]))

(defrecord Pair [start end ^int distance])

(defn parse-line
  [line]
  (let [[_ start end d] (re-find #"^(\w+) to (\w+) = (\d+)$" line)
        distance (aoc/->int d)]
    [(->Pair start end distance) (->Pair end start distance)]))

(def day09-input
  (->>
   (aoc/read-lines parse-line "2015/day09.txt")
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

(testing "Part 01"
  (is (= 207 (part01 day09-input))))

(defn part02
  [input]
  (apply max (visit-all input)))

(testing "Part 02"
  (is (= 804 (part02 day09-input))))
