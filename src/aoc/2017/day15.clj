(ns aoc.2017.day15
  (:require [aoc.collections :as collections]))

(def input
  {:a {:seed 703 :factor 16807}
   :b {:seed 516 :factor 48271}})

(def bit-mask (partial bit-and 0xffff))

(defn generate-seq [prev factor]
  (lazy-seq
    (cons prev
          (generate-seq (rem (* prev factor) 2147483647) factor))))

(def generate-low-seq
  (comp (partial map bit-mask)
        generate-seq))

(defn part01
  [{:keys [a b]}]
  (->> (map =
            (generate-low-seq (:seed a) (:factor a))
            (generate-low-seq (:seed b) (:factor b)))
       (drop 1)
       (take 40000000)
       (collections/count-by identity)))

(defn generate-with-mod
  [n]
  (comp
    (partial map bit-mask)
    (partial filter #(zero? (rem % n)))
    generate-seq))

(defn part02
  [{:keys [a b]}]
  (let [generate-mod-4 (generate-with-mod 4)
        generate-mod-8 (generate-with-mod 8)]
  (->> (map =
            (generate-mod-4 (:seed a) (:factor a))
            (generate-mod-8 (:seed b) (:factor b)))
       (drop 1)
       (take 5000000)
       (collections/count-by identity))))
