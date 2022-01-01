(ns aoc.2016.day23
  (:require [aoc.parse :as parse]
            [aoc.file :as file]))

;; Looking at input file
;; lines 20 and 21:
;;   cpy 73 c
;;   jnz 80 d
;;
;; Part 1:
;; 73 * 80 + 7! = 10880
;;
;; Part 2:
;; 73 * 80 + 12! = 479007440

(defn parse-line
  [line]
  (let [[_ & rest] (re-find #"([a-z]{3}) ([a-z]|-?\d+)\s*([a-z]|-?\d+)?" line)]
    (mapv #(or (parse/try->int %) %) rest)))

(defn parse-file
  [file]
  (->> file
       (file/read-lines parse-line)
       (map-indexed vector)
       (into {})))

(def input (parse-file "2016/day23.txt"))
(def example (parse-file "2016/day23-example.txt"))

(defn toggle
  [[op a b]]
  (case op
    "inc" ["dec" a]
    "dec" ["inc" a]
    "cpy" ["jnz" a b]
    "tgl" ["inc" a]
    "jnz" ["cpy" a b]))

(defn execute
  [input {:keys [ip] :as state}]
  (if (<= (count input) ip)
    [input (assoc state :stopped true)]
    (let [[op a b] (get input ip)]
      (case op
        "tgl" (let [ip' (+ ip (get state a))]
                (if (and (<= 0 ip') (< ip' (count input)))
                  [(assoc input ip' (toggle (get input ip')))
                   (update state :ip inc)]
                  [input (update state :ip inc)]))
        "cpy" [input
               (-> state
                   (assoc b (get state a a))
                   (update :ip inc))]
        "inc" [input
               (-> state
                   (update a inc)
                   (update :ip inc))]
        "dec" [input
               (-> state
                   (update a dec)
                   (update :ip inc))]
        "jnz" [input
               (update state :ip #(if (zero? (get state a a))
                                    (inc %)
                                    (+ (if (number? b) b (get state b)) %)))]))))

(defn execute-all
  [input state]
  (let [[next-input next-state] (execute input state)]
    (if (:stopped next-state)
      state
      (recur next-input next-state))))

(def computer (into {:ip 0} (map vector ["a" "b" "c" "d"] (repeat 0))))

(defn part01
  [input]
  (get (execute-all input (assoc computer "a" 7)) "a"))
