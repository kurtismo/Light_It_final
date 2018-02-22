package com.lonespark.lightit;


public class Game {
    private int moveCount = 0;
    boolean[][] mData = new boolean[6][6];

    public void colourSwitch(int x, int y) {
        mData[x][y] = !mData[x][y];
    }

    public int getMoveCount() {
        return this.moveCount;
    }

    public void touch(double x, double y, int sideLength) {
        for(int i = 0; i < 6; i++) {
            for(int j = 0; j < 6; j++) {
                if ((x > (sideLength*i)+(sideLength/6)) && (x < ((sideLength*i)+(sideLength/6)) + sideLength)) {
                        if((y > (sideLength*j)+(sideLength/6)) && (y < ((sideLength*j)+(sideLength/6)) + sideLength)) {
                            System.out.println("button pressed is: " + i + ", " + j);
                            colourSwitch(j, i);
                            if (j < 5) {colourSwitch(j+1, i);}
                            if (j > 0) {colourSwitch(j-1, i);}
                            if (i < 5) {colourSwitch(j, i+1);}
                            if (i > 0) {colourSwitch(j, i-1);}
                            moveCount++;
                        }
                }
            }
        }
    }

    public boolean checkWin() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                if (!mData[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }
}
