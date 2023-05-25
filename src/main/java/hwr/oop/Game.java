package hwr.oop;

import java.util.ArrayList;
import java.util.List;

public class Game {
    List<Player> players;

    public Game(List<String> playerNames) {
        players = new ArrayList<>();
        for (String playerName :
                playerNames) {
            players.add(new Player(playerName));
        }
    }

    public List<Player> getPlayers() {
        return players;
    }

    public int getRound() {
        int currentRound = 0;
        for (Player player :
                players) {
            if (player.getRound() > currentRound) {
                currentRound = player.getRound();
            }
        }
        return currentRound;
    }

    public Player getCurrentPlayer() {
        int tempRound = 10;
        Player currentPlayer = null;
        for( int i = players.size() -1; i>=0; i--) {
            if (players.get(i).getRound() <= tempRound) {
                tempRound = players.get(i).getRound();
                currentPlayer = players.get(i);
            }
        }
        return currentPlayer;
    }
}
