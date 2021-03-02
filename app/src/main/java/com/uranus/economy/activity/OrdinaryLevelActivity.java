package com.uranus.economy.activity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
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

import java.util.Random;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class OrdinaryLevelActivity extends BaseActivity implements View.OnClickListener{

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

    @BindView(R.id.layout_tip)
    protected LinearLayout layoutTip;

    @BindView(R.id.text_tip)
    protected TextView textTip;

    @BindView(R.id.button_change_level)
    protected Button buttonChangeLevel;

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
    private int costSocre = 0;//玩本关卡要消耗的积分
    private int bonusSocre = 0;//通关本关卡奖励的积分
    private int curNeedClickNumber = 1;//当前需要点击的数字

    @BindView(R.id.text_explain)
    protected TextView textExplain;

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
            //开始本关卡按钮
            case R.id.button_property_java:
                layoutBegin.setVisibility(View.GONE);
                curUser.score -= costSocre;
                curScore.setText("当前积分：" + curUser.score);
                AppUtils.saveUserInfo(getApplicationContext(),curUser);
                startJavaProperty();
                break;
            case R.id.button_change_level:
                layoutChangeLevel.setVisibility(View.GONE);
                layoutBegin.setVisibility(View.VISIBLE);
                curLevel.setText("当前关卡：" + curUser.ordinary_level);
                refreshData();
                break;
        }
    }

    @Override
    protected void initData() {
        curUser = AppUtils.getUserInfo(getApplicationContext());
        curLevel.setText("当前关卡：" + curUser.ordinary_level);
        curScore.setText("当前积分：" + curUser.score);
        curDiffNum = curUser.ordinary_level;
        refreshData();
    }

    private void refreshData(){
        int common = 30;

        moveNum = 3 + curDiffNum / 8;
        if(curDiffNum < 3){
            costSocre = 2;
            bonusSocre = 3;
            imageNum = 2;
        } else if(curDiffNum < 20){
            costSocre = 3;
            bonusSocre = 5;
            imageNum = 3;
        }  else if(curDiffNum < 50){
            costSocre = 5;
            bonusSocre = 10;
            imageNum = 4;
        } else {
            costSocre = 8;
            bonusSocre = 15;
            imageNum = 5;
        }
        durationX = 40 - curDiffNum / 4;
        if(durationX < 10){
            durationX = 15;
        }
        durationX = durationX * common;
        textExplain.setText("说明：开始本关卡需要消耗" + costSocre + "积分，闯关成功可获取" + bonusSocre + "积分");

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
                        .load(R.mipmap.ic_number_1)
                        .into(curImage);
            } else if(i == 1){
                Picasso.with(mContext)
                        .load(R.mipmap.ic_number_2)
                        .into(curImage);
            } else if(i == 2){
                Picasso.with(mContext)
                        .load(R.mipmap.ic_number_3)
                        .into(curImage);
            } else if(i == 3){
                Picasso.with(mContext)
                        .load(R.mipmap.ic_number_4)
                        .into(curImage);
            } else if(i == 4){
                Picasso.with(mContext)
                        .load(R.mipmap.ic_number_5)
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
        if(imageIndex == 0){
            picReverse(mImages[imageIndex],R.mipmap.ic_number_1,null);
        } else if(imageIndex == 1){
            picReverse(mImages[imageIndex],R.mipmap.ic_number_2,null);
        } else if(imageIndex == 2){
            picReverse(mImages[imageIndex],R.mipmap.ic_number_3,null);
        } else if(imageIndex == 3){
            picReverse(mImages[imageIndex],R.mipmap.ic_number_4,null);
        } else if(imageIndex == 4){
            picReverse(mImages[imageIndex],R.mipmap.ic_number_5,null);
        }

        if(imageIndex == curNeedClickNumber - 1){
            curUser.score += bonusSocre;
            curUser.ordinary_level++;
//            curLevel.setText("当前关卡：" + curUser.cur_level);
            curScore.setText("当前积分：" + curUser.score);
            ToastUtils.showShort("恭喜你，选择正确，积分加 " + bonusSocre + "，可挑战下一关");
//            picReverse(mImages[imageIndex],R.mipmap.ic_right,null);
            buttonChangeLevel.setText("挑战下一关");
        } else {
            curUser.ordinary_level--;
            if(curUser.ordinary_level < 0){
                curUser.ordinary_level = 0;
            }
            ToastUtils.showShort("很遗憾，选择错误，关卡减 1");
            buttonChangeLevel.setText("挑战上一关");
        }
        curDiffNum = curUser.ordinary_level;
        layoutTip.setVisibility(View.GONE);
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

    private void startJavaProperty() {
        Random r = new Random();
        curNeedClickNumber = r.nextInt(imageNum) + 1;

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
                        layoutTip.setVisibility(View.VISIBLE);
                        textTip.setText("请点击数字：" + curNeedClickNumber);
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
