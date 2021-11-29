(ns aoc.2015.day07
  (:require
   [aoc.file :as file]
   [aoc.parse :as parse]))

(def mask
  "16 bit mask"
  0xffff)

(defn gate-and
  [x y]
  (bit-and x y mask))

(defn gate-or
  [x y]
  (bit-and (bit-or x y) mask))

(defn gate-lshift
  [x y]
  (bit-and (bit-shift-left x y) mask))

(defn gate-rshift
  [x y]
  (bit-and (unsigned-bit-shift-right x y) mask))

(defn gate-not
  [x]
  (bit-and (bit-not x) mask))

(defn uint-or-string
  [value]
  (try
    (parse/->uint value)
    (catch NumberFormatException _ value)))

(defn parse-line
  [line]
  (condp re-matches line
    #"^(\w+) -> ([a-z]+)$"
    :>> (fn [[_ value result]] [:assign result (uint-or-string value)])

    #"^(\w+) AND (\w+) -> ([a-z]+)$"
    :>> (fn [[_ left right result]] [:and result (uint-or-string left) (uint-or-string right)])

    #"^(\w+) OR (\w+) -> ([a-z]+)$"
    :>> (fn [[_ left right result]] [:or result (uint-or-string left) (uint-or-string right)])

    #"^(\w+) LSHIFT (\w+) -> ([a-z]+)$"
    :>> (fn [[_ left right result]] [:lshift result (uint-or-string left) (uint-or-string right)])

    #"^(\w+) RSHIFT (\w+) -> ([a-z]+)$"
    :>> (fn [[_ left right result]] [:rshift result (uint-or-string left) (uint-or-string right)])

    #"^NOT (\w+) -> ([a-z]+)$"
    :>> (fn [[_ left result]] [:not result (uint-or-string left)])))

(def input
  (file/read-lines parse-line "2015/day07.txt"))

(defn update-circuit
  [circuit result f & args]
  (let [values (map #(get circuit % %) args)]
    (if (every? number? values)
      (assoc circuit result (apply f values))
      circuit)))

(defn emulate
  [circuit [op result argument-1 argument-2]]
  (case op
    :assign (update-circuit circuit result identity argument-1)

    :not (update-circuit circuit result gate-not argument-1)

    :and (update-circuit circuit result gate-and argument-1 argument-2)

    :or (update-circuit circuit result gate-or argument-1 argument-2)

    :lshift (update-circuit circuit result gate-lshift argument-1 argument-2)

    :rshift (update-circuit circuit result gate-rshift argument-1 argument-2)))

;; brute force seems to work ok
;; "Elapsed time: 17.869453 msecs"
(defn part01
  [input]
  (loop [circuit {}]
    (let [value (get circuit "a")]
      (if (not (nil? value))
        value
        (recur (reduce emulate circuit input))))))

;; just replace the input (-;
(def input-with-override
  (map (fn [[_ v :as line]]
         (if (= v "b")
           [:assign "b" (part01 input)]
           line)) input))
