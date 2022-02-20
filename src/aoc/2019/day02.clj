(ns aoc.2019.day02
  (:require [aoc.file :as file]
            [aoc.parse :as parse]
            [clojure.math.combinatorics :as combo]))

(defn parse-line
  [line]
  (->> (re-seq #"\d+" line)
       (mapv parse/string->int)))

(def input (->> (file/read-lines parse-line "2019/day02.txt")
                (first)))

(def operators {1  +
                2  *
                99 nil})

(defn fetch
  [memory ip n]
  (subvec memory ip (+ ip n 1)))

(defn step
  [{:keys [memory ip] :as state}]
  (if-let [op (operators (memory ip))]
    (let [[param-1 param-2 result] (fetch memory (inc ip) 3)]
      (-> state
          (assoc-in [:memory result] (op (memory param-1)
                                         (memory param-2)))
          (update :ip + 4)))
    (assoc state :stopped true)))

(defn program-seq
  [program]
  (let [next-state (step program)]
    (lazy-seq (if (:stopped next-state)
                [program]
                (cons program (program-seq next-state))))))

(defn setup
  [program noun verb]
  (-> {:memory program :ip 0}
      (assoc-in [:memory 1] noun)
      (assoc-in [:memory 2] verb)))

(defn run-until-halt
  [program]
  (-> (program-seq program)
      (last)))

(defn part01
  [input]
  (-> (setup input 12 2)
      (run-until-halt)
      (get-in [:memory 0])))

(defn part02
  [input]
  (->> (combo/cartesian-product (range 0 100) (range 0 100))
       (some (fn [[noun verb]]
               (let [result (-> (setup input noun verb)
                                (run-until-halt)
                                (get-in [:memory 0]))]
                 (when (= result 19690720)
                   (+ verb (* 100 noun))))))))
