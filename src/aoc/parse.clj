(ns aoc.parse)

(defn string->int
  [int]
  (Integer/parseInt int))

(defn try->int
  [int]
  (try
    (Integer/parseInt int)
    (catch NumberFormatException _ nil)))

(defn try-or-v->int
  [v]
  (try
    (Integer/parseInt v)
    (catch NumberFormatException _ v)))

(defn ->uint
  [int]
  (Integer/parseUnsignedInt int))

(defn ->long
  [l]
  (Long/parseLong l))

(defn binary-string->int
  [^String s]
  (Integer/parseInt s 2))
