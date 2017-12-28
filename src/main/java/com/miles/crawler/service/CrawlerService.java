package com.miles.crawler.service;

import com.miles.crawler.beans.GoodsInfo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

//@Service
public interface CrawlerService {

    void crawlerData(String url, Map<String,String> params);

    List<GoodsInfo> parseHtml(String html);

}
