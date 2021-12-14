(ns aoc.2021.day14
  (:require [aoc.file :as file]))

(defn parse-file
  [input]
  (let [lines (file/read-lines input)
        template (first lines)
        rules (into {} (mapv (fn [line]
                               (let [[_ a b c] (re-find #"(\w)(\w) -> (\w)" line)]
                                 [(str a b) [(str a c) (str c b)]]))
                             (drop 2 lines)))]
    {:template template
     :rules    rules}))

(def example
  (parse-file "2021/day14-example.txt"))

(def input
  (parse-file "2021/day14.txt"))

(defn step
  [rules polymer]
  (reduce-kv (fn [m pair freq]
               (reduce (fn [acc rule]
                         (update acc rule (fnil + 0) freq))
                       m
                       (get rules pair)))
             {}
             polymer))

(defn count-polymer-elements
  [original-template polymer]
  (reduce (fn [acc [[a _] freq]]
            (update acc a (fnil + 0) freq))
          (-> {}
              (update (first original-template) (fnil inc 0))
              (update (last original-template) (fnil inc 0)))
          polymer))

(defn grow-polymer
  [{:keys [template rules]} generations]
  (->> template
       (partition 2 1)
       (map #(apply str %))
       (frequencies)
       (iterate (partial step rules))
       (drop generations)
       (first)
       (count-polymer-elements template)
       (vals)
       (apply (juxt max min))
       (apply -)))

(defn part01
  [input]
  (grow-polymer input 10))

(defn part02
  [input]
  (grow-polymer input 40))
