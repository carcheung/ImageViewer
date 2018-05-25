package com.example.carolyncheung.hisimageviewer.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Carolyn Cheung on 5/8/2018.
 *
 * Resizable rectangle based on user touch events during selection mode.
 * Finds the area/pixels selected and saves the values to help in pixel manipulation
 */

public class DrawRectangle extends View {
    Paint mPaint;
    RectF mRectF = new RectF();
    float strokeWidth = 1.0f;
    int navBarLocation = 0;

    public DrawRectangle(Context context) {
        super(context);
        mPaint = new Paint();

        // set color, stroke width, and style
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);

        super.setOnTouchListener(new PrivateOnTouchListener());
    }

    // if touch events are here, ignore them
    public void setNavBarLocation(int h) {
        navBarLocation = h;
    }

    // set from zoom
    public void setStrokeWidth(float w) {
        strokeWidth = w;
    }

    // set from coordinates
    public void setRectCoords(RectF r) {
        mRectF = r;
    }

    // get coordinates
    public RectF getRectCoords() {
        return mRectF;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPaint.setStrokeWidth(strokeWidth);
        canvas.drawRect(mRectF, mPaint);
    }

    // save the area that was selected
    public class PrivateOnTouchListener implements OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            float x = event.getX();
            float y = event.getY();
            if (y > navBarLocation) {
                return false;
            }

            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    mRectF.left = x;
                    mRectF.top = y;
                    mRectF.right = x;
                    mRectF.bottom = y;
                    invalidate();
                    break;
                case MotionEvent.ACTION_MOVE:
                case MotionEvent.ACTION_UP:
                    mRectF.right = x;
                    mRectF.bottom = y;
                    invalidate();
                    break;
            }

            return true;
        }
    }
}
