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
//        SimpleDateFormat createTimeSdf1 = new SimpleDateFormat("yyyy-MM-dd");
        List<String> labels = new ArrayList<>();
        labels.add("ID:" + UserManager.getInstance().getUser().id + "  " + UserManager.getInstance().getUser().name);
//        labels.add(""+ createTimeSdf1.format(new Date()));
//        labels.add("不可扩散");
        setBackground(new WaterMarkBg(getContext(), labels, -30, 13, 40));
    }
}
