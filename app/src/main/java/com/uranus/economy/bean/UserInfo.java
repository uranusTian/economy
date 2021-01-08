package com.uranus.economy.bean;

import java.io.Serializable;

public class UserInfo implements Serializable {

    private static final long serialVersionUID = -337770796318534558L;
    //设备号
    public String device_num;
    //用户id
    public int id;

    //当前关卡
    public int cur_level;

    //当前得分
    public int score;

    public String toString(){
        return "device_num:"+device_num+"; id:"+id+"; cur_level:"+cur_level+"; score" + score;
    }
}
