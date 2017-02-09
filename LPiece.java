package Tetris2;

public class LPiece extends Piece {

    /**
     * constructor. invokes the constructor of the superclass
     * @param c - the piece color
     */
    public LPiece(int c) {
        super(2,c);

        this.createShape();
        this.changeCoordinates(6, 1);
        this.setCoordinates();

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
     * method to change the piece coordinates, all blocks relative to block 0
     * @param x - x coordinate of block 0
     * @param y - y coordinate of block 0
     */
    public void changeCoordinates(int x,int y) {
        /**
         * keep track of old coordinates before changing them
         */
        oldX1 = x1;
        oldX2 = x2;
        oldX3 = x3;
        oldX4 = x4;

        oldY1 = y1;
        oldY2 = y2;
        oldY3 = y3;
        oldY4 = y4;

        x1 = x; y1 = y;

        /**
         * create the shape depending on piece direction
         */
        switch (direction) {
            case 0 :

                // ...
                //   .

                x2 = x ; y2 = y-1;
                x3 = x-1 ; y3 = y-1;
                x4 = x-2 ; y4 = y-1;

                break;

            case 1 :

                //  .
                //  .
                // ..

                x2 = x+1 ; y2 = y;
                x3 = x+1 ; y3 = y-1;
                x4 = x+1 ; y4 = y-2;

                break;

            case 2:

                // .
                // ...
                x2 = x ; y2 = y+1;
                x3 = x+1 ; y3 = y+1;
                x4 = x+2 ; y4 = y+1;

                break;

            case 3 :

                // ..
                // .
                // .

                x2 = x-1 ; y2 = y;
                x3 = x-1 ; y3 = y+1;
                x4 = x-1 ; y4 = y+2;

                break;
        }
    }

    /**
     * set the piece edges
     */
    public void setEdges() {

        botEdge.clear();
        topEdge.clear();
        leftEdge.clear();
        rightEdge.clear();

        switch (direction) {
            case 0 :
                topEdge.add(pieceShape[1]);
                topEdge.add(pieceShape[2]);
                topEdge.add(pieceShape[3]);

                botEdge.add(pieceShape[0]);
                botEdge.add(pieceShape[2]);
                botEdge.add(pieceShape[3]);

                rightEdge.add(pieceShape[0]);
                rightEdge.add(pieceShape[1]);

                leftEdge.add(pieceShape[0]);
                leftEdge.add(pieceShape[3]);

                break;

            case 1 :
                botEdge.add(pieceShape[0]);
                botEdge.add(pieceShape[1]);

                topEdge.add(pieceShape[0]);
                topEdge.add(pieceShape[3]);

                leftEdge.add(pieceShape[0]);
                leftEdge.add(pieceShape[2]);
                leftEdge.add(pieceShape[3]);

                rightEdge.add(pieceShape[1]);
                rightEdge.add(pieceShape[2]);
                rightEdge.add(pieceShape[3]);

                break;


            case 2 :
                botEdge.add(pieceShape[1]);
                botEdge.add(pieceShape[2]);
                botEdge.add(pieceShape[3]);

                topEdge.add(pieceShape[0]);
                topEdge.add(pieceShape[2]);
                topEdge.add(pieceShape[3]);

                leftEdge.add(pieceShape[0]);
                leftEdge.add(pieceShape[1]);

                rightEdge.add(pieceShape[0]);
                rightEdge.add(pieceShape[3]);

                break;

            case 3 :
                topEdge.add(pieceShape[0]);
                topEdge.add(pieceShape[1]);

                botEdge.add(pieceShape[0]);
                botEdge.add(pieceShape[3]);

                rightEdge.add(pieceShape[0]);
                rightEdge.add(pieceShape[2]);
                rightEdge.add(pieceShape[3]);

                leftEdge.add(pieceShape[1]);
                leftEdge.add(pieceShape[2]);
                leftEdge.add(pieceShape[3]);

                break;
        }
    }

    /**
     * method to rotate the piece
     * moves block 0 clockwise around block 1, all other blocks relative to block 0
     * @param d - the new direction
     * @return - true if successful, false otherwise
     */
    public boolean changeDirection(int d) {
        oldD = direction;
        direction = d;
        switch (d) {
            case 0 : newX = pieceShape[0].getX(); newY = pieceShape[0].getY()+2; break;
            case 1 : newX = pieceShape[0].getX()-2; newY = pieceShape[0].getY(); break;
            case 2 : newX = pieceShape[0].getX(); newY = pieceShape[0].getY()-2; break;
            case 3 : newX = pieceShape[0].getX()+2; newY = pieceShape[0].getY(); break;
        }
        if(checkValidCoordinates(newX, newY)) { setCoordinates(); setEdges(); return true; }
        else direction = oldD;
        return false;
    }

    /**
     * checks if the coordinates are valid
     * invokes method checkValidCoordinates() from the superclass
     * @param x - x coordinate for block 0
     * @param y - y coordinate for block 0
     * @return - true if coordinates are valid, false otherwise
     */
    public boolean checkValidCoordinates(int x, int y) {
        changeCoordinates(x,y);
        if(!checkValidCoordinates()) {
            reverseCoordinates();
            return false;
        }
        return true;
    }

}
