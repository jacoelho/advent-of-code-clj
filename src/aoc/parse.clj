(ns aoc.parse)

(defn ->int [int]
  (Integer/parseInt int))

(defn ->uint [int]
  (Integer/parseUnsignedInt int))

(defn ->long [l]
  (Long/parseLong l))
