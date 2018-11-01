package com.yinhetianze.business;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;

import java.util.List;
import java.util.Map;

public class TestMain {


    public static void main(String[] args)throws Exception{

        //testDelete();
        //testDeleteByQuery();
        //testQuery();
        testConflictQuery();
        //testSave();
    }

    /*
     * 测试向索引库中添加文档
     */
    public static void testSave() throws Exception{

        HttpSolrClient solrServer= new HttpSolrClient.Builder("http://localhost:8080/solr/myTest")
                .withConnectionTimeout(5000)
                .withSocketTimeout(100000)
                .build();

        //1.创建连接对象
        //2.创建一个文档对象
        SolrInputDocument inputDocument = new SolrInputDocument();
        //向文档中添加域以及对应的值,注意：所有的域必须在schema.xml中定义过,前面已经给出过我定义的域。
        //这里不做
        inputDocument.addField("id", "1");
        inputDocument.addField("name", "苹果6s");
        inputDocument.addField("brand", "apple");
        inputDocument.addField("market_price", "6544");
        inputDocument.addField("sell_price", "6232");
        inputDocument.addField("count", "231");
        inputDocument.addField("createTime","20170912");
        solrServer.add(inputDocument);

        inputDocument = new SolrInputDocument();
        inputDocument.addField("id", "2");
        inputDocument.addField("name", "华为荣耀");
        inputDocument.addField("brand", "华为");
        inputDocument.addField("market_price", "3544");
        inputDocument.addField("sell_price", "2991");
        inputDocument.addField("count", "60");
        inputDocument.addField("createTime","20180522");
        solrServer.add(inputDocument);

        inputDocument = new SolrInputDocument();
        inputDocument.addField("id", "3");
        inputDocument.addField("name", "华为Nova");
        inputDocument.addField("brand", "华为");
        inputDocument.addField("market_price", "1544");
        inputDocument.addField("sell_price", "991");
        inputDocument.addField("count", "192");
        inputDocument.addField("createTime","20160111");
        solrServer.add(inputDocument);

        inputDocument = new SolrInputDocument();
        inputDocument.addField("id", "4");
        inputDocument.addField("name", "三星盖乐世galax");
        inputDocument.addField("brand", "三星");
        inputDocument.addField("market_price", "5544");
        inputDocument.addField("sell_price", "4991");
        inputDocument.addField("count", "2");
        inputDocument.addField("createTime","20070912");

        //3.将文档写入索引库中

        UpdateResponse updateResponse =  solrServer.add(inputDocument);
        System.out.println(updateResponse.getElapsedTime());
        //提交
        solrServer.commit();

        solrServer.close();
        //solrServer.rollback();
    }

    public static void testDelete() throws Exception{
        //1.创建连接对象
        HttpSolrClient solrServer= new HttpSolrClient.Builder("http://localhost:8080/solr/myTest")
                .withConnectionTimeout(5000)
                .withSocketTimeout(100000)
                .build();
        //删除文档
        solrServer.deleteById("a");
        //提交
        solrServer.commit();

        solrServer.close();
    }

    public static void testDeleteByQuery() throws Exception{
        //1.创建连接对象
        HttpSolrClient solrServer= new HttpSolrClient.Builder("http://localhost:8080/solr/myTest")
                .withConnectionTimeout(5000)
                .withSocketTimeout(100000)
                .build();
        //删除文档
        solrServer.deleteByQuery("*:*");
        //提交
        solrServer.commit();
        solrServer.close();
    }



    public static void testQuery() throws Exception{

        HttpSolrClient solrServer= new HttpSolrClient.Builder("http://localhost:8080/solr/myTest")
                .withConnectionTimeout(5000)
                .withSocketTimeout(100000)
                .build();

        SolrQuery query = new SolrQuery();
        //3.设置查询条件
        query.set("q", "*:*");

        QueryResponse queryResponse = solrServer.query(query);
        SolrDocumentList documentList = queryResponse.getResults();
        for (SolrDocument solrDocument : documentList) {
            System.out.println(solrDocument.getFieldValue("id"));
            //取各个文档信息
            System.out.println("商品id:"+solrDocument.get("id")+" ");
            System.out.println("商品标题:"+solrDocument.get("name")+" ");
            System.out.println("商品价格:"+solrDocument.get("sell_price")+" ");
            System.out.println("市场价格:"+solrDocument.get("market_price")+" ");
            System.out.println("销量:"+solrDocument.get("count")+" ");
        }
    }

    public static void testConflictQuery() throws Exception{

        HttpSolrClient solrServer= new HttpSolrClient.Builder("http://localhost:8080/solr/myTest")
                .withConnectionTimeout(5000)
                .withSocketTimeout(100000)
                .build();
        SolrQuery query = new SolrQuery();

        //3.设置查询条件
        //query.setQuery("count:[100 TO 300]");
        //query.set("q", "id:*");
        //query.set("df","name");
        //query.setSort("name", SolrQuery.ORDER.asc);

        //设置分页
        query.setStart(0);
        query.setRows(10);

        query.setQuery("(name:*果* OR name:*乐*)AND count:[1 TO 300]");

        //设置返回字段
        //query.setFields("id,name,sell_price");





        //设置高亮
        /*
        query.setQuery("id:* AND count:[1 TO 300]");
        //高亮字段必须出现在query中
        query.addHighlightField("id");
        query.addHighlightField("count");
        query.setHighlight(true);
        query.setHighlightSimplePre("<em>");
        query.setHighlightSimplePost("</em>");
        */


        //设置facet
        //query.setFacet(true);
        //query.setFacetPrefix("brand_facet","三星");
        query.addFacetField("brand_facet");
        query.addFacetField("count");
        query.setFacetLimit(2);

        QueryResponse queryResponse = solrServer.query(query);
        SolrDocumentList documentList = queryResponse.getResults();
        Map<String, Map<String, List<String>>> highlighting =queryResponse.getHighlighting();



        List<FacetField> facetFields = queryResponse.getFacetFields();
        System.out.println("facetFields:"+facetFields);
        if(facetFields == null || facetFields.isEmpty()){
            System.out.println("facetFields is null or empty");
        }else{

            for(FacetField facetField:facetFields){
                System.out.println("getName:"+facetField.getName());
                System.out.println("getValueCount:"+facetField.getValueCount());
                System.out.println("getValues:"+facetField.getValues());

                for(FacetField.Count count:facetField.getValues()){
                    System.out.println("getAsFilterQuery："+count.getAsFilterQuery());
                    System.out.println("getName："+count.getName());
                    System.out.println("getCount："+count.getCount());
                }
            }
        }






        for (SolrDocument solrDocument : documentList) {
            //取各个文档信息
            //System.out.println("solrDocument"+highlighting.get(solrDocument.get("id")));
            System.out.println("商品id:"+solrDocument.getFieldValue("id")+" ");
            System.out.println("商品标题:"+solrDocument.get("name")+" ");
            System.out.println("商品价格:"+solrDocument.get("sell_price")+" ");
            System.out.println("市场价格:"+solrDocument.get("market_price")+" ");
            System.out.println("销量:"+solrDocument.get("count")+" ");
            System.out.println("时间:"+solrDocument.get("createTime")+" ");
        }

        solrServer.close();
    }
}
