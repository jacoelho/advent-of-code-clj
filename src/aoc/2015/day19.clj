(ns aoc.2015.day19
  (:require
   [aoc.file :as file]
   [clojure.test :refer [testing is]]
   [clojure.string :as str]))

(defn parse-replace
  [line]
  (let [[_ source destination] (re-find #"^(\w+) => (\w+)$" line)]
    {source destination}))

(def input
  (let [input        (file/read-lines str/split-lines "2015/day19.txt")
        medicine     (peek input)
        replacements (->> (drop-last 2 input)
                          (map (comp parse-replace first))
                          (group-by (comp first keys))
                          (reduce-kv #(assoc %1 %2 (mapcat vals %3)) {}))]
    [replacements (first medicine)]))

(defn replace-single
  [^String s ^long i ^long j ^String replace]
  (let [sb (StringBuilder. s)]
    (.replace sb i j replace)
    (.toString sb)))

(defn subs-safe
  [s i]
  (when (>= (count s) (inc i))
    (subs s i (inc i))))

(defn replacements
  [mapping medicine]
  (let [replaces (set (keys mapping))] 
    (loop [i      (long 0)
           result []]
      (if (= i (count medicine))
        (set result)
        (let [atom-1                 (subs-safe medicine i)
              atom-2                 (subs-safe medicine (inc i))
              [end-idx translations] (cond
                                       (replaces (str atom-1 atom-2)) [2 (get mapping (str atom-1 atom-2))]
                                       (replaces atom-1) [1 (get mapping atom-1)]
                                       :else [0 []])]
          (recur
           (inc i)
           (into result (map (partial replace-single medicine i (+ i end-idx)) translations))))))))

(defn part01
  [medicine translations]
  (count (replacements translations medicine)))

(testing "Part 01"
  (is (= 576 (part01 (second input) (first input)))))

(defn complex->from [m]
  (reduce-kv (fn [m k v]
               (reduce #(assoc % %2 k) m v))
             {} m))

(defn compact
  [^String s mapping ^String goal]
  (loop [steps    0
         molecule s]
    (let [reverse-mapping (complex->from mapping)
          keys'           (shuffle (keys reverse-mapping))]
      (if (= goal molecule) 
        steps
        (let [[v idx] (->> keys'
                           (some (fn [v]
                                   (let [idx (.indexOf molecule v)]
                                     (when (not (neg? idx))
                                       [v idx])))))]
          (if (or (nil? v)
                  (nil? idx))
            -1
            (recur
             (inc steps)
             (replace-single molecule idx (+ idx (count v)) (get reverse-mapping v)))))))))

;; brute force
(defn part02
  [medicine translations]
  (apply min (filter pos? 
                     (take 40 (repeatedly (fn []
                                            (compact medicine translations "e")))))))

(testing "Part 02"
  (is (= 207 (part02 (second input) (first input)))))