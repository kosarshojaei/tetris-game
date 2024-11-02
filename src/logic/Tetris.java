package logic;

import javafx.scene.paint.Color;
import ui.GameController;

public class Tetris {
    private static Tetris instance = new Tetris();
    private GameBoard gameBoard;
    private SettingManager settingManger;
    private ScoreManager scoreManager;
    private int score;
    private int heart;
    private int level;
    private GameController gameController;

    private Tetris() {
        settingManger = new SettingManager();
        scoreManager = new ScoreManager();
        load();

        //initialize values
        newgame();
        gameBoard = new GameBoard(this, level);
    }

    public GameController getGameController() {
        return gameController;
    }

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public SettingManager getSettingManger() {
        return settingManger;
    }


    public ScoreManager getScoreManager() {
        return scoreManager;
    }


    public int getScore() {
        return score;
    }


    public int getHeart() {
        return heart;
    }

    public void die() {
        this.heart--;
    }


    public int getLevel() {
        return level;
    }

    public static Tetris getInstance() {
        return instance;
    }

    public void newgame() {
        level = settingManger.getStartLevel();
        heart = 3;
        score = 0;
    }

    public boolean load() {
        return settingManger.load() & scoreManager.load();
    }

    public boolean save() {
        return settingManger.save() & scoreManager.save();
    }

    public void moveDown() {
        gameBoard.moveDown();
    }

    public void move(Direction direction) {
        gameBoard.move(direction);
    }

    public void rotate() {
        gameBoard.rotate();
    }

    public void start() {
        gameBoard.start();
    }

    public void stop() {
        gameBoard.stop();
    }

    public Color getBackgroundColor() {
        return settingManger.getBackgroundColor();
    }

    public void rowCleared() {
        score += settingManger.getScorePerRow();
        int level = Integer.min((score / 100) + 1, 10);
        if (level != this.level) {
            this.level = level;
            gameController.levelup();
        }
    }
}
