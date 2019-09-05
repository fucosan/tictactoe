(ns tictactoe.events
  (:require [re-frame.core :refer [reg-event-db dispatch]]
            [tictactoe.db :refer [game-db]]))

(reg-event-db
 ::intialize-game
 (fn [_ _]
   game-db))

(reg-event-db
 ::restart-game
 (fn [db]
   (-> (assoc db :board [[:e :e :e]
                         [:e :e :e]
                         [:e :e :e]])
       (#(assoc % :player-turn (game-db :player-turn))))))

(reg-event-db
 ::change-board-size
 (fn [db [_ size]]
   (assoc db :board-size size)))

(reg-event-db
 ::change-player-turn
 (fn [db]
   (assoc db :player-turn (if (= :o (db :player-turn)) :x :o))))

(reg-event-db
 ::change-board-cell
 (fn [db [_ i j]]
   (-> (assoc-in db [:board i j] (db :player-turn))
       (#(assoc % :player-turn (if (= :o (% :player-turn)) :x :o))))))

(reg-event-db
 ::change-player-score
 (fn [db [_ winner]]
   (update-in db [:player-score winner] inc)))

(reg-event-db
 ::change-game-status
 (fn [db [_ status]]
   (assoc db :game-status status)))
