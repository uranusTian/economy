package com.uranus.economy.activity;

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
        long freqLong = 70000000;
        long bandwidthLong = 10000000;
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
        long samplingFreq = 1;
        if(TextUtils.isEmpty(samplingFreqVal)){
            Log.d("tian", "getDefSampFreq : " + freqLong + " , " + bandwidthLong);
            samplingFreq = Util.getDefSampFreq(freqLong,bandwidthLong);
        } else {
            try {
                Log.d("tian", "samplingFreqVal : " + samplingFreqVal);
                samplingFreq = Long.parseLong(samplingFreqVal.replace(",", ""));
                Log.d("tian", "samplingFreq : " + samplingFreq);
            } catch (Exception e){
            }
        }
        pic1.setFreq(freqLong,bandwidthLong);
        pic1.invalidate();
        pic2.setFreq(freqLong,bandwidthLong,samplingFreq);
        pic2.invalidate();
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
                    String sss = Util.longToInternal(string);
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
                    String sss = Util.longToInternal(string);
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
                    String sss = Util.longToInternal(string);
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
