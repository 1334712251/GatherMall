package com.gathermall.common.config;

import org.apache.http.client.config.RequestConfig;

public class HttpClientConfig {
    public static RequestConfig getRequestConfig(){
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(10000)
                .setSocketTimeout(10000)
                .setConnectionRequestTimeout(6000)
                .build(); return requestConfig;
    }
}
