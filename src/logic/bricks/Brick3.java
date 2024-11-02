package logic.bricks;

import logic.GameBoard;

//OOO
//O##
//O##
public class Brick3 extends Brick {
    int rotation = 0;

    public Brick3(GameBoard gameBoard, int x, int y) {
        super(gameBoard, x, y);
    }

    public Brick3(GameBoard gameBoard) {
        super(gameBoard);
    }

    @Override
    void fillShape() {
        shape[1][1] = true;
        shape[2][1] = true;
        shape[1][2] = true;
        shape[2][2] = true;
    }

    @Override
    public void rotate() {
    }
}
