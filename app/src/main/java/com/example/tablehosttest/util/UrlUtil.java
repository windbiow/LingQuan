package com.example.tablehosttest.util;

public class UrlUtil {

    public static String createHomePagerUrl(Long materialId,int page){
        return "discovery/"+materialId+"/"+page;
    }

    public static String getCoverPath(String pict_url) {
        return  "https:"+pict_url;
    }
}
