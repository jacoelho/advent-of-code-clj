(ns aoc.2015.day20
  (:require
   [aoc.file :as aoc]
   [clojure.test :refer [testing is]]
   [clojure.string :as str]))

(defn factors
  ([^long n]
   (loop [res #{}
          i 1]
     (if (> (* i i) n)
       res
       (recur
        (if (= 0 (rem n i))
          (conj res i (quot n i))
          res)
        (inc i))))))

(defn sum-factors
  ([f n]
   (->> n
        factors
        (f)
        (reduce +)))
  ([n] (sum-factors identity n)))

(defn part01
  [^long bound]
  (let [sigma (quot bound 10)
        lower 1]
    (loop [i lower]
      (if (>= (sum-factors i) sigma)
        i
        (recur (inc i))))))

(testing "Part 01"
  (is (= 786240 (part01 34000000))))

(defn part02
  [^long bound]
  (let [sigma (quot bound 11)
        lower 1]
    (loop [i lower]
      (if (>= (sum-factors (partial filter #(<= (quot i %) 50)) i) sigma)
        i
        (recur (inc i))))))

(testing "Part 02"
  (is (= 831600 (part02 34000000))))
