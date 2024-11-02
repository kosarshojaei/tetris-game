package logic.bricks;


import javafx.scene.paint.Color;
import logic.Direction;
import logic.GameBoard;

import java.util.Random;

abstract public class  Brick {
    public int x, y;
    static int HEIGHT = 3, WIDTH = 3;
    Color color;
    boolean[][] shape;
    GameBoard gameBoard;

    public Brick(GameBoard gameBoard, int x, int y) {
        this.gameBoard = gameBoard;
        this.x = x;
        this.y = y;
        color = randomColor();
        shape = new boolean[HEIGHT][WIDTH];
        fillShape();
    }

    public int getHEIGHT() {
        return HEIGHT;
    }

    public Brick(GameBoard gameBoard) {
        this(gameBoard, 0, 0);
    }

    public int getWIDTH() {
        return WIDTH;
    }

    private Color randomColor() {
        Color returnPaint;
        switch (new Random(System.currentTimeMillis()).nextInt(7)) {
            case 0: 
                returnPaint = Color.BURLYWOOD;
                break;
            case 1:
                returnPaint = Color.AQUA;
                break;
            case 2:
                returnPaint = Color.BLUEVIOLET;
                break;
            case 3:
                returnPaint = Color.DARKGREEN;
                break;
            case 4:
                returnPaint = Color.YELLOW;
                break;
            case 5:
                returnPaint = Color.RED;
                break;
            case 6:
                returnPaint = Color.AZURE;
                break;
            default:
                returnPaint = Color.WHITE;
                break;
        }
        return returnPaint;
    }

    public Color getColor() {
        return color;
    }

    public boolean[][] getShape() {
        return shape;
    }


    public void paint() {
        gameBoard.paint();
    }

    public void freeze() {
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                if (shape[i][j]) {
                    gameBoard.fill(x + i, y + j);
                }
            }
        }
    }

    public boolean move(Direction direction) {
        if (direction == Direction.RIGHT) {
            for (int i = 0; i < WIDTH; i++) {
                for (int j = 0; j < HEIGHT; j++) {
                    if (shape[i][j] && gameBoard.isFilled(x + i + 1, y + j)) {
                        return false;
                    }
                }
            }
            x++;
            paint();
        } else if (direction == Direction.LEFT) {
            for (int i = 0; i < WIDTH; i++) {
                for (int j = 0; j < HEIGHT; j++) {
                    if (shape[i][j] && gameBoard.isFilled(x + i - 1, y + j)) {
                        return false;
                    }
                }
            }
            x--;
            paint();
        } else {
            for (int i = 0; i < WIDTH; i++) {
                for (int j = 0; j < HEIGHT; j++) {
                    if (shape[i][j] && gameBoard.isFilled(x + i, y + j + 1)) {
                        return false;
                    }
                }
            }
            y++;
            paint();
        }
        return true;
    }

    abstract void fillShape();

    abstract public void rotate();

}
