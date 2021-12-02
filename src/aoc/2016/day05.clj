(ns aoc.2016.day05
  (:require [aoc.parse :as parse]
            [aoc.digest :as digest]))

(defn password?
  [pass]
  (let [s (subs pass 0 5)]
    (= s "00000")))

(def input
  "cxdnnyjw")

(defn generate-passwords
  [input]
  (->> (range)
       (map (comp digest/md5
                  (partial str input)))
       (filter password?)))

(defn part01
  [input]
  (->> input
       (generate-passwords)
       (take 8)
       (map #(nth % 5))
       (apply str)))

(defn password-hacked?
  [pass]
  (= #{0 1 2 3 4 5 6 7} (set (keys pass))))

(defn part02
  [input]
  (let [password (reduce (fn [pass [idx char]]
                           (if-let [i (parse/try->int (str idx))]
                             (if (and (not (contains? pass i))
                                      (<= 0 i 7))
                               (let [pass' (assoc pass i char)]
                                 (if (password-hacked? pass')
                                   (reduced pass')
                                   pass'))
                               pass)
                             pass))
                         {}
                         (->> input
                              (generate-passwords)
                              (map (fn [[_ _ _ _ _ idx char]]
                                     [idx char]))))]
    (->> password
         (sort-by key)
         (vals)
         (apply str))))
