(ns tictactoe.subs
  (:require [re-frame.core :refer [reg-sub]]))

(reg-sub
 ::board
 (fn [db]
   (db :board)))

(reg-sub
 ::board-size
 (fn [db]
   (db :board-size)))


(reg-sub
 ::board-cell
 (fn [db [_ i j]]
   (get-in db [:board i j])))

(reg-sub
 ::player-turn
 (fn [db]
   (db :player-turn)))

(reg-sub
 ::player-score
 (fn [db [_ player]]
   (get-in db [:player-score player])))

(reg-sub
 ::game-status
 (fn [db]
   (db :game-status)))
