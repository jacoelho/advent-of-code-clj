(ns aoc.2021.day18
  (:require [aoc.file :as file]
            [clojure.edn :as edn]
            [clojure.zip :as z]))

(def input
  (file/read-lines edn/read-string "2021/day18.txt"))

(defn split
  [n]
  (let [res (quot n 2)]
    [res (- n res)]))

(defn depth
  [zipper]
  (loop [loc   zipper
         level -1]
    (if (nil? loc)
      level
      (recur (z/up loc)
             (inc level)))))

(defn left-leaf
  [zipper]
  (when zipper
    (when-let [loc (z/prev zipper)]
      (if (number? (z/node loc))
        loc
        (recur loc)))))

(defn right-leaf
  [zipper]
  (when zipper
    (if-let [right (z/right zipper)]
      (loop [down right]
        (if (number? (z/node down))
          down
          (recur (z/down down))))
      (recur (z/up zipper)))))

(defn explodes?
  [zipper]
  (and (z/branch? zipper)
       (every? number? (z/node zipper))
       (= 4 (depth zipper))))

(defn update-snail-number
  [coll pred f]
  (when-let [found (->> coll
                        (z/vector-zip)
                        (iterate z/next)
                        (take-while #(not (z/end? %)))
                        (some (fn [pos]
                                (when (pred pos)
                                  pos))))]
    (z/root (f found))))

(defn split?
  [zipper]
  (let [node (z/node zipper)]
    (and (not (z/branch? zipper))
         (number? node)
         (< 9 node))))

(defn leftmost-split
  [coll]
  (update-snail-number coll split? #(z/replace % (split (z/node %)))))

(defn explode
  [coll]
  (update-snail-number coll explodes?
                       (fn [zipper]
                         (let [[left right] (z/node zipper)]
                           (-> zipper
                               (z/replace 0)
                               ((fn [zipper]
                                  (if-let [leaf (left-leaf zipper)]
                                    (-> leaf
                                        (z/edit + left)
                                        (right-leaf))
                                    zipper)))
                               ((fn [zipper]
                                  (if-let [leaf (right-leaf zipper)]
                                    (z/edit leaf + right)
                                    zipper))))))))

(defn reduce-snail-number
  [coll]
  (if-let [res (or (explode coll)
                   (leftmost-split coll))]
    (recur res)
    coll))

(defn magnitude [x]
  (if (number? x)
    x
    (+ (* 3 (magnitude (first x)))
       (* 2 (magnitude (second x))))))

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
