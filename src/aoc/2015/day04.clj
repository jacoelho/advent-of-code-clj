(ns aoc.2015.day04
  (:require [clojure.string :as str])
  (:import (java.security MessageDigest)))

(defn md5 [^String s]
  (let [algorithm (MessageDigest/getInstance "MD5")
        raw       (.digest algorithm (.getBytes s))]
    (format "%032x" (BigInteger. 1 raw))))

(defn coin?
  [input suffix]
  (when (str/starts-with? input suffix)
    input))

(defn mine
  [password suffix]
  (->> (range)
       (map (fn [el] [el (md5 (str password el))]))
       (some (fn [[el checksum]] (when (coin? checksum suffix) el)))))

(defn part01
  [password]
  (mine password "00000"))

(defn part02
  [password]
  (mine password "000000"))

