(ns aoc.2016.day14
  (:require [aoc.digest :as digest]))

(def input "ihaygndm")

(defn n-consecutive-equal?
  [n s]
  (->> s
       (partition n 1)
       (some #(when (apply = %) (first %)))))

(defn nth-hash
  [salt n]
  (digest/md5 (str salt n)))

(defn n-keys
  [hash-fn salt n]
  (let [memo (memoize (partial hash-fn salt))]
    (->> (range)
         (map memo)
         (map-indexed vector)
         (filter (fn [[idx hash]]
                   (when-let [el (n-consecutive-equal? 3 hash)]
                     (->> (range (inc idx) (+ idx 1001))
                          (map memo)
                          (some #(= el (n-consecutive-equal? 5 %)))))))
         (take n))))

(defn part01
  [salt]
  (->> (n-keys nth-hash salt 64)
       (last)
       (first)))

(defn nth-hash-stretch
  [salt n]
  (->> (digest/md5 (str salt n))
       (iterate digest/md5)
       (drop 2016)
       (first)))

(defn part02
  [salt]
  (->> (n-keys nth-hash-stretch salt 64)
       (last)
       (first)))
