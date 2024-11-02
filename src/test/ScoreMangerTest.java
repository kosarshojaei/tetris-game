package test;

import logic.Player;
import logic.ScoreManager;

public class ScoreMangerTest {
    public static void main(String[] args) {
        Player player1 = new Player(50, "player1");
        Player player2 = new Player(25, "player2");
        Player player3 = new Player(60, "player3");
        Player player4 = new Player(45, "player4");
        Player player5 = new Player(86, "player5");
        Player player6 = new Player(645, "player6");
        Player player7 = new Player(25, "player7");
        Player player8 = new Player(42, "player8");
        Player player9 = new Player(78, "player9");
        Player player10 = new Player(45, "player10");
        Player player11 = new Player(77, "player11");
        ScoreManager scoreManager = new ScoreManager();
        scoreManager.addPlayer(player1);
        scoreManager.addPlayer(player2);
        scoreManager.addPlayer(player3);
        scoreManager.addPlayer(player4);
        scoreManager.addPlayer(player5);
        scoreManager.addPlayer(player6);
        scoreManager.addPlayer(player7);
        scoreManager.addPlayer(player8);
        scoreManager.addPlayer(player9);
        scoreManager.addPlayer(player10);
        scoreManager.addPlayer(player11);

        scoreManager.save();

    }
}
