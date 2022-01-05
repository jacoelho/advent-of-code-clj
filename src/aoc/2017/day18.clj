(ns aoc.2017.day18
  (:require [aoc.file :as file]
            [aoc.parse :as parse])
  (:import (clojure.lang PersistentQueue)))

(defn parse-line
  [line]
  (let [[_ op a b] (re-find #"([a-z]{3}) ([a-z]+|-?\d+)\s*(-?\d+|[a-z]+)?" line)]
    [op (parse/try-or-v->int a) (parse/try-or-v->int b)]))

(def example (file/read-lines parse-line "2017/day18-example.txt"))
(def input (file/read-lines parse-line "2017/day18.txt"))

(defn op-snd
  [state a & _]
  (update state :sound (fnil conj []) (get state a 0)))

(defn op-snd-queue
  [state a & _]
  (-> state
      (update :sending (fnil conj []) (get state a 0))
      (update :sent (fnil inc 0))))

(defn op-set
  [state a b]
  (assoc state a (get state b b)))

(defn op-add
  [state a b]
  (update state a (fnil + 0) (get state b b)))

(defn op-mul
  [state a b]
  (update state a (fnil * 0) (get state b b)))

(defn op-mod
  [state a b]
  (update state a mod (get state b b)))

(defn op-rcv-stop
  [state a & _]
  (let [v (peek (get state :sound 0))]
    (if (zero? v)
      state
      (-> state (assoc a v) (assoc :status :stopped)))))

(defn op-rcv-queue
  [state a & _]
  (if-let [v (peek (get state :receive))]
    (-> state
        (assoc a v)
        (update :ip inc)
        (update :receive pop))
    (assoc state :status :waiting)))

(defn op-jgz
  [state a b]
  (let [x (get state a a)]
    (if (pos? x)
      (update state :ip + (get state b b))
      (update state :ip inc))))

(defn ip-inc
  [state]
  (update state :ip inc))

(def op-mapping
  {"jgz" op-jgz
   "snd" (comp ip-inc op-snd)
   "rcv" (comp ip-inc op-rcv-stop)
   "mod" (comp ip-inc op-mod)
   "mul" (comp ip-inc op-mul)
   "add" (comp ip-inc op-add)
   "set" (comp ip-inc op-set)})

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
       (:sound)
       (last)))

(defn try-restart-program
  [s name]
  (if (< 0 (count (get-in s [name :receive])))
    (update-in s [name :status] #(if (= % :waiting) :running %))
    s))

(defn copy-between
  [s a b]
  (-> (update-in s [b :receive] into (get-in s [a :sending]))
      (assoc-in [a :sending] [])))

(defn execute-two
  [op-mapping input]
  (loop [programs {:a {:ip 0 "p" 0 :status :running :receive (PersistentQueue/EMPTY)}
                   :b {:ip 0 "p" 1 :status :running :receive (PersistentQueue/EMPTY)}}
         current  :a]
    (let [other ({:a :b :b :a} current)]
      (cond
        (and (not= (get-in programs [:a :status]) :running)
             (not= (get-in programs [:b :status]) :running)) programs

        (= (get-in programs [current :status]) :waiting)
        (recur programs other)

        :else
        (recur
          (-> (assoc programs current (step op-mapping input (get programs current)))
              (copy-between current other)
              (try-restart-program other))
          current)))))

(defn part02
  [input]
  (get-in (execute-two
            (-> op-mapping
                (assoc "rcv" op-rcv-queue)
                (assoc "snd" (comp ip-inc op-snd-queue)))
                input)
            [:b :sent]))
