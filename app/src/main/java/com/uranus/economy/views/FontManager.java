package com.uranus.economy.views;

import android.graphics.Typeface;
import android.widget.TextView;

import com.uranus.economy.base.App;

public class FontManager {
    /**
     * 改变字体样式
     * 如果指定的字体不包含，或者查找失败，则会使用当前字体
     */
    public static void setFontType(TextView textView, FontType type) {
        Typeface typeface = textView.getTypeface();
        switch (type) {
            case No:
                typeface = Typeface.DEFAULT;
                break;
            case RndBold:
                typeface = Typeface.createFromAsset(App.context.getAssets(), "GothamRnd-Bold.ttf");
                break;
            case RndBook:
                typeface = Typeface.createFromAsset(App.context.getAssets(), "GothamRnd-Book.otf");
                break;
            case RoundedLight:
                typeface = Typeface.createFromAsset(App.context.getAssets(), "GothamRounded-Light.otf");
                break;
            case RoundedMedium:
                typeface = Typeface.createFromAsset(App.context.getAssets(), "GothamRounded-Medium.otf");
                break;
            case FrankfurterMedium:
                typeface = Typeface.createFromAsset(App.context.getAssets(), "FrankfurterMediumStd.otf");
                break;

        }
        textView.setTypeface(typeface);
    }
    /**
     * 改变字体样式
     * 如果指定的字体不包含，或者查找失败，则会使用当前字体
     */
    public static Typeface getFontType(FontType type) {
        Typeface typeface = null;
        switch (type) {
            case No:
                typeface = Typeface.DEFAULT;
                break;
            case RndBold:
                typeface = Typeface.createFromAsset(App.context.getAssets(), "GothamRnd-Bold.ttf");
                break;
            case RndBook:
                typeface = Typeface.createFromAsset(App.context.getAssets(), "GothamRnd-Book.otf");
                break;
            case RoundedLight:
                typeface = Typeface.createFromAsset(App.context.getAssets(), "GothamRounded-Light.otf");
                break;
            case RoundedMedium:
                typeface = Typeface.createFromAsset(App.context.getAssets(), "GothamRounded-Medium.otf");
                break;
            case FrankfurterMedium:
                typeface = Typeface.createFromAsset(App.context.getAssets(), "FrankfurterMediumStd.otf");
                break;

        }
        return typeface;
    }
}
