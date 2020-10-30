package com.uranus.economy.activity;

import android.graphics.Color;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.uranus.economy.R;
import com.uranus.economy.base.BaseActivity;
import com.uranus.economy.util.Util;
import com.uranus.economy.views.CoordinateView;
import com.uranus.economy.views.PeriodView;

import butterknife.BindView;

public class ShowFreqActivity extends BaseActivity {

    @BindView(R.id.center_freq)
    protected EditText centerFreq;

    @BindView(R.id.bandwidth)
    protected EditText bandwidthEdit;

    @BindView(R.id.sampling_freq)
    protected EditText samplingFreq;


    @BindView(R.id.pic1)
    protected CoordinateView pic1;

    @BindView(R.id.pic2)
    protected PeriodView pic2;

    @BindView(R.id.is_mix_text)
    protected TextView isMixText;

    @BindView(R.id.is_mix_view)
    protected View isMixView;

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
        String samplingFreqVal = samplingFreq.getText().toString();
        double freqLong = 70000000;
        double bandwidthLong = 10000000;
        if(!TextUtils.isEmpty(freq)){
            try {
                freqLong = Double.parseDouble(freq.replace(",", ""));
            } catch (Exception e){
            }

        }
        if(!TextUtils.isEmpty(bandwidth)){
            try {
                bandwidthLong = Double.parseDouble(bandwidth.replace(",", ""));
            } catch (Exception e){
            }
        }
        double samplingFreqLong = 1;
        if(TextUtils.isEmpty(samplingFreqVal)){
            samplingFreqLong = Util.getDefSampFreq(freqLong,bandwidthLong);
            String sss = Util.doubleToInternal(Util.doubleToStr(samplingFreqLong));
            samplingFreq.setHint(sss);
        } else {
            try {
                samplingFreqLong = Double.parseDouble(samplingFreqVal.replace(",", ""));
            } catch (Exception e){
            }
        }
        pic1.setFreq(freqLong,bandwidthLong);
        pic1.invalidate();
        pic2.setFreq(freqLong,bandwidthLong,samplingFreqLong);
        pic2.invalidate();
        boolean isMix = Util.isMix(freqLong,bandwidthLong,samplingFreqLong);
        if(isMix){
            isMixText.setText("是");
            isMixView.setBackgroundColor(Color.parseColor("#DC143C"));
        } else {
            isMixText.setText("否");
            isMixView.setBackgroundColor(Color.parseColor("#00FF00"));
        }
    }

    @Override
    protected void initData() {
        centerFreq.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count != before) {
                    String string = s.toString().replace(",", "");
                    String sss = Util.doubleToInternal(string);
                    if (string.length() >= 3 ) {
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
                    String string = s.toString().replace(",", "");
                    String sss = Util.doubleToInternal(string);
                    if (string.length() >= 3 ) {
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

        samplingFreq.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count != before) {
                    String string = s.toString().replace(",", "");
                    String sss = Util.doubleToInternal(string);
                    if (string.length() >= 3 ) {
                        samplingFreq.setText(sss);
                    }
                    refreshPic();
                }
                samplingFreq.setSelection(samplingFreq.getText().length());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

}
