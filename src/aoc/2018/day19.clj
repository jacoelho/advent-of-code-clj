(ns aoc.2018.day19
  (:require [aoc.parse :as parse]
            [aoc.file :as file]
            [aoc.2018.day16 :as day16]))

(defn parse-line
  [line]
  (let [[_ op & xs] (re-find #"(\w+) (\d+) (\d+) (\d+)" line)
        values      (mapv parse/string->int xs)]
    [op (into [0] values)]))

(defn parse-file
  [f]
  (let [file'      (file/read-lines f)
        program    (->> (drop 1 file')
                        (mapv parse-line))
        ip-binding (->> (first file')
                        (re-find #"\d+")
                        (parse/string->int))]
    {:ip-binding ip-binding
     :program    program}))

(def input (parse-file "2018/day19.txt"))

(defn call
  [registers [instr & args]]
  (apply (day16/op-mapping instr) registers args))

(defn run-program
  [{:keys [ip-binding program]} registers]
  (loop [registers registers]
    (let [ip (nth registers ip-binding)]
      (if-let [next-instr (get program ip)]
        (recur (-> registers
                   (call next-instr)
                   (update ip-binding inc)))
        registers))))

(defn part01
  [input]
  (->> (run-program input [0 0 0 0 0 0])
       (first)))

(defn factors
  [n]
  (filter #(zero? (rem n %)) (range 1 (inc n))))

(defn part02
  [_]
  (let [line-23 6
        line-25 21]
    (->> (+ 10551236 (* line-23 22) line-25)
         (factors)
         (reduce +))))
