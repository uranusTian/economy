package com.uranus.economy.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.uranus.economy.util.ScreenUtil;

public class CoordinateView extends View {

    private int viewHeight = 300;
    private long freq = 700000;
    private long showValue = 500000;

    private long bandwidth = 1000;
    private long showBandwidth = 1000;

    public void setFreq(long freq, long bandwidth){
        this.freq = freq;
        long tempFreq = freq;
        long rate = 1;
        while(tempFreq >= 10){
            rate *= 10;
            tempFreq = tempFreq / 10;
        }
        if(tempFreq <= 1){
            showValue = rate;
        } else if (tempFreq <= 2){
            showValue = 2 * rate;
        } else if (tempFreq <= 7){
            showValue = 5 * rate;
        } else {
            showValue = 10 * rate;
        }

        this.bandwidth = bandwidth;
        long tempbandwidth = bandwidth;
        long rateBand = 1;
        while(tempbandwidth >= 10){
            rateBand *= 10;
            tempbandwidth = tempbandwidth / 10;
        }
        if(tempbandwidth <= 1){
            showBandwidth = rateBand;
        } else if (tempbandwidth <= 2){
            showBandwidth = 2 * rateBand;
        } else if (tempbandwidth <= 7){
            showBandwidth = 5 * rateBand;
        } else {
            showBandwidth = 10 * rateBand;
        }


    }

    private Paint mPaint = new Paint();

    public CoordinateView(Context context) {
        super(context);
    }

    public CoordinateView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint.setColor(Color.parseColor("#000000"));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int xStartX = ScreenUtil.dp2px(0);
        int xEndX = getWidth();
        int xStartY = ScreenUtil.dp2px(viewHeight - 50);
        int xEndY = ScreenUtil.dp2px(viewHeight - 50);
        mPaint.setColor(Color.parseColor("#000000"));
        mPaint.setStrokeWidth((float) 2.0);
        canvas.drawLine(xStartX,xStartY,xEndX,xEndY,mPaint);
        int yStartX = getWidth() / 2;
        int yEndX = getWidth() / 2;
        int yStartY = ScreenUtil.dp2px(50f);
        int yEndY = ScreenUtil.dp2px(viewHeight);
        canvas.drawLine(yStartX,yStartY,yEndX,yEndY,mPaint);

        String text="Y轴";
        mPaint.setTextSize(ScreenUtil.sp2px(20));
        canvas.drawText(text, getWidth() / 2,ScreenUtil.dp2px(40f), mPaint);

        int perSmallScal = getWidth() / 40;
        int perLargeScal = getWidth() / 8;

//        int perYScal = ScreenUtil.dp2px(50f);
//        for(int i = 1;i <= 4; i++){
//            int yScalStartX = getWidth() / 2 - perSmallScal/2;
//            int yScalEndX = getWidth() / 2 + perSmallScal/2;
//            int yScalStartY = perYScal * i;
//            int yScalEndY = perYScal * i;
//            canvas.drawLine(yScalStartX,yScalStartY,yScalEndX,yScalEndY,mPaint);
//        }
//        mPaint.setTextSize(ScreenUtil.sp2px(10));
//        canvas.drawText("" + showBandwidth, getWidth()/2 + ScreenUtil.dp2px(10),
//                ScreenUtil.dp2px(viewHeight/2), mPaint);

        int scalHeight = (int)(bandwidth * ScreenUtil.dp2px(100) / showBandwidth);
        int scalY = ScreenUtil.dp2px(viewHeight - 50) - scalHeight;

        for(int i = 1;i <= 8; i++){
            int largeScalStartX = i * perLargeScal;
            int largeScalEndX = i * perLargeScal;
            int largeScalStartY = ScreenUtil.dp2px(viewHeight - 50);
            int largeScalEndY = ScreenUtil.dp2px(viewHeight - 40);
            canvas.drawLine(largeScalStartX,largeScalStartY,largeScalEndX,largeScalEndY,mPaint);
        }

        int value1X = perLargeScal * 2 - ScreenUtil.dp2px(10f);
        int value2X = perLargeScal * 6 - ScreenUtil.dp2px(10f);
        int valueY = ScreenUtil.dp2px(viewHeight - 20);

        if(showValue > 0){
            canvas.drawText("-" + showValue, value1X,valueY, mPaint);
            canvas.drawText("" + showValue, value2X,valueY, mPaint);
        }

        mPaint.setStrokeWidth((float) 1.0);
        for(int i = 1;i <= 40; i++){
            int smallScalStartX = i * perSmallScal;
            int smallScalEndX = i * perSmallScal;
            int smallScalStartY = ScreenUtil.dp2px(viewHeight - 50);
            int smallScalEndY = ScreenUtil.dp2px(viewHeight - 45);
            canvas.drawLine(smallScalStartX,smallScalStartY,smallScalEndX,smallScalEndY,mPaint);
        }

        int trapMinusMidX = getWidth() / 2 - (int)(freq * getWidth() / showValue) /4;
        int trapPlusMidX = getWidth() / 2 + (int)(freq * getWidth() / showValue) /4;

        int trapMinusX1 = trapMinusMidX - perSmallScal * 2;
        int trapMinusY1 = ScreenUtil.dp2px(viewHeight - 50);

        int trapMinusX2 = trapMinusMidX + perSmallScal * 2;
        int trapMinusY2 = ScreenUtil.dp2px(viewHeight - 50);

        int trapMinusX3 = trapMinusMidX + perSmallScal * 2;
        int trapMinusY3 = scalY;

        int trapMinusX4 = trapMinusMidX - perSmallScal * 2;
        int trapMinusY4 = scalY + ScreenUtil.dp2px(20);

        mPaint.setColor(Color.parseColor("#00008B"));
        mPaint.setStrokeWidth((float) 3.0);
        canvas.drawLine(trapMinusX1,trapMinusY1,trapMinusX2,trapMinusY2,mPaint);
        canvas.drawLine(trapMinusX2,trapMinusY2,trapMinusX3,trapMinusY3,mPaint);
        canvas.drawLine(trapMinusX3,trapMinusY3,trapMinusX4,trapMinusY4,mPaint);
        canvas.drawLine(trapMinusX4,trapMinusY4,trapMinusX1,trapMinusY1,mPaint);

        int trapPlusX1 = trapPlusMidX - perSmallScal * 2;
        int trapPlusY1 = ScreenUtil.dp2px(viewHeight - 50);

        int trapPlusX2 = trapPlusMidX + perSmallScal * 2;
        int trapPlusY2 = ScreenUtil.dp2px(viewHeight - 50);

        int trapPlusX3 = trapPlusMidX + perSmallScal * 2;
        int trapPlusY3 = scalY + ScreenUtil.dp2px(20);

        int trapPlusX4 = trapPlusMidX - perSmallScal * 2;
        int trapPlusY4 = scalY;

        mPaint.setColor(Color.parseColor("#FF0000"));
        mPaint.setStrokeWidth((float) 3.0);
        canvas.drawLine(trapPlusX1,trapPlusY1,trapPlusX2,trapPlusY2,mPaint);
        canvas.drawLine(trapPlusX2,trapPlusY2,trapPlusX3,trapPlusY3,mPaint);
        canvas.drawLine(trapPlusX3,trapPlusY3,trapPlusX4,trapPlusY4,mPaint);
        canvas.drawLine(trapPlusX4,trapPlusY4,trapPlusX1,trapPlusY1,mPaint);

    }
}
