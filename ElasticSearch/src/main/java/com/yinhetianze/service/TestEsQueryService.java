package com.yinhetianze.service;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;

import java.util.Map;

public class TestEsQueryService {



    public static void testWildardQuery(TransportClient client,String index,String type){

        //模糊查询  查询的字段 会根据分词进行查询 否则 默认分词可能会查不到需要的结果
        QueryBuilder queryBuilder = QueryBuilders.wildcardQuery("name","*罗");
        System.out.println("queryBuilder:"+queryBuilder);
        //高亮
        HighlightBuilder hiBuilder=new HighlightBuilder();
        hiBuilder.preTags("abc#");
        hiBuilder.postTags("#def");
        hiBuilder.field("name");

        SearchResponse searchResponse = client.prepareSearch(index)
                .setTypes(type)
                //设置查找模糊查询
                .setQuery(queryBuilder)
                //设置排序
                //.addSort("storage", SortOrder.DESC)
                //从哪条开始 (类似current-1)*pageSize
                .setFrom(0)
                //每页显示多少条 类似pageSize
                .setSize(10)
                .highlighter(hiBuilder)
                .get();
        SearchHits searchHits = searchResponse.getHits();
        System.out.println("total:"+searchHits.getTotalHits());
        for(SearchHit hit:searchHits.getHits()){
            Map<String, Object> source =hit.getSourceAsMap();
            System.out.println("source:"+source);

            Map<String,HighlightField> h = hit.getHighlightFields();
            System.out.println("hightlight:"+h);
        }


        System.out.println("response:"+searchResponse);
    }


    public static void testTermQuery(TransportClient client,String index,String type){
        QueryBuilder termBuilder;
        //精确查询 查询到的是分词后的结果
        //termBuilder = QueryBuilders.termQuery("name","皮耶罗");
        //精确查询 多条件 in (xx,xx)
        //termBuilder  = QueryBuilders.termsQuery("name","皮耶罗","贝克汉姆");

        //条件组合  must 相当于 and  where 条件A and 条件B
        /*
        termBuilder = QueryBuilders.boolQuery()
                .must(QueryBuilders.termQuery("name","皮耶罗"))
                .must(QueryBuilders.termQuery("club","尤文图斯"));
        */
        //should 相当于 or

        termBuilder = QueryBuilders.boolQuery()
                .should(QueryBuilders.termQuery("kw","大卫贝克汉姆曼彻斯特联英格兰")
                )
                .should(QueryBuilders.termQuery("name","克里斯"));

        //mustNot 相当于 not
        /*
        termBuilder = QueryBuilders.boolQuery()
                .mustNot(QueryBuilders.termQuery("name","1234"))
                .mustNot(QueryBuilders.termQuery("m_price","19.3"));
        */

        System.out.println("termBuilder:"+termBuilder);

        SearchResponse searchResponse =client.prepareSearch(index)
                .setTypes(type)
                .setQuery(termBuilder)
                .get();

        SearchHits searchHits = searchResponse.getHits();
        System.out.println("total:"+searchHits.getTotalHits());
        for(SearchHit hit:searchHits.getHits()){
            Map<String, Object> source =hit.getSourceAsMap();
            System.out.println("source:"+source);

            Map<String,HighlightField> h = hit.getHighlightFields();
            System.out.println("hightlight:"+h);
        }

        System.out.println("response:"+searchResponse);
    }


}
