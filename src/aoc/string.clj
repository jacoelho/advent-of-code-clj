(ns aoc.string)

(defn permutations-set [s]
  (lazy-seq
   (if (next s)
     (for [head s
           tail (permutations-set (disj s head))]
       (cons head tail))
     [s])))

(defn subs-index
  [^String s ^String v]
  (loop [result (transient [])
         idx    (int 0)]
    (let [idx' (.indexOf s v idx)]
      (if (neg? idx')
        (persistent! result)
        (recur (conj! result [v idx']) (inc idx'))))))