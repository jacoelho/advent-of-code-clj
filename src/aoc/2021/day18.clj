(ns aoc.2021.day18
  (:require [aoc.file :as file]
            [clojure.edn :as edn]
            [clojure.zip :as z]))

(def input
  (file/read-lines edn/read-string "2021/day18.txt"))

(defn update-snail-number
  [coll pred f]
  (when-let [found (->> coll
                        (z/vector-zip)
                        (iterate z/next)
                        (take-while #(not (z/end? %)))
                        (some #(when (pred %) %)))]
    (z/root (f found))))

(defn split?
  [zipper]
  (let [node (z/node zipper)]
    (and (not (z/branch? zipper))
         (number? node)
         (< 9 node))))

(defn split
  [n]
  (let [res (quot n 2)]
    [res (- n res)]))

(defn leftmost-split
  [coll]
  (update-snail-number coll split? #(z/replace % (split (z/node %)))))

(def leaf?
  (comp number?
        z/node))

(defn find-leaf
  [iterator zipper]
  (->> zipper
       (iterator)
       (iterate iterator)
       (take-while #(not (or (nil? %)
                             (z/end? %))))
       (some #(when (leaf? %) %))))

(def left-leaf
  (partial find-leaf z/prev))

(def right-leaf
  (partial find-leaf z/next))

(def depth
  (comp count
        z/path))

(defn explodes?
  [zipper]
  (and (z/branch? zipper)
       (every? number? (z/node zipper))
       (= 4 (depth zipper))))

(defn update-left-leaf
  [zipper value]
  (if-let [leaf (left-leaf zipper)]
    (-> leaf
        (z/edit + value)
        (right-leaf))
    zipper))

(defn update-right-leaf
  [zipper value]
  (if-let [leaf (right-leaf zipper)]
    (-> leaf
        (z/edit + value)
        (left-leaf))
    zipper))

(defn explode
  [coll]
  (update-snail-number coll explodes?
                       (fn [zipper]
                         (let [[left right] (z/node zipper)]
                           (-> zipper
                               (z/replace 0)
                               (update-left-leaf left)
                               (update-right-leaf right))))))

(defn reduce-snail-number
  [coll]
  (if-let [res (or (explode coll)
                   (leftmost-split coll))]
    (recur res)
    coll))

(defn magnitude
  [snail-number]
  (if (number? snail-number)
    snail-number
    (+ (* 3 (magnitude (first snail-number)))
       (* 2 (magnitude (second snail-number))))))

(defn add-snail-numbers
  [a b]
  (reduce-snail-number [a b]))

(defn part01
  [input]
  (->> input
       (reduce add-snail-numbers)
       (magnitude)))

(defn part02
  [input]
  (apply max
         (for [a input
               b input
               :when (not= a b)]
           (magnitude (add-snail-numbers a b)))))
