package com.miles.crawler.dao.Impl;

import com.miles.crawler.beans.GoodsInfo;
import com.miles.crawler.dao.GoodsInfoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GoodsInfoDaoImpl implements GoodsInfoDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void saveGoodsInfo(List<GoodsInfo> infos) {
        String sql = "REPLACE INTO goods_info(" + "goods_id," + "goods_name," + "goods_price," + "img_url) "
                + "VALUES(?,?,?,?)";
        for(GoodsInfo info : infos) {
            jdbcTemplate.update(sql, info.getGoodsId(), info.getGoodsName(), info.getGoodsPrice(), info.getImgUrl());
        }
    }
}
