(ns aoc.2017.day17)

(def input 376)

(defn insert-at
  [coll idx n]
  (let [pre  (subvec coll 0 idx)
        post (subvec coll idx)]
    (into (conj pre n) post)))

(defn spinlock
  [{:keys [pos step memory] :or {pos 0 memory [0]}} elem]
  (let [pos' (inc (mod (+ pos step) (count memory)))]
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
  [step n]
  (loop [[x & xs] (range 1 n)
         slot-one 0
         pos 0]
    (if x
      (let [pos' (inc (mod (+ pos step) x))]
        (recur xs (if (= pos' 1) x slot-one) pos'))
      slot-one)))

(defn part02
  [input]
  (spinlock-two input 50000000))
