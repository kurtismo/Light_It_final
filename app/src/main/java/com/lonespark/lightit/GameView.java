package com.lonespark.lightit;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.constraint.solver.widgets.Rectangle;
import android.util.AttributeSet;
import android.view.View;

import java.util.Random;


public class GameView extends View {
    private Random rand = new Random();
    private Paint gridPaint;
    Rect rect = new Rect();
    private int[] colours = {Color.RED, Color.WHITE};
    boolean firstDraw = true;

    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        gridPaint = new Paint();
    }

    @SuppressLint("DrawAllocation")
    public void onDraw(Canvas canvas) {
        int rectanglex;
        int rectangley;
        int colour;
        int width = this.getWidth();
        int screenHeight = this.getHeight();
        int sideLength = (width / 5) - 50;

        gridPaint.setStyle(Paint.Style.FILL);
        gridPaint.setAntiAlias(true);

        if (firstDraw) {
            for(int i = 0; i < 5; i++) {
                for(int j = 0; j < 5; j++) {
                    colour = colours[rand.nextInt(2)];
                    gridPaint.setColor(colour);

                    rectanglex = sideLength * i;
                    rectangley = sideLength * j;

                    rect = new Rect(rectanglex+(50), rectangley+(50), rectanglex+sideLength, rectangley+sideLength);
                    canvas.drawRect(rect, gridPaint);
                }
            }
        }
    }
}
