(ns tictactoe.core
  (:require [re-frame.core :refer [dispatch subscribe]]
            [reagent.core :as r]
            [tictactoe.db :as db]
            [tictactoe.events :as events]
            [tictactoe.subs :as sub]
            [tictactoe.view :as view]
            [tictactoe.dispatch-handler :as dh]))

(enable-console-print!)
(prn "HELLO tictactoe")


(defn page []
  [:div
   [:h4 "tictactoe"]
   [view/score-view]
   [view/player-turn-view]
   [view/restart-game-view]
   [view/game-view]])

(dispatch [::events/intialize-game])
(r/render [page]
         (js/document.getElementById "app"))
