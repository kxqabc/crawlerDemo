package com.miles.crawler;

public interface SysConstant {

    String DEFAULT_CHARSET = "utf-8";   //默认字符集

    String TARGET_URL = "https://search.jd.com/Search";     //要爬取的网站地址

    String DEFAULT_DATE_FORMAT = "yyy-MM-dd HH:mm:ss";      //日期格式

    /**
     * Http请求头
     */
    interface Header{
        String ACCEPT = "Accept";
        String ACCEPT_ENCODING = "Accept-Encoding";
        String ACCEPT_LANGUAGE = "Accept-Language";
        String CACHE_CONTROL = "Cache-Controle";
        String COOKIE = "Cookie";
        String HOST = "Host";
        String PROXY_CONNECTION = "Proxy-Connection";
        String REFERER = "Referer";
        String USER_AGENT = "User-Agent";
    }
}
