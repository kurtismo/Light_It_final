package com.lonespark.lightit;


public class Game {
    private int moveCount = 0;
    private int movesRemaining = 50;
    boolean[][] mData = new boolean[5][5];

    public void colourSwitch(int x, int y) {
        mData[x][y] = !mData[x][y];
    }

    public int getMoveCount() {
        return this.moveCount;
    }

    public int getMovesRemaining() {
        return movesRemaining;
    }

    public void touch(double x, double y, int sideLength) {
        for(int i = 0; i < 5; i++) {
            for(int j = 0; j < 5; j++) {
                if ((x > (sideLength*i)+(sideLength/5)) && (x < ((sideLength*i)+(sideLength/5)) + sideLength)) {
                        if((y > (sideLength*j)+(sideLength/5)) && (y < ((sideLength*j)+(sideLength/5)) + sideLength)) {
                            System.out.println("button pressed is: " + i + ", " + j);
                            colourSwitch(j, i);
                            if (j < 4) {colourSwitch(j+1, i);}
                            if (j > 0) {colourSwitch(j-1, i);}
                            if (i < 4) {colourSwitch(j, i+1);}
                            if (i > 0) {colourSwitch(j, i-1);}
                            moveCount++;
                            movesRemaining--;
                        }
                }
            }
        }
    }

    public boolean checkWin() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (!mData[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }
}
