package com.uranus.economy.activity;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.uranus.economy.R;
import com.uranus.economy.base.BaseActivity;
import com.uranus.economy.bean.UserInfo;
import com.uranus.economy.interpolator.CurveInterpolator;
import com.uranus.economy.listener.PicReverseListener;
import com.uranus.economy.util.AppUtils;
import com.uranus.economy.util.ScreenUtil;
import com.uranus.economy.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class SearchTrueActivity extends BaseActivity implements View.OnClickListener{

    @BindView(R.id.top_layout)
    protected RelativeLayout topLayout;

    @BindView(R.id.cur_level)
    protected TextView curLevel;

    @BindView(R.id.cur_score)
    protected TextView curScore;

    @BindView(R.id.layout_begin)
    protected LinearLayout layoutBegin;

    @BindView(R.id.layout_change_level)
    protected LinearLayout layoutChangeLevel;

    @BindView(R.id.button_change_level)
    protected Button buttonChangeLevel;

//    @BindView(R.id.low_diff)
//    protected Button lowDiff;
//
//    @BindView(R.id.add_diff)
//    protected Button addDiff;
//
//    @BindView(R.id.cur_diff)
//    protected TextView curDiff;

    @BindView(R.id.button_property_java)
    protected Button mButtonJava;

    private Context mContext;
    private CircleImageView[] mImages;
    private int curDiffNum = 16;
    private int moveNum = 5;
    private int imageNum = 4;
    private static int IMAGE_SIZE = 80;
    private int durationX = 2000;
    private int durationY = 600;
    private int moveXDistance = 100;
    private boolean canClickImage = true;
    private float[] lastLocationY = new float[imageNum];
    private int[] lastLocation = new int[imageNum];
    private UserInfo curUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mContext = this;
        super.onCreate(savedInstanceState);

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_search_true;
    }

    @OnClick({R.id.button_property_java,R.id.button_change_level})
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_property_java:
                layoutBegin.setVisibility(View.GONE);
                startJavaProperty();
                break;
            case R.id.button_change_level:
                layoutChangeLevel.setVisibility(View.GONE);
                layoutBegin.setVisibility(View.VISIBLE);
                curLevel.setText("当前关卡：" + curUser.cur_level);
                refreshData();
                break;
        }
    }

    @Override
    protected void initData() {
        curUser = AppUtils.getUserInfo(getApplicationContext());
        curLevel.setText("当前关卡：" + curUser.cur_level);
        curScore.setText("当前关卡：" + curUser.score);
        curDiffNum = curUser.cur_level;
        refreshData();
    }

    private void refreshData(){
        int common = 30;

        moveNum = 3 + curDiffNum / 8;
        if(curDiffNum < 4){
            imageNum = 2;
        } else if(curDiffNum < 20){
            imageNum = 3;
        }  else if(curDiffNum < 50){
            imageNum = 4;
        } else {
            imageNum = 5;
        }
        durationX = 40 - curDiffNum / 4;
        if(durationX < 10){
            durationX = 15;
        }
        durationX = durationX * common;

//        switch (curDiffNum){
//            case 1:
//                moveNum = 4;
//                imageNum = 2;
//                durationX = 40 * common;
//                break;
//            case 2:
//                moveNum = 6;
//                imageNum = 2;
//                durationX = 30 * common;
//                break;
//            case 3:
//                moveNum = 3;
//                imageNum = 3;
//                durationX = 30 * common;
//                break;
//            case 4:
//                moveNum = 6;
//                imageNum = 3;
//                durationX = 30 * common;
//                break;
//            case 5:
//                moveNum = 8;
//                imageNum = 3;
//                durationX = 30 * common;
//                break;
//            case 6:
//                moveNum = 10;
//                imageNum = 3;
//                durationX = 30 * common;
//                break;
//            case 7:
//                moveNum = 13;
//                imageNum = 3;
//                durationX = 20 * common;
//                break;
//            case 8:
//                moveNum = 7;
//                imageNum = 4;
//                durationX = 20 * common;
//                break;
//            case 9:
//                moveNum = 10;
//                imageNum = 4;
//                durationX = 20 * common;
//                break;
//            case 10:
//                moveNum = 13;
//                imageNum = 4;
//                durationX = 20 * common;
//                break;
//            case 11:
//                moveNum = 15;
//                imageNum = 4;
//                durationX = 20 * common;
//                break;
//            case 12:
//                moveNum = 18;
//                imageNum = 4;
//                durationX = 20 * common;
//                break;
//            case 13:
//                moveNum = 20;
//                imageNum = 4;
//                durationX = 15 * common;
//                break;
//            case 14:
//                moveNum = 8;
//                imageNum = 5;
//                durationX = 20 * common;
//                break;
//            case 15:
//                moveNum = 14;
//                imageNum = 5;
//                durationX = 18 * common;
//                break;
//            case 16:
//                moveNum = 20;
//                imageNum = 5;
//                durationX = 15 * common;
//                break;
//        }


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
            if(i == (imageNum - 1) / 2){
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
                    if(canClickImage) {
                        canClickImage = false;
                        clickImage(curImageIndex);
                    }
                }
            });
        }
    }

    private void clickImage(int imageIndex){
        if(imageIndex == (imageNum - 1) / 2){
            curUser.score++;
            curUser.cur_level++;
//            curLevel.setText("当前关卡：" + curUser.cur_level);
            curScore.setText("当前积分：" + curUser.score);
            curDiffNum = curUser.cur_level;
            ToastUtils.showShort("恭喜你，选择正确，积分加 1，可挑战下一关");
            picReverse(mImages[imageIndex],R.mipmap.ic_right,null);
            buttonChangeLevel.setText("挑战下一关");
        } else {
            curUser.cur_level--;
            if(curUser.cur_level < 0){
                curUser.cur_level = 0;
            }
            ToastUtils.showShort("很遗憾，选择错误，关卡减 1");
            picReverse(mImages[imageIndex],R.mipmap.ic_wrong,null);
            buttonChangeLevel.setText("挑战上一关");
        }
        layoutChangeLevel.setVisibility(View.VISIBLE);
        AppUtils.saveUserInfo(getApplicationContext(),curUser);
//        refreshData();
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

    private void test() {

        ObjectAnimator scaleX = ObjectAnimator.ofFloat(mImages[0], "translationX", 0f,250,0f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(mImages[0], "translationY", 0f,400);
        AnimatorSet animatorSet1 = new AnimatorSet();
        scaleX.setDuration(10000);
        scaleX.setInterpolator(new CurveInterpolator());
        scaleY.setDuration(10000);
        animatorSet1.playTogether(scaleX,scaleY);
        animatorSet1.start();
    }

    private int subAbs(int a, int b){
        if(a>b){
            return a - b;
        }
        return b - a;
    }

    private void startJavaProperty() {
        canClickImage = false;
        AnimatorSet[] animatorSets = new AnimatorSet[imageNum];
        int moveY = (ScreenUtil.getScreenHeight() - IMAGE_SIZE * imageNum) / (imageNum + 1) + IMAGE_SIZE;
        durationY = durationX * moveY / moveXDistance;
        for(int i = 0;i<imageNum;i++){
            animatorSets[i] = new AnimatorSet();
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
                int randomNum = (int)(Math.random()*(9));
                float largeNum = (float)(randomNum + 10)/10f;
                float endX = moveXDistance * largeNum;
                if(direction == 1){
                    endX = -endX;
                }

                float endY = (movePath[n][m] - movePath[n][m - 1]) * moveY + lastLocationY[n];
                ObjectAnimator scaleX = ObjectAnimator.ofFloat(mImages[n], "translationX", beginX,endX,beginX);
                ObjectAnimator scaleY = ObjectAnimator.ofFloat(mImages[n], "translationY", lastLocationY[n], endY);
                scaleX.setDuration(durationX);
                scaleX.setInterpolator(new CurveInterpolator());

                scaleY.setDuration(durationX);

                animatorSets[n].playTogether(scaleX,scaleY);
                if(m > 1){
                    animatorSets[n].playSequentially(lastAnimator[n],scaleX);
                }
                lastLocationY[n] = endY;
                lastAnimator[n] = scaleX;

            }
        }

        picsReverse(mImages, R.mipmap.ic_bg, new PicReverseListener() {
            @Override
            public void onSuccess() {
                animatorSets[imageNum -1].addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                    }
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        canClickImage = true;
                    }
                    @Override
                    public void onAnimationCancel(Animator animation) {
                    }
                    @Override
                    public void onAnimationRepeat(Animator animation) {
                    }
                });
                for(int i = 0;i<imageNum;i++){
                    animatorSets[i].start();
                }
            }
        });


    }
}
