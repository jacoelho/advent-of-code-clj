(ns aoc.2015.day22
  (:require
    [aoc.file :as aoc]
    [clojure.test :refer [testing is]]))

(def player
  {:hp    50
   :mana  500
   :armor 0
   :spent 0})

(def boss
  {:hp  58
   :dmg 9})

(def spells
  {
   :magic-missile {:cost  -53
                   :enemy {:hp -4}}
   :drain         {:cost   -73
                   :player {:hp 2}
                   :enemy  {:hp -2}}
   :shield        {:cost   -113
                   :player {:armor 7}
                   :turns  6}
   :poison        {:cost  -173
                   :enemy {:hp -3}
                   :turns 6}
   :recharge      {:cost   -229
                   :player {:mana 101}
                   :turns  5}})

(defn update-effect
  [{:keys [:player :enemy]} effect]
  {:player (merge-with + player (:player effect))
   :enemy  (merge-with + enemy (:enemy effect))})

(defn instant-spell?
  [spell]
  (not (contains? spell :turns)))

(defn update-effects-duration
  [effects]
  (reduce-kv (fn [m k v]
               (let [v' (update v :turns (fnil dec 0))]
                 (if (> (:turns v') 0)
                   (assoc m k v')
                   m)))
             {} effects))

(defn any-dead?
  [entities]
  (some #(<= (get % :hp 1) 0) (vals entities)))

(defn next-turn
  [spells action {:keys [:player :enemy :effects]}]
  (let [{:keys [:player :enemy] :as entities}
        (reduce update-effect {:player (assoc player :armor 0) :enemy enemy} (vals effects))
        effects' (update-effects-duration effects)]
    (cond
      (any-dead? entities) entities
      (= action :boss) {:player  (assoc player :hp (- (:hp player) (max 1 (- (:dmg enemy) (:armor player)))))
                        :enemy   enemy
                        :effects effects'}
      (instant-spell? (action spells)) {:player  (-> player
                                                     (update :spent + (* -1 (:cost (action spells))))
                                                     (update :mana + (:cost (action spells))))
                                        :enemy   (merge-with + enemy (:enemy (action spells)))
                                        :effects effects'}
      :else {:player  (-> player
                          (update :spent + (* -1 (:cost (action spells))))
                          (update :mana + (:cost (action spells))))
             :enemy   enemy
             :effects (conj effects' [action (action spells)])})))

(defn full-turn
  [spells entities action]
  (->> entities
       (next-turn spells action)
       (next-turn spells :boss)))

(defn available-spells
  [spells {:keys [:player :effects]}]
  (let [spells' (->> spells
                     (filter #(>= (:mana player) (* -1 (:cost (second %)))))
                     (keys)
                     (set))]
    (clojure.set/difference spells' (set (keys effects)))))

(defn success?
  [{:keys [:player :enemy]}]
  (and (<= (:hp enemy) 0)
       (<= 0 (:hp player))))





(shuffle #{1 2 3})
;; 1362 too high

(def input "foo")

(defn part01
  [s]
  s)

(defn part02
  [s]
  s)