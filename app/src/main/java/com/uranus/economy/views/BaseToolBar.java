package com.uranus.economy.views;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.uranus.economy.R;
import com.uranus.economy.util.AppUtils;
import com.uranus.economy.util.RxViewUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.functions.Consumer;

/**
 * 项目的通用toolBar
 */
public class BaseToolBar extends FrameLayout implements io.reactivex.functions.Consumer<View> {
    @BindView(R.id.toolBarBack)
    FrameLayout toolBarBack;
    @BindView(R.id.toolBarBackImg)
    ImageView toolBarBackImg;
    @BindView(R.id.toolBarTitle)
    FontTextView toolBarTitle;
    @BindView(R.id.toolBarMenu)
    FontTextView toolBarMenu;
    @BindView(R.id.toolBarLine)
    View toolBarLine;
    @BindView(R.id.rlToolBar)
    RelativeLayout rlToolBar;

    private Activity activity;

    public BaseToolBar(@NonNull Context context) {
        super(context);
        initView(context);
    }

    public BaseToolBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public BaseToolBar(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initView(context);
    }

    private void initView(Context context) {
        if (context instanceof Activity) {
            this.activity = (Activity) context;
        }
        if (activity == null) {
            return;
        }
        View view = activity.getLayoutInflater().inflate(R.layout.base_toolbar, this, false);
        ButterKnife.bind(this, view);
        addView(view);

        RxViewUtils.setOnClickListeners(toolBarBack, this);
    }

    @Override
    public void accept(View view) throws Exception {
        switch (view.getId()) {
            case R.id.toolBarBack://回退
                if (activity != null && !activity.isFinishing()) {
                    activity.onBackPressed();
                }
                break;
        }
    }

    /**
     * 标题文案
     */
    public void setTitle(String title) {
        toolBarTitle.setText(title);
    }

    /**
     * 标题颜色
     */
    public void setTitleColor(int color) {
        toolBarTitle.setTextColor(AppUtils.getColor(color));
    }

    /**
     * 整个Toolbar的背景色
     */
    public void setBackgroundToolbar(int color) {
        rlToolBar.setBackgroundColor(AppUtils.getColor(color));
    }

    /**
     * 设置菜单文案
     *
     * @param menu          文案
     * @param clickListener 点击事件 ，id为 toolBarMenu
     */
    public void setMenu(String menu, Consumer<View> clickListener) {
        toolBarMenu.setText(menu);
        RxViewUtils.setOnClickListeners(toolBarMenu, clickListener);
    }

    /**
     * 设置菜单文本颜色
     */
    public void setMenuColor(int color) {
        toolBarMenu.setTextColor(AppUtils.getColor(color));

    }

    /**
     * 隐藏回退按钮
     */
    public void hideBack() {
        toolBarBack.setVisibility(View.GONE);
    }


    /**
     * 设置分割线颜色
     */
    public void setLineColor(int color) {
        toolBarLine.setBackgroundColor(AppUtils.getColor(color));
    }

    /**
     * 设置回退图标
     */
    public void setBackIcon(int resource) {
        toolBarBackImg.setImageResource(resource);
    }

    /**
     * 隐藏分割线
     */
    public void hideLine() {
        toolBarLine.setVisibility(View.INVISIBLE);
    }

    /**
     * 隐藏分割线
     */
    public void showLine() {
        toolBarLine.setVisibility(View.VISIBLE);
    }


    /**
     * 设置菜单是否可以点击
     *
     * @param isEnable true为可以点击，false为不可点击
     */
    public void setMenuEnable(boolean isEnable) {
        toolBarMenu.setEnabled(isEnable);
    }

    /**
     * 设置文本菜单的字体颜色，注意需要传递color/select的drawable文件
     */
    public void setMenuEnableColor(int resource) {
        ColorStateList colorStateList= getResources().getColorStateList(resource);
        toolBarMenu.setTextColor(colorStateList);

    }


}