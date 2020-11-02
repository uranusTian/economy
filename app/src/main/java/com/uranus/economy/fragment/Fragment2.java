package com.uranus.economy.fragment;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.uranus.economy.R;
import com.uranus.economy.base.BaseFragment;
import com.uranus.economy.util.Util;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;

public class Fragment2 extends BaseFragment {

    @BindView(R.id.work_freq)
    protected EditText workFreq;

    @BindView(R.id.space_distance)
    protected EditText spaceDistance;

    @BindView(R.id.cal_res)
    protected TextView calRes;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment2;
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private double calculateRes(double d1,double d2){
        double res = 20 * Math.log10(d1) + 20 * Math.log10(d2) + 32.44;
        return res;
    }

    private void refreshRes(){
        String workFreqStr = workFreq.getText().toString();
        String spaceDistanceStr = spaceDistance.getText().toString();
        double workFreqDouble = 2200;
        double spaceDistanceDouble = 1000;
        if(!TextUtils.isEmpty(workFreqStr)){
            try {
                workFreqDouble = Double.parseDouble(workFreqStr.replace(",", ""));
            } catch (Exception e){
            }

        }
        if(!TextUtils.isEmpty(spaceDistanceStr)){
            try {
                spaceDistanceDouble = Double.parseDouble(spaceDistanceStr.replace(",", ""));
            } catch (Exception e){
            }
        }
        calRes.setText(Util.doubleToInternal(Util.doubleToStr(calculateRes(workFreqDouble,spaceDistanceDouble))));


    }

    @Override
    protected void initData() {
        workFreq.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count != before) {
                    String string = s.toString().replace(",", "");
                    String sss = Util.doubleToInternal(string);
                    if (string.length() >= 3 ) {
                        workFreq.setText(sss);
                    }
                    refreshRes();
                }
                workFreq.setSelection(workFreq.getText().length());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        spaceDistance.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count != before) {
                    String string = s.toString().replace(",", "");
                    String sss = Util.doubleToInternal(string);
                    if (string.length() >= 3 ) {
                        spaceDistance.setText(sss);
                    }
                    refreshRes();
                }
                spaceDistance.setSelection(spaceDistance.getText().length());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
