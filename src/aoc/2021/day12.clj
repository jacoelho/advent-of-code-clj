(ns aoc.2021.day12
  (:require [aoc.file :as file]))

(defn categorise-cave
  [^String cave]
  (cond
    (= cave "start") :start
    (= cave "end") :end
    (Character/isUpperCase ^Character (nth cave 0)) :big
    :else :small))

(defn parse-line
  [line]
  (let [[_ a b] (re-find #"(\w+)\-(\w+)" line)]
    [{:start a :end b}
     {:start b :end a}]))

(def input
  (->> "2021/day12.txt"
       (file/read-lines parse-line)
       (apply concat)
       (group-by :start)))

(def example
  (->> ["start-A"
        "start-b"
        "A-c"
        "A-b"
        "b-d"
        "A-end"
        "b-end"]
       (mapv parse-line)
       (apply concat)
       (group-by :start)))

(defn small-cave?
  [cave]
  (= :small (categorise-cave cave)))

(defn small-caves-visit-allowance
  [input visits]
  (into {}
        (mapv vector
              (filter small-cave? (keys input))
              (repeat visits))))

(defn caves-available
  [visited cur caves]
  (filter #(< 0 (get visited (:end %) 1)) (get caves cur)))

(defn explore'
  [caves visited path current-cave]
  (let [destinations (caves-available visited current-cave caves)]
    (cond
      (= current-cave "end") (clojure.string/join "," (conj path current-cave))
      (empty? destinations) nil
      :else (map #(explore' caves
                            (case (categorise-cave current-cave)
                              :start (assoc visited current-cave 0)
                              :small (update visited current-cave dec)
                              :big visited)
                            (conj path current-cave)
                            (:end %))
                 destinations))))

(defn explore
  [input m]
  (remove nil? (flatten (explore' input (assoc m "start" 1) [] "start"))))

(defn part01
  [input]
  (count (explore input (small-caves-visit-allowance input 1))))

(defn part02
  [input]
  (let [small-caves (filter small-cave? (keys input))
        visits (small-caves-visit-allowance input 1)]
    (->> small-caves
         (mapcat #(explore input (update visits % inc)))
         (distinct)
         (count))))

