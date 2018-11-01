package com.yinhetianze.business.product.executor;


import com.yinhetianze.business.product.model.ProductGuessModel;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.PageData;
import com.yinhetianze.core.cachedata.CacheData;
import com.yinhetianze.core.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * 猜你喜欢
 */
//TODO 业务未拆分
@Service
public class GuessProductListExecutor extends AbstractRestBusiExecutor<Object>
{
    @Autowired
    private CacheData<List<Map>> guessCacheData;


    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException
    {
        List<Map> list=guessCacheData.getCacheData();
        ProductGuessModel productGuessModel=(ProductGuessModel)model;
        PageData pageData = new PageData();
        if(CommonUtil.isNotEmpty(productGuessModel.getIsAll())){
            if(list.size()<productGuessModel.getIsAll()) {
                pageData.setListData(list);
                pageData.setTotalRecord(Long.parseLong(productGuessModel.getIsAll()+""));
                return pageData;
            }
            Random random=new Random();
            List<Integer> tempList=new ArrayList<Integer>();
            List<Map> newList=new ArrayList<Map>();
            int temp=0;
            for(int i=0;i<productGuessModel.getIsAll();i++){
                temp=random.nextInt(list.size());//将产生的随机数作为被抽list的索引
                if(!tempList.contains(temp)){
                    tempList.add(temp);
                    newList.add(list.get(temp));
                }
                else{
                    i--;
                }
            }
            pageData.setListData(newList);
            pageData.setTotalRecord(Long.parseLong((list!=null?list.size():0)+""));
            return pageData;
        }else{
            if(productGuessModel.getCurrentPage()!=0 ||  productGuessModel.getPageSize()!=0){
                int currentPage=productGuessModel.getCurrentPage();
                currentPage=currentPage<=0?1:currentPage;
                int pageSize=productGuessModel.getPageSize();
                int listSize = (list!=null)?list.size():0;
                if(listSize == 0){
                    return pageData;
                }
                int startIndex = (currentPage-1)*pageSize;
                if(startIndex > listSize){
                    return pageData;
                }
                int endIndex = currentPage*pageSize;
                if(listSize>endIndex){
                    list=list.subList(startIndex,endIndex);
                }else{
                    list=list.subList(startIndex,listSize);
                }
            }
            pageData.setListData(list);
            pageData.setTotalRecord(Long.parseLong((guessCacheData.getCacheData()!=null?guessCacheData.getCacheData().size():0)+""));
            return pageData;
        }
    }
}
