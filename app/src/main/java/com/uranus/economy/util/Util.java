package com.uranus.economy.util;

import android.text.TextUtils;

import java.text.DecimalFormat;

public class Util {

    public static void main(String[] args){
        String testStr = "74580.125";
        System.out.println(doubleToInternal(testStr));
//        System.out.println(testStr);
    }

    public static String doubleToInternal(String string){
        if(TextUtils.isEmpty(string)){
            return string;
        }
        String end = "";
        String strInt = string;
        String sss = string;
        if(string.contains(".")){
            int decIndex = string.indexOf(".");
            end = string.substring(decIndex,string.length());
            strInt = string.substring(0,decIndex);
            sss = strInt;
        }


        int b = strInt.length() / 3;
        if (strInt.length() >= 3 ) {
            sss = "";
            int yushu = strInt.length() % 3;
            if (yushu == 0) {
                b = strInt.length() / 3 - 1;
                yushu = 3;
            }
            for (int i = 0; i < b; i++) {
                sss = sss + strInt.substring(0, yushu) + "," + strInt.substring(yushu, 3);
                strInt = strInt.substring(3, strInt.length());
            }
            sss = sss + strInt;
        }
        return sss + end;
    }

    public static double getDefSampFreq(double freqLong, double bandwidthLong){
        if(freqLong > 0 && bandwidthLong > 0){
            int numN = 0;
            while(2 * freqLong/(2 * numN + 1) >= bandwidthLong){
                numN++;
            }
            numN--;
            double res = 4 * freqLong/(2 * numN + 1);
            if(res >= 2 * bandwidthLong){
                return res;
            } else {
                return 2 * bandwidthLong;
            }
        }
        return 1;
    }

    public static String doubleToStr(double doubleVal){
        DecimalFormat df = new DecimalFormat("0.000");
        String a = df.format(doubleVal);
        return a;
    }

    public static boolean isMix(double centerFreqLong,double bandwidthLong,double samplingFreqLong){
        double high1 = centerFreqLong - bandwidthLong /2;
        double high2 = centerFreqLong + bandwidthLong /2;
        double low1 = -centerFreqLong - bandwidthLong /2;
        double low2 = -centerFreqLong + bandwidthLong /2;
        double lowMid = -centerFreqLong;

        if(samplingFreqLong <= 0){
            return false;
        }

        while(low1 < high2){
            if(low1 > high1 && low1 < high2){
                return true;
            } else if(low2 > high1 && low2 < high2){
                return true;
            } else if(lowMid >= high1 && lowMid <= high2){
                return true;
            }
            low1 += samplingFreqLong;
            low2 += samplingFreqLong;
            lowMid += samplingFreqLong;
        }
        return false;
    }


}

