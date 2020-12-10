package com.uranus.economy.activity;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.squareup.picasso.Picasso;
import com.uranus.economy.R;
import com.uranus.economy.base.BaseActivity;
import com.uranus.economy.util.KeyboardUtils;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class SearchTrueActivity extends BaseActivity implements View.OnClickListener{

    @BindView(R.id.image_icon)
    protected CircleImageView mImageView;

    @BindView(R.id.button_property_xml)
    protected Button mButtonXml;

    @BindView(R.id.button_property_java)
    protected Button mButtonJava;

    private Context mContext;

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
        Picasso.with(mContext)
                .load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1607539463582&di=51d1efe017b2548b07a86fca386156f9&imgtype=0&src=http%3A%2F%2Fbpic.588ku.com%2F%2Felement_pic%2F20%2F06%2F30%2F82773c149795c8d4818ee1fced309ca4.jpg")
                .into(mImageView);
    }

    private void startXmlProperty() {
        AnimatorSet animationSet = (AnimatorSet) AnimatorInflater.loadAnimator(mContext, R.animator.property_set);
        animationSet.setTarget(mImageView);
        animationSet.start();
    }

    private void startJavaProperty() {
        Log.d("tian","startJavaProperty");
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator objectXAnimator = ObjectAnimator.ofFloat(mImageView, "rotation", 0f, 360f);
        objectXAnimator.setDuration(10000);
        animatorSet.playTogether(objectXAnimator);

        ObjectAnimator objectAlphaAnimator = ObjectAnimator.ofFloat(mImageView, "alpha", 0f, 1f);
        objectAlphaAnimator.setDuration(10000);

        AnimatorSet animatorSetResult = new AnimatorSet();
        animatorSetResult.playTogether(animatorSet, objectAlphaAnimator);
        animatorSetResult.start();
    }
}
