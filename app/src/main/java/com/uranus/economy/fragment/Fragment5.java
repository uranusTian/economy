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

import butterknife.BindView;

public class Fragment5 extends BaseFragment {

    @BindView(R.id.param1)
    protected EditText param1;

    @BindView(R.id.param2)
    protected EditText param2;

    @BindView(R.id.cal_res)
    protected TextView calRes;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment5;
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public double getArcCos(double x) {
        double i = 0, j = Math.PI;
        double result = (i + j) / 2;
        double judge = Math.cos(result) - x;
        double temp;
        while(Math.abs(judge) > 0.000000000000001) {
            result = (i + j) / 2;
            temp = Math.cos(result);
            if(temp - x > 0) {
                i = result;
            } else {
                j = result;
            }
            judge = Math.cos(result) - x;

        }
        return result;
    }

    private double calculateRes(double d1,double d2){
//        double t1 = d2 / 6378.137;
//        double t2 =  getArcCos(6378137 / (6378137 + d1));

        double t1 = d2 / 4.12;
        double t2 =  Math.sqrt(d1);

        if(t1 < t2){
            return 0;
        } else {
            double temp = d2 / 4.12 - Math.sqrt(d1);
            return temp * temp;
        }
    }

    private void refreshRes(){
        String workFreqStr = param1.getText().toString();
        String spaceDistanceStr = param2.getText().toString();
        double workFreqDouble = 100;
        double spaceDistanceDouble = 100;
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
        param1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count != before) {
                    String string = s.toString().replace(",", "");
                    String sss = Util.doubleToInternal(string);
                    if (string.length() >= 3 ) {
                        param1.setText(sss);
                    }
                    refreshRes();
                }
                param1.setSelection(param1.getText().length());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        param2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count != before) {
                    String string = s.toString().replace(",", "");
                    String sss = Util.doubleToInternal(string);
                    if (string.length() >= 3 ) {
                        param2.setText(sss);
                    }
                    refreshRes();
                }
                param2.setSelection(param2.getText().length());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
