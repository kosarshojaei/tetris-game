package logic;

import javafx.scene.paint.Color;

import java.io.*;
import java.util.Scanner;

public class SettingManager {
    private int scorePerRow = 10;
    private Color backgroundColor = new Color(0.1, 0.1, 0.1, 1);
    private int startLevel = 1;

    boolean load() {
        File settingFile = new File("setting.txt");
        try {
            Scanner scanner = new Scanner(settingFile);
            String[] settings = scanner.nextLine().split(",");
            this.scorePerRow = Integer.parseInt(settings[0]);
            this.startLevel = Integer.parseInt(settings[1]);
            this.backgroundColor = new Color(Double.parseDouble(settings[2]), Double.parseDouble(settings[3]), Double.parseDouble(settings[4]), Double.parseDouble(settings[5]));

        } catch (FileNotFoundException e) {
            return false;
        }
        return true;
    }

    boolean save() {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter("setting.txt");
        } catch (IOException e) {
            return false;
        }
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.println(scorePerRow + "," + startLevel + "," + backgroundColor.getRed() + "," + backgroundColor.getBlue() + "," + backgroundColor.getGreen() + "," + backgroundColor.getOpacity());
        printWriter.close();
        return true;
    }

    public int getScorePerRow() {
        return scorePerRow;
    }

    public void setScorePerRow(int scorePerRow) {
        this.scorePerRow = scorePerRow;
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public int getStartLevel() {
        return startLevel;
    }

    public void setStartLevel(int startLevel) {
        this.startLevel = startLevel;
    }
}
