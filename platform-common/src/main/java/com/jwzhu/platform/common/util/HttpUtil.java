package com.jwzhu.platform.common.util;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.AbstractHttpEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.jwzhu.platform.common.exception.SystemException;

public class HttpUtil {

    public static String doGet(RequestBean bean) {
        HttpGet httpGet = new HttpGet(getUrl(bean, true));

        httpGet.setConfig(getRequestConfig());

        setHeaders(httpGet, bean);

        try (CloseableHttpClient httpClient = HttpClients.createDefault(); CloseableHttpResponse response = httpClient.execute(httpGet)) {
            return returnResult(response);
        } catch (IOException e) {
            throw new SystemException("请求失败");
        }
    }

    public static String doPost(RequestBean bean) {
        HttpPost httpPost = new HttpPost(getUrl(bean, false));

        httpPost.setConfig(getRequestConfig());

        setHeaders(httpPost, bean);

        try {
            AbstractHttpEntity entity = null;
            if(!StringUtil.isEmpty(bean.getBody())){
                entity = new StringEntity(bean.getBody());
            }else if(bean.getParams() != null){
                List<NameValuePair> list = mapToPair(bean.getParams());
                entity = new UrlEncodedFormEntity(list, "UTF-8");
            }
            httpPost.setEntity(entity);
        } catch (Exception e) {
            throw new SystemException("设置请求体错误");
        }

        try (CloseableHttpClient httpClient = HttpClients.createDefault(); CloseableHttpResponse response = httpClient.execute(httpPost)) {
            return returnResult(response);
        } catch (IOException e) {
            throw new SystemException("请求失败");
        }
    }

    private static String getUrl(RequestBean bean, boolean isGet) {
        URIBuilder uriBuilder;
        try {
            uriBuilder = new URIBuilder(bean.getUrl());
        } catch (URISyntaxException e) {
            throw new SystemException("请求地址错误");
        }

        List<NameValuePair> list = mapToPair(bean.getUrlParams());

        boolean paramsToUrl = false;
        if(bean.getParams() != null){
            if(isGet){
                paramsToUrl = true;
            }else if(StringUtil.isEmpty(bean.getBody())){
                paramsToUrl = true;
            }
        }

        if(paramsToUrl){
            for (Map.Entry<String, String> entry : bean.getParams().entrySet()) {
                if(StringUtil.isEmpty(entry.getKey()) || StringUtil.isEmpty(entry.getValue())){
                    continue;
                }
                BasicNameValuePair valuePair = new BasicNameValuePair(entry.getKey(), entry.getValue());
                list.add(valuePair);
            }
        }

        uriBuilder.setParameters(list);
        return uriBuilder.toString();
    }

    private static void setHeaders(HttpRequestBase requestBase, RequestBean bean){
        if (bean.getHeaders() != null) {
            for (Map.Entry<String, String> entry : bean.getHeaders().entrySet()) {
                if(StringUtil.isEmpty(entry.getKey()) || StringUtil.isEmpty(entry.getValue())){
                    continue;
                }
                requestBase.addHeader(entry.getKey(), entry.getValue());
            }
        }

        requestBase.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
    }

    private static List<NameValuePair> mapToPair(Map<String, String> map){
        List<NameValuePair> list = new ArrayList<>();
        if (map != null) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                if(StringUtil.isEmpty(entry.getKey()) || StringUtil.isEmpty(entry.getValue())){
                    continue;
                }
                BasicNameValuePair valuePair = new BasicNameValuePair(entry.getKey(), entry.getValue());
                list.add(valuePair);
            }
        }
        return list;
    }

    private static RequestConfig getRequestConfig() {
        RequestConfig.Builder custom = RequestConfig.custom();
        custom.setConnectTimeout(35000);
        custom.setConnectionRequestTimeout(35000);
        custom.setSocketTimeout(60000);
        return custom.build();
    }

    private static String returnResult(CloseableHttpResponse response) throws IOException {
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode != 200) {
            throw new SystemException("服务请求失败：" + statusCode);
        }
        HttpEntity entity = response.getEntity();
        String result = EntityUtils.toString(entity);
        EntityUtils.consume(entity);
        return result;
    }

    public static class RequestBean {
        /**
         * 请求地址
         */
        private String url;
        /**
         * 请求头
         */
        private Map<String, String> headers;
        /**
         * 请求地址参数
         */
        private Map<String, String> urlParams;
        /**
         * 请求体参数<br/>
         * 当请求为POST，body不为空时会被添加到地址参数中<br/>
         * 当请求为GET，添加到地址参数中
         */
        private Map<String, String> params;
        /**
         * 请求体<br/>
         * 当请求为GET，会被忽略
         */
        private String body;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public Map<String, String> getHeaders() {
            return headers;
        }

        public void setHeaders(Map<String, String> headers) {
            this.headers = headers;
        }

        public void addHeader(String key, String value) {
            if (this.headers == null) {
                this.headers = new HashMap<>();
            }
            this.headers.put(key, value);
        }

        public Map<String, String> getUrlParams() {
            return urlParams;
        }

        public void setUrlParams(Map<String, String> urlParams) {
            this.urlParams = urlParams;
        }

        public void addUrlParam(String key, String value) {
            if (this.urlParams == null) {
                this.urlParams = new HashMap<>();
            }
            this.urlParams.put(key, value);
        }

        public Map<String, String> getParams() {
            return params;
        }

        public void setParams(Map<String, String> params) {
            this.params = params;
        }

        public void addParam(String key, String value) {
            if (this.params == null) {
                this.params = new HashMap<>();
            }
            this.params.put(key, value);
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }
    }

}
