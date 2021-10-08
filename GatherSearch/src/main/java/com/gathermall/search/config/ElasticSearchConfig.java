package com.gathermall.search.config;


import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticSearchConfig {

    @Bean
    public RestHighLevelClient ElasticSearchRestConfig(){
        return new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("120.26.84.226", 9200, "http")));
    }
}
