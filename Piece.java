package Tetris2;

import java.awt.*;
import java.util.ArrayList;

public class Piece {

    /**
     * initial coordinates
     * start at the middle of screen, right at the top (row 0, column 3)
     */
    static final int xI = 3, yI = 0;

    /**
     * 1 <= pieceShapeInt <= 5 ; indicates the piece shape
     *
     * 1 (square)      2 (L)      3 (line)      4 (T)     5 (Z)
     *    ..            ...         ....          .         ..
     *    ..              .                      ...         ..
     *
     * 0 <= pieceColorInt <= 5 ; indicates the piece color (index for the color vector)
     *
     * 0 = red  ;  1 = blue ; 2 = green ; 3 = orange ; 4 = pink ; 5 = lime
     * pieceColour holds the piece colour
     */
    int pieceShapeInt, pieceColorInt;

    /**
     * indicates the direction of the piece ; initially 0
     * 0 - normal; 1 - 90CW; 2 - 180CW; 3 - 270CW
     */
    int direction;
    int oldD;

    static Color[] colors = {new Color(255,0,0), new Color(0,100,255), new Color(33, 161, 91),
            new Color(255,135,20), new Color(240,120,205), new Color(161,255, 77), };
    Color pieceColor;

    /**
     * 4 ArrayLists to hold the piece edges ; contains between 0 and 4 GridBlock objects
     */
    ArrayList<GridBlock> botEdge, topEdge, leftEdge, rightEdge;

    /**
     * the actual piece, made of 4 GridBlock objects
     */
    GridBlock[] pieceShape;

    /**
     * xi, yi - pair of coordinates of GridBlock i from the piece
     */
    int x1,x2,x3,x4,y1,y2,y3,y4;
    int oldX1, oldX2, oldX3, oldX4, oldY1, oldY2, oldY3, oldY4;

    /**
     * variables to indicate collision
     */
    boolean hitLeft, hitRight, hitTop, hitBottom;

    /**
     * variables to hold new values of x and y for GridBlock 1 if they get changed
     */
    int newX, newY;

    /**
     * constructor
     * @param p - randomly generated int indicating the piece shape
     * @param c - randomly generated int indicating the piece colour
     */
    Piece (int p, int c) {

        pieceShapeInt = p;
        pieceColorInt = c;
        pieceColor = colors[pieceColorInt];

        /**
         * initialise the piece as not collided
         */
        hitLeft = false;
        hitRight = false;
        hitTop = false;
        hitBottom = false;

        /**
         * create the piece shape and initialise all coordinates to xI and yI
         */
        pieceShape = new GridBlock[4];

        x1 = xI;
        x2 = xI;
        x3 = xI;
        x4 = xI;

        y1 = yI;
        y2 = yI;
        y3 = yI;
        y4 = yI;

        /**
         * create the edge ArrayLists
         */
        botEdge = new ArrayList<GridBlock>();
        topEdge = new ArrayList<GridBlock>();
        leftEdge = new ArrayList<GridBlock>();
        rightEdge = new ArrayList<GridBlock>();

        /**
         * initialise the direction to normal (0) and the new X and Y coordinates to 0
         */
        direction = 0;
        newX = 0;
        newY = 0;

    }


    /**
     * rotates the piece by changing its direction; method overwritten in most subclasses
     * @param d - the new direction
     * @return - true if rotation succeeded, false otherwise
     */
    public boolean changeDirection (int d) {
        direction = d;
        return true;
    }

    /**
     * moves the piece one column to the left
     */
    public void moveLeft () {
        for (GridBlock i:pieceShape) {
            i.moveLeft();
        }
    }

    /**
     * moves the piece one column to the right
     */
    public void moveRight () {
        for (GridBlock i:pieceShape) {
            i.moveRight();
        }
    }

    /**
     * moves the piece one row down
     */
    public void moveDown () {
        for (GridBlock i:pieceShape) {
            i.moveDown();
        }
    }

    /**
     * sets coordinates of the 4 piece blocks to x1 - y1, x2 - y2, x3 - y3, x4 - y4
     * @return true if successful, false otherwise
     */
    public boolean setCoordinates() {
        pieceShape[0].setXY(x1, y1);
        pieceShape[1].setXY(x2, y2);
        pieceShape[2].setXY(x3, y3);
        pieceShape[3].setXY(x4, y4);
        return true;
    }

    /**
     * creates the 4 GridBlock objects that make up the piece
     */
    public void createShape() {
        pieceShape[0] = new GridBlock(x1,y1);
        pieceShape[1] = new GridBlock(x2,y2);
        pieceShape[2] = new GridBlock(x3,y3);
        pieceShape[3] = new GridBlock(x4,y4);
    }

    /**
     * checks if the coordinates are valid ( 0 <= x <= 9 and 0 <= y <= 14)
     * @return true if coordinates are valid, false otherwise
     */
    public boolean checkValidCoordinates() {
        if (x1>=0 && x1<=9 && x2>=0 && x2<=9 && x3>=0 && x3<=9 && x4>=0 && x4<=9)
            if (y1>=0 && y1<=14 && y2>=0 && y2<=14 && y3>=0 && y3<=14 && y4>=0 && y4<=14)
                return true;
        return false;
    }

    /**
     * change coordinates to the old values
     */
    public void reverseCoordinates() {
        x1 = oldX1;
        x2 = oldX2;
        x3 = oldX3;
        x4 = oldX4;

        y1 = oldX1;
        y2 = oldY1;
        y3 = oldY3;
        y4 = oldY4;
    }

    public void setEdges() {}

    public void changeCoordinates(int x,int y) {}
}
