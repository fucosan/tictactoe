(ns tictactoe.db)

(def game-db {:board [["e" "e" "e"]
                      ["e" "e" "e"]
                      ["e" "e" "e"]]
              :board-size 3
              :game-status "playing"
              :player-turn "x"
              :player-score {"x" 0 "o" 0}})
