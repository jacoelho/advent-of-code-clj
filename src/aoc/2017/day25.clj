(ns aoc.2017.day25)

(def input
  ;; state { current [write-v direction next-state] }
  {:a {false [true 1 :b]
       true  [false -1 :b]}
   :b {false [true -1 :c]
       true  [false 1 :e]}
   :c {false [true 1 :e]
       true  [false -1 :d]}
   :d {false [true -1 :a]
       true  [true -1 :a]}
   :e {false [false 1 :a]
       true  [false 1 :f]}
   :f {false [true 1 :e]
       true  [true 1 :a]}})

(defn execute
  [instructions {:keys [cursor state tape] :as computer}]
  (let [[write direction next-state] (get-in instructions [state (contains? tape cursor)])]
    (-> computer
        (update :cursor + direction)
        (update :tape (if write conj disj) cursor)
        (assoc :state next-state))))

(defn part01
  [input]
  (->> {:cursor 0 :state :a :tape #{}}
       (iterate (partial execute input))
       (drop 12861455)
       (first)
       (:tape)
       (count)))
