package com.miles.crawler.util;

import com.miles.crawler.SysConstant;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Http发送请求工具类
 */
public class HttpClientUtils {
    private final static Logger logger = LoggerFactory.getLogger(HttpClientUtils.class);

    private final static String GET_METHOD = "GET";
    private final static String POST_METHOD = "POST";

    public static String sendGet(String url, Map<String, String> headers, Map<String, String> params) {

        CloseableHttpClient client = HttpClients.createDefault();
        StringBuilder reqUrl = new StringBuilder(url);
        String result = "";

        if (params != null && params.size() > 0) {
            reqUrl.append("?");
            for (Entry<String, String> param : params.entrySet()) {
                reqUrl.append(param.getKey() + "=" + param.getValue() + "&");
            }
            url = reqUrl.subSequence(0, reqUrl.length() - 1).toString();
        }
        logger.debug("[url:" + url + ",method:" + GET_METHOD + "]");
        HttpGet httpGet = new HttpGet(url);

        logger.debug("Header\n");
        if (headers != null && headers.size() > 0) {
            for (Entry<String, String> header : headers.entrySet()) {
                httpGet.addHeader(header.getKey(), header.getValue());
                logger.debug(header.getKey() + " : " + header.getValue());
            }
        }
        CloseableHttpResponse response = null;
        try {
            response = client.execute(httpGet);

            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                result = EntityUtils.toString(entity, SysConstant.DEFAULT_CHARSET);
            }
        } catch (IOException e) {
            logger.error("网络请求出错，请检查原因");
        } finally {

            try {
                if (response != null) {
                    response.close();
                }
                client.close();
            } catch (IOException e) {
                logger.error("网络关闭错误错，请检查原因");
            }
        }
        return result;
    }


    public static String sendPost(String url, Map<String, String> headers, Map<String, String> params) {
        CloseableHttpClient client = HttpClients.createDefault();
        String result = "";
        HttpPost httpPost = new HttpPost(url);

        if (params != null && params.size() > 0) {
            List<NameValuePair> paramList = new ArrayList<>();
            for (Entry<String, String> param : params.entrySet()) {
                paramList.add(new BasicNameValuePair(param.getKey(), param.getValue()));
            }
            logger.debug("[url: " + url + ",method: " + POST_METHOD + "]");

            try {
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList, SysConstant.DEFAULT_CHARSET);
                httpPost.setEntity(entity);
            } catch (UnsupportedEncodingException e) {
                logger.error("不支持的编码");
            }

            if (headers != null && headers.size() > 0) {
                logger.debug("Header\n");
                if (headers != null && headers.size() > 0) {
                    for (Entry<String, String> header : headers.entrySet()) {
                        httpPost.addHeader(header.getKey(), header.getValue());
                        logger.debug(header.getKey() + " : " + header.getValue());
                    }
                }
            }
            CloseableHttpResponse response = null;
            try {
                response = client.execute(httpPost);
                HttpEntity entity = response.getEntity();
                result = EntityUtils.toString(entity, SysConstant.DEFAULT_CHARSET);
            } catch (IOException e) {
                logger.error("网络请求出错，请检查原因");
            } finally {
                try {
                    if (response != null) {
                        response.close();
                    }
                    client.close();
                } catch (IOException e) {
                    logger.error("网络关闭错误");
                }
            }
        }
        return result;
    }

    public static String senPostJson(String url, String json, Map<String, String> headers) {
        CloseableHttpClient client = HttpClients.createDefault();
        String result = "";
        HttpPost httpPost = new HttpPost(url);
        StringEntity stringEntity = new StringEntity(json, ContentType.APPLICATION_JSON);
        httpPost.setEntity(stringEntity);
        logger.debug("[url: " + url + ",method: " + POST_METHOD + ", json: " + json + "]");

        if (headers != null && headers.size() > 0) {
            logger.debug("Header\n");
            if (headers != null && headers.size() > 0) {
                for (Entry<String, String> header : headers.entrySet()) {
                    httpPost.addHeader(header.getKey(), header.getValue());
                    logger.debug(header.getKey() + " : " + header.getValue());
                }
            }
        }
        CloseableHttpResponse response = null;
        try {
            response = client.execute(httpPost);
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity, SysConstant.DEFAULT_CHARSET);
        } catch (IOException e) {
            logger.error("网络请求出错，请检查原因");
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                client.close();
            } catch (IOException e) {
                logger.error("网络关闭错误");
            }
        }
        return result;
    }
}
