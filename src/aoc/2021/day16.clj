(ns aoc.2021.day16
  (:require [aoc.file :as file]))

(def hex->bin-table
  {\0 [0 0 0 0]
   \1 [0 0 0 1]
   \2 [0 0 1 0]
   \3 [0 0 1 1]
   \4 [0 1 0 0]
   \5 [0 1 0 1]
   \6 [0 1 1 0]
   \7 [0 1 1 1]
   \8 [1 0 0 0]
   \9 [1 0 0 1]
   \A [1 0 1 0]
   \B [1 0 1 1]
   \C [1 1 0 0]
   \D [1 1 0 1]
   \E [1 1 1 0]
   \F [1 1 1 1]})

(defn hex->bin
  [input]
  (->> input
       (mapcat hex->bin-table)
       (vec)))

(def input
  (->> "2021/day16.txt"
       (file/read-input)
       (hex->bin)))

(def type-literal-value 4)

(defn bits->long
  [col]
  (reduce #(+ (* 2 %) %2) (long 0) col))

(defn split-at-bits
  [n col]
  (let [[left right] (split-at n col)]
    [(bits->long left)
     right]))

(defn parse-literal
  [bits]
  (loop [bits bits
         groups []]
    (let [[prefix a b c d] bits
          groups' (conj groups a b c d)
          bits' (drop 5 bits)]
      (if (zero? prefix)
        [(bits->long groups') bits']
        (recur bits' groups')))))

(declare parse-packet)

(defn parse-total-length
  [bits]
  (let [[total-length bits] (split-at-bits 15 bits)
        [bits' bits] (split-at total-length bits)]
    (loop [result []
           input bits']
      (if (empty? input)
        [result bits]
        (let [[packet rest] (parse-packet input)]
          (recur (conj result packet) rest))))))

(defn parse-sub-packets
  [bits]
  (let [[sub-packets-count bits] (split-at-bits 11 bits)]
    (loop [bits bits
           result []
           count 0]
      (if (= count sub-packets-count)
        [result bits]
        (let [[packet rest] (parse-packet bits)]
          (recur rest (conj result packet) (inc count)))))))

(defn parse-operator
  [bits]
  (let [[length-type bits] (split-at-bits 1 bits)]
    (if (zero? length-type)
      (parse-total-length bits)
      (parse-sub-packets bits))))

(defn parse-packet
  [bits]
  (let [[version bits] (split-at-bits 3 bits)
        [type bits] (split-at-bits 3 bits)
        [payload bits] (if (= type type-literal-value)
                         (parse-literal bits)
                         (parse-operator bits))]
    [{:version version
      :type    type
      :payload payload} bits]))

(defn sum-versions
  [{:keys [version type payload]}]
  (if (= type type-literal-value)
    version
    (->> payload
         (map sum-versions)
         (reduce + version))))

(defn part01
  [input]
  (->> input
       (parse-packet)
       (first)
       (sum-versions)))

(def type->operator
  {0 +
   1 *
   2 min
   3 max
   5 >
   6 <
   7 =})

(defn to-int
  [input]
  (cond (number? input) input
        (= true input) 1
        :else 0))

(defn evaluate
  [{:keys [type payload]}]
  (if (= type type-literal-value)
    payload
    (->> payload
         (map evaluate)
         (reduce (type->operator type))
         (to-int))))

(defn part02
  [input]
  (->> input
       (parse-packet)
       (first)
       (evaluate)))

