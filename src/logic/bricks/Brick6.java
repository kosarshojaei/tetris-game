package logic.bricks;

import logic.GameBoard;

//OOO
//###
//#O#
public class Brick6 extends Brick {
    int rotation = 0;

    public Brick6(GameBoard gameBoard, int x, int y) {
        super(gameBoard, x, y);
    }

    public Brick6(GameBoard gameBoard) {
        super(gameBoard);
    }

    @Override
    void fillShape() {
        shape[0][1] = true;
        shape[1][1] = true;
        shape[2][1] = true;
        shape[0][2] = true;
        shape[2][2] = true;
    }

    @Override
    public void rotate() {
        rotation = ++rotation % 4;

        if (rotation == 0) {
            //OOO
            //###
            //#O#
            shape = new boolean[WIDTH][HEIGHT];
            shape[0][1] = true;
            shape[1][1] = true;
            shape[2][1] = true;
            shape[0][2] = true;
            shape[2][2] = true;
        } else if(rotation == 1) {
            //##O
            //O#O
            //##O
            shape = new boolean[WIDTH][HEIGHT];
            shape[0][0] = true;
            shape[1][0] = true;
            shape[1][1] = true;
            shape[0][2] = true;
            shape[1][2] = true;
        }
        else if(rotation== 2){
            //#O#
            //###
            //OOO
            shape = new boolean[WIDTH][HEIGHT];
            shape[0][0] = true;
            shape[2][0] = true;
            shape[0][1] = true;
            shape[1][1] = true;
            shape[2][1] = true;
        }
        else{
            //O##
            //O#O
            //O##
            shape = new boolean[WIDTH][HEIGHT];
            shape[1][0] = true;
            shape[1][1] = true;
            shape[1][2] = true;
            shape[2][0] = true;
            shape[2][2] = true;
        }
    }
}
