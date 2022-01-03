(ns aoc.2017.day07
  (:require [aoc.parse :as parse]
            [clojure.string :as string]
            [aoc.file :as file]
            [clojure.set :as set]))

(defn parse-line
  [line]
  (let [[_ node weight above] (re-find #"(\w+) \((\d+)\)(?:\s*-> (.+))?" line)]
    [node {:weight (parse/string->int weight)
           :above  (if (nil? above)
                     []
                     (string/split above #", "))}]))

(defn parse-file
  [f]
  (into {} (file/read-lines parse-line f)))

(def example (parse-file "2017/day07-example.txt"))
(def input (parse-file "2017/day07.txt"))

(defn part01
  [input]
  (first (set/difference (set (keys input))
                         (set (mapcat :above (vals input))))))

(defn tower-sum
  [tower program]
  (let [{:keys [weight above]} (get tower program)]
    (+ weight
       (reduce + (map (partial tower-sum tower) above)))))

(defn unbalanced-element
  [pairs]
  (->> (group-by second pairs)
       (some (fn [[_ v]]
               (when (= 1 (count v))
                 (ffirst v))))))

(defn find-unbalanced
  [tower program]
  (when-let [{:keys [above]} (get tower program)]
    (let [siblings (->> (map (partial tower-sum tower) above)
                        (map vector above))
          found-unbalanced      (unbalanced-element siblings)]
      (if (nil? found-unbalanced)
        (mapcat (partial find-unbalanced tower) above)
        (let [difference (- (apply max (map second siblings))
                            (apply min (map second siblings)))]
          (concat [[found-unbalanced difference]]
                  (mapcat (partial find-unbalanced tower) (get-in tower [found-unbalanced :above]))))))))

(defn part02
  [input]
  (let [[node difference] (last (find-unbalanced input (part01 input)))]
    (- (get-in input [node :weight]) difference)))
