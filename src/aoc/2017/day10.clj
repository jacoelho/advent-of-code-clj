(ns aoc.2017.day10
  (:require [aoc.file :as file]
            [aoc.parse :as parse]
            [aoc.collections :as collections]))

(def input (->> "2017/day10.txt"
                (file/read-lines #(mapv parse/string->int (re-seq #"\d+" %)))
                (first)))

(defn reverse-section
  [coll pos length]
  (let [size    (count coll)
        pos     (mod pos size)
        ring    (cycle coll)
        twisted (reverse (take length (drop pos ring)))]
    (->> (drop (+ pos length) ring)
         (concat twisted)
         (take size)
         (collections/rotate (- size pos)))))

(defn knot-step
  [{:keys [pos skip hash] :as state} length]
  (-> state
      (assoc :pos (+ length skip pos))
      (update :skip inc)
      (assoc :hash (reverse-section hash pos length))))

(defn knot-hash
  [length input]
  (reduce knot-step
          {:pos  0
           :skip 0
           :hash (range length)}
          input))

(defn part01
  [input]
  (let [{:keys [hash]} (knot-hash 256 input)
        [a b] hash]
    (* a b)))

(def input-string (->> "2017/day10.txt"
                       (file/read-lines)
                       (first)))

(def padding [17 31 73 47 23])

(defn knot-hash-64
  [input]
  (->> (concat (map byte input) padding)
       (repeat)
       (take 64)                                            ;; 64 rounds
       (flatten)
       (knot-hash 256)
       (:hash)
       (partition 16)                                       ;; dense hash
       (map (partial apply bit-xor))
       (map (partial format "%02x"))
       (apply str)))

(defn part02
  [input]
  (knot-hash-64 input))
