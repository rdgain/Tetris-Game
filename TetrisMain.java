package Tetris2;

/**
 * main game functionality
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;

public class TetrisMain extends JPanel implements MouseListener {

    /**
     * currentPiece, nextPiece - objects of type Piece, the current piece moving in the grid and the next piece
     * shape, color - randomly generated integers indicating the piece shape and color
     * buttonClicked - string, indicates what mouse button has been clicked
     * isButtonPressed - boolean, true if a button is pressed, false otherwise
     * randomGenerator - a random generator
     * score - integer, the user score, starts at 0
     * grid - object of type Grid
     * direction - integer, starts at 0
     * background - image used for background
     * backgroundFrame - image used as a frame
     * finished - boolean, true if game finished, false otherwise; starts as false
     * rotated - boolean, true if a piece has been rotated, false otherwise; starts as false
     * triedRotation - boolean, true if piece rotation was attempted, false otherwise; starts as false
     */
    protected Piece currentPiece, nextPiece;
    int shape, color;
    int nextShape, nextColor;
    String buttonClicked = "none";
    boolean isButtonPressed;
    Random randomGenerator;
    int score = 0;
    Grid grid;
    int direction;
    Image background;
    Image backgroundFrame;
    boolean finished = false, started = false, paused = true;

    /**
     * constructor
     */
    public TetrisMain () {
        addMouseListener( this );

        grid = new Grid (10,15,100);
        randomGenerator = new Random();

        try {
            background = ImageIO.read(this.getClass().getResource("bg3.png"));
            backgroundFrame = ImageIO.read(this.getClass().getResource("bgframe.png"));
        }
        catch (IOException e) {}

        generatePiece();
        generateNextPiece();
        //grid.addPiece(currentPiece);
    }

    /**
     * overwrites paint method
     */
    public void paint (Graphics g) {

        /**
         * clears the grid
         */
        for (int i = 0 ; i < 10 ; i++) {
            for (int j = 0 ; j < 15 ; j++) {
                    g.clearRect(48 + i * 30, 37 + j * 30, 30, 30);
            }
        }

        /**
         * draws background image
         */
        super.paintComponent(g);
        g.drawImage(background, 0, 0, 393, 520, null);

        if (checkHitBottom() && started ) {

            /**
             * if the current piece hit the bottom of the grid, calculate the new score
             * make the next piece the current piece and generate a new next piece
             */
            grid.fullRow();
            grid.addScore(10);
            score = grid.getScore();
            if (!finished) {
                currentPiece = nextPiece;
                generateNextPiece();
            }

            /**
             * if the piece can't be added to the grid the game has finished
             */
            if(!grid.addPiece(currentPiece)) finished = true;

        }



        /**
         * draw the grid
         */
        for (int i = 0 ; i < 10 ; i++) {
            for (int j = 0 ; j < 15 ; j++) {
                if (grid.getGrid()[i][j].isFilled() ) {
                    g.setColor(Piece.colors[grid.getGrid()[i][j].getColor()]);
                    g.fill3DRect(48 + i * 30, 37 + j * 30, 30, 30, true);
                }
            }
        }
        for (int i = 0 ; i < 10 ; i++) {
            for (int j = 0 ; j < 15 ; j++) {
                if (!grid.getGrid()[i][j].isFilled() ) {
                    g.setColor(new Color(255,255,255,0));
                    g.fillRect(48 + i * 30, 37 + j * 30, 30, 30);
                }
            }
        }

        /**
         * draw the background frame
         */
        g.drawImage(backgroundFrame, 0, 0, 393, 520, null);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        switch ( e.getButton() )
        {
            case MouseEvent.BUTTON1 : buttonClicked = "left"   ; break ;
            case MouseEvent.BUTTON2 : buttonClicked = "middle" ; break ;
            case MouseEvent.BUTTON3 : buttonClicked = "right"  ; break ;
            default : buttonClicked = "none";
        }
        if (started && !paused && !finished) {
            if (buttonClicked == "left" && !checkHitLeft()) {
                /**
                 * if the left mouse button is clicked, move the piece to the left
                 */
                grid.addEmptyPiece(currentPiece);
                currentPiece.moveLeft();
                grid.addPiece(currentPiece);
                buttonClicked = "none";
            }
            else if (buttonClicked == "right" && !checkHitRight()) {
                /**
                 * if the right mouse button is clicked, move the piece to the right
                 */
                grid.addEmptyPiece(currentPiece);
                currentPiece.moveRight();
                grid.addPiece(currentPiece);
                buttonClicked = "none";
            }
            else if (buttonClicked == "middle") {
                /**
                 * if the middle mouse button is clicked, rotate the piece
                 */
                grid.addEmptyPiece(currentPiece);
                if (direction == 3) direction = 0;
                else direction++;
                if (!currentPiece.changeDirection(direction)) direction--;
                if (!grid.addPiece(currentPiece)) finished = true;
                buttonClicked = "none";
            }
        }
        repaint();
        e.consume();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        isButtonPressed = true;
        e.consume();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        isButtonPressed = false;
        e.consume();
    }

    @Override
    public void mouseEntered(MouseEvent e) { }

    @Override
    public void mouseExited(MouseEvent e) { }

    /**
     * checks collision against the left edge of the piece
     * @return - true if the piece hit something to the left, false otherwise
     */
    public boolean checkHitLeft () {

        for (GridBlock i:currentPiece.leftEdge) {
            int x = i.getX();
            int y = i.getY();
            if (x<=0) return true;
            else if (y>=0 && y<=14) if(grid.getGrid()[x-1][y].isFilled()) return true;
        }
        return false;
    }

    /**
     * checks collision against the right edge of the piece
     * @return - true if the piece hit something to the right, false otherwise
     */
    public boolean checkHitRight () {

        for (GridBlock i:currentPiece.rightEdge) {
            int x = i.getX();
            int y = i.getY();
            if (x>=9) return true;
            else if (y>=0 && y<=14) if(grid.getGrid()[x+1][y].isFilled()) return true;
        }

        return false;
    }

    /**
     * checks collision against the bottom edge of the piece
     * @return - true if the piece hit something at the bottom, false otherwise
     */
    public boolean checkHitBottom () {

        if (!currentPiece.botEdge.isEmpty()) {
            for (GridBlock i:currentPiece.botEdge) {
                int x = i.getX();
                int y = i.getY();
                if (y>=14) return true;
                else if (x>=0 && x<=9) if(grid.getGrid()[x][y+1].isFilled()) return true;
            }
        }

        return false;
    }

    /**
     * checks collision against the top edge of the piece
     * @return - true if the piece hit something at the top, false otherwise
     */
    public boolean checkHitTop () {

        for (GridBlock i:currentPiece.topEdge) {
            int x = i.getX();
            int y = i.getY();
            if (y<=0) return true;
            else if (x>=0 && x<=9) if(grid.getGrid()[x][y-1].isFilled()) return true;
        }

        return false;
    }

    /**
     * @return - the score
     */
    public int getScore () {
        return score;
    }

    /**
     * checks if game finished
     * @return - boolean finished, true if game finished, false otherwise
     */
    public boolean finished () {
        if (checkHitTop() && checkHitBottom() || !grid.checkSpace(currentPiece.pieceShapeInt)) finished = true;
        return finished;
    }

    /**
     * @return - the next piece
     */
    public Piece getNextPiece() {
        return nextPiece;
    }

    /**
     * @return - the current piece
     */
    public Piece getCurrentPiece() {
        return currentPiece;
    }

    /**
     * generates a current piece
     */
    public void generatePiece() {

        shape = randomGenerator.nextInt(7)+1;
        color = randomGenerator.nextInt(6);
        switch (shape) {
            case 1 : currentPiece = new SquarePiece(color); break;
            case 2 : currentPiece = new LPiece(color); break;
            case 3 : currentPiece = new LinePiece(color); break;
            case 4 : currentPiece = new TPiece(color); break;
            case 5 : currentPiece = new ZPiece(color); break;
            case 6 : currentPiece = new LPieceR(color); break;
            case 7 : currentPiece = new ZPieceR(color); break;
        }
        direction = 0;

    }

    /**
     * generates the next piece
     */
    public void generateNextPiece() {
        nextShape = randomGenerator.nextInt(7)+1;
        nextColor = randomGenerator.nextInt(6);
        switch (nextShape) {
            case 1 : nextPiece = new SquarePiece(nextColor); break;
            case 2 : nextPiece = new LPiece(nextColor); break;
            case 3 : nextPiece = new LinePiece(nextColor); break;
            case 4 : nextPiece = new TPiece(nextColor); break;
            case 5 : nextPiece = new ZPiece(nextColor); break;
            case 6 : nextPiece = new LPieceR(nextColor); break;
            case 7 : nextPiece = new ZPieceR(nextColor); break;
        }
        direction = 0;
    }

}
