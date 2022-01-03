(ns aoc.2017.day05
  (:require [aoc.file :as file]
            [aoc.parse :as parse]))

(def input (->> "2017/day05.txt"
                (file/read-lines)
                (mapv parse/string->int)
                (map-indexed vector)
                (into {})))

(def example (->> [0 3 0 1 -3]
                  (map-indexed vector)
                  (into {})))

(defn step
  [{:keys [instructions ip] :as state}]
  (when-let [v (get instructions ip)]
    (-> state
        (update-in [:instructions ip] inc)
        (update :ip + v))))

(defn until-exit
  [step-fn state]
  (when-not (nil? state)
    (lazy-seq
      (cons state
            (until-exit step-fn (step-fn state))))))

(defn part01
  [input]
  (->> (until-exit step {:instructions input :ip 0})
       (count)
       (dec)))

(defn step-cond
  [{:keys [instructions ip] :as state}]
  (when-let [v (get instructions ip)]
    (-> state
        (update-in [:instructions ip] (if (<= 3 v) dec inc))
        (update :ip + v))))

(defn part02
  [input]
  (->> (until-exit step-cond {:instructions input :ip 0})
       (count)
       (dec)))
