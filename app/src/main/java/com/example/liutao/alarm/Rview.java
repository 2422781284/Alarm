package com.example.liutao.alarm;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by liutao on 2015/5/15.
 */
public class Rview extends View {
    private Bitmap mbitmap;
    public float mdegree = 0;
    private Matrix matrix = new Matrix();
    private float viewHalfWidth;
    private float viewHalfHeight;
    private PointF mCurMove = new PointF();

    private PointF viewCenter = new PointF();

    private static final String TAG = "RviewAlarm";

    public Rview(Context context, AttributeSet attr) {
        super(context, attr);

        TypedArray mTyped = getContext().obtainStyledAttributes(attr, R.styleable.Rview);
        Drawable src = mTyped.getDrawable(R.styleable.Rview_src);
        if (src instanceof BitmapDrawable) {
            BitmapDrawable bd = (BitmapDrawable) src;
            this.mbitmap = bd.getBitmap();
        }

        mTyped.recycle();
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mbitmap == null) return;

        canvas.drawBitmap(mbitmap, matrix, null);
        viewHalfWidth = canvas.getWidth() / 2;
        viewHalfHeight = canvas.getHeight() / 2;

        viewCenter.set(viewHalfWidth, viewHalfHeight);
    }

    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                mCurMove.set(event.getX(), event.getY());
                mDegree(mCurMove);
                rotate(mdegree);
                break;
            case MotionEvent.ACTION_UP:
                break;

        }
        return true;
    }

    public void mDegree(PointF cur) {
        float x = cur.x - viewCenter.x;
        float y = cur.y - viewCenter.y;
        if (y == 0) {
            if (x > 0) {
                mdegree = -90;
            } else if (x < 0) {
                mdegree = 90;
            }
        } else {
            float edge = (float) (Math.atan(Math.abs(x / y)) / (Math.PI) * 180);

            if (x >= 0 && y > 0) {
                mdegree = -edge;
            } else if (x > 0 && y < 0) {
                mdegree = -180 + edge;
            } else if (x <= 0 && y < 0) {
                mdegree = 180 - edge;
            } else if (x < 0 && y > 0) {
                mdegree = edge;
            }
        }
    }

    public void rotate(float degree) {
        matrix.setScale(1.0f, 1.0f);

        matrix.postRotate(degree, mbitmap.getWidth() / 2, mbitmap.getHeight() / 2);
        invalidate();
    }
}
