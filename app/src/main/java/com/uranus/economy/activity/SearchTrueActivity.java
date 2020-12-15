package com.uranus.economy.activity;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.uranus.economy.R;
import com.uranus.economy.base.BaseActivity;
import com.uranus.economy.listener.PicReverseListener;
import com.uranus.economy.util.ScreenUtil;
import com.uranus.economy.util.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class SearchTrueActivity extends BaseActivity implements View.OnClickListener{

    @BindView(R.id.top_layout)
    protected RelativeLayout topLayout;

    @BindView(R.id.low_diff)
    protected Button lowDiff;

    @BindView(R.id.add_diff)
    protected Button addDiff;

    @BindView(R.id.cur_diff)
    protected TextView curDiff;

    @BindView(R.id.button_property_java)
    protected Button mButtonJava;

    private Context mContext;
    private CircleImageView[] mImages;
    private int curDiffNum = 1;
    private int moveNum = 5;
    private int imageNum = 4;
    private static int IMAGE_SIZE = 80;
    private int durationX = 200;
    private int durationY = 600;
    private int moveXDistance = 100;
    private boolean canClick = true;
    private float[] lastLocationY = new float[imageNum];
    private int[] lastLocation = new int[imageNum];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mContext = this;
        super.onCreate(savedInstanceState);

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_search_true;
    }

    @OnClick({R.id.low_diff,R.id.add_diff,R.id.button_property_java})
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_diff:
                if(curDiffNum >= 16){
                    ToastUtils.showShort("已达到最大难度16，不能再增加难度了");
                } else {
                    curDiffNum++;
                    curDiff.setText(curDiffNum + "");
                    refreshData();
                }
                break;
            case R.id.low_diff:
                if(curDiffNum <= 1){
                    ToastUtils.showShort("已达到最小难度1，不能再减少难度了");
                } else {
                    curDiffNum--;
                    curDiff.setText(curDiffNum + "");
                    refreshData();
                }
                break;
            case R.id.button_property_java:
                startJavaProperty();
                break;
        }
    }

    @Override
    protected void initData() {
        refreshData();
    }

    private void refreshData(){

        switch (curDiffNum){
            case 1:
                moveNum = 2;
                imageNum = 2;
                durationX = 400;
                break;
            case 2:
                moveNum = 3;
                imageNum = 2;
                durationX = 400;
                break;
            case 3:
                moveNum = 3;
                imageNum = 3;
                durationX = 300;
                break;
            case 4:
                moveNum = 4;
                imageNum = 3;
                durationX = 300;
                break;
            case 5:
                moveNum = 5;
                imageNum = 3;
                durationX = 300;
                break;
            case 6:
                moveNum = 6;
                imageNum = 3;
                durationX = 300;
                break;
            case 7:
                moveNum = 6;
                imageNum = 3;
                durationX = 200;
                break;
            case 8:
                moveNum = 5;
                imageNum = 4;
                durationX = 200;
                break;
            case 9:
                moveNum = 6;
                imageNum = 4;
                durationX = 200;
                break;
            case 10:
                moveNum = 7;
                imageNum = 4;
                durationX = 200;
                break;
            case 11:
                moveNum = 8;
                imageNum = 4;
                durationX = 200;
                break;
            case 12:
                moveNum = 10;
                imageNum = 4;
                durationX = 200;
                break;
            case 13:
                moveNum = 10;
                imageNum = 4;
                durationX = 150;
                break;
            case 14:
                moveNum = 8;
                imageNum = 5;
                durationX = 200;
                break;
            case 15:
                moveNum = 10;
                imageNum = 5;
                durationX = 180;
                break;
            case 16:
                moveNum = 10;
                imageNum = 5;
                durationX = 150;
                break;
        }


        moveXDistance = ScreenUtil.getScreenWidth() / 5;
        IMAGE_SIZE = ScreenUtil.getScreenWidth() / 6;
        lastLocationY = new float[imageNum];
        lastLocation = new int[imageNum];
        for(int i = 0;i< imageNum;i++){
            lastLocation[i] = i;
            lastLocationY[i] = 0.0f;
        }

        topLayout.removeAllViews();

        mImages = new CircleImageView[imageNum];
        int margintop = (ScreenUtil.getScreenHeight() - ScreenUtil.dp2px(20) - IMAGE_SIZE * imageNum) / (imageNum + 1) + IMAGE_SIZE;
        for(int i = 0;i<imageNum;i++){
            CircleImageView curImage = new CircleImageView(this);
            final RelativeLayout.LayoutParams layoutParams =  new RelativeLayout.LayoutParams(IMAGE_SIZE,
                    IMAGE_SIZE);
            layoutParams.topMargin = (i + 1) * margintop - IMAGE_SIZE;
            layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
            curImage.setLayoutParams(layoutParams);
            if(i == 0){
                Picasso.with(mContext)
                        .load(R.mipmap.ic_right)
                        .into(curImage);
            } else {
                Picasso.with(mContext)
                        .load(R.mipmap.ic_wrong)
                        .into(curImage);
            }

            topLayout.addView(curImage);
            mImages[i] = curImage;
            final int curImageIndex = i;
            curImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickImage(curImageIndex);
                }
            });
        }
    }

    private void clickImage(int imageIndex){
        if(imageIndex == 0){
            ToastUtils.showShort("恭喜你，选择正确");
            picReverse(mImages[0],R.mipmap.ic_right,null);
        } else {
            ToastUtils.showShort("选择错误");
            picReverse(mImages[imageIndex],R.mipmap.ic_wrong,null);
        }
    }

    private void picsReverse(ImageView[] imageViews, int picId, PicReverseListener listener){
        int[] reverseRes = new int[imageViews.length];
        for (int i = 0;i < imageViews.length;i++){
            final int curIndex = i;
            picReverse(imageViews[i], picId, new PicReverseListener() {
                @Override
                public void onSuccess() {
                    if(listener != null){
                        reverseRes[curIndex] = 1;
                        for(int j = 0; j < imageViews.length;j++){
                            if(reverseRes[j] != 1){
                                return;
                            }
                        }
                        listener.onSuccess();
                    }
                }
            });
        }
    }

    private void picReverse(ImageView imageView, int picId, PicReverseListener listener){
        final int duration = 300;
        final int degree = 90;
        final int degree2 = -degree;
        final ObjectAnimator a, b;
        a = ObjectAnimator.ofFloat(imageView, "rotationY", 0, degree);
        b = ObjectAnimator.ofFloat(imageView, "rotationY", degree2, 0);
        a.setDuration(duration);
        b.setDuration(duration);
        a.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }
            @Override
            public void onAnimationEnd(Animator animation) {
                Picasso.with(mContext)
                        .load(picId)
                        .into(imageView);
            }
            @Override
            public void onAnimationCancel(Animator animation) {
            }
            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
        b.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }
            @Override
            public void onAnimationEnd(Animator animation) {
                canClick = true;
                if(listener != null){
                    listener.onSuccess();
                }
            }
            @Override
            public void onAnimationCancel(Animator animation) {
            }
            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
        AnimatorSet set = new AnimatorSet();
        set.play(a).before(b);
        set.start();
    }

    private void startXmlProperty() {
//        AnimatorSet animationSet = (AnimatorSet) AnimatorInflater.loadAnimator(mContext, R.animator.property_set);
//        animationSet.setTarget(mImageView);
//        animationSet.start();
    }

    private int subAbs(int a, int b){
        if(a>b){
            return a - b;
        }
        return b - a;
    }

    private void startJavaProperty() {
        canClick = false;
        AnimatorSet[] animatorSets = new AnimatorSet[imageNum];
        int moveY = (ScreenUtil.getScreenHeight() - IMAGE_SIZE * imageNum) / (imageNum + 1) + IMAGE_SIZE;
        durationY = durationX * moveY / moveXDistance;
        for(int i = 0;i<imageNum;i++){
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSets[i] = animatorSet;
        }
        int[][] movePath = new int[imageNum][moveNum];
        int[] tempLocation = new int[imageNum];
        ObjectAnimator[] lastAnimator = new ObjectAnimator[imageNum];
        for(int i = 0;i< imageNum;i++){
            movePath[i][0] = lastLocation[i];
            tempLocation[i] = i;
        }

        //随机生成运动轨迹
        for(int m=1;m < moveNum;m++){
            for(int n = 0;n< imageNum;n++){
                int num = (int)(Math.random()*(imageNum-n));
                movePath[n][m] = tempLocation[num];
                int temp = tempLocation[num];
                tempLocation[num] = tempLocation[imageNum - 1 - n];
                tempLocation[imageNum - 1 - n] = temp;
            }
            for(int n = 0;n< imageNum;n++){
                if(movePath[n][m] == movePath[n][m - 1]){
                    m--;
                    break;
                }
            }
        }

        for(int i = 0;i< imageNum;i++){
            lastLocation[i] = movePath[i][moveNum - 1];
        }

        for(int m=1;m < moveNum;m++){
            for(int n = 0;n< imageNum;n++){
                //0 向右，1 向左
                int direction = (int)(Math.random()*(2));
                float beginX = 0.0f;
                float endX = moveXDistance;
                if(direction == 1){
                    endX = -moveXDistance;
                }
                ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(mImages[n], "translationX", beginX, endX);//沿着x轴平移
                objectAnimator1.setDuration(durationX);
                if(m > 1){
                    animatorSets[n].playSequentially(lastAnimator[n],objectAnimator1);
                }

//                int curY = subAbs(movePath[n][m] , movePath[n][m - 1]) * durationY;
                float endY = (movePath[n][m] - movePath[n][m - 1]) * moveY + lastLocationY[n];
                ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(mImages[n], "translationY", lastLocationY[n], endY);//沿着Y轴平移
                objectAnimator2.setDuration(durationY);
                animatorSets[n].playSequentially(objectAnimator1,objectAnimator2);
                lastLocationY[n] = endY;

                ObjectAnimator objectAnimator3 = ObjectAnimator.ofFloat(mImages[n], "translationX", endX, beginX);//沿着x轴平移
                objectAnimator3.setDuration(durationX);
                animatorSets[n].playSequentially(objectAnimator2,objectAnimator3);

                lastAnimator[n] = objectAnimator3;
            }
        }

        picsReverse(mImages, R.mipmap.ic_bg, new PicReverseListener() {
            @Override
            public void onSuccess() {
                for(int i = 0;i<imageNum;i++){
                    animatorSets[i].start();
                }
            }
        });


//        int sHeigh = ScreenUtil.getScreenHeight();
//        int imageSize = ScreenUtil.dp2px(IMAGE_SIZE);
//        Log.d("tian","sHeigh : " + sHeigh);
//        Log.d("tian","imageSize : " + imageSize);
//        Log.d("tian","moveY : " + moveY);
//
//
//        AnimatorSet animatorSet1 = new AnimatorSet();
//        AnimatorSet animatorSet2 = new AnimatorSet();
//        ObjectAnimator objectAnimator1 =
//                ObjectAnimator.ofFloat(mImages[0], "translationX", 0.0f, 150.0f);//沿着x轴平移
//        objectAnimator1.setDuration(1000);
//        ObjectAnimator objectAnimator2 =
//                ObjectAnimator.ofFloat(mImages[0], "translationY", 0.0f, 350.0f);//沿着Y轴平移
//        objectAnimator2.setDuration(1000);
//        ObjectAnimator objectAnimator5 =
//                ObjectAnimator.ofFloat(mImages[0], "translationX", 150.0f, 0.0f);//沿着Y轴平移
//        objectAnimator2.setDuration(1000);
//        animatorSet1.playSequentially(objectAnimator1,objectAnimator2);
//        animatorSet1.playSequentially(objectAnimator2,objectAnimator5);
//
//        ObjectAnimator objectAnimator3 =
//                ObjectAnimator.ofFloat(mImages[1], "translationX", 150.0f, 0.0f);//沿着x轴平移
//        objectAnimator3.setDuration(1000);
//        ObjectAnimator objectAnimator4 =
//                ObjectAnimator.ofFloat(mImages[1], "translationY", 0.0f, 350.0f);//沿着x轴平移
//        objectAnimator4.setDuration(1000);
//        animatorSet2.playSequentially(objectAnimator3,objectAnimator4);
//        animatorSet1.start();
//        animatorSet2.start();

//        AnimatorSet animatorSet1 = new AnimatorSet();
//        AnimatorSet animatorSet2 = new AnimatorSet();
//        ObjectAnimator objectAnimator1 =
//                ObjectAnimator.ofFloat(mImageView, "translationX", 0.0f, 150.0f);//沿着x轴平移
//        objectAnimator1.setDuration(1000);
//        ObjectAnimator objectAnimator2 =
//                ObjectAnimator.ofFloat(mImageView, "translationY", 0.0f, 350.0f);//沿着Y轴平移
//        objectAnimator2.setDuration(1000);
//        animatorSet1.playSequentially(objectAnimator1,objectAnimator2);
//
//        ObjectAnimator objectAnimator3 =
//                ObjectAnimator.ofFloat(mImageView, "translationX", 150.0f, 0.0f);//沿着x轴平移
//        objectAnimator3.setDuration(1000);
//        animatorSet1.playSequentially(objectAnimator2,objectAnimator3);
//        animatorSet1.start();
//        animatorSet2.start();
    }
}
