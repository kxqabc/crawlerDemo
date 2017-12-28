package com.miles.crawler.dao;

import com.miles.crawler.beans.GoodsInfo;

import java.util.List;

public interface GoodsInfoDao {
    /**
     * 插入多个商品信息
     * @param infos
     */
    void saveGoodsInfo(List<GoodsInfo> infos);
}
