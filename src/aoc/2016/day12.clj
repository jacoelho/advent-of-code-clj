(ns aoc.2016.day12
  (:require [aoc.file :as file]
            [aoc.parse :as parse]))

(defn parse-line
  [line]
  (let [[_ & rest] (re-find #"([a-z]{3}) ([a-z]|-?\d+)\s*([a-z]|-?\d+)?" line)]
    (mapv #(or (parse/try->int %) %) rest)))

(def input (file/read-lines parse-line "2016/day12.txt"))

(defn execute
  [input {:keys [ip] :as state}]
  (if (= ip (count input))
    (assoc state :stopped true)
    (let [[op a b] (get input ip)]
      (case op
        "cpy" (-> state
                  (assoc b (get state a a))
                  (update :ip inc))
        "inc" (-> state
                  (update a inc)
                  (update :ip inc))
        "dec" (-> state
                  (update a dec)
                  (update :ip inc))
        "jnz" (update state :ip #(if (zero? (get state a a))
                                   (inc %)
                                   (+ b %)))))))

(defn execute-all
  [input state]
  (let [next-state (execute input state)]
    (if (:stopped next-state)
      state
      (recur input next-state))))

(def computer (into {:ip 0} (map vector ["a" "b" "c" "d"] (repeat 0))))

(defn part01
  [input]
  (get (execute-all input computer) "a"))

(defn part02
  [input]
  (get (execute-all input (assoc computer "c" 1)) "a"))