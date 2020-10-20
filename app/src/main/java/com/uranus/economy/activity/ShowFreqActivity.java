package com.uranus.economy.activity;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.uranus.economy.R;
import com.uranus.economy.base.BaseActivity;
import com.uranus.economy.views.CoordinateView;

import butterknife.BindView;

public class ShowFreqActivity extends BaseActivity {

    @BindView(R.id.center_freq)
    protected EditText centerFreq;

    @BindView(R.id.bandwidth)
    protected EditText bandwidthEdit;



    @BindView(R.id.pic1)
    protected CoordinateView pic1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_show_freq;
    }

    private void refreshPic(){
        String freq = centerFreq.getText().toString();
        String bandwidth = bandwidthEdit.getText().toString();
        long freqLong = 7000;
        long bandwidthLong = 1000;
        if(!TextUtils.isEmpty(freq)){
            try {
                freqLong = Long.parseLong(freq.replace(",", ""));
            } catch (Exception e){

            }

        }
        if(!TextUtils.isEmpty(bandwidth)){
            try {
                bandwidthLong = Long.parseLong(bandwidth.replace(",", ""));
            } catch (Exception e){

            }
        }
        pic1.setFreq(freqLong,bandwidthLong);
        pic1.invalidate();
    }

    @Override
    protected void initData() {
        centerFreq.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                refreshPic();
                if (count != before) {
                    String sss = "";
                    String string = s.toString().replace(",", "");
                    int b = string.length() / 3;
                    if (string.length() >= 3 ) {
                        int yushu = string.length() % 3;
                        if (yushu == 0) {
                            b = string.length() / 3 - 1;
                            yushu = 3;
                        }
                        for (int i = 0; i < b; i++) {
                            sss = sss + string.substring(0, yushu) + "," + string.substring(yushu, 3);
                            string = string.substring(3, string.length());
                        }
                        sss = sss + string;
                        centerFreq.setText(sss);
                    }
                    refreshPic();
                }
                centerFreq.setSelection(centerFreq.getText().length());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        bandwidthEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count != before) {
                    String sss = "";
                    String string = s.toString().replace(",", "");
                    int b = string.length() / 3;
                    if (string.length() >= 3 ) {
                        int yushu = string.length() % 3;
                        if (yushu == 0) {
                            b = string.length() / 3 - 1;
                            yushu = 3;
                        }
                        for (int i = 0; i < b; i++) {
                            sss = sss + string.substring(0, yushu) + "," + string.substring(yushu, 3);
                            string = string.substring(3, string.length());
                        }
                        sss = sss + string;
                        bandwidthEdit.setText(sss);
                    }
                    refreshPic();
                }
                bandwidthEdit.setSelection(bandwidthEdit.getText().length());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

}
