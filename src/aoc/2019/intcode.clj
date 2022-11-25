(ns aoc.2019.intcode
  (:require [aoc.parse :as parse]
            [aoc.file :as file]))

(def spy #(do (println "DEBUG:" %) %))

(defn parse-line
  [line]
  (->> (re-seq #"-?\d+" line)
       (mapv parse/string->int)))

(defn parse-input
  [f]
  (->> (file/read-lines parse-line f)
       (first)))

(defn parameter-mode
  [operand n]
  (case n
    0 (rem (quot operand 100) 10)
    1 (rem (quot operand 1000) 10)
    2 (rem (quot operand 10000) 10)))

(defn parameter-value
  [{:keys [memory ip]} operand n]
  (let [offset (+ ip 1 n)]
    (case (parameter-mode operand n)
      0 (nth memory (nth memory offset))
      1 (nth memory offset))))

(defn parameter
  [{:keys [memory ip]} n]
  (nth memory (+ ip 1 n)))

(defn save
  [state address value]
  (assoc-in state [:memory address] value))

(defn update-ip
  [state offset]
  (update state :ip + offset))

(defn step
  [{:keys [memory ip] :as state}]
  (let [instruction (get memory ip)
        opcode      (rem instruction 100)
        param-value (partial parameter-value state instruction)]
    (case opcode
      1 (-> state
            (spy)
            (update-ip 4)
            (save (parameter state 2) (+ (param-value 0) (param-value 1))))

      2 (-> state
            (update-ip 4)
            (save (parameter state 2) (* (param-value 0) (param-value 1))))

      3 (-> state
            (update-ip 2)
            (save (parameter state 0) (peek (:input state)))
            (update :input pop))

      4 (-> state
            (update-ip 2)
            (update :output conj (param-value 0)))

      5 (-> state
            (assoc :ip (if (zero? (param-value 0))
                         (+ ip 3)
                         (param-value 1))))

      6 (-> state
            (assoc :ip (if (zero? (param-value 0))
                         (param-value 1)
                         (+ ip 3))))

      7 (-> state
            (update-ip 4)
            (save (parameter state 2) (if (< (param-value 0) (param-value 1)) 1 0)))

      8 (-> state
            (update-ip 4)
            (save (parameter state 2) (if (= (param-value 0) (param-value 1)) 1 0)))

      nil)))

(defn until-halt
  [state]
  (->> (iterate step state)
       (take-while some?)
       (last)))

(defn initialize
  [memory]
  {:memory memory
   :input  []
   :output []
   :ip     0})
