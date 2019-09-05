(ns tictactoe.dispatch-handler
  (:require [re-frame.core :refer [dispatch]]
            [tictactoe.events :as events]))

(defn winner?
  "return winner (x or o) or return nil"
  [data]
  (let [n (count data) fdata (flatten data)
        sknario (concat
                 data
                 (apply map vector data)
                 [(map #(nth fdata %) (range 0 (* n n) (inc n)))]
                 [(map #(nth fdata %) (range (dec n) (- (* n n) (- n 1)) (dec n)))])]
    (->> sknario
         (filter (fn [a] (apply = a)))
         (flatten)
         (some #{"x" "o"}))))

(comment (winner? [["e" "e" "e"]
                   ["x" "x" "x"]
                   ["o" "o" "e"]])

         (winner? [["e" "e" "e"]
                   ["e" "e" "e"]
                   ["e" "e" "e"]]))


(defn draw?
  "i : board
   o : boolean"
  [board]
  (->> board
       (flatten)
       (some #{"e"})
       (nil?)))





