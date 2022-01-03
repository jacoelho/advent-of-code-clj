(ns aoc.2016.day21
  (:require [aoc.parse :as parse]
            [aoc.file :as file]
            [aoc.collections :as collections]
            [clojure.math.combinatorics :as combo]))

(defn parse-line
  [line]
  (condp re-matches line
    #"^rotate (\w+) (\d+) steps?$" :>> (fn [[_ a b]]
                                         [:rotate a (parse/string->int b)])


    #"^rotate based on position of letter (\w+)$" :>> (fn [[_ letter]]
                                                        [:rotate-letter letter])

    #"^swap letter (\w+) with letter (\w+)$" :>> (fn [[_ a b]]
                                                   [:swap a b])

    #"^swap position (\d+) with position (\d+)$" :>> (fn [[_ a b]]
                                                       [:swap-position (parse/string->int a) (parse/string->int b)])

    #"^reverse positions (\d+) through (\d+)$" :>> (fn [[_ a b]]
                                                     [:reverse (parse/string->int a) (parse/string->int b)])

    #"^move position (\d+) to position (\d+)$" :>> (fn [[_ a b]]
                                                     [:move (parse/string->int a) (parse/string->int b)])))


(def input (file/read-lines parse-line "2016/day21.txt"))
(def alphabet ["a" "b" "c" "d" "e" "f" "g" "h"])

(defn swap-positions
  [coll a b]
  (-> coll
      (assoc a (get coll b))
      (assoc b (get coll a))))

(defn swap-letters
  [coll a b]
  (mapv (fn [el]
          (if-let [v ({a b b a} el)]
            v
            el)) coll))

(defn rotate-steps
  [coll direction steps]
  (->> coll
       (collections/rotate (* (if (= direction "right") -1 1) steps))
       (vec)))

(defn rotate-letter
  [coll letter]
  (let [idx       (some (fn [[idx l]]
                          (when (#{letter} l) idx))
                        (map-indexed vector coll))
        rotations (+ idx 1 (if (<= 4 idx) 1 0))]
    (vec (collections/rotate (* -1 rotations) coll))))

(defn reverse-section
  [coll a b]
  (-> (subvec coll 0 a)
      (into (reverse (subvec coll a (inc b))))
      (into (subvec coll (inc b)))))

(defn move
  [coll a b]
  (let [coll' (into (subvec coll 0 a) (subvec coll (inc a)))
        [before after] (split-at b coll')]
    (-> (apply vector before)
        (conj (get coll a))
        (into after))))

(def operations
  {:rotate        rotate-steps
   :rotate-letter rotate-letter
   :swap          swap-letters
   :swap-position swap-positions
   :reverse       reverse-section
   :move          move})

(defn step
  [coll [op & args]]
  (apply (operations op) coll args))

(defn part01
  [input alphabet]
  (apply str (reduce step alphabet input)))

(defn part02
  [input]
  (->> alphabet
       (combo/permutations)
       (map (fn [el]
              [(part01 input (vec el)) (apply str el)]))
       (some (fn [[scrambled src]]
                  (when (#{"fbgdceah"} scrambled)
                    src)))))
