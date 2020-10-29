package com.uranus.economy.util;

public class Util {
    public static String longToInternal(String string){
        String sss = string;
        int b = string.length() / 3;
        if (string.length() >= 3 ) {
            sss = "";
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
        }
        return sss;
    }

    public static long getDefSampFreq(long freqLong, long bandwidthLong){
        if(freqLong > 0 && bandwidthLong > 0){
            int numN = 0;
            while(2 * freqLong/(2 * numN + 1) >= bandwidthLong){
                numN++;
            }
             return 4 * freqLong/(2 * numN + 1);
        }
        return 1;
    }
}

