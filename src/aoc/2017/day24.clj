(ns aoc.2017.day24
  (:require [aoc.parse :as parse]
            [aoc.file :as file]))

(defn parse-line
  [line]
  (mapv parse/string->int (re-seq #"\d+" line)))

(def input (->> "2017/day24.txt"
                (file/read-lines parse-line)
                (concat)
                (set)))

(def example (->> "2017/day24-example.txt"
                  (file/read-lines parse-line)
                  (concat)
                  (set)))

(defn pluggable?
  [[_ b] [c d :as rhs]]
  (cond (= b c) [rhs [c d]]
        (= b d) [rhs [d c]]
        :else nil))

(defn bridges-seq
  [input]
  (letfn [(bridges [path input pin]
            (lazy-seq
              (let [components (keep (partial pluggable? pin) input)
                    new-path   (conj path pin)]
                (cons new-path
                      (mapcat (fn [[bridge aligned]]
                                (bridges new-path (disj input bridge) aligned))
                              components)))))]
    (bridges [] input [0 0])))

(defn strength
  [coll]
  (reduce (fn [acc [a b]]
            (+ acc a b))
          0
          coll))

(defn part01
  [input]
  (->> (bridges-seq input)
       (map strength)
       (apply max)))

(defn part02
  [input]
  (let [bridges (->> (bridges-seq input)
                     (sort-by count >))
        size    (count (first bridges))]
    (->> (filter #(= size (count %)) bridges)
         (map strength)
         (apply max))))
