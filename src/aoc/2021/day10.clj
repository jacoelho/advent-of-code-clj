(ns aoc.2021.day10
  (:require [aoc.file :as file]
            [aoc.collections :as collections]))

(def input
  (file/read-lines "2021/day10.txt"))

(def opens
  {\( \)
   \[ \]
   \{ \}
   \< \>})

(def closes
  (collections/map-invert opens))

(defn analyse
  [line]
  (reduce (fn [{:keys [unmatched]} el]
            (case el
              (\) \] \} \>)
              (if (not= (closes el)
                        (first unmatched))
                (reduced {:corrupted el})
                {:unmatched (rest unmatched)})

              (\( \[ \{ \<)
              {:unmatched (conj unmatched el)}))
          {:unmatched '()}
          line))

(defn part01
  [input]
  (->> input
       (map analyse)
       (keep (fn [m]
               (get m :corrupted)))
       (map {\) 3
             \] 57
             \} 1197
             \> 25137})
       (reduce +)))

(defn completion-score
  [line]
  (reduce (fn [acc el]
            (+ (* acc 5)
               ({\) 1
                 \] 2
                 \} 3
                 \> 4} el)))
          0
          line))

(defn middle
  [input]
  (nth (sort input)
       (quot (count input) 2)))

(defn part02
  [input]
  (->> input
        (map analyse)
        (keep (fn [m]
                (get m :unmatched)))
        (map (comp
               completion-score
               (partial map opens)))
        (middle)))
