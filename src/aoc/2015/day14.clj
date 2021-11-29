(ns aoc.2015.day14
  (:require
   [aoc.file :as file]
   [clojure.test :refer [testing is]]))

(defn parse-line
  [line]
  (let [[_ reindeer speed resistence rest] (re-find #"^(\w+) can fly (\d+) km/s for (\d+) seconds, but then must rest for (\d+) seconds\.$" line)]
    {:reindeer   reindeer
     :kms        (file/->int speed)
     :resistence (file/->int resistence)
     :rest       (file/->int rest)}))

(def input
  (file/read-lines parse-line "2015/day14.txt"))

(def comet
  (parse-line "Comet can fly 14 km/s for 10 seconds, but then must rest for 127 seconds."))

(def dancer
  (parse-line "Dancer can fly 16 km/s for 11 seconds, but then must rest for 162 seconds."))

(defn race
  [race-time {kms :kms resistence :resistence rest-time :rest}]
  (let [cycle-time (+ rest-time resistence)
        steps (quot race-time cycle-time)
        final-cycle (min resistence (rem race-time cycle-time))]
    (+ (* final-cycle kms)
       (* steps (* kms resistence)))))

(defn part01
  [input time]
  (apply max (map (partial race time) input)))

(testing "Part 01"
  (is (= 2660 (part01 input 2503))))

(defn race-tick-winner
  [input race-time]
  (->> input
       (map (partial race race-time))
       (map-indexed vector)
       (apply max-key second)
       (first)))

(defn part02
  [input time]
  (->> (range 1 time)
       (map (partial race-tick-winner input))
       (frequencies)
       (vals)
       (apply max)))

(testing "Part 02"
  (is (= 1256 (part02 input 2503))))
