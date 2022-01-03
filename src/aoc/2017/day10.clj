(ns aoc.2017.day10
  (:require [aoc.file :as file]
            [aoc.parse :as parse]
            [aoc.collections :as collections]))

(def input (->> "2017/day10.txt"
                (file/read-lines #(mapv parse/->int (re-seq #"\d+" %)))
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

(def input-bytes (->> "2017/day10.txt"
                      (file/read-lines)
                      (first)
                      (mapv byte)))

(def padding [17 31 73 47 23])

(defn part02
  [input]
  (->> (into input padding)
       (repeat)
       (take 64)                                            ;; 64 rounds
       (flatten)
       (knot-hash 256)
       (:hash)
       (partition 16)                                       ;; dense hash
       (map (partial apply bit-xor))
       (map (partial format "%02x"))
       (apply str)))
