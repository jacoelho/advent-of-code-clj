(ns aoc.2018.day13
  (:require [aoc.file :as file]
            [aoc.geometry :as geometry]
            [aoc.collections :as collections]
            [clojure.string :as string]))

(def cart->track {\^ \|
                  \v \|
                  \> \-
                  \< \-})

(def directions {\^ :up
                 \v :down
                 \> :right
                 \< :left})

(defn parse-file
  [f]
  (let [grid (->> (file/read-lines f)
                  (geometry/map-grid-2d)
                  (collections/remove-vals #{\space}))]
    {:tracks (collections/map-vals #(get cart->track % %) grid)
     :carts  (->> (collections/select-vals directions grid)
                  (collections/map-vals (fn [el]
                                          {:direction (directions el)
                                           :turn      :left})))}))

(def input (parse-file "2018/day13.txt"))

(def turn-seq {:left     :straight
               :straight :right
               :right    :left})

(def turn? #{\+})

(defn update-direction
  [{:keys [direction turn] :as cart} track]
  (if (turn? track)
    (-> cart
        (assoc :direction
               (case [direction turn]
                 [:up :left] :left
                 [:up :right] :right
                 [:down :left] :right
                 [:down :right] :left
                 [:right :left] :up
                 [:right :right] :down
                 [:left :left] :down
                 [:left :right] :up
                 direction))
        (update :turn turn-seq))
    (assoc cart :direction
                (case [direction track]
                  [:up \/] :right
                  [:up \\] :left
                  [:down \\] :right
                  [:down \/] :left
                  [:right \\] :down
                  [:right \/] :up
                  [:left \/] :down
                  [:left \\] :up
                  direction))))

(def direction->step {:up    [0 -1]
                      :down  [0 1]
                      :right [1 0]
                      :left  [-1 0]})

(defn move
  [{:keys [direction]} pos]
  (mapv + (direction->step direction) pos))

(defn next-turn
  [state]
  (reduce (fn [{:keys [tracks carts] :as state} cart-pos]
            (if-let [cart (carts cart-pos)]
              (let [new-pos  (move cart cart-pos)
                    new-cart (update-direction cart (tracks new-pos))]
                (if (carts new-pos)
                  (-> state
                      (update :carts dissoc cart-pos)
                      (update :carts dissoc new-pos)
                      (update :crashes (fnil conj []) new-pos))
                  (-> state
                      (update :carts dissoc cart-pos)
                      (assoc-in [:carts new-pos] new-cart))))
              state))
          state
          (sort-by (juxt second first) (keys (:carts state)))))

(defn part01
  [input]
  (->> input
       (iterate next-turn)
       (some #(when-let [v (:crashes %)]
                (string/join "," (first v))))))

(defn part02
  [input]
  (->> input
       (iterate next-turn)
       (some #(when (= (count (:carts %)) 1)
                (->> (:carts %)
                     (keys)
                     (first)
                     (string/join ","))))))
