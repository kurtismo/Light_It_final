package com.lonespark.lightit;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.constraint.solver.widgets.Rectangle;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import java.util.Random;

import static java.lang.Math.PI;
import static java.lang.Math.floor;


public class GameView extends View {
    Game mGame = new Game();
    private GestureDetector mGestureDetector;
    private Random rand = new Random();
    private Paint gridPaint;
    Rect rect = new Rect();
    private int[] colours = {Color.parseColor("#00a6b6"), Color.WHITE};
    boolean firstDraw = true;
    int sideLength;

    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        gridPaint = new Paint();
        mGestureDetector = new GestureDetector(context, new MyGestureListener());
    }

    @SuppressLint("DrawAllocation")
    public void onDraw(Canvas canvas) {
        int rectanglex;
        int rectangley;
        int colour;
        int width = this.getWidth();
        int screenHeight = this.getHeight();

        TextView txtMoves = ((GameActivity) getContext()).findViewById(R.id.txtMoves);
        txtMoves.setText("Moves Remaining: " + mGame.getMovesRemaining());

        sideLength = width/5;
        gridPaint.setStyle(Paint.Style.FILL);
        gridPaint.setAntiAlias(true);

        if (firstDraw) {
            for(int i = 0; i < 5; i++) {
                for(int j = 0; j < 5; j++) {
                    colour = colours[rand.nextInt(2)];
                    mGame.mData[i][j] = colour == colours[0];
                    gridPaint.setColor(colour);

                    rectanglex = sideLength * i;
                    rectangley = sideLength * j;

                    rect = new Rect(rectanglex+(sideLength / 5), rectangley+(sideLength / 5), rectanglex+sideLength, rectangley+sideLength);
                    canvas.drawRect(rect, gridPaint);

                    firstDraw = false;
                }
            }
        }
        else {
            for(int i = 0; i < 5; i++) {
                for(int j = 0; j < 5; j++) {
                    if (mGame.mData[i][j]) {
                        gridPaint.setColor(colours[0]);
                    }
                    else {
                        gridPaint.setColor(colours[1]);
                    }


                    rectanglex = sideLength * i;
                    rectangley = sideLength * j;

                    rect = new Rect(rectanglex+(sideLength / 5), rectangley+(sideLength / 5), rectanglex+sideLength, rectangley+sideLength);
                    canvas.drawRect(rect, gridPaint);
                }
            }
        }

        if (mGame.checkWin()) {
            SharedPreferences prefs = getContext().getSharedPreferences("highScore", Context.MODE_PRIVATE);
            if (mGame.getMoveCount() < prefs.getInt("ScoreKey", 0)) {
                SharedPreferences.Editor editor = prefs.edit();
                editor.putInt("ScoreKey", mGame.getMoveCount());
                editor.commit();
            }
            Intent intent = new Intent(getContext(), MainActivity.class);
            getContext().startActivity(intent);
        }
    }

    //Handles player button presses
    public boolean onTouchEvent(MotionEvent event) {
        boolean r = mGestureDetector.onTouchEvent(event);
        if(!r) {
            if(event.getAction() == MotionEvent.ACTION_UP) {
                r = true;
            }
        }
        return super.onTouchEvent(event) || r;
    }



    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDown(MotionEvent ev) {
            return true;
        }

        public boolean onSingleTapConfirmed(MotionEvent event) {
            double x = floor(event.getX());
            double y = floor(event.getY());

            mGame.touch(y, x, sideLength);
            invalidate();



            return true;
        }
    }
}
