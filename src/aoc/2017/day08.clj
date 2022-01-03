(ns aoc.2017.day08
  (:require [aoc.parse :as parse]
            [aoc.file :as file]))

(def operators
  {">"   >
   ">="  >=
   "<"   <
   "<="  <=
   "=="  =
   "!="  not=
   "inc" +
   "dec" -})

(defn parse-line
  [line]
  (let [[_ a op value b condition c] (re-find #"(\w+) (\w+) (-?\d+) if (\w+) (.{1,2}) (-?\d+)" line)]
    [(operators op) a (parse/->int value) b (operators condition) (parse/->int c)]))

(def example (file/read-lines parse-line "2017/day08-example.txt"))
(def input (file/read-lines parse-line "2017/day08.txt"))

(defn step
  [input {:keys [ip] :as state}]
  (if-let [[op a v b condition cv] (get input ip)]
    (if (condition (get-in state [:register b] 0) cv)
      (-> state
          (update-in [:register a] (fnil op 0) v)
          (update :ip inc))
      (update state :ip inc))
    (assoc state :stopped true)))

(defn part01
  [input]
  (->> {:stopped false :ip 0 :register {}}
       (iterate (partial step input))
       (drop-while #(not (get % :stopped)))
       (first)
       (:register)
       (vals)
       (apply max)))

(defn part02
  [input]
  (->> {:stopped false :ip 0 :register {}}
       (iterate (partial step input))
       (take-while #(not (get % :stopped)))
       (mapcat #(vals (:register %)))
       (apply max)))