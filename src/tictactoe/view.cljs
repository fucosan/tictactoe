(ns tictactoe.view
  (:require [re-frame.core :refer [subscribe dispatch]]
            [tictactoe.subs :as subs]
            [tictactoe.events :as events]))

(defn restart-game-view
  []
  [:div#restart
   [:button
    {:on-click #(dispatch [::events/restart-game])}
    "restart game"]])

(defn score-view
  []
  (let [x @(subscribe [::subs/player-score :x])
        o @(subscribe [::subs/player-score :o])]
    [:div#scoreview
     [:p (str "X score: " x)]
     [:p (str "O score: " o)]]))


(defn board-view5 []
  (let [cell #(subscribe [::subs/board-cell %1 %2])
        size @(subscribe [::subs/board-size])]
    (into [:div#content]
          (map
           (fn [[i j]] [:div
                       {:on-click (if (= :e @(cell i j))
                                    #(dispatch [::events/change-board-cell i j])
                                    false)}
                       (when-not (= :e @(cell i j)) @(cell i j))])

           (for [i (range size)
                 j (range size)]
             [i j])))))

 
