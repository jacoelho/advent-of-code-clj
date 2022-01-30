(ns aoc.2018.day16
  (:require [aoc.file :as file]
            [aoc.parse :as parse]
            [aoc.collections :as collections]))

(defn parse-chunk
  [coll]
  (let [[before inst after] (map (comp #(mapv parse/string->int %)
                                       #(re-seq #"\d+" %)) coll)]
    {:before      before
     :after       after
     :instruction inst}))

(def input-section1 (->> (file/read-lines "2018/day16-section1.txt")
                         (partition-all 4)
                         (map parse-chunk)))

(def input-section2 (->> (file/read-lines "2018/day16-section2.txt")
                         (map (comp #(mapv parse/string->int %)
                                    #(re-seq #"\d+" %)))))

(defn op-addr
  [registers [_ a b c]]
  (assoc registers c (+ (nth registers a) (nth registers b))))

(defn op-addi
  [registers [_ a b c]]
  (assoc registers c (+ (nth registers a) b)))

(defn op-mulr
  [registers [_ a b c]]
  (assoc registers c (* (nth registers a) (nth registers b))))

(defn op-muli
  [registers [_ a b c]]
  (assoc registers c (* (nth registers a) b)))

(defn op-banr
  [registers [_ a b c]]
  (assoc registers c (bit-and (nth registers a) (nth registers b))))

(defn op-bani
  [registers [_ a b c]]
  (assoc registers c (bit-and (nth registers a) b)))

(defn op-borr
  [registers [_ a b c]]
  (assoc registers c (bit-or (nth registers a) (nth registers b))))

(defn op-bori
  [registers [_ a b c]]
  (assoc registers c (bit-or (nth registers a) b)))

(defn op-setr
  [registers [_ a _ c]]
  (assoc registers c (nth registers a)))

(defn op-seti
  [registers [_ a _ c]]
  (assoc registers c a))

(defn op-gtir
  [registers [_ a b c]]
  (assoc registers c (if (< (nth registers b) a)
                       1 0)))

(defn op-gtri
  [registers [_ a b c]]
  (assoc registers c (if (< b (nth registers a))
                       1 0)))

(defn op-gtrr
  [registers [_ a b c]]
  (assoc registers c (if (< (nth registers b)
                            (nth registers a))
                       1 0)))

(defn op-eqir
  [registers [_ a b c]]
  (assoc registers c (if (= a (nth registers b))
                       1 0)))

(defn op-eqri
  [registers [_ a b c]]
  (assoc registers c (if (= (nth registers a) b)
                       1 0)))

(defn op-eqrr
  [registers [_ a b c]]
  (assoc registers c (if (= (nth registers a)
                            (nth registers b))
                       1 0)))

(def op-mapping
  {"addr" op-addr
   "addi" op-addi
   "mulr" op-mulr
   "muli" op-muli
   "banr" op-banr
   "bani" op-bani
   "borr" op-borr
   "bori" op-bori
   "setr" op-setr
   "seti" op-seti
   "gtir" op-gtir
   "gtri" op-gtri
   "gtrr" op-gtrr
   "eqir" op-eqir
   "eqri" op-eqri
   "eqrr" op-eqrr})

(defn count-op-matches
  [{:keys [before after instruction]}]
  (->> (vals op-mapping)
       (filter #(= after (% before instruction)))
       (count)))

(defn part01
  [input]
  (->> (map count-op-matches input)
       (collections/count-by #(<= 3 %))))

(defn matches?
  [[code instructions] [name op]]
  (when (every? (fn [{:keys [before after instruction]}]
                  (= after (op before instruction)))
                instructions)
    [code name]))

(defn find-matches
  [input]
  (loop [instructions (->> input
                           (group-by (comp first :instruction)))
         found        {}
         op-mapping   op-mapping]
    (if (seq instructions)
      (let [[op name] (->> instructions
                           (map (fn [v] (keep #(matches? v %) op-mapping)))
                           (some #(when (= (count %) 1) (first %))))]
        (recur (dissoc instructions op)
               (assoc found op name)
               (dissoc op-mapping name)))
      found)))

(defn part02
  [input-1 input-2]
  (let [m (->> (find-matches input-1)
               (collections/map-vals #(op-mapping %)))]
    (->> input-2
         (reduce (fn [registers [op :as instruction]]
              ((m op) registers instruction))
            [0 0 0 0])
         (first))))
