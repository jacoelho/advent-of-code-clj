(ns aoc.2015.day15
  (:require
   [aoc.file :as file]
   [clojure.test :refer [testing is]]
   [clojure.string :as str]))

;; Sprinkles: capacity 5, durability -1, flavor 0, texture 0, calories 5
;; PeanutButter: capacity -1, durability 3, flavor 0, texture 0, calories 1
;; Frosting: capacity 0, durability -1, flavor 4, texture 0, calories 6
;; Sugar: capacity -1, durability 0, flavor 0, texture 2, calories 8

(def re #"^(\w+): capacity (-?\d+), durability (-?\d+), flavor (-?\d+), texture (-?\d+), calories (\d+)$")

(defn parse-line
  [line]
  (let [[_ name & attributes] (re-find re line)
        [c d f t cal]           (map file/->int attributes)]
    [name {
           :capacity   c
           :durability d
           :flavor     f
           :texture    t
           :calories   cal
           }
    ]))

(def example
  (->> "Butterscotch: capacity -1, durability -2, flavor 6, texture 3, calories 8
Cinnamon: capacity 2, durability 3, flavor -2, texture -1, calories 3"
       (str/split-lines)
       (map parse-line)
       (into {})))

(def input
  (into {} (file/read-lines parse-line "2015/day15.txt")))

(defn score
  [ingredients mixture]
  (->> mixture
       (mapv (fn [[ingredient qty]]
               (let [{c :capacity
                      d :durability
                      f :flavor
                      t :texture
                      cal :calories} (get ingredients ingredient)]
                 (mapv #(* qty %) [c d f t cal]))))
       (apply mapv +)
       (map #(max 0 %))))
  
(defn mixtures
  []
  (for [a (range 0 101)
        b (range 0 101)
        c (range 0 101)
        d (range 0 101)
        :when (= (+ a b c d) 100)]
    [["Sprinkles" a] ["PeanutButter" b] ["Frosting" c] ["Sugar" d]]))

(defn mixtures-example
  []
  (for [a (range 1 101)
        b (range 1 101)
        :when (= (+ a b) 100)]
    [["Butterscotch" a] ["Cinnamon" b]]))

(defn part01
  [input mix]
  (->> mix
       (map
        (comp
         (partial apply *)
         (partial drop-last)
         (partial score input)))
       (apply max)))

(testing "Part 01"
  (is (= 13882464 (part01 input (mixtures)))))

(defn part02
  [input mix]
  (->> mix
       (map (partial score input))
       (filter #(= (last %) 500))
       (map
        (comp
         (partial apply *)
         (partial drop-last)))
       (apply max)))

(testing "Part 02"
  (is (= 11171160 (part02 input (mixtures)))))
