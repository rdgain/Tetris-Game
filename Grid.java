package Tetris2;

public class Grid {

    /**
     * the gird is a 2D array of GridBlock objects
     * width - integer, holds the width of the grid
     * height - integer, holds the height of the grid
     * score - integer, the score obtained by the user on the current grid
     * scoreMultiplier - integer, the amount by which the score will be multiplied
     * if the user completes more than 1 row at a time
     */
    GridBlock[][] grid;
    int width, height;
    int score, scoreMultiplier;

    /**
     * constructor
     * @param w - width of the grid
     * @param h - height of the grid
     * @param m - score multiplier
     * score initialised to 0
     * creates the 2D array of GridBlock objects
     */
    public Grid(int w, int h, int m) {
        width = w;
        height = h;
        grid = new GridBlock[width][height];
        for (int i=0;i<width;i++) {
            for (int j=0;j<height;j++) {
                grid[i][j] = new GridBlock(i,j);
            }
        }

        score = 0;
        scoreMultiplier = m;
    }

    /**
     * @return - the 2D array of GridBlock objects
     */
    public GridBlock[][] getGrid() {
        return grid;
    }

    /**
     * modifies the grid
     * @param g - the new grid
     */
    public void setGrid(GridBlock[][] g) {
        grid = g;
    }

    /**
     * adds a new GridBlock to the grid
     * @param b - the new GridBlock
     * @return - true if successful, false otherwise
     * the addition is successful if the GridBlock at position (x,y) doesn't already exist
     */
    public boolean addBlock (GridBlock b) {
        int x = b.getX();
        int y = b.getY();
        if (!grid[x][y].isFilled())
            if (x>=0 && x<=width && y>=0 && y<=height)
            {
                grid[x][y].setFilled(b.isFilled());
                grid[x][y].setColor(b.getColor());
                return true;
            }
        return false;
    }

    /**
     * adds an empty GridBlock object to the grid (erases the current block at position (x,y) )
     * @param b - the GridBlock object to be removed from the grid
     */

    public void addEmptyBlock (GridBlock b) {
        int x = b.getX();
        int y = b.getY();
        if (x>=0 && x<=width && y>=0 && y<=height) {
            grid[x][y].setFilled(false);
        }
    }

    /**
     * adds a new piece to the grid
     * @param p - the piece to be added
     * @return - true if successful, false otherwise
     * adds all the GridBlock objects belonging to the piece
     * fails if any of the GridBlock objects fails to be added or if there is no space
     */
    public boolean addPiece (Piece p) {
        if(checkSpace(p.pieceShapeInt))
            for (GridBlock i:p.pieceShape) {
                if (!this.addBlock(i)) {
                    return false;
                }
            }
        else return false;
        return true;
    }

    /**
     * adds an empty piece to the grid (by removing the piece at that location)
     * @param p - piece to be removed
     */
    public void addEmptyPiece (Piece p) {
        for (GridBlock i:p.pieceShape) {
            this.addEmptyBlock(i);
        }
    }

    /**
     * checks if there is a complete row in the grid
     * calls the deleteGridRow method if true and calculates the new score
     */
    public void fullRow () {
        boolean[] isRow = new boolean[height];
        int multiple = 0;
        for (int j=0;j<height;j++){
            isRow[j]=true;
            for (int i = 0 ; i < width ; i++) {
                if(!grid[i][j].isFilled()) isRow[j]=false;
            }
            if (isRow[j]) {
                multiple++;
                score += multiple*scoreMultiplier;
                this.deleteGridRow(j);
            }
        }
    }

    /**
     * deletes a row in the grid by moving the GridBlocks above one row down
     * @param r - row to be deleted
     */
    public void deleteGridRow (int r) {
        for (int j = r; j >= 1; j--) {
            for (int i = 0; i < 10; i++) {
                grid[i][0].setFilled(false);
                grid[i][j].setFilled(grid[i][j-1].isFilled());
                if (grid[i][j-1].isFilled()) {
                    grid[i][j].setColor(grid[i][j-1].getColor());
                }
            }
        }
    }


    /**
     * @return - the score
     */
    public int getScore () {
        return score;
    }

    /**
     * adds score
     * @param s - integer, score to be added
     */
    public void addScore (int s) {
        score += s;
    }

    /**
     * checks if there is space left at the top of the grid for a new piece
     * @param shape - integer, indicates the piece shape
     * @return - true if there is space, false otherwise
     */
    public boolean checkSpace(int shape) {
        boolean[] emptyRow = new boolean[height];
        for (int i=0; i<height; i++) {
            emptyRow[i] = true;
        }
        for (int i=0; i<width; i++) {
            for (int j=0; j<height; j++) {
                if (grid[i][j].isFilled()) emptyRow[i]=false;
            }
        }
        if(height>3) {
            for (int i=3;i<height;i++) {
                if (emptyRow[i]) { return true;}
            }
        }
        if (emptyRow[2]) return true;
        else if (shape == 4) return false;
        else if (emptyRow[1]) return true;
        else if (shape == 1 || shape == 2 || shape == 5 || shape == 6 || shape == 7) return false;
        else if (emptyRow[0]) return true;
        else return false;
    }
}
