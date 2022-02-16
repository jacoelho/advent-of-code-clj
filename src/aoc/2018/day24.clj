(ns aoc.2018.day24
  (:require [aoc.parse :as parse]
            [aoc.file :as file]
            [aoc.collections :as collections]))

(def line-re #"(\d+) units each with (\d+) hit points (\([^\)]+\))?\s?with an attack that does (\d+) (\w+) damage at initiative (\d+)")

(defn parse-modifiers
  [line]
  (if (seq line)
    (reduce (fn [acc [_ modifier elements]]
              (assoc acc
                     (keyword modifier)
                     (->> (re-seq #"[a-z]+" elements)
                          (map keyword)
                          (set))))
            {}
            (re-seq #"(\w+) to (\w+(?:, \w+)*)" line))
    {}))

(defn parse-line
  [line]
  (let [[_ units hp modifiers atk atk-type initiative] (re-find line-re line)]
    {:units      (parse/string->int units)
     :hp         (parse/string->int hp)
     :modifiers  (parse-modifiers modifiers)
     :atk        (parse/string->int atk)
     :atk-type   (keyword atk-type)
     :initiative (parse/string->int initiative)}))

(defn parse-system
  [f system-name]
  (->> (file/read-lines parse-line f)
       (map-indexed (fn [idx group]
                      [(inc idx) (-> group
                               (assoc :system system-name)
                               (assoc :id (inc idx)))]))
       (into {})))

(def input
  {:immune    (parse-system "2018/day24-immune.txt" :immune)
   :infection (parse-system "2018/day24-infection.txt" :infection)})

(def example
  {:immune    (parse-system "2018/day24-example-immune.txt" :immune)
   :infection (parse-system "2018/day24-example-infection.txt" :infection)})

(defn all-groups
  [{:keys [immune infection]}]
  (concat (vals immune)
          (vals infection)))

(def enemy {:immune    :infection
            :infection :immune})

(defn effective-power
  [{:keys [units atk]}]
  (* units atk))

(defn estimate-damage
  [{:keys [units atk atk-type]} {target-modifiers :modifiers}]
  (let [factor (cond
                 ((get target-modifiers :weak #{}) atk-type) 2
                 ((get target-modifiers :immune #{}) atk-type) 0
                 :else 1)]
    (* factor units atk)))

(defn select-target
  [{:keys [system]
    :as   attacker} systems]
  (->> (systems (enemy system))
       (vals)
       (sort-by (fn [enemy]
                  [(- (estimate-damage attacker enemy))
                   (- (effective-power enemy))
                   (- (:initiative enemy))]))
       (remove #(zero? (estimate-damage attacker %)))
       (first)))

(def target-selection-criteria (juxt (comp - effective-power)
                                     (comp - :initiative)))

(defn select-targets
  [systems]
  (:result
   (reduce (fn [{:keys [defending]
                 :as   state} attacker]
             (if-let [{:keys [id system]} (select-target attacker defending)]
               (-> state
                   (update :result conj [attacker [system id]])
                   (update-in [:defending system] dissoc id))
               state))
           {:result    []
            :defending systems}
           (sort-by target-selection-criteria (all-groups systems)))))

(defn resolve-damage
  [attacker {:keys [units hp]
             :as   defender}]
  (let [damage          (estimate-damage attacker defender)
        total-hp        (* hp units)
        remaining-hp    (- total-hp damage)
        remaining-units (max (int (Math/ceil (/ remaining-hp hp))) 0)]
    (assoc defender :units remaining-units)))

(defn fight-step
  [systems]
  (reduce (fn [res [{:keys [system id]} defender]]
            (let [attacker (get-in res [system id])
                  defender (get-in res defender)]
              (if (and attacker defender)
                (let [{:keys [id system units]
                       :as   defender} (resolve-damage attacker defender)]
                  (if (zero? units)
                    (update res system dissoc id)
                    (assoc-in res [system id] defender)))
                res)))
          systems
          (->> (select-targets systems)
               (sort-by (comp (comp - :initiative) first)))))

(defn fight-seq
  [systems]
  (let [next-state (fight-step systems)]
    (lazy-seq (if (= systems next-state)
                [systems]
                (cons systems (fight-seq next-state))))))

(defn fight
  [systems]
  (last (fight-seq systems)))

(defn count-units
  [systems]
  (->> (all-groups systems)
       (map :units)
       (reduce +)))

(defn part01
  [input]
  (->> (fight input)
       (count-units)))

(defn boost
  [{:keys [immune] :as systems} v]
   (assoc systems :immune (collections/map-vals #(update % :atk + v) immune)))

(defn immune-win?
  [{:keys [immune infection]}]
  (and (< 0 (count immune))
       (zero? (count infection))))

(defn part02
  [input]
  (->> (range)
       (some (fn [factor]
               (let [boosted (boost input factor)
                     result  (fight boosted)]
                 (when (immune-win? result)
                   result))))
       (count-units)))
