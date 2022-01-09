(ns aoc.character)

(defn upper-case
  [ch]
  (Character/toUpperCase ^char ch))

(defn lower-case
  [ch]
  (Character/toLowerCase ^char ch))