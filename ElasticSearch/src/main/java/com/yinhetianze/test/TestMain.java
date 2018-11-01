package com.yinhetianze.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yinhetianze.pojo.ProductInfo;
import com.yinhetianze.service.TestEsIndexService;
import com.yinhetianze.service.TestEsQueryService;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.transport.client.PreBuiltTransportClient;


import org.elasticsearch.common.settings.Settings;

import java.math.BigDecimal;
import java.net.InetAddress;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class TestMain {

    public final static String host = "192.168.8.140";
    public final static int port = 9300;


    public static void main(String[] args) throws Exception{

        TransportClient client = new PreBuiltTransportClient(Settings.EMPTY)
                //.addTransportAddress(new TransportAddress(InetAddress.getByName("host1"), 9300))
                .addTransportAddress(new TransportAddress(InetAddress.getByName(host), 9300));
        // on shutdown
        //addIndex(client);
        //getIndex(client);
        //getIndexList(client);
        //update(client);
        //delete(client);

        //TestEsIndexService.createIndexAndMapping(client);

        //TestEsIndexService.addData(client,"football","playerInfo","3",TestEsIndexService.getDataMap());

        //TestEsIndexService.refreshMapping(client,"football","playerInfo");

        //TestEsQueryService.testWildardQuery(client,"football","playerInfo");
        TestEsQueryService.testTermQuery(client,"football","playerInfo");


        client.close();
    }

    static void getIndex(TransportClient client){
        //GetResponse response = client.prepareGet("twitter", "tweet", "1").get();
        GetResponse response = client.prepareGet("twitter" ,"tweet","1").setStoredFields("user").get();
        System.out.println("response:"+response);
    }


    static void addIndex(TransportClient client) throws Exception{

        String index = "product";

        String type = "phone";

        ProductInfo productInfo = new ProductInfo("1","华为手机-honer",new BigDecimal("19.3"),
                new BigDecimal("15.3"),new Date(),10);

        ProductInfo productInfo1 = new ProductInfo("2","中兴手机",new BigDecimal("7990"),
                new BigDecimal("7552"),new Date(),100);

        ProductInfo productInfo2 = new ProductInfo("3","苹果手机-6",new BigDecimal("10999"),
                new BigDecimal("9999"),new Date(),500);

        ProductInfo productInfo3 = new ProductInfo("4","苹果手机-x",new BigDecimal("5532"),
                new BigDecimal("564"),new Date(),300);

        ProductInfo productInfo4 = new ProductInfo("5","华为手机-nove",new BigDecimal("5532"),
                new BigDecimal("764"),new Date(),219);

        ObjectMapper objectMapper = new ObjectMapper();


        List<ProductInfo> l = new ArrayList<ProductInfo>();
        l.add(productInfo);
        l.add(productInfo1);
        l.add(productInfo2);
        l.add(productInfo3);
        l.add(productInfo4);
        IndexRequestBuilder indexRequestBuilder = client.prepareIndex(index, type);
        for(ProductInfo p :l){
            IndexResponse response = indexRequestBuilder.setId(p.getId())
                    .setSource(objectMapper.writeValueAsString(p),XContentType.JSON)
                    .get();

            String _index = response.getIndex();
            // Type name
            String _type = response.getType();
            // Document ID (generated or not)
            String _id = response.getId();
            // Version (if it's the first time you index this document, you will get: 1)
            long _version = response.getVersion();
            // status has stored current instance statement.
            RestStatus status = response.status();

            System.out.println("_index:"+_index);
            System.out.println("_type:"+_type);
            System.out.println("_id:"+_id);
            System.out.println("_version:"+_version);
            System.out.println("status:"+status);
        }

        /*
        IndexResponse response = client.prepareIndex("twitter", "tweet", "1")
                .setSource(jsonBuilder()
                        .startObject()
                        .field("user", "kimchy")
                        .field("postDate", new Date())
                        .field("message", "trying out Elasticsearch")
                        .endObject()
                )
                .get();
        */

        // Index name
    }

    static void update(TransportClient client){

        String json = "{" +
                "\"user\":\"kimchy1234567\"," +
                "\"postDate\":\"2018-01-22\"," +
                "\"message\":\"这是更新后的内容\"" +
                "}";
        client.prepareUpdate().setIndex("twitter").setType("tweet").setId("1")
                .setDoc(json,XContentType.JSON)
                .get();

    }

    static void delete(TransportClient client){
        DeleteResponse deleteResponse = client.prepareDelete("product","phone","5")
                .get();


        System.out.println("deleteResponse:"+deleteResponse);
    }
}
