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
  (let [[x y] (get-in card [:numbers number] [-1 -1])]
    (if (neg? x)
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

(defn play-card
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
            (map (partial play-card round number) cards))
          cards
          (map-indexed vector numbers)))

(defn score-card
  [card]
  (* (:last-called card)
     (reduce + (keys (:numbers card)))))

(defn part01
  [cards numbers]
  (let [cards (play cards numbers)
        winner (first (sort-by :round cards))]
    (score-card winner)))

(defn part02
  [cards numbers]
  (let [cards (play cards numbers)
        winner (last (sort-by :round cards))]
    (score-card winner)))
