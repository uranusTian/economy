package com.uranus.economy.fragment;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.uranus.economy.R;
import com.uranus.economy.base.BaseFragment;
import com.uranus.economy.nav.NavFragment;
import com.uranus.economy.nav.NavigationButton;
import com.uranus.economy.util.Util;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

public class Fragment4 extends BaseFragment implements View.OnClickListener{

    @BindView(R.id.param1)
    protected EditText param1;

    @BindView(R.id.param2)
    protected EditText param2;

    @BindView(R.id.arror1)
    protected ImageView arror1;

    @BindView(R.id.arror2)
    protected ImageView arror2;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment4;
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private double calculateRightRes(double d1){
        double res = 10 * Math.log10(d1);
        return res;
    }

    private double calculateLeftRes(double d2){
        double res = Math.pow(10,d2/10);
        return res;
    }

    @OnClick({R.id.arror1, R.id.arror2})
    @Override
    public void onClick(View v) {
        String param1Str = param1.getText().toString();
        String param2Str = param2.getText().toString();
        double param1Double = 1000;
        double param2Double = 30;
        if(!TextUtils.isEmpty(param1Str)){
            try {
                param1Double = Double.parseDouble(param1Str.replace(",", ""));
            } catch (Exception e){
            }

        }
        if(!TextUtils.isEmpty(param2Str)){
            try {
                param2Double = Double.parseDouble(param2Str.replace(",", ""));
            } catch (Exception e){
            }
        }

        if (v == arror1) {
            param2.setText(Util.doubleToInternal(Util.doubleToStr(calculateRightRes(param1Double))));
        } else if(v == arror2){
            param1.setText(Util.doubleToInternal(Util.doubleToStr(calculateLeftRes(param2Double))));
        }
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
                }
                param2.setSelection(param2.getText().length());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

}
