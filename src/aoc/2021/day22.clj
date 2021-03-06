(ns aoc.2021.day22
  (:require [aoc.parse :as parse]
            [aoc.file :as file]))

(def line-re
  #"^(on|off) x=(-?\d+)..(-?\d+),y=(-?\d+)..(-?\d+),z=(-?\d+)..(-?\d+)$")

(def op->int {"on" true "off" false})

(defn parse-line
  [line]
  (let [[_ op & rest] (re-find line-re line)
        [x-min x-max y-min y-max z-min z-max] (mapv parse/string->int rest)]
    [(op->int op) [x-min y-min z-min] [x-max y-max z-max]]))

(def example-small (file/read-lines parse-line "2021/day22-example-small.txt"))

(def example-larger (file/read-lines parse-line "2021/day22-example-larger.txt"))

(def input (file/read-lines parse-line "2021/day22.txt"))

(defn some-smaller?
  [cube-1 cube-2]
  (->> (map vector cube-1 cube-2)
       (some (fn [[a b]] (< a b)))))

(defn disjoint?
  [[_ cube-min cube-max] [_ cube-min' cube-max']]
  (or (some-smaller? cube-max cube-min')
      (some-smaller? cube-max' cube-min)))

(defn intersect
  [op [_ cube-min cube-max :as cube-1] [_ cube-min' cube-max' :as cube-2]]
  (when-not (disjoint? cube-1 cube-2)
    [op (mapv max cube-min cube-min') (mapv min cube-max cube-max')]))

(defn sum
  [coll]
  (reduce (fn [cubes [turn :as cube]]
            (let [intersections (reduce (fn [acc [turn' :as other-cube]]
                                          (if-let [v (intersect (not turn') cube other-cube)]
                                            (conj acc v)
                                            acc))
                                        []
                                        cubes)]
              (into cubes (if turn
                            (conj intersections cube)
                            intersections))))
          []
          coll))

(defn volume
  [[_ cube-min cube-max]]
  (->> (mapv (comp inc -) cube-max cube-min)
       (reduce *)))

(defn volume-all
  [cubes]
  (->> cubes
       (map (fn [[turn :as cube]]
              (* (volume cube) (if turn 1 -1))))
       (reduce + 0)))

(defn part01
  [input]
  (let [area [true [-50 -50 -50] [50 50 50]]]
    (->> input
         (filter (fn [[turn :as cube]]
                   (intersect turn cube area)))
         (sum)
         (volume-all))))

(defn part02
  [input]
  (->> input
       (sum)
       (volume-all)))

