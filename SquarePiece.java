package Tetris2;

public class SquarePiece extends Piece {

    /**
     * constructor. invokes the constructor of the superclass
     * @param c - the piece color
     */
    public SquarePiece(int c) {
        super(1,c);

        this.createShape();
        this.setCoordinates(4,yI);

        /**
         * define the piece
         */
        for (int i=0;i<4;i++) {
            pieceShape[i].setFilled(true);
            pieceShape[i].setColor(pieceColorInt);
        }
        this.setEdges();

    }

    /**
     * sets the coordinates, checking if they're valid
     * @param x - x coordinate of block 0
     * @param y - y coordinate of block 0
     * @return - true if successful, false otherwise
     */
    public boolean setCoordinates(int x, int y) {

        x1 = x    ; y1 = y;
        x2 = x1+1 ; y2 = y1;
        x3 = x1   ; y3 = y1+1;
        x4 = x1+1 ; y4 = y1+1;

        if (checkValidCoordinates()){
                pieceShape[0].setXY(x1, y1);
                pieceShape[1].setXY(x2, y2);
                pieceShape[2].setXY(x3, y3);
                pieceShape[3].setXY(x4, y4);
                return true;
            }

        return false;
    }

    /**
     * set the piece edges
     */
    public void setEdges() {

        botEdge.clear();
        topEdge.clear();
        leftEdge.clear();
        rightEdge.clear();

        botEdge.add(pieceShape[2]);
        botEdge.add(pieceShape[3]);
        topEdge.add(pieceShape[0]);
        topEdge.add(pieceShape[1]);
        leftEdge.add(pieceShape[0]);
        leftEdge.add(pieceShape[2]);
        rightEdge.add(pieceShape[1]);
        rightEdge.add(pieceShape[3]);

    }


}
