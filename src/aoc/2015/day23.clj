(ns aoc.2015.day23
  (:require [aoc.file :as file]
            [aoc.parse :as parse]))

(defn parse-line
  [line]
  (let [[_ instruction register-or-offset offset] (re-find #"(\w{3}) (\w|[\+\-]\d+)(?:, ([\+\-]\d+))?" line)]
    (case instruction
      "hlf" [:hlf register-or-offset]
      "tpl" [:tpl register-or-offset]
      "inc" [:inc register-or-offset]
      "jmp" [:jmp (parse/->int register-or-offset)]
      "jie" [:jie register-or-offset (parse/->int offset)]
      "jio" [:jio register-or-offset (parse/->int offset)])))

(def input
  (file/read-lines parse-line "2015/day23.txt"))

(defn half
  [v]
  (quot v 2))

(defn triple
  [v]
  (* 3 v))

(defn execute
  [{:keys [a b]} input]
  (loop [ip 0
         registers {"a" a
                    "b" b}]
    (if (or (<= (count input) ip)
            (< ip 0))
      registers
      (let [[instruction register-or-offset offset] (get input ip)
            [ip' register'] (case instruction
                              :hlf
                              [(inc ip)
                               (update registers register-or-offset half)]

                              :tpl
                              [(inc ip)
                               (update registers register-or-offset triple)]

                              :inc
                              [(inc ip)
                               (update registers register-or-offset inc)]

                              :jmp
                              [(+ ip register-or-offset)
                               registers]

                              :jie
                              [(if (even? (get registers register-or-offset))
                                 (+ ip offset)
                                 (inc ip))
                               registers]

                              :jio [(if (= 1 (get registers register-or-offset))
                                      (+ ip offset)
                                      (inc ip))
                                    registers])]
        (recur ip' register')))))

(defn part01
  [input]
  (get (execute {:a 0 :b 0} input) "b"))

(defn part02
  [input]
  (get (execute {:a 1 :b 0} input) "b"))
