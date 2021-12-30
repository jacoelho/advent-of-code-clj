(ns aoc.2016.day02
  (:require [aoc.file :as file]
            [aoc.convert :as convert]
            [clojure.string :as str]))

(defn parse-line
  [line]
  (re-seq #"[UDLR]" line))

(def input
  (file/read-lines parse-line "2016/day02.txt"))

(def example
  (mapv parse-line ["ULL"
                    "RRDDD"
                    "LURDL"
                    "UUUUD"]))

(def keymap
  (zipmap (for [x (range 3)
                y (range 3)]
            [y x])
          (range 1 10)))

(defn translate
  [press]
  ({"L" [-1 0]
    "R" [1 0]
    "U" [0 -1]
    "D" [0 1]} press))

(defn insert-sequence
  [keymap starting-pos coll]
  (reduce (fn [pos press]
            (let [updated-pos (mapv + pos (translate press))]
              (if (contains? keymap updated-pos)
                updated-pos
                pos)))
          starting-pos
          coll))

(defn insert-codes
  [keymap start coll]
  (reduce (fn [current v]
            (let [code (insert-sequence keymap (:starting-key current) v)]
              (-> current
                  (assoc :starting-key code)
                  (update :codes conj code))))
          {:starting-key start
           :codes        []}
          coll))

(defn part01
  [input]
  (let [{:keys [codes]} (insert-codes keymap [1 1] input)]
    (convert/digits->long (map keymap codes))))

(def keymap-star
  {[2 0] 1
   [1 1] 2
   [2 1] 3
   [3 1] 4
   [0 2] 5
   [1 2] 6
   [2 2] 7
   [3 2] 8
   [4 2] 9
   [1 3] "A"
   [2 3] "B"
   [3 3] "C"
   [2 4] "D"})

(defn part02
  [input]
  (let [{:keys [codes]} (insert-codes keymap-star [0 2] input)]
    (str/join (map keymap-star codes))))
