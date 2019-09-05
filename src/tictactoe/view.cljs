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


(defn who-win?
  [data]
  (let [n (count data) fdata (flatten data)
        sknario (concat
                 data
                 (apply map vector data)
                 [(map #(nth fdata %) (range 0 (* n n) (inc n)))]
                 [(map #(nth fdata %) (range (dec n) (- (* n n) (- n 1)) (dec n)))])]
    (->> sknario
         (filter (fn [a] (apply = a)))
         flatten
         (some #{:x :o}))))

 

(defn game-status-view
  []
  [:div#game-status
   (let [st @(subscribe [::subs/game-status])]
     (cond
       (= :x st) "X WIN!!"
       (= :o st)  "O WIN!!"
       (= "draw" st) "DRAW!!"
       :else #()))])

(defn board-view
  []
  (let [size @(subscribe [::subs/board-size])
        cell (fn [disp sub] [:div {:on-click (if (= :e @(subscribe sub))
                                              #(dispatch disp) false)}
                            (if (not= :e @(subscribe sub)) @(subscribe sub) "")])
        content (fn [p] (for [x (range size)
                             y (range size)]
                         [p x y]))]
    (into [:div#content]
          (map
           #(cell % %2)
           (content ::events/change-board-cell)
           (content ::subs/board-cell)))))

(defn tictactoe-view
  []
  (let [board @(subscribe [::subs/board])
        winner? (who-win? board)]
    (cond
      winner? (do (dispatch [::events/change-game-status winner?])
                  (dispatch [::events/change-player-score winner?])
                  [game-status-view])

      (nil? (some #(= :e %) (flatten board))) (do (dispatch [::events/change-game-status "draw"])
                                                  [game-status-view])

      :else [board-view])))




