(ns aoc.2015.day13
  (:require
   [aoc.file :as file]
   [aoc.parse :as parse]
   [aoc.string :as aoc-string]
   [clojure.string :as str]))

(defn parse-line
  [line]
  (let [[_ source sign d destination] (re-find #"^(\w+) would (lose|gain) (\d+) happiness units by sitting next to (\w+)\.$" line)
        happiness (parse/->int d)]
    {[source destination] (if (= sign "lose") (* -1 happiness) happiness)}))

(def input
  (->> (file/read-lines parse-line "2015/day13.txt")
       (into {})))

(def example
  (into {} 
        (map
         parse-line
         (str/split-lines
          "Alice would gain 54 happiness units by sitting next to Bob.
Alice would lose 79 happiness units by sitting next to Carol.
Alice would lose 2 happiness units by sitting next to David.
Bob would gain 83 happiness units by sitting next to Alice.
Bob would lose 7 happiness units by sitting next to Carol.
Bob would lose 63 happiness units by sitting next to David.
Carol would lose 62 happiness units by sitting next to Alice.
Carol would gain 60 happiness units by sitting next to Bob.
Carol would gain 55 happiness units by sitting next to David.
David would gain 46 happiness units by sitting next to Alice.
David would lose 7 happiness units by sitting next to Bob.
David would gain 41 happiness units by sitting next to Carol."))))

(defn arrangement-happiness
  [potencial arrangement]
  (->> 
   (reduce (fn [table [from to]]
             (-> table 
                 (update [from to] #(+ (get potencial [from to] 0) (if (nil? %) 0 %)))
                 (update [to from] #(+ (get potencial [to from] 0) (if (nil? %) 0 %)))))
           {} arrangement)
   (vals)
   (reduce + 0)))

(defn arrangements
  [input]
  (->> input
       (keys)
       (map first)
       (set)
       (aoc-string/permutations-set)
       (map (comp
             (partial map (partial into []))
             (partial partition 2 1)
             #(conj % (first %))
             (partial into [])))))

(defn part01
  [input]
  (apply max (map (partial arrangement-happiness input) (arrangements input))))

