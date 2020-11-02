package com.uranus.economy.fragment;

import android.graphics.Color;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.uranus.economy.R;
import com.uranus.economy.base.BaseFragment;
import com.uranus.economy.util.Util;
import com.uranus.economy.views.CoordinateView;
import com.uranus.economy.views.PeriodView;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;

public class Fragment1 extends BaseFragment {

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

    @BindView(R.id.fornt_center_freq)
    protected TextView forntCenterFreq;

    @BindView(R.id.reverse_center_freq)
    protected TextView reverseCenterFreq;

    @BindView(R.id.front_highest_freq)
    protected TextView fronthighestfreq;

    @BindView(R.id.reverse_highest_freq)
    protected TextView reverseHighestFreq;

    @BindView(R.id.front_last_freq)
    protected TextView frontLastFreq;

    @BindView(R.id.reverse_last_freq)
    protected TextView reverseLastFreq;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment1;
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
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
        double forntCenterFreqDouble = Util.getFrontCenterFreq(freqLong,samplingFreqLong);
        double reverseCenterFreqDouble = forntCenterFreqDouble * (-1);
        forntCenterFreq.setText(Util.doubleToInternal(Util.doubleToStr(forntCenterFreqDouble)));
        reverseCenterFreq.setText(Util.doubleToInternal(Util.doubleToStr(reverseCenterFreqDouble)));
        fronthighestfreq.setText(Util.doubleToInternal(Util.doubleToStr(forntCenterFreqDouble + bandwidthLong/2)));
        reverseHighestFreq.setText(Util.doubleToInternal(Util.doubleToStr(reverseCenterFreqDouble + bandwidthLong/2)));
        frontLastFreq.setText(Util.doubleToInternal(Util.doubleToStr(forntCenterFreqDouble - bandwidthLong/2)));
        reverseLastFreq.setText(Util.doubleToInternal(Util.doubleToStr(reverseCenterFreqDouble - bandwidthLong/2)));
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
