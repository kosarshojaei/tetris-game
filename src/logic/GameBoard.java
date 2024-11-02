package logic;

import javafx.scene.paint.Color;
import logic.bricks.*;

import java.util.Random;

public class GameBoard {
    private boolean[][] board;
    private State state;
    private Color backgroundColor;
    private int level;
    private static int COL = 15, ROW = 20;
    private static int CELL_SIZE = 20;
    private Tetris tetris;
    private Brick currentBrick;
    private Brick nextBrick;
    private Random random;

    public GameBoard(Tetris tetris, int level) {
        this.board = new boolean[COL][ROW];
        this.tetris = tetris;
        this.level = level;
        this.state = State.STOP;
        this.backgroundColor = tetris.getBackgroundColor();
        this.random = new Random(System.currentTimeMillis());
        this.currentBrick = generateBrick();
        this.nextBrick = generateBrick();
    }

    public State getState() {
        return state;
    }

    public int getCOL() {
        return COL;
    }

    public int getROW() {
        return ROW;
    }

    public int getCellSize() {
        return CELL_SIZE;
    }

    public Brick getCurrentBrick() {
        return currentBrick;
    }

    public Brick getNextBrick() {
        return nextBrick;
    }

    public boolean[][] getBoard() {
        return board;
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public int getLevel() {
        return level;
    }

    private Brick generateBrick() {
        int r = random.nextInt(9) + 1;
        switch (r) {
            case 1:
                return new Brick1(this, COL / 2 - 1, 0);
            case 2:
                return new Brick2(this, COL / 2 - 1, 0);
            case 3:
                return new Brick3(this, COL / 2 - 1, 0);
            case 4:
                return new Brick4(this, COL / 2 - 1, 0);
            case 5:
                return new Brick5(this, COL / 2 - 1, 0);
            case 6:
                return new Brick6(this, COL / 2 - 1, 0);
            case 7:
                return new Brick7(this, COL / 2 - 1, 0);
            case 8:
                return new Brick8(this, COL / 2 - 1, 0);
            case 9:
                return new Brick9(this, COL / 2 - 1, 0);
            default:
                return new Brick9(this, COL / 2 - 1, 0);
        }

    }

    public void start() {
        this.state = State.START;
    }

    public void stop() {
        this.state = State.STOP;
    }

    public void moveDown() {
        if (this.state == State.STOP) {
            return;
        }
        if (!currentBrick.move(Direction.DOWN)) {
            currentBrick.freeze();
            checkBoard();
            currentBrick = nextBrick;
            nextBrick = generateBrick();
            if (checkGameOver()) {
                if (tetris.getHeart() > 1) {
                    board = new boolean[COL][ROW];
                    tetris.die();
                } else {
                    tetris.getGameController().gameOver();
                }

            }
        }

    }

    private boolean checkGameOver() {
        for (int i = 0; i < currentBrick.getWIDTH(); i++) {
            for (int j = 0; j < currentBrick.getHEIGHT(); j++) {
                if (currentBrick.getShape()[i][j] && board[currentBrick.x + i][currentBrick.y + j]) {
                    return true;
                }
            }
        }
        return false;
    }

    private void checkBoard() {
        for (int j = 0; j < ROW; j++) {
            boolean completeRow = true;
            for (int i = 0; i < COL; i++) {
                if (!board[i][j]) {
                    completeRow = false;
                    break;
                }
            }
            if (completeRow) {
                for (int jj = j; jj > 1; jj--) {
                    for (int ii = 0; ii < COL; ii++) {
                        board[ii][jj] = board[ii][jj - 1];
                    }
                }
                tetris.rowCleared();
            }

        }
    }

    public void move(Direction direction) {
        if (this.state == State.STOP) {
            return;
        }
        currentBrick.move(direction);
    }

    public boolean isFilled(int x, int y) {
        try {
            return board[x][y];
        } catch (IndexOutOfBoundsException e) {
            return true;
        }
    }

    public void fill(int x, int y) {
        board[x][y] = true;
    }

    public void paint() {
        tetris.getGameController().repaint();
    }


    public void rotate() {
        if (this.state == State.STOP) {
            return;
        }
        currentBrick.rotate();
    }
}
