(ns aoc.2016.day04
  (:require [aoc.parse :as parse]
            [aoc.file :as file]
            [clojure.string :as str]))

(defn parse-line
  [line]
  (let [[_ name sector checksum] (re-find #"^([-a-z]+)+\-(\d+)\[([a-z]+)\]$" line)]
    {:name     (str/split name #"\-")
     :sector   (parse/->int sector)
     :checksum checksum}))

(def example
  (mapv parse-line ["aaaaa-bbb-z-y-x-123[abxyz]"
                    "a-b-c-d-e-f-g-h-987[abcde]"
                    "not-a-real-room-404[oarel]"
                    "totally-real-room-200[decoy]"]))

(def input
  (file/read-lines parse-line "2016/day04.txt"))

(defn is-real?
  [{:keys [name checksum]}]
  (let [v (->> name
               (apply str)
               (frequencies)
               (sort-by (fn [[a b]] [(- b) (int a)]))
               (keys)
               (apply str))]
    (= (subs v 0 5) checksum)))

(defn part01
  [input]
  (reduce (fn [acc room]
            (if (is-real? room)
              (+ acc (:sector room))
              acc))
          0
          input))

(defn caesar-cipher
  [rotation text]
  (let [alphabet "abcdefghijklmnopqrstuvwxyz"
        cipher (->> (cycle alphabet)
                    (drop rotation)
                    (take 26)
                    (zipmap alphabet))]
    (apply str (replace cipher text))))

(defn part02
  [input]
  (->> input
       (map (fn [{:keys [name sector]}]
              [sector (mapv (partial caesar-cipher sector) name)]))
       (some (fn [[sector words]]
               (if (some #(= "northpole" %) words)
                 sector)))))
