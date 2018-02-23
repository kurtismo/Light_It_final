package com.lonespark.lightsout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.util.Random;

import static java.lang.Math.floor;


public class GameView extends View {
    private InterstitialAd mInterstitialAd;
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

        mInterstitialAd = new InterstitialAd(getContext());
        mInterstitialAd.setAdUnitId("ca-app-pub-3935766831192873/9148723139");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

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
        txtMoves.setText("Moves: " + mGame.getMoveCount());

        sideLength = width/6;
        gridPaint.setStyle(Paint.Style.FILL);
        gridPaint.setAntiAlias(true);

        if (firstDraw) {
            for(int i = 0; i < 6; i++) {
                for(int j = 0; j < 6; j++) {
                    colour = colours[rand.nextInt(2)];
                    mGame.mData[i][j] = colour == colours[0];
                    gridPaint.setColor(colour);

                    rectanglex = sideLength * i;
                    rectangley = sideLength * j;

                    rect = new Rect(rectanglex+(sideLength / 6), rectangley+(sideLength / 6), rectanglex+sideLength, rectangley+sideLength);
                    canvas.drawRect(rect, gridPaint);

                    firstDraw = false;
                }
            }
        }
        else {
            for(int i = 0; i < 6; i++) {
                for(int j = 0; j < 6; j++) {
                    if (mGame.mData[i][j]) {
                        gridPaint.setColor(colours[0]);
                    }
                    else {
                        gridPaint.setColor(colours[1]);
                    }


                    rectanglex = sideLength * i;
                    rectangley = sideLength * j;

                    rect = new Rect(rectanglex+(sideLength / 6), rectangley+(sideLength / 6), rectanglex+sideLength, rectangley+sideLength);
                    canvas.drawRect(rect, gridPaint);
                }
            }
        }

        if (mGame.checkWin()) {
            if (mInterstitialAd.isLoaded()) {
                if (rand.nextInt(5) == 3) {
                    mInterstitialAd.show();
                    mInterstitialAd.setAdListener(new AdListener() {
                        public void onAdClosed() {
                            SharedPreferences prefs = getContext().getSharedPreferences("highScore", Context.MODE_PRIVATE);
                            if ((mGame.getMoveCount() < prefs.getInt("ScoreKey", 0)) || (prefs.getInt("ScoreKey", 0) == 0)) {
                                SharedPreferences.Editor editor = prefs.edit();
                                editor.putInt("ScoreKey", mGame.getMoveCount());
                                editor.commit();
                            }
                            Intent intent = new Intent(getContext(), MainActivity.class);
                            getContext().startActivity(intent);
                        }
                    });
                }
                else {
                    SharedPreferences prefs = getContext().getSharedPreferences("highScore", Context.MODE_PRIVATE);
                    if ((mGame.getMoveCount() < prefs.getInt("ScoreKey", 0)) || (prefs.getInt("ScoreKey", 0) == 0)) {
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putInt("ScoreKey", mGame.getMoveCount());
                        editor.commit();
                    }
                    Intent intent = new Intent(getContext(), MainActivity.class);
                    getContext().startActivity(intent);
                }
            }
            else {
                SharedPreferences prefs = getContext().getSharedPreferences("highScore", Context.MODE_PRIVATE);
                if ((mGame.getMoveCount() < prefs.getInt("ScoreKey", 0)) || (prefs.getInt("ScoreKey", 0) == 0)) {
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putInt("ScoreKey", mGame.getMoveCount());
                    editor.commit();
                }
                Intent intent = new Intent(getContext(), MainActivity.class);
                getContext().startActivity(intent);
            }
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
