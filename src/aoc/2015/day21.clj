(ns aoc.2015.day21
  (:require 
   [clojure.math.combinatorics :refer [combinations]]))

(def weapons
  [
   {:name   "Dagger"
    :cost   8
    :damage 4
    :armor  0}
   {:name   "Shortsword"
    :cost   10
    :damage 5
    :armor  0}
   {:name   "Warhammer"
    :cost   25
    :damage 6
    :armor  0}
   {:name   "Longsword"
    :cost   40
    :damage 7
    :armor  0}
   {:name   "Greataxe"
    :cost   74
    :damage 8
    :armor  0}])

(def armor
  [
   {:name   "Leather"
    :cost   13
    :damage 0
    :armor  1}
   {:name   "Chainmail"
    :cost   31
    :damage 0
    :armor  2}
   {:name   "Splintmail"
    :cost   53
    :damage 0
    :armor  3}
   {:name   "Bandedmail"
    :cost   75
    :damage 0
    :armor  4}
   {:name   "Platemail"
    :cost   102
    :damage 0
    :armor  5}])

(def rings
  [
   { :name   "Damage +1"
    :cost   25
    :damage 1
    :armor  0}
   { :name   "Damage +2"
    :cost   50
    :damage 2
    :armor  0}
   { :name   "Damage +3"
    :cost   100
    :damage 3
    :armor  0}
   { :name   "Defense +1"
    :cost   20
    :damage 0
    :armor  1}
   { :name   "Defense +2"
    :cost   40
    :damage 0
    :armor  2}
   { :name   "Defense +3"
    :cost   80
    :damage 0
    :armor  3}])

(def boss
  {:hp     109
   :damage 8
   :armor  2})

(def player
  {:hp     100
   :damage 0
   :armor  0})

(defn fight
  [{p1-hp    :hp
    p1-dmg   :damage
    p1-armor :armor} 
   {p2-hp    :hp
    p2-dmg   :damage
    p2-armor :armor}]
  (let [p1-dmg-after-armor (max 1 (- p1-dmg p2-armor))
        p2-dmg-after-armor (max 1 (- p2-dmg p1-armor))
        p1-turns           (Math/ceil (/ p1-hp p2-dmg-after-armor))
        p2-turns           (Math/ceil (/ p2-hp p1-dmg-after-armor))]
    (>= p1-turns p2-turns)))

(defn equipment-sets
  []
  (for [weapon weapons
        armor'  (conj armor {})
        rings' (combinations (conj rings {} {}) 2)]
    (apply merge-with (fnil + {}) (map #(dissoc % :name) (conj rings' armor' weapon)))))

(defn part01
  [player boss]
  (->> (equipment-sets)
       (map (partial merge-with + player))
       (filter #(fight % boss))
       (map :cost)
       (apply min)))

(defn part02
  [player boss]
  (->> (equipment-sets)
       (map (partial merge-with + player))
       (filter #(not (fight % boss)))
       (map :cost)
       (apply max)))
