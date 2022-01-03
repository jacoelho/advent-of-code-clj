(ns aoc.2016.day09
  (:require [aoc.parse :as parse]
            [aoc.file :as file]
            [clojure.string :as str]))

(def input
  (first (file/read-lines "2016/day09.txt")))

(defn parse-marker
  [line]
  (let [[_ elements count] (re-find #"\((\d+)x(\d+)\)" line)]
    (when (not (nil? elements))
      [(parse/string->int elements)
       (parse/string->int count)])))

(defn decompress
  [unloop line]
  (let [marker-start (str/index-of line \()]
    (if (nil? marker-start)
      (count line)
      (let [marker-end (str/index-of line \))
            [elements count] (parse-marker line)
            end-section (+ 1 marker-end elements)]
        (+
          marker-start
          (decompress unloop (subs line end-section))
          (* count
             (if unloop
               (decompress unloop (subs line
                                        (inc marker-end)
                                        end-section))
               (- end-section (inc marker-end)))))))))

(defn part01
  [input]
  (decompress false input))

(defn part02
  [input]
  (decompress true input))
