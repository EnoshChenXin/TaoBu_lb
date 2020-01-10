package com.lianbei.taobu.shop.view;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class gradual extends View {
    private int animatedValue;
    private int colorEnd ;
    private int colorStart;
    private int animatedValue1;

    private int startColorIntTop1 =0; //初始计算数值
    private int endColorIntTop2 =-6997975; //初始计算数值


    private int startColorIntBottom1;//初始计算数值
    private int endColorIntBottom2;//初始计算数值


    public gradual(Context context) {
        super(context);
        init();
    }

    public gradual(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        System.out.println(111);
        init();
    }

    public void setColorEnd(int colorEnd) {
        this.colorEnd = colorEnd;
    }

    public void setColorStart(int colorStart) {
        this.colorStart = colorStart;
    }

    public void setColorInt(int ColorIntTop, int ColorIntBottom) {
        this.endColorIntTop2 = ColorIntTop;
        this.endColorIntBottom2 = ColorIntBottom;
        init();
    }

    public void init() {
        postInvalidate();
        ValueAnimator colorAnim = ObjectAnimator.ofInt(startColorIntTop1, endColorIntTop2);
        colorAnim.setDuration(500);
        colorAnim.setEvaluator(new ArgbEvaluator());
        colorAnim.setRepeatCount(ValueAnimator.INFINITE);
        colorAnim.setRepeatCount(0);
        startColorIntTop1 = endColorIntTop2;
        colorAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                colorStart = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        colorAnim.start();

        ValueAnimator colorAnimend = ObjectAnimator.ofInt(startColorIntBottom1, endColorIntBottom2);
        colorAnimend.setDuration(500);
        colorAnimend.setEvaluator(new ArgbEvaluator());
        colorAnimend.setRepeatCount(ValueAnimator.INFINITE);
        colorAnimend.setRepeatCount(0);
        startColorIntBottom1 = endColorIntBottom2;
        colorAnimend.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                colorEnd = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        colorAnimend.start();


    }

    public void init2() {
        postInvalidate();
        ValueAnimator animator = ValueAnimator.ofInt(0, 255);
        animator.setDuration(1000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                animatedValue = (int) animation.getAnimatedValue();
                if (animatedValue < 255) {
                    colorStart = Color.rgb(255, animatedValue, 255 - animatedValue);
                    colorEnd = Color.rgb(animatedValue, 0, 255 - animatedValue);

                } else if (animatedValue == 255) {
                    ValueAnimator animator1 = ValueAnimator.ofInt(0, 255);
                    animator1.setDuration(1000);
                    animator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            animatedValue1 = (int) animation.getAnimatedValue();
                            colorStart = Color.rgb(255 - animatedValue1, 255 - animatedValue1, animatedValue1);
                            colorEnd = Color.rgb(255, 0, animatedValue1);
                            if (animatedValue1 == 255) {
                                ValueAnimator animator2 = ValueAnimator.ofInt(0, 255);
                                animator2.setDuration(1000);
                                animator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                    @Override
                                    public void onAnimationUpdate(ValueAnimator animation) {
                                        int animatedValue2 = (int) animation.getAnimatedValue();
                                        colorStart = Color.rgb(animatedValue2, 0, 255);
                                        colorEnd = Color.rgb(255 - animatedValue2, 0, 255);
                                        invalidate();
                                    }
                                });
                                animator2.start();
                            }

                            invalidate();
                        }
                    });
                    animator1.start();
                }
                invalidate();

            }
        });
        animator.start();
    }

    public gradual(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        System.out.println(333);
        requestLayout();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //获取View的宽高
        int width = getWidth();
        int height = getHeight();
        Double height2 = Double.valueOf(height * 0.42 + "");
        int Xheight = (new Double(height2)).intValue();
        int Arcbottom = Xheight - 90;

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        LinearGradient backGradient = new LinearGradient(0, 0, 0, height, new int[]{colorStart, colorEnd}, new float[]{0, 1f}, Shader.TileMode.CLAMP);
        paint.setShader(backGradient);

        canvas.drawRect(0, 0, width, Arcbottom, paint);
        paint.setStyle(Paint.Style.FILL);//描边加填充
        RectF rectF6 = new RectF(0-20, Arcbottom - 90, width+20, Xheight);
        canvas.drawArc(rectF6, 0, 180, true, paint); // 绘制弧形图形2
        //canvas.drawArc(rectF6, 180, 180, false, paint);//用中心
    }
}
