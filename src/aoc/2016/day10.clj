(ns aoc.2016.day10
  (:require [aoc.parse :as parse]
            [aoc.file :as file]
            [clojure.set :as set]))

(defn parse-line
  [line]
  (condp re-matches line
    #"^bot (\d+) gives low to (\w+) (\d+) and high to (\w+) (\d+)$" :>> (fn [[_ bot low-dst low high-dst high]]
                                                                          [:move {:bot  (parse/string->int bot)
                                                                                  :low  [(keyword low-dst) (parse/string->int low)]
                                                                                  :high [(keyword high-dst) (parse/string->int high)]}])


    #"^value (\d+) goes to bot (\d+)$" :>> (fn [[_ value bot]]
                                             [:set {:bot   (parse/string->int bot)
                                                    :value (parse/string->int value)}])))

(def input
  (file/read-lines parse-line "2016/day10.txt"))

(defn low-high-values
  [[a b :as values]]
  (when (and
          (not (nil? values))
          (= (count values) 2))
    (if (< a b)
      [a b]
      [b a])))

(defn apply-instruction
  [state [operation instruction]]
  (case operation
    :set
    (let [{:keys [bot value]} instruction]
      [true (update-in state [:bot bot] (fnil conj []) value)])

    :move
    (let [{:keys [bot low high]} instruction
          [low-chip high-chip] (low-high-values (get-in state [:bot bot]))]
      (if (nil? low-chip)
        [false state]
        [true (-> state
                  (update-in low (fnil conj []) low-chip)
                  (update-in high (fnil conj []) high-chip)
                  (update :bot dissoc bot))]))))

(defn find-chips
  [chips state]
  (let [set-chips (set chips)]
    (some (fn [[k v]]
            (when (= set-chips (set v))
              k))
          (get state :bot))))

(defn apply-instructions
  [predicate input]
  (loop [state {}
         input input
         skipped []]
    (if (or (predicate state)
            (and (empty? input) (empty? skipped)))
      state
      (let [[x & xs] input]
        (if (seq x)
          (let [[ok state'] (apply-instruction state x)]
            (recur state' xs (if ok skipped (conj skipped x))))
          (recur state skipped []))))))

(defn part01
  [input]
  (let [predicate (partial find-chips [61 17])]
    (->> input
         (apply-instructions predicate)
         (predicate))))

(defn find-outputs
  [outputs state]
  (let [output-set (set outputs)]
    (empty? (set/difference output-set (set (keys (get state :output)))))))

(defn part02
  [input]
  (let [outputs [0 1 2]
        predicate (partial find-outputs outputs)
        state (apply-instructions predicate input)]
    (->> outputs
         (mapcat #(get-in state [:output %]))
         (reduce *))))
