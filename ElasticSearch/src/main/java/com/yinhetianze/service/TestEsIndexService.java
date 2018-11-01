package com.yinhetianze.service;

import org.elasticsearch.action.ActionFuture;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.admin.indices.refresh.RefreshRequestBuilder;
import org.elasticsearch.action.delete.DeleteRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.Date;

public class TestEsIndexService {



    public static IndexResponse indexDocByXContentBuilder(TransportClient client,String index,String type,String id,Object obj){

        Map<String,Object> paraMap = new HashMap<>();
        paraMap.put("name","托雷斯");
        paraMap.put("club","马德里竞技");
        paraMap.put("country","西班牙");

        List<Map<String ,Object>> list = new ArrayList<>();
        Map<String,Object> caeer = new HashMap<>();
        caeer.put("workName","利物浦");
        caeer.put("year",2018);
        list.add(caeer);

        caeer = new HashMap<>();
        caeer.put("workName","切尔西");
        caeer.put("year",2016);
        list.add(caeer);

        paraMap.put("caeer",list);
        paraMap.put("date",new Date());


        //client.pr


        return null;
    }


    public static void createIndexAndMapping(TransportClient client,
                                             String index,String type){
        XContentBuilder xContentBuilder;
        CreateIndexRequestBuilder requestBuilder;
        try {
            xContentBuilder = XContentFactory.jsonBuilder()
                    .startObject()
                        .startObject("properties")
                            .startObject("name")
                                .field("type", "text")
                                .field("analyzer", "ik_max_word")
                                //.field("searchAnalyzer", "ik")
                            .endObject()
                            .startObject("club")
                                .field("type", "text")
                            .endObject()
                            .startObject("country")
                                .field("type", "text")
                            .endObject()
                            .startObject("date")
                                .field("type", "date")
                            .endObject()
                        .endObject()
                    .endObject();

            //PutMappingRequest putmap = Requests.putMappingRequest("football").type("playerInfo").source(xContentBuilder);
            //创建索引
            requestBuilder = client.admin().indices().prepareCreate(index);
            CreateIndexResponse response = requestBuilder.get();
            System.out.println("response:"+response);
            //为索引添加映射
            //client.admin().indices().putMapping(putmap).actionGet();
            client.admin().indices().preparePutMapping(index)
                    .setType(type)
                    .setSource(xContentBuilder)
                    .get();

        }catch (Exception e) {
            //requestBuilder.
            System.out.println(e.getMessage());
            IndicesExistsRequest indicesExistsRequest = new IndicesExistsRequest("football");
            ActionFuture<IndicesExistsResponse> ss =  client.admin().indices().exists(indicesExistsRequest);
            System.out.println("ss:"+ss.isCancelled());
            System.out.println("ss:"+ss.isDone());
            DeleteIndexRequestBuilder delete =   client.admin().indices().prepareDelete("football");
            delete.get();
        }
    }

    /**
     *
     * @param client 连接对象
     * @param idnex  索引
     * @param type 类型
     * @param dataMap 数据内容
     */
    public static void addData(TransportClient client,
                               String idnex,String type,String id,Map<String,Object> dataMap){
        try{
            client.prepareIndex(idnex,type)
                    .setId(id)
                    .setSource(
                           dataMap,XContentType.JSON
                    )
                    .get();
        }catch (Exception e){
            e.printStackTrace();
        }
    }



    public static Map<String,Object> getDataMap(){

        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("name","大卫贝克汉姆");
        dataMap.put("club","曼彻斯特联");
        dataMap.put("country","英格兰");
        dataMap.put("date",new Date());
        dataMap.put("kw","大卫贝克汉姆曼彻斯特联英格兰");

        return dataMap;
    }


    public static void refreshMapping(TransportClient client,
                                    String index,String type){
        XContentBuilder xContentBuilder = null;
        try{
            //keyword 会建立索引 不会分词
            xContentBuilder = XContentFactory.jsonBuilder()
                    .startObject()
                        .startObject("properties")
                            .startObject("kw")
                                .field("type", "keyword")
                    //.field("searchAnalyzer", "ik")
                            .endObject()
                        .endObject()
                    .endObject();

        }catch (Exception e){
            e.printStackTrace();
        }

        client.admin().indices().preparePutMapping(index)
                .setType(type)
                .setSource(xContentBuilder)
                .get();
    }
}

