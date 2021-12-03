(ns aoc.parse)

(defn ->int
  [int]
  (Integer/parseInt int))

(defn try->int
  [int]
  (try
    (Integer/parseInt int)
    (catch NumberFormatException _ nil)))

(defn ->uint
  [int]
  (Integer/parseUnsignedInt int))

(defn ->long
  [l]
  (Long/parseLong l))

(defn binary-string->int
  [^String s]
  (Integer/parseInt s 2))
