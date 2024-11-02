package logic.bricks;

import logic.GameBoard;

//OOO
//##O
//O##
public class Brick1 extends Brick {
    int rotation = 0;

    public Brick1(GameBoard gameBoard, int x, int y) {
        super(gameBoard, x, y);
    }

    public Brick1(GameBoard gameBoard) {
        super(gameBoard);
    }

    @Override
    void fillShape() {
        shape[0][1] = true;
        shape[1][1] = true;
        shape[1][2] = true;
        shape[2][2] = true;
    }

    @Override
    public void rotate() {
        rotation = ++rotation % 2;

        if (rotation == 0) {
            //OOO
            //##O
            //O##
            shape = new boolean[WIDTH][HEIGHT];
            shape[0][1] = true;
            shape[1][1] = true;
            shape[1][2] = true;
            shape[2][2] = true;
        } else {
            //O#O
            //##O
            //#OO
            shape = new boolean[WIDTH][HEIGHT];
            shape[1][0] = true;
            shape[0][1] = true;
            shape[1][1] = true;
            shape[0][2] = true;
        }
    }
}
