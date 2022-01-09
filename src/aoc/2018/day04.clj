(ns aoc.2018.day04
  (:require [aoc.file :as file]
            [clojure.string :as string]
            [aoc.parse :as parse]
            [aoc.collections :as collections]))

(defn parse-line
  [line]
  (let [[_ date hour minute operation guard] (re-find #"^\[(\d{4}\-\d{2}\-\d{2}) (\d{2}):(\d{2})\].*(falls|wakes|#(\d+))" line)
        time (mapv parse/string->int [hour minute])]
    (condp #(string/starts-with? %2 %1) operation
      "falls" [date :sleep time]
      "wakes" [date :wake time]
      [date :shift time (parse/string->int guard)])))

(defn parse-file
  [f]
  (->> (file/read-lines f)
       (sort)
       (map parse-line)))

(def input (parse-file "2018/day04.txt"))
(def example (parse-file "2018/day04-example.txt"))

(defn minutes-sleeping
  [[[_ _ [_ m]] [_ _ [_ m']]]]
  (range m m'))

(defn timecard
  [shifts]
  (lazy-seq
    (when (seq shifts)
      (let [[date op _ guard] (first shifts)]
        (if (not= op :shift)
          (throw (Exception. "expected shift"))
          (let [events (->> (next shifts)
                            (take-while (fn [[_ op]] (not= op :shift))))
                sleep  (->> events
                            (partition 2)
                            (mapcat minutes-sleeping))]
            (cons [guard date sleep]
                  (timecard (drop (inc (count events)) shifts)))))))))

(defn timecard-to-freq
  [card]
  (reduce (fn [m [guard _ sleep]]
            (update m guard (partial merge-with +) (frequencies sleep)))
          {}
          card))

(defn count-minutes
  [m]
  (collections/map-vals (comp (partial reduce +) vals) m))

(defn part01
  [input]
  (let [t      (timecard-to-freq (timecard input))
        guard  (key (apply max-key val (count-minutes t)))
        minute (key (apply max-key val (get t guard)))]
    (* guard minute)))

(defn most-frequent-minute-sleeping
  [m]
  (collections/map-vals #(apply max-key val %) m))

(defn part02
  [input]
  (->> (timecard-to-freq (timecard input))
       (into {} (remove (comp empty? second)))
       (most-frequent-minute-sleeping)
       (apply max-key (comp second val))
       ((fn [[guard [minute]]]
          (* guard minute)))))
