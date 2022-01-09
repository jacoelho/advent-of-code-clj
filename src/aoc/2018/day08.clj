(ns aoc.2018.day08
  (:require [aoc.file :as file]
            [aoc.parse :as parse]))

(defn parse-line
  [line]
  (map parse/string->int (re-seq #"\d+" line)))

(def input (first (file/read-lines parse-line "2018/day08.txt")))

(defn nodes-tree
  [input]
  (letfn [(rec [[children-count metadata-count & rest]]
            (loop [want      children-count
                   remaining rest
                   children  []]
              (if (zero? want)
                (let [[metadata rest] (split-at metadata-count remaining)]
                  [{:metadata (vec metadata)
                    :children children} rest])
                (let [[new-child rest] (rec remaining)]
                  (recur (dec want) rest (conj children new-child))))))]
    (first (rec input))))

(defn sum-metadata
  [{:keys [children metadata]}]
  (+ (reduce + metadata)
     (reduce + 0 (map sum-metadata children))))

(defn part01
  [input]
  (->> (nodes-tree input)
       (sum-metadata)))

(defn value-tree
  [{:keys [children metadata]}]
  (if (seq children)
    (reduce + (map value-tree (map #(get children (dec %)) metadata)))
    (reduce + metadata)))

(defn part02
  [input]
  (->> (nodes-tree input)
       (value-tree)))
