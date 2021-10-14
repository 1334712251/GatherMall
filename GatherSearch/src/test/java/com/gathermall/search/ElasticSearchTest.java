package com.gathermall.search;

import com.gathermall.common.utils.R;
import com.gathermall.search.config.ElasticSearchConfig;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.Data;
import lombok.ToString;
import org.apache.tomcat.util.json.JSONParserTokenManager;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.Avg;
import org.elasticsearch.search.aggregations.metrics.AvgAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.naming.directory.SearchResult;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ElasticSearchTest {


    @Autowired
    private RestHighLevelClient restHighLevelClient;


    /**
     * 查询数据 GET 索引名/_search   例如：GET users/_search
     *
     */


    /**
     * 保存数据
     */
    @Test
    public void indexData() {
        IndexRequest indexRequest = new IndexRequest("users");   //保存的索引数据
        indexRequest.id("1");       //数据的id
        User user = new User();
        user.setUserName("zhangsan");
        user.setAge(18);
        user.setGender("男");
        indexRequest.source(new Gson().toJson(user), XContentType.JSON);     //要保存的内容

        try {

            //执行操作
            IndexResponse index = restHighLevelClient.index(indexRequest, ElasticSearchConfig.COMMON_OPTIONS);

            //提取有用的响应数据
            System.out.println(index);

            //IndexResponse[index=users,type=_doc,id=1,version=1,result=created,seqNo=0,primaryTerm=1,shards={"total":2,"successful":1,"failed":0}]
            //IndexResponse[index=users,type=_doc,id=1,version=3,result=updated,seqNo=2,primaryTerm=1,shards={"total":2,"successful":1,"failed":0}]
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 搜索数据
     */
    @Test
    public void searchData() {
        //创建检索请求
        SearchRequest searchRequest = new SearchRequest();
        //指定索引
        searchRequest.indices("bank");
        //指定DSL，检索条件
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //构造检索条件
//        searchSourceBuilder.query();
//        searchSourceBuilder.from();
//        searchSourceBuilder.size();
//        searchSourceBuilder.aggregation();
        //字段       //值
        searchSourceBuilder.query(QueryBuilders.matchQuery("address", "mill"));

        //聚合条件  按照年龄的值分布聚合
        TermsAggregationBuilder ageAgg = AggregationBuilders.terms("ageAgg").field("age").size(10);
        searchSourceBuilder.aggregation(ageAgg);
        //计算平均薪资
        AvgAggregationBuilder balanceAvg = AggregationBuilders.avg("balanceAvg").field("balance");
        searchSourceBuilder.aggregation(balanceAvg);


        //{"query":{"match":{"address":{"query":"mill","operator":"OR","prefix_length":0,"max_expansions":50,"fuzzy_transpositions":true,"lenient":false,"zero_terms_query":"NONE","auto_generate_synonyms_phrase_query":true,"boost":1.0}}}}
        System.out.println(searchSourceBuilder);


        searchRequest.source(searchSourceBuilder);


        try {
            //执行检索
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest, ElasticSearchConfig.COMMON_OPTIONS);

            //分析结果
            //{"took":0,"timed_out":false,"_shards":{"total":1,"successful":1,"skipped":0,"failed":0},"hits":{"total":{"value":4,"relation":"eq"},"max_score":5.4032025,"hits":[{"_index":"bank","_type":"account","_id":"970","_score":5.4032025,"_source":{"account_number":970,"balance":19648,"firstname":"Forbes","lastname":"Wallace","age":28,"gender":"M","address":"990 Mill Road","employer":"Pheast","email":"forbeswallace@pheast.com","city":"Lopezo","state":"AK"}},{"_index":"bank","_type":"account","_id":"136","_score":5.4032025,"_source":{"account_number":136,"balance":45801,"firstname":"Winnie","lastname":"Holland","age":38,"gender":"M","address":"198 Mill Lane","employer":"Neteria","email":"winnieholland@neteria.com","city":"Urie","state":"IL"}},{"_index":"bank","_type":"account","_id":"345","_score":5.4032025,"_source":{"account_number":345,"balance":9812,"firstname":"Parker","lastname":"Hines","age":38,"gender":"M","address":"715 Mill Avenue","employer":"Baluba","email":"parkerhines@baluba.com","city":"Blackgum","state":"KY"}},{"_index":"bank","_type":"account","_id":"472","_score":5.4032025,"_source":{"account_number":472,"balance":25571,"firstname":"Lee","lastname":"Long","age":32,"gender":"F","address":"288 Mill Street","employer":"Comverges","email":"leelong@comverges.com","city":"Movico","state":"MT"}}]}}
            System.out.println(searchResponse);

            SearchHits hits = searchResponse.getHits();
            SearchHit[] searchHits = hits.getHits();
            for (SearchHit hit : searchHits) {
                hit.getIndex();
                hit.getType();
                hit.getId();
                String sourceAsString = hit.getSourceAsString();
                Accout accout = new Gson().fromJson(sourceAsString, Accout.class);
                System.out.println(accout);
            }
            //获取这次的检索到的分析信息
            Aggregations aggregations = searchResponse.getAggregations();
            for (Aggregation aggregation : aggregations.asList()) {
                System.out.println("当前聚合" + aggregation.getName());
            }
            Terms ageAgg1 = aggregations.get("ageAgg");
            for (Terms.Bucket bucket : ageAgg1.getBuckets()) {
                String keyAsString = bucket.getKeyAsString();
                System.out.println("年龄" + keyAsString + "有" + bucket.getDocCount() + "个");
            }

            Avg balanceAvg1 = aggregations.get("balanceAvg");
            System.out.println("平均薪资" + balanceAvg1.getValue());


        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    @Data
    class User {
        private String userName;
        private String gender;
        private int age;
    }

    @ToString
    @Data
    static class Accout {
        private int account_number;

        private int balance;

        private String firstname;

        private String lastname;

        private int age;

        private String gender;

        private String address;

        private String employer;

        private String email;

        private String city;

        private String state;
    }


    @Test
    public void contextLoads() {
        System.out.println(restHighLevelClient);
    }


    @Test
    public void getData(){
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);

        R r = new R().ok();
        R r1 = r.setData(list);


        List<Integer> data = r1.getData(new TypeToken<List<Integer>>() {
        });

        System.out.println(data.toString());


    }
}
