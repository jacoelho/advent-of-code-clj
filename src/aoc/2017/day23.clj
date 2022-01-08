(ns aoc.2017.day23
  (:require [aoc.parse :as parse]
            [aoc.file :as file]
            [aoc.math :as math]))

(defn parse-line
  [line]
  (let [[_ op a b] (re-find #"([a-z]{3}) ([a-z]+|-?\d+)\s*(-?\d+|[a-z]+)?" line)]
    [op (parse/try-or-v->int a) (parse/try-or-v->int b)]))

(def input (file/read-lines parse-line "2017/day23.txt"))

(defn register-v
  [v]
  (if (number? v) v 0))

(defn op-set
  [state a b]
  (assoc state a (get state b (register-v b))))

(defn op-sub
  [state a b]
  (update state a (fnil - 0) (get state b (register-v b))))

(defn op-mul
  [state a b]
  (update state a (fnil * 0) (get state b (register-v b))))

(defn op-jnz
  [state a b]
  (let [x (get state a (register-v a))]
    (if (zero? x)
      (update state :ip inc)
      (update state :ip + (get state b (register-v b))))))

(defn ip-inc
  [state]
  (update state :ip inc))

(defn count-mul
  [state]
  (update state :mul (fnil inc 0)))

(def op-mapping
  {"set" (comp ip-inc op-set)
   "sub" (comp ip-inc op-sub)
   "mul" (comp ip-inc count-mul op-mul)
   "jnz" op-jnz})

(defn step
  [op-mapping instructions {:keys [ip] :as state}]
  (if-let [[op & rest] (get instructions ip)]
    (apply (op-mapping op) state rest)
    (assoc state :status :stopped)))

(defn step-seq
  [op-mapping instructions state]
  (lazy-seq
    (if (= (get state :status) :stopped)
      [state]
      (cons state
            (step-seq op-mapping instructions
                      (step op-mapping instructions state))))))

(defn part01
  [input]
  (->> (step-seq op-mapping input {:ip 0})
       (last)
       (:mul)))

; after checking reddit
; I mean the code:

; b:
; set b 67
; mul b 100
; sub b -100000

; c:
; set c b
; sub c -17000
(defn part02
  [input]
  (let [b (+ 100000 (* 100 (nth (first input) 2)))
        c (+ b 17000)]
    (->> (range b (inc c) 17)
         (remove math/prime?)
         (count))))
