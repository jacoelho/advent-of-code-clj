(ns aoc.2016.day07
  (:require [aoc.file :as file]
            [clojure.string :as str]))

(defn condj [v val]
  (if val (conj v val) v))

(defn parse-line
  [line]
  (->> line
       (re-seq #"([a-z]+)|(?:\[([a-z]+)\])")
       (reduce (fn [[bridge hypernet] [_ b h]]
                 [(condj bridge b)
                  (condj hypernet h)])
               [[] []])))

(def input
  (file/read-lines parse-line "2016/day07.txt"))

(defn contains-abba?
  [s]
  (->> s
       (partition 4 1)
       (some (fn [[a b c d]]
               (and (= b c)
                    (= a d)
                    (not= a b))))))

(defn tls?
  [[bridges hypernet]]
  (let [b' (count (filter contains-abba? bridges))
        h' (count (filter contains-abba? hypernet))]
    (and (< 0 b')
         (zero? h'))))

(defn part01
  [input]
  (->> input
       (filter tls?)
       (count)))

(defn take-aba
  [s]
  (->> s
       (partition 3 1)
       (filter (fn [[a b c]]
                 (and (= a c)
                      (not= a b))))
       (mapv (partial apply str))))

(defn aba->bab
  [[a b _]]
  (str b a b))

(defn ssl?
  [[bridges hypernet]]
  (let [aba (mapcat take-aba bridges)
        bab (map aba->bab aba)]
    (some (fn [v]
            (some #(str/includes? % v) hypernet)) bab)))

(defn part02
  [input]
  (->> input
       (filter ssl?)
       (count)))
