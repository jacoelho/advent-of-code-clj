(ns aoc.2017.day16
  (:require [aoc.file :as file]
            [clojure.string :as string]
            [aoc.parse :as parse]
            [aoc.collections :as collections]))

(def programs ["a" "b" "c" "d" "e" "f" "g" "h" "i" "j" "k" "l" "m" "n" "o" "p"])

(defn int-or-string
  [s]
  (if-let [v (parse/try->int s)] v s))

(def input (->> "2017/day16.txt"
                (file/read-lines #(string/split % #","))
                (first)
                (mapv (fn [el]
                        (let [[_ move a b] (re-find #"([sxp])(\w+)(?:\/(\w+))?" el)]
                          [move (int-or-string a) (int-or-string b)])))))

(defn spin
  [coll n]
  (vec (collections/rotate (- n) coll)))

(defn swap
  [coll a b]
  (-> coll
      (assoc b (get coll a))
      (assoc a (get coll b))))

(defn positions
  [coll a b]
  (let [want #{a b}]
    (keep-indexed #(when (want %2) %) coll)))

(defn dance
  [state [move a b]]
  (case move
    "s" (spin state a)
    "x" (swap state a b)
    "p" (apply swap state (positions state a b))))

(defn part01
  [input]
  (->> (reduce dance programs input)
       (apply str)))

(defn indexed-cycle
  [pred coll]
  (filter (fn [[_ v :as x]]
            (when (pred v) x))
          (map-indexed vector coll)))

(defn cycle-size
  [input]
  (->> (cycle input)
       (reductions dance programs)
       (indexed-cycle #{programs})
       (take 2)
       (last)
       (first)))

(defn part02
  [input]
  (let [size (cycle-size input)]
    (->> (cycle input)
         (reductions dance programs)
         ((fn [coll]
            (nth coll (rem 1000000000 size))))
         (apply str))))
