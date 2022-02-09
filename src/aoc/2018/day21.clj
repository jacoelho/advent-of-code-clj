(ns aoc.2018.day21)

;; constant found line 9
(def input 10283511)

;; https://www.reddit.com/r/adventofcode/comments/a86jgt/comment/ec8swng/
(defn find-reg0 [r1 r4]
  (let [r5  (bit-and r4 0xFF)
        r1+ (bit-and 0xFFFFFF
                     (* 65899
                        (bit-and 0xFFFFFF
                                 (+ r1 r5))))]
    (if (< r4 0x100)
      r1+
      (find-reg0 r1+ (quot r4 0x100)))))

(defn part01
  [input]
  (find-reg0 input 0x10000))

(defn part02
  [input]
  (loop [seen #{}
         r1   0]
    (let [r4  (bit-or r1 0x10000)
          r1+ (find-reg0 input r4)]
      (if (seen r1+)
        r1
        (recur (conj seen r1+) r1+)))))
