package com.uranus.economy.views;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

import com.uranus.economy.manager.UserManager;

import java.util.ArrayList;
import java.util.List;

public class WaterMarkView extends AppCompatTextView {
    public WaterMarkView(Context context) {
        super(context);
        init();
    }

    public WaterMarkView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public WaterMarkView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        List<String> labels = new ArrayList<>();
        labels.add("王东");
        labels.add("4C1K-WXY");
        setBackground(new WaterMarkBg(getContext(), labels, -30, 13, 40));
    }
}
