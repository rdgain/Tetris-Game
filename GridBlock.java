package Tetris2;


public class GridBlock {

    /**
     * filled - boolean, true if the block is filled / exists, false otherwise
     * color - integer, indicates the block color
     * x, y - integer, block coordinate
     */
    boolean filled;
    int color;
    int x,y;

    /**
     * constructor
     * creates the block as not filled, default color 0 (red)
     * @param xI - x coordinate
     * @param yI - y coordinate
     */
    public GridBlock(int xI, int yI) {
        filled = false;
        color = 0;
        x = xI;
        y = yI;
    }

    /**
     * modifies the filled parameter
     * @param f - boolean, true if filled, false otherwise
     */
    public void setFilled(boolean f) {
        filled = f;
    }

    /**
     * checks if a block is filled
     * @return - filled (true if filled, false otherwise)
     */
    public boolean isFilled() {
        return filled;
    }

    /**
     * sets the block color
     * @param c - specifies the new color of the block
     */
    public void setColor(int c) {
        color = c;
    }

    /**
     * @return - block color
     */
    public int getColor() {
        return color;
    }

    /**
     * @return - x coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * @return - y coordinate
     */
    public int getY() {
        return y;
    }

    /**
     * sets the X and Y coordinates of the block
     * @param newX - new X coordinate
     * @param newY - new Y coordinate
     */
    public void setXY(int newX, int newY) {
        x = newX;
        y = newY;
    }

    /**
     * moves the block down one step
     */
    public void moveDown(){
        y++;
    }

    /**
     * moves the block left one step
     */
    public void moveLeft(){
        x--;
    }

    /**
     * moves the block right one block
     */
    public void moveRight(){
        x++;
    }
}
