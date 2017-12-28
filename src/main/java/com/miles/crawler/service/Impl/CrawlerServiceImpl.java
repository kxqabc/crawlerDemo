package com.miles.crawler.service.Impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.miles.crawler.beans.GoodsInfo;
import com.miles.crawler.dao.GoodsInfoDao;
import com.miles.crawler.service.CrawlerService;
import com.miles.crawler.util.HttpClientUtils;
import org.jsoup.Jsoup;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CrawlerServiceImpl implements CrawlerService {
    private static Logger logger = LoggerFactory.getLogger(CrawlerServiceImpl.class);

    private String HTTPS_PROTOCOL = "https:";
    @Autowired
    private GoodsInfoDao goodsInfoDao;

    @Override
    public void crawlerData(String url, Map<String, String> params) {
        String html = HttpClientUtils.sendGet(url,null,params);
        if ( !StringUtil.isBlank(html) ){
            List<GoodsInfo> goodsInfos = parseHtml(html);
            goodsInfoDao.saveGoodsInfo(goodsInfos);
        }
    }

    @Override
    public List<GoodsInfo> parseHtml(String html) {
        List<GoodsInfo> goodsInfos = Lists.newArrayList();
        //获取DOM并解析
        Document document = Jsoup.parse(html);
        //根据class获取元素s，其中每个element中包含着一个商品的所有信息
        Elements elements = document.select("ul[class=gl-warp clearfix]").select("li[class=gl-item]");
        int count = 0;
        for (Element element:elements){
            String goodsId = element.attr("data-sku");
            String goodsName = element.select("div[class=p-name p-name-type-2]").select("em").text();
            String goodsPrice = element.select("div[class=p-price]").select("strong").select("i").text();
            String imgUrl = HTTPS_PROTOCOL + element.select("div[class=p-img]").select("a").select("img").attr("src");
            GoodsInfo goodsInfo = new GoodsInfo(goodsId, goodsName, imgUrl, goodsPrice);
            goodsInfos.add(goodsInfo);
            String jsonStr = JSON.toJSONString(goodsInfo);
            logger.info("成功爬取【" + goodsName + "】的基本信息 ");
            logger.info(jsonStr);
            //每个页面获取10个商品的信息
            if (count++ >= 9)
                break;
        }
        return goodsInfos;
    }

}
