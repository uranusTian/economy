package com.uranus.economy.activity;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.squareup.picasso.Picasso;
import com.uranus.economy.R;
import com.uranus.economy.base.BaseActivity;
import com.uranus.economy.util.ScreenUtil;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class SearchTrueActivity extends BaseActivity implements View.OnClickListener{

    @BindView(R.id.top_layout)
    protected RelativeLayout topLayout;

    @BindView(R.id.button_property_xml)
    protected Button mButtonXml;

    @BindView(R.id.button_property_java)
    protected Button mButtonJava;

    private Context mContext;
    private CircleImageView[] mImages;
    private int moveNum = 10;
    private int imageNum = 3;
    private static int IMAGE_SIZE = 80;
    private int durationX = 200;
    private int durationY = 600;
    private int moveXDistance = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mContext = this;
        super.onCreate(savedInstanceState);

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_search_true;
    }

    @OnClick({R.id.button_property_xml,R.id.button_property_java})
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_property_xml:
                startXmlProperty();
                break;
            case R.id.button_property_java:
                startJavaProperty();
                break;
        }
    }

    @Override
    protected void initData() {
        mImages = new CircleImageView[imageNum];
        int margintop = (ScreenUtil.getScreenHeight() - ScreenUtil.dp2px(IMAGE_SIZE) * imageNum) / (imageNum + 1) + ScreenUtil.dp2px(IMAGE_SIZE);
        for(int i = 0;i<imageNum;i++){
            CircleImageView curImage = new CircleImageView(this);
            final RelativeLayout.LayoutParams layoutParams =  new RelativeLayout.LayoutParams(ScreenUtil.dp2px(IMAGE_SIZE),
                    ScreenUtil.dp2px(IMAGE_SIZE));
            layoutParams.topMargin = (i + 1) * margintop - ScreenUtil.dp2px(IMAGE_SIZE);
            layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
            curImage.setLayoutParams(layoutParams);
            Picasso.with(mContext)
                .load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1607539463582&di=51d1efe017b2548b07a86fca386156f9&imgtype=0&src=http%3A%2F%2Fbpic.588ku.com%2F%2Felement_pic%2F20%2F06%2F30%2F82773c149795c8d4818ee1fced309ca4.jpg")
                .into(curImage);
            topLayout.addView(curImage);
            mImages[i] = curImage;
        }
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
        AnimatorSet[] animatorSets = new AnimatorSet[imageNum];
        int moveY = (ScreenUtil.getScreenHeight() - ScreenUtil.dp2px(IMAGE_SIZE) * imageNum) / (imageNum + 1) + ScreenUtil.dp2px(IMAGE_SIZE);
        durationY = durationX * moveY / moveXDistance;
        for(int i = 0;i<imageNum;i++){
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSets[i] = animatorSet;
        }
        int[][] movePath = new int[imageNum][moveNum];
        int[] lastLocation = new int[imageNum];
//        float[] lastLocationX = new float[imageNum];
        float[] lastLocationY = new float[imageNum];
        int[] tempLocation = new int[imageNum];
        ObjectAnimator[] lastAnimator = new ObjectAnimator[imageNum];
        for(int i = 0;i< imageNum;i++){
            movePath[i][0] = i;
            lastLocation[i] = i;
            tempLocation[i] = i;
//            lastLocationX[i] = 0.0f;
            lastLocationY[i] = 0.0f;
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

        for(int i = 0;i<imageNum;i++){
            animatorSets[i].start();
        }


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
