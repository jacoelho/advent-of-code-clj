(ns aoc.file 
  (:require [clojure.java.io :as io]))

(defn read-lines
  "Reads aoc lines"
  ([input]
   (-> (io/resource input)
       (io/reader)
       (line-seq)))
  ([f input]
   (mapv f (read-lines input))))

(defn read-input
  "Reads aoc lines"
  [input]
   (slurp (io/resource input)))
