package com.jwzhu.platform.common.util;

import java.io.IOException;
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
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.jwzhu.platform.common.exception.SystemException;

public class HttpUtil {

    public static String doGet(RequestBean bean) {
        RequestConfig.Builder custom = RequestConfig.custom();
        custom.setConnectTimeout(35000);
        custom.setConnectionRequestTimeout(35000);
        custom.setSocketTimeout(60000);
        RequestConfig requestConfig = custom.build();

        HttpGet httpGet;
        try {
            URIBuilder uriBuilder = new URIBuilder(bean.getUrl());

            if (bean.getParams() != null) {
                List<NameValuePair> list = new ArrayList<>();
                for (Map.Entry<String, String> entry : bean.getParams().entrySet()) {
                    BasicNameValuePair valuePair = new BasicNameValuePair(entry.getKey(), entry.getValue());
                    list.add(valuePair);
                }
                uriBuilder.setParameters(list);
            }

            httpGet = new HttpGet(uriBuilder.build());
            httpGet.setConfig(requestConfig);
        } catch (Exception e) {
            throw new SystemException("构建请求对象失败");
        }

        if (bean.getHeaders() != null) {
            for (Map.Entry<String, String> entry : bean.getHeaders().entrySet()) {
                httpGet.addHeader(entry.getKey(), entry.getValue());
            }
        }

        httpGet.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
        try (CloseableHttpClient httpClient = HttpClients.createDefault(); CloseableHttpResponse response = httpClient.execute(httpGet)) {
            return returnResult(response);
        } catch (IOException e) {
            throw new SystemException("请求失败");
        }
    }

    public static String doPost(RequestBean bean) {
        RequestConfig.Builder custom = RequestConfig.custom();
        custom.setConnectTimeout(35000);
        custom.setConnectionRequestTimeout(35000);
        custom.setSocketTimeout(60000);
        RequestConfig requestConfig = custom.build();

        HttpPost httpPost = new HttpPost(bean.getUrl());
        httpPost.setConfig(requestConfig);

        if (bean.getHeaders() != null) {
            for (Map.Entry<String, String> entry : bean.getHeaders().entrySet()) {
                httpPost.addHeader(entry.getKey(), entry.getValue());
            }
        }

        httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
        if (bean.getParams() != null) {
            try {
                List<NameValuePair> list = new ArrayList<>();
                for (Map.Entry<String, String> entry : bean.getParams().entrySet()) {
                    BasicNameValuePair valuePair = new BasicNameValuePair(entry.getKey(), entry.getValue());
                    list.add(valuePair);
                }
                UrlEncodedFormEntity entityParam = new UrlEncodedFormEntity(list, "UTF-8");
                httpPost.setEntity(entityParam);
            } catch (Exception e) {
                throw new SystemException("转换参数失败");
            }
        }

        try (CloseableHttpClient httpClient = HttpClients.createDefault(); CloseableHttpResponse response = httpClient.execute(httpPost)) {
            return returnResult(response);
        } catch (IOException e) {
            throw new SystemException("请求失败");
        }
    }

    private static String returnResult(CloseableHttpResponse response) throws IOException {
        HttpEntity entity = response.getEntity();
        String result = EntityUtils.toString(entity);
        EntityUtils.consume(entity);
        return result;
    }

    public static class RequestBean {
        private String url;
        private Map<String, String> headers;
        private Map<String, String> params;

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
    }

}
