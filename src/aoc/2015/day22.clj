(ns aoc.2015.day22)

(def player
  {:hp    50
   :mana  500
   :armor 0
   :spent 0})

(def boss
  {:hp     58
   :damage 9})

(def initial-state
  {:over    false
   :turn    :player
   :player  player
   :boss    boss
   :effects {}})

(def spells
  {:magic-missile {:cost 53 :damage 4}
   :drain         {:cost 73 :damage 2 :heal 2}
   :shield        {:cost 113 :effect {:turns 6 :armor 7}}
   :poison        {:cost 173 :effect {:turns 6 :damage 3}}
   :recharge      {:cost 229 :effect {:turns 5 :mana 101}}})

(defn cast-spell
  [spells state spell-name]
  (let [s (spell-name spells)]
    (cond
      (> (:cost s) (get-in state [:player :mana]))
      state

      (contains? (get state :effects) spell-name)
      state

      :else (-> state
                (update-in [:player :spent] + (:cost s))
                (update-in [:player :mana] - (:cost s))
                (update-in [:player :hp] + (get s :heal 0))
                (update-in [:boss :hp] - (get s :damage 0))
                (update :effects (fn [m]
                                   (if (contains? s :effect)
                                     (assoc m spell-name (:effect s))
                                     m)))))))

(defn reset-player-armor
  [state]
  (assoc-in state [:player :armor] 0))

(defn tick-effects
  [state]
  (reduce-kv (fn [state spell-name effect]
               (-> state
                   (update-in [:player :mana] + (get effect :mana 0))
                   (update-in [:player :armor] + (get effect :armor 0))
                   (update-in [:boss :hp] - (get effect :damage 0))
                   (update :effects (fn [effects]
                                      (let [{turns :turns} effect]
                                        (if (= turns 1)
                                          (dissoc effects spell-name)
                                          (update-in effects [spell-name :turns] dec)))))))
             (reset-player-armor state) (:effects state)))

(defn game-over?
  [state]
  (or
    (= (:over state) true)
    (< (get-in state [:player :hp]) 1)
    (< (get-in state [:boss :hp]) 1)))

(defn boss-win?
  [state]
  (and (game-over? state)
       (> (get-in state [:boss :hp]) 0)))

(defn player-win?
  [state]
  (and (game-over? state)
       (> (get-in state [:player :hp]) 0)))

(defn boss-attacks
  [state]
  (update-in state [:player :hp] - (max 1 (- (get-in state [:boss :damage])
                                             (get-in state [:player :armor])))))

(defn castable-spells
  [spells state]
  (let [effects (set (keys (get state :effects)))]
    (reduce-kv (fn [acc spell-name spell]
                 (cond
                   (effects spell-name) acc
                   (> (:cost spell) (get-in state [:player :mana])) acc
                   :else (conj acc spell-name)))
               [] spells)))

(defn next-states
  [spells state]
  (if (game-over? state)
    (vector (assoc state :over true))
    (let [s' (tick-effects state)]
      (if (game-over? s')
        (vector (assoc s' :over true))
        (case (get s' :turn)
          :boss (vector (-> s'
                            (boss-attacks)
                            (assoc :turn :player)))
          :player (let [available-spells (castable-spells spells s')
                        s'' (assoc s' :turn :boss)]
                    (if (empty? available-spells)
                      (vector (assoc s'' :over true))
                      (mapv (partial cast-spell spells s'') available-spells))))))))

(defn next-states-hard-mode
  [spells state]
  (let [s' (if (= (get state :turn) :player)
             (update-in state [:player :hp] dec)
             state)]
    (next-states spells s')))

(defn simulate
  [next-states-fn {:keys [best states]}]
  (if (seq states)
    (recur
      next-states-fn
      (reduce (fn [acc state]
                (reduce (fn [inner-acc inner-state]
                          (cond
                            (< (:best inner-acc) (get-in inner-state [:player :spent]))
                            inner-acc

                            (boss-win? inner-state)
                            inner-acc

                            (player-win? inner-state)
                            (assoc inner-acc :best (get-in inner-state [:player :spent]))

                            :else
                            (update inner-acc :states conj inner-state)))
                        acc
                        (next-states-fn spells state)))
              {:best   best
               :states []}
              states))
    best))

(defn part01
  [state]
  (simulate next-states {:best   Integer/MAX_VALUE
                         :states [state]}))

(defn part02
  [state]
  (simulate next-states-hard-mode {:best   Integer/MAX_VALUE
                                   :states [state]}))
