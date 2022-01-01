(ns aoc.2016.day16)

(defn parse-input
  [input]
  (mapv {\0 0 \1 1} input))

(def example (parse-input "10000"))

(def input (parse-input "11011110011011101"))

(defn fill-step
  [col]
  (into (conj col 0) (map {0 1 1 0} (reverse col))))

(defn fill
  [disk-size coll]
  (if (<= disk-size (count coll))
    (take disk-size coll)
    (recur disk-size (fill-step coll))))

(defn checksum
  [coll]
  (let [c' (->> (partition 2 coll)
                (map #(if (apply = %) 1 0)))]
    (if (odd? (count c'))
      c'
      (recur c'))))

(defn part01
  [input]
  (->> input
       (fill 272)
       (checksum)
       (apply str)))

(defn part02
  [input]
  (->> input
       (fill 35651584)
       (checksum)
       (apply str)))
