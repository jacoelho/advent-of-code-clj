(ns aoc.2015.day12
  (:require
   [aoc.file :as file]
   [clojure.data.json :as json]
   [clojure.walk :as w]
   [clojure.test :refer [testing is]]))

(def input
  (json/read-str (file/read-input "2015/day12.txt")))

(defn cardinality-many? [x]
  (boolean
   (some #(% x)
         [list?
          #(instance? clojure.lang.IMapEntry %)
          seq?
          #(instance? clojure.lang.IRecord %)
          coll?])))

(defn walk-reduce [f acc x]
  (reduce f
          (if (cardinality-many? x)
            (reduce (partial walk-reduce f) acc x)
            acc)
          [x]))

(defn adder
  [acc v]
  (if (number? v)
    (+ acc v)
    acc))

(defn part01
  [input]
  (->> input
       (walk-reduce adder 0)))

(testing "Part 01"
  (is (= 191164 (part01 input))))

(defn discard-red
  [input]
  (w/prewalk (fn [node]
             (if (and (map? node)
                      (some #{"red"} (vals node)))
               {}
               node))
           input))

(defn part02
  [input]
  (->> input
       (discard-red)
       (walk-reduce adder 0)))

(testing "Part 02"
  (is (= 87842 (part02 input))))



(some #{"red"} '(1 2 "red" 3))





