package com.uranus.economy.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.uranus.economy.util.ScreenUtil;
import com.uranus.economy.util.Util;

public class CoordinateView extends View {

    private int viewHeight = 150;
    private int viewBottomHeight = 30;
    private long freq = 70000000;
    private long showValue = 40000000;
    private String showValueStr = "40,000,000";

    private long bandwidth = 10000000;
    private long showBandwidth = 10000000;

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
        } else if (tempFreq <= 3){
            showValue = 2 * rate;
        } else if (tempFreq <= 7){
            showValue = 4 * rate;
        } else {
            showValue = 10 * rate;
        }
        showValueStr = Util.longToInternal(showValue + "");

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
            showBandwidth = 4 * rateBand;
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
        int xStartY = ScreenUtil.dp2px(viewHeight - viewBottomHeight);
        int xEndY = ScreenUtil.dp2px(viewHeight - viewBottomHeight);
        mPaint.setColor(Color.parseColor("#000000"));
        mPaint.setStrokeWidth((float) 2.0);
        canvas.drawLine(xStartX,xStartY,xEndX,xEndY,mPaint);
        int yStartX = getWidth() / 2;
        int yEndX = getWidth() / 2;
        int yStartY = ScreenUtil.dp2px(viewBottomHeight);
        int yEndY = ScreenUtil.dp2px(viewHeight);
        canvas.drawLine(yStartX,yStartY,yEndX,yEndY,mPaint);

//        String text="Y轴";
//        mPaint.setTextSize(ScreenUtil.sp2px(20));
//        canvas.drawText(text, getWidth() / 2,ScreenUtil.dp2px(40f), mPaint);

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

//        int scalHeight = (int)(bandwidth * ScreenUtil.dp2px(100) / showBandwidth);
//        int scalY = ScreenUtil.dp2px(viewHeight - 50) - scalHeight;
        int scalHeight = ScreenUtil.dp2px(100);
        int scalY = ScreenUtil.dp2px(viewHeight - viewBottomHeight)- scalHeight;;

        for(int i = 1;i <= 8; i++){
            int largeScalStartX = i * perLargeScal;
            int largeScalEndX = i * perLargeScal;
            int largeScalStartY = ScreenUtil.dp2px(viewHeight - viewBottomHeight);
            int largeScalEndY = ScreenUtil.dp2px(viewHeight - viewBottomHeight + 10);
            canvas.drawLine(largeScalStartX,largeScalStartY,largeScalEndX,largeScalEndY,mPaint);
        }



        if(showValue > 0){
            mPaint.setTextSize(ScreenUtil.sp2px(10));
            int textOffset = ScreenUtil.dp2px(showValueStr.length() * 3);
            int value1X = perLargeScal * 2 - textOffset;
            int value2X = perLargeScal * 6 - textOffset;
            int valueY = ScreenUtil.dp2px(viewHeight - viewBottomHeight + 25);
            canvas.drawText("-" + showValueStr, value1X,valueY, mPaint);
            canvas.drawText("" + showValueStr, value2X,valueY, mPaint);
        }

        mPaint.setStrokeWidth((float) 1.0);
        for(int i = 1;i <= 40; i++){
            int smallScalStartX = i * perSmallScal;
            int smallScalEndX = i * perSmallScal;
            int smallScalStartY = ScreenUtil.dp2px(viewHeight - viewBottomHeight);
            int smallScalEndY = ScreenUtil.dp2px(viewHeight - viewBottomHeight + 5);
            canvas.drawLine(smallScalStartX,smallScalStartY,smallScalEndX,smallScalEndY,mPaint);
        }

        int trapMinusMidX = getWidth() / 2 - (int)(freq * getWidth() / showValue) /4;
        int trapPlusMidX = getWidth() / 2 + (int)(freq * getWidth() / showValue) /4;
        long trapWidthLong = bandwidth * getWidth() / showValue / 4;
//        int trapWidth = (int)(bandwidth * getWidth() / showValue / 4);
        int trapWidth = 2;
        if(trapWidthLong >= getWidth()){
            trapWidth = getWidth();
        } else if(trapWidthLong <= 2){
            trapWidth = 2;
        } else {
            trapWidth = (int)trapWidthLong;
        }

        int trapMinusX1 = trapMinusMidX - trapWidth / 2;
        int trapMinusY1 = ScreenUtil.dp2px(viewHeight - viewBottomHeight);

        int trapMinusX2 = trapMinusMidX + trapWidth / 2;
        int trapMinusY2 = ScreenUtil.dp2px(viewHeight - viewBottomHeight);

        int trapMinusX3 = trapMinusMidX + trapWidth / 2;
        int trapMinusY3 = scalY;

        int trapMinusX4 = trapMinusMidX - trapWidth / 2;
        int trapMinusY4 = scalY + ScreenUtil.dp2px(20);

        Path path=new Path();                                 //初始化路径（用于填充颜色）
        path.moveTo(trapMinusX1,trapMinusY1);                           //路径移动到第一个点（从点 1 开始）
        path.lineTo(trapMinusX2,trapMinusY2);                           //路径直线到第二个点
        path.lineTo(trapMinusX3,trapMinusY3);                           //路径直线到第四个点
        path.lineTo(trapMinusX4,trapMinusY4);                           //路径直线到第三个点
        path.lineTo(trapMinusX1,trapMinusY1);                           //路径直线到第一个点（闭合形成四边形）
        mPaint.setColor(Color.parseColor("#00008B"));
        canvas.drawPath(path,mPaint);

//        mPaint.setColor(Color.parseColor("#00008B"));
//        mPaint.setStrokeWidth((float) 3.0);
//        canvas.drawLine(trapMinusX1,trapMinusY1,trapMinusX2,trapMinusY2,mPaint);
//        canvas.drawLine(trapMinusX2,trapMinusY2,trapMinusX3,trapMinusY3,mPaint);
//        canvas.drawLine(trapMinusX3,trapMinusY3,trapMinusX4,trapMinusY4,mPaint);
//        canvas.drawLine(trapMinusX4,trapMinusY4,trapMinusX1,trapMinusY1,mPaint);

        int trapPlusX1 = trapPlusMidX - trapWidth / 2;
        int trapPlusY1 = ScreenUtil.dp2px(viewHeight - viewBottomHeight);

        int trapPlusX2 = trapPlusMidX + trapWidth / 2;
        int trapPlusY2 = ScreenUtil.dp2px(viewHeight - viewBottomHeight);

        int trapPlusX3 = trapPlusMidX + trapWidth / 2;
        int trapPlusY3 = scalY + ScreenUtil.dp2px(20);

        int trapPlusX4 = trapPlusMidX - trapWidth / 2;
        int trapPlusY4 = scalY;

        Path path2=new Path();                                 //初始化路径（用于填充颜色）
        path2.moveTo(trapPlusX1,trapPlusY1);                           //路径移动到第一个点（从点 1 开始）
        path2.lineTo(trapPlusX2,trapPlusY2);                           //路径直线到第二个点
        path2.lineTo(trapPlusX3,trapPlusY3);                           //路径直线到第四个点
        path2.lineTo(trapPlusX4,trapPlusY4);                           //路径直线到第三个点
        path2.lineTo(trapPlusX1,trapPlusY1);                           //路径直线到第一个点（闭合形成四边形）
        mPaint.setColor(Color.parseColor("#FF0000"));
        canvas.drawPath(path2,mPaint);

//        mPaint.setColor(Color.parseColor("#FF0000"));
//        mPaint.setStrokeWidth((float) 3.0);
//        canvas.drawLine(trapPlusX1,trapPlusY1,trapPlusX2,trapPlusY2,mPaint);
//        canvas.drawLine(trapPlusX2,trapPlusY2,trapPlusX3,trapPlusY3,mPaint);
//        canvas.drawLine(trapPlusX3,trapPlusY3,trapPlusX4,trapPlusY4,mPaint);
//        canvas.drawLine(trapPlusX4,trapPlusY4,trapPlusX1,trapPlusY1,mPaint);

    }
}
