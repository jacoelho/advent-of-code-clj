(ns aoc.2021.day10
  (:require [aoc.file :as file]))

(def input
  (file/read-lines "2021/day10.txt"))

(def closes
  {\) \(
   \] \[
   \} \{
   \> \<})

(defn corrupted?
  [line]
  (loop [input line
         unclosed '()]
    (let [[x & xs] input]
      (if (nil? x)
        nil
        (case x
          (\) \] \} \>)
          (if (= (closes x)
                 (first unclosed))
            (recur xs (rest unclosed))
            x)

          (\( \[ \{ \<)
          (recur xs (conj unclosed x)))))))

;; 370407
(defn part01
  [input]
  (->> input
       (map corrupted?)
       (remove nil?)
       (map {\) 3
             \] 57
             \} 1197
             \> 25137})
       (reduce +)))

(def opens
  {\( \)
   \[ \]
   \{ \}
   \< \>})

(defn incomplete?
  [line]
  (loop [input line
         unclosed '()]
    (let [[x & xs] input]
      (if (nil? x)
        (when (not (empty? unclosed))
          (map opens unclosed))
        (case x
          (recur xs (case x
                      (\) \] \} \>)
                      (rest unclosed)
                      (\( \[ \{ \<)
                      (conj unclosed x))))))))

(defn score-completion
  [line]
  (reduce (fn [acc el]
            (+ (* acc 5)
               ({\) 1
                 \] 2
                 \} 3
                 \> 4} el)))
          0
          line))

(->> input
     (remove corrupted?)
     (map incomplete?)
     (mapcat {\) 1
              \] 2
              \} 3
              \> 4})
     (reduce +))

;; 3249889609
(defn part02
  [input]
  (let [completions (->> input
                         (remove corrupted?)
                         (map incomplete?)
                         (map score-completion)
                         (sort))]
    (nth completions (quot (count completions) 2))))

