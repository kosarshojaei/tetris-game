package logic.bricks;

import logic.GameBoard;

//O#O
//O##
//OOO
public class Brick7 extends Brick {
    int rotation = 0;

    public Brick7(GameBoard gameBoard, int x, int y) {
        super(gameBoard, x, y);
    }

    public Brick7(GameBoard gameBoard) {
        super(gameBoard);
    }

    @Override
    void fillShape() {
        shape[1][0] = true;
        shape[1][1] = true;
        shape[2][1] = true;
    }

    @Override
    public void rotate() {
        rotation = ++rotation % 4;

        if (rotation == 0) {
            //O#O
            //O##
            //OOO
            shape = new boolean[WIDTH][HEIGHT];
            shape[1][0] = true;
            shape[1][1] = true;
            shape[2][1] = true;
        } else if(rotation == 1) {
            //OOO
            //O##
            //O#O
            shape = new boolean[WIDTH][HEIGHT];
            shape[1][2] = true;
            shape[1][1] = true;
            shape[2][1] = true;

        } else if(rotation== 2){
            //OOO
            //##O
            //O#O
            shape = new boolean[WIDTH][HEIGHT];
            shape[0][1] = true;
            shape[1][1] = true;
            shape[1][2] = true;
        } else{
            //O#O
            //##O
            //OOO
            shape = new boolean[WIDTH][HEIGHT];
            shape[0][1] = true;
            shape[1][1] = true;
            shape[1][0] = true;
        }
    }
}
