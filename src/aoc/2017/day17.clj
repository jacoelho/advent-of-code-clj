(ns aoc.2017.day17)

(def input 376)

(defn insert-at
  [coll idx n]
  (let [pre  (subvec coll 0 idx)
        post (subvec coll idx)]
    (into (conj pre n) post)))

(defn spinlock
  [{:keys [pos step memory] :or {pos 0 memory [0]}} elem]
  (let [pos' (inc (rem (+ pos step) (count memory)))]
    {:pos    pos'
     :memory (insert-at memory pos' elem)
     :step   step}))

(defn part01
  [input]
  (->> (range 1 2018)
       (reduce spinlock {:step input})
       (:memory)
       (drop-while #(not= 2017 %))
       (second)))

(defn spinlock-two
  "spinlock defined in part01 is too slow
  we only need to track index one value"
  [step n]
  (loop [elem     1
         slot-one 0
         pos      0]
    (let [next-pos  (+ 1 (rem (+ pos step) elem))
          next-elem (+ 1 elem)]
      (cond
        (= elem n) slot-one
        (= next-pos 1) (recur next-elem elem next-pos)
        :else (recur next-elem slot-one next-pos)))))

(defn part02
  [input]
  (spinlock-two input 50000000))
