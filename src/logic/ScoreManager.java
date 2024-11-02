package logic;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class ScoreManager {

    private ArrayList<Player> players;


    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public ScoreManager() {
        players = new ArrayList<>();
        this.load();
    }

    public boolean addPlayer(Player player) {
        int size = players.size();
        if (size == 0) {
            players.add(player);
            return true;
        }
        for (int i = 0; i < size; i++) {
            if (player.getScore() > players.get(i).getScore()) {
                Player first = players.get(i);
                Player temp = null;
                for (int j = i; j < size - 1; j++) {
                    temp = players.get(j + 1);
                    players.set(j + 1, first);
                    first = temp;
                }
                if (size < 10) {
                    players.add(first);
                }
                players.set(i, player);
                return true;
            }
        }
        if (size < 10) {
            players.add(player);
            return true;
        }
        return false;
    }


    public boolean load() {
        ArrayList<Player> loadedPlayers = new ArrayList<>();
        try {
            File MyObj = new File("score.txt");
            Scanner myReader = new Scanner(MyObj);
            String[] s;
            String line;
            while (myReader.hasNextLine()) {
                line = myReader.nextLine();
                s = line.split(",");
                Player p = new Player();
                p.setName(s[0]);
                p.setScore(Integer.parseInt(s[1]));
                loadedPlayers.add(p);
            }
        } catch (Exception e) {
            return false;
        }
        players = loadedPlayers;
        return true;
    }

    public boolean save() {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter("score.txt");
        } catch (IOException e) {
            return false;
        }
        PrintWriter printWriter = new PrintWriter(fileWriter);
        for (Player player : players) {
            printWriter.println(player.getName() + "," + player.getScore());
        }
        if (players.size() == 0) {
            printWriter.println();
        }
        printWriter.close();
        return true;
    }
}
