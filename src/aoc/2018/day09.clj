(ns aoc.2018.day09
  (:import (java.util ArrayDeque Deque)))

(def input {:players 476 :last-marble 71657})

(defn shift ^Deque [^Deque deque ^Integer amount]
  (let [forward   (fn [_] (.addLast deque (.removeFirst deque)))
        backwards (fn [_] (.addFirst deque (.removeLast deque)))]
    (when-not (.isEmpty deque)
      (if (pos? amount)
        (run! backwards (range amount))
        (run! forward (range (Math/abs amount))))))
  deque)

(defn game-step
  [{:keys [^Deque circle players] :as game} marble]
  (if (zero? (mod marble 23))
    (let [score      (+ (.removeFirst (shift circle -7)) marble)
          player-idx (mod marble players)]
      (shift circle 1)
      (update-in game [:scores player-idx] (fnil + 0) score))
    (do
      (shift circle 1)
      (.addFirst circle marble)
      game)))

(defn part01
  [{:keys [players last-marble]}]
  (->> (range 1 last-marble)
       (reduce game-step {:circle  (ArrayDeque. [0])
                          :players players})
       (:scores)
       (vals)
       (apply max)))

(defn part02
  [input]
  (part01 (update input :last-marble * 100)))