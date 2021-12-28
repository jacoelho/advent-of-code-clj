(ns aoc.2021.day24
  (:require [aoc.parse :as parse]
            [aoc.file :as file]))

(defn parse-line
  [line]
  (let [[_ op a b] (re-find #"^(\w{3}) (\w)\s*(-?\d+|\w)?$" line)]
    (if-let [v (parse/try->int b)]
      [op a v]
      [op a b])))

(def input
  (->> "2021/day24.txt"
       (file/read-lines parse-line)
       (partition 18)
       (mapv (fn [block]
               ;; magic constants positions deducted for inspecting the input
               (let [a (nth block 4)
                     b (nth block 5)
                     c (nth block 15)]
                 (mapv #(nth % 2) [a b c]))))))

(defn step [z input q a b]
  (if (= input (+ a (mod z 26)))
    (quot z q)
    (+ input b (* 26 (quot z q)))))

(defn solution
  [predicate input]
  (->
    (reduce
      (fn [result [q a b]]
        (->> (for [[z inputs] result
                   input (range 1 10)]
               [(step z input q a b) (+ input (* 10 inputs))])
             (reduce
               (fn [result [z inputs]]
                 (update result z (fnil predicate inputs) inputs))
               {})))
      {0 0}
      input)
    (get 0)))

(defn part01
  [input]
  (solution max input))

(defn part02
  [input]
  (solution min input))
