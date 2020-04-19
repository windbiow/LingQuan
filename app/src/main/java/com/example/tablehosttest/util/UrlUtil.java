package com.example.tablehosttest.util;

public class UrlUtil {

    public static String createHomePagerUrl(int materialId,int page){
        return "discovery/"+materialId+"/"+page;
    }
}
