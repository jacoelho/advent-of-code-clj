(ns aoc.2016.day12
  (:require [aoc.file :as file]
            [aoc.parse :as parse]))

(defn parse-line
  [line]
  (let [[_ op a b] (re-find #"([a-z]{3}) ([a-z]|-?\d+)\s*([a-z]|-?\d+)?" line)]
    [op a b]))

(defn operand
  [v]
  (if-let [v' (parse/try->int v)]
    [:digit v']
    [:register v]))

(defn parse-instruction
  [[op a b]]
  (let [[t v] (operand a)]
    (case [op t]
      ["cpy" :digit] [:cpy-digit v b]
      ["cpy" :register] [:cpy-register v b]
      ["inc" :register] [:inc v]
      ["dec" :register] [:dec v]
      ["jnz" :digit] [:jnz-digit v (parse/->int b)]
      ["jnz" :register] [:jnz-register v (parse/->int b)])))

(def input (file/read-lines (comp parse-instruction parse-line) "2016/day12.txt"))

(defn execute
  [input {:keys [ip] :as state}]
  (if (= ip (count input))
    (assoc state :stopped true)
    (let [[op a b] (get input ip)]
      (case op
        :cpy-register (-> state
                          (assoc b (get state a 0))
                          (update :ip inc))
        :cpy-digit (-> state
                       (assoc b (get state a a))
                       (update :ip inc))
        :inc (-> state
                 (update a (fnil inc 0))
                 (update :ip inc))
        :dec (-> state
                 (update a (fnil dec 0))
                 (update :ip inc))
        :jnz-register (update state :ip #(if (zero? (get state a 0))
                                           (inc %)
                                           (+ b %)))
        :jnz-digit (update state :ip #(if (zero? a)
                                        (inc %)
                                        (+ b %)))))))

(defn execute-all
  [input state]
  (let [next-state (execute input state)]
    (if (:stopped next-state)
      state
      (recur input next-state))))

;; 318077
(execute-all input {:ip 0})

;; 9227731
(execute-all input {:ip 0 "c" 1})