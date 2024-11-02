package logic.bricks;

import logic.GameBoard;

//OO#
//OO#
//OO#
public class Brick5 extends Brick {
    int rotation = 0;

    public Brick5(GameBoard gameBoard, int x, int y) {
        super(gameBoard, x, y);
    }

    public Brick5(GameBoard gameBoard) {
        super(gameBoard);
    }

    @Override
    void fillShape() {
        shape[1][0] = true;
        shape[1][1] = true;
        shape[1][2] = true;
    }

    @Override
    public void rotate() {
        rotation = ++rotation % 2;

        if (rotation == 0) {
            //O#O
            //O#O
            //O#O
            shape = new boolean[WIDTH][HEIGHT];
            shape[2][0] = true;
            shape[2][1] = true;
            shape[2][2] = true;
        } else{
            //OOO
            //###
            //OOO
            shape = new boolean[WIDTH][HEIGHT];
            shape[0][1] = true;
            shape[1][1] = true;
            shape[2][1] = true;
        }
    }
}
