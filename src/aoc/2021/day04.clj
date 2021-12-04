(ns aoc.2021.day04
  (:require [aoc.file :as file]
            [aoc.parse :as parse]))

(def data
  (file/read-lines "2021/day04.txt"))

(def numbers
  (->> data
       (first)
       (re-seq #"[^,]+")
       (mapv parse/->int)))

(def raw-cards
  (->> data
       (drop 1)
       (remove #(= % ""))
       (map (fn [line]
              (let [[_ & rest] (re-find #"(\d+)\s+(\d+)\s+(\d+)\s+(\d+)\s+(\d+)" line)]
                (mapv parse/->int rest))))
       (partition 5)))

(def cards
  (->> raw-cards
       (reduce (fn [cards raw]
                 (let [card (zipmap (apply concat raw)
                                    (for [x (range 5)
                                          y (range 5)]
                                      [x y]))]
                   (conj cards {:numbers card})))
               [])))

(defn update-card
  [number card]
  (let [[x y] (get-in card [:numbers number])]
    (if (nil? x)
      card
      (-> card
          (update :numbers dissoc number)
          (update-in [:rows x] (fnil conj #{}) number)
          (update-in [:columns y] (fnil conj #{}) number)))))

(defn bingo?
  [card]
  (when (or (some (fn [[_ v]]
                    (= (count v) 5))
                  (get card :rows))
            (some (fn [[_ v]]
                    (= (count v) 5))
                  (get card :columns)))
    card))

(defn play-number
  [round number card]
  (if (bingo? card)
    card
    (let [card' (update-card number card)]
      (if (bingo? card')
        (-> card'
            (assoc :last-called number)
            (assoc :round round))
        card'))))

(defn play
  [cards numbers]
  (reduce (fn [cards [round number]]
            (map (partial play-number round number) cards))
          cards
          (map-indexed vector numbers)))

(defn score-card
  [card]
  (* (:last-called card)
     (reduce + (keys (:numbers card)))))

(defn find-winner
  [winner-seq-pos cards numbers]
  (let [cards (play cards numbers)
        winner (winner-seq-pos (sort-by :round cards))]
    (score-card winner)))

(defn part01
  [cards numbers]
  (find-winner first cards numbers))

(defn part02
  [cards numbers]
  (find-winner last cards numbers))
