package com.example.demo.Security;

import java.util.LinkedHashMap;
import java.util.Map;

public class Group {
    public static final int none = 0;
    public static final int student = 1;
    public static final int teacher = 2;
    public static final int admin = 3;
    public static String getGroupName(Integer gid){
        switch (gid.intValue()){
            case student:
                return "student";
            case teacher:
                return "teacher";
            case admin:
                return "admin";
            default:
                return "none";
        }
    }
    public static String getGroupName(Long gid){
        switch (gid.intValue()){
            case student:
                return "student";
            case teacher:
                return "teacher";
            case admin:
                return "admin";
            default:
                return "none";
        }
    }
    /* 返回Map类型的权限列表
     *
     */
    public static Map groupMap(){
        Map t = new LinkedHashMap();
        t.put("", -1);
        t.put("none", 0);
        t.put("student", 1);
        t.put("teacher", 2);
        t.put("admin", 3);
        return t;

    }
}
