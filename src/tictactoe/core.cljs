(ns tictactoe.core
  (:require [re-frame.core :as rf]
            [reagent.core :as r]
            [tictactoe.db :as db]
            [tictactoe.events :as events]
            [tictactoe.subs :as sub]
            [tictactoe.view :as view]))

(enable-console-print!)
(prn "HELLO tictactoe")

(defn page []
  [:div
   [:h4 "tictactoe"]
   [view/score-view]
   [view/restart-game-view]
   ;[view/board-view]
   ;[view/board-view2]
   ;[view/board-view3]
   ;[view/board-view4]
   [view/board-view5]])


(rf/dispatch [::events/intialize-game])
(r/render [page]
         (js/document.getElementById "app"))
