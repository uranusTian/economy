package com.uranus.economy.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

import com.uranus.economy.R;

public class FontTextView extends AppCompatTextView {

    public FontType fontType;

    public FontTextView(Context context) {
        super(context);
    }

    public FontTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        initView(context, attrs);
    }

    public FontTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initView(context, attrs);

    }

    private void initView(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.FontTextView);
        //注意这里是value值，然后根据value值去枚举里找对应下标的类型
        //所以在添加自定义枚举时，注意下标一定要匹配
        this.fontType = FontType.values()[ta.getInt(R.styleable.FontTextView_fontType, 0)];

        ta.recycle();


        setFontType(fontType);
    }

    /**
     * 改变字体样式
     * 如果指定的字体不包含，或者查找失败，则会使用当前字体
     */
    public void setFontType(FontType type) {
        fontType = type;
        FontManager.setFontType(this, type);
    }


}
