(ns aoc.2015.day22)

(def player
  {:hp    50
   :mana  500
   :armor 0
   :spent 0})

(def boss
  {:hp  58
   :dmg 9})

(def initial-state
  {:player player
   :boss boss
   :effects {}})

(def spells
  {:magic-missile {:cost 53 :damage 4}
   :drain {:cost 73 :damage 2 :heal 2}
   :shield {:cost 113 :effect {:turns 6 :armor 7}}
   :poison {:cost 173 :effect {:turns 6 :damage 3}}
   :recharge {:cost 229 :effect {:turns 5 :mana 101}}})

(defn cast-spell
  [spells state spell-name]
  (let [s (spell-name spells)]
    (cond
      (> (:cost s) (get-in state [:player :mana])) state
      (contains? (get state :effects) spell-name) state
      :else (-> state
                (update-in [:player :spent] + (:cost s))
                (update-in [:player :mana] - (:cost s))
                (update-in [:player :hp] + (get s :heal 0))
                (update-in [:boss :hp] - (get s :damage 0))
                (update :effects (fn [m]
                                   (if (not (contains? s :effect))
                                     m
                                     (assoc m spell-name (:effect s)))))))))

(cast-spell spells
            (cast-spell spells (cast-spell spells initial-state :recharge) :poison) :shield)