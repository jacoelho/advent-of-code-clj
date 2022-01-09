(ns aoc.2018.day07
  (:require [aoc.file :as file]
            [clojure.set :as set]))

(defn parse-line
  [line]
  (let [[_ start end] (re-find #"Step (\w+) must be finished before step (\w+) can begin." line)]
    [start end]))

(defn parse-file
  [f]
  (file/read-lines parse-line f))

(defn child->parents
  [coll]
  (reduce (fn [m [a b]]
            (update m b (fnil conj #{}) a))
          {}
          coll))

(def example (parse-file "2018/day07-example.txt"))
(def input (parse-file "2018/day07.txt"))

(defn remove-node
  [m node]
  (persistent!
    (reduce-kv (fn [m k v]
                 (let [u (disj v node)]
                   (if (seq u)
                     (assoc! m k u)
                     m)))
               (transient (empty m)) m)))

(defn resolve-order
  [input]
  (let [steps (set (flatten input))
        graph (child->parents input)]
    (loop [to-visit steps
           graph    graph
           result   []]
      (if (seq to-visit)
        (let [next-step (first (sort (set/difference to-visit (set (keys graph)))))]
          (recur (disj to-visit next-step)
                 (remove-node graph next-step)
                 (conj result next-step)))
        (apply str result)))))

(defn part01
  [input]
  (resolve-order input))

(defn step-cost
  [ch]
  (+ 61 (- (int (Character/codePointAt ^CharSequence ch 0)) (int \A))))

(defn assign-work
  [workers task elapsed]
  (assoc workers task (+ elapsed (step-cost task))))

(defn wait-step-completion
  [workers]
  (let [[task elapsed] (apply min-key val workers)]
    [task elapsed (dissoc workers task)]))

(defn wait-longest-step-completion
  [workers]
  (second (apply max-key val workers)))

(defn resolve-timed
  [input]
  (let [steps        (set (flatten input))
        graph        (child->parents input)
        worker-count 5]
    (loop [to-visit steps
           graph    graph
           elapsed  0
           workers  {}]
      (if (seq to-visit)
        (let [next-step (first (sort (set/difference to-visit (set (keys graph)))))]
          (if (and next-step (< (count workers) worker-count))
            (recur (disj to-visit next-step)
                   graph
                   elapsed
                   (assign-work workers next-step elapsed))
            (let [[task elapsed workers] (wait-step-completion workers)]
              (recur to-visit
                     (remove-node graph task)
                     (long elapsed)
                     workers))))
        (wait-longest-step-completion workers)))))

(defn part02
  [input]
  (resolve-timed input))