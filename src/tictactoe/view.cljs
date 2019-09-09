(ns tictactoe.view
  (:require [re-frame.core :refer [subscribe dispatch dispatch-sync]]
            [tictactoe.subs :as subs]
            [tictactoe.events :as events]
            [tictactoe.dispatch-handler :as dh]))

(defn restart-game-view
  []
  [:div#restart
   [:button
    {:on-click #(do (dispatch [::events/restart-board])
                    (dispatch [::events/change-game-status "playing"]))}
    "restart game"]])

(defn player-turn-view
  []
  (let [status @(subscribe [::subs/game-status])
        player-turn @(subscribe [::subs/player-turn])]
    [:p (if (= status "playing")
          (str "Player " player-turn " turn")
          "GAME OVER")]))

(defn score-view
  []
  (let [x @(subscribe [::subs/player-score "x"])
        o @(subscribe [::subs/player-score "o"])]
    [:div#scoreview
     [:p (str "X score: " x)]
     [:p (str "O score: " o)]]))


(defn cell-clicking
  [i j]
  (let [board @(subscribe [::subs/board])
        w (dh/winner? board)]
    (cond
      w
      (do 
        (dispatch [::events/change-game-status w])
        (dispatch [::events/change-player-score w]))

      (dh/draw? board)
      (dispatch [::events/change-game-status "draw"])

      :else
      (dispatch [::events/change-game-status "playing"]))))


(defn board-view []
  (let [cell #(subscribe [::subs/board-cell %1 %2])
        size @(subscribe [::subs/board-size])]
    (into [:div#content]
          (map
           (fn [[i j]] [:div
                       {:on-click (if (= "e" @(cell i j))
                                    #(do (dispatch-sync [::events/change-board-cell i j]) 
                                         (cell-clicking i j)))
                        }
                       (when-not (= "e" @(cell i j)) @(cell i j))])

           (for [i (range size)
                 j (range size)]
             [i j])))))


(defn game-status-view
  []
  [:div#game-status {:on-click #(do (dispatch [::events/restart-board])
                                    (dispatch [::events/change-game-status "playing"]))
                     }
   (let [st @(subscribe [::subs/game-status])]
     (cond
       (= "x" st) "player X WIN!!"
       (= "o" st)  "player O WIN!!"
       (= "draw" st) "DRAW!!"
       :else #()))])

(defn game-view []
  (let [game-status @(subscribe [::subs/game-status])]
    (if (= game-status "playing")
      [board-view]
      [game-status-view])))

