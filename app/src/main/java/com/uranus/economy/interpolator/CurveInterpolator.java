package com.uranus.economy.interpolator;

import android.util.Log;
import android.view.animation.BaseInterpolator;
import android.view.animation.Interpolator;

public class CurveInterpolator extends BaseInterpolator {

        @Override
        public float getInterpolation(float input) {
//            return (float)Math.sqrt(input * (2 - input));
            if (input > 0 && input < 0.5) {
                return (float)Math.sqrt(input * (1 - input));
            } else if (input < 0.8){
                return 1- (float)Math.sqrt(input * (1 - input));
            } else {
                float f1 = 1 - (float)Math.sqrt(input * (1 - input));
                if(f1 > input * input){
                    return f1;
                } else {
                    return input * input;
                }

            }
        }

}
