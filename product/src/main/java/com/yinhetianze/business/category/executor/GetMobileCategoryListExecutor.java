package com.yinhetianze.business.category.executor;

import com.yinhetianze.business.category.service.CategoryInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class GetMobileCategoryListExecutor extends AbstractRestBusiExecutor {
    @Autowired
    private CategoryInfoService categoryInfoServiceImpl;

    @Override
    protected List<Map<String,Object>> executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException
    {
        //查询所有分类
        List<Map<String,Object>> list = categoryInfoServiceImpl.selectMap();
        //一级分类
        List<Map<String,Object>> typelist1 = new ArrayList<Map<String,Object>>();
        //二级分类
        List<Map<String,Object>> typelist2 = new ArrayList<Map<String,Object>>();
        //三级分类
        List<Map<String,Object>> typelist3 = new ArrayList<Map<String,Object>>();
        if(CommonUtil.isNotNull(list)){
            for (int i = 0; i < list.size(); i++) {
                String level = list.get(i).get("cateLevel").toString();
                if("1".equals(level)){
                    typelist1.add(list.get(i));
                }else if("2".equals(level)){
                    typelist2.add(list.get(i));
                }else if("3".equals(level)){
                    typelist3.add(list.get(i));
                }
            }
            for (int i = 0; i < typelist1.size(); i++) {
                List<Map<String,Object>> tempList = new ArrayList<Map<String,Object>>();
                for (int j = 0; j < typelist2.size(); j++) {
                    if(typelist1.get(i).get("id").toString().equals(typelist2.get(j).get("parentId").toString())){
                        tempList.add(typelist2.get(j));
                    }
                    List<Map<String,Object>> tempList2 = new ArrayList<Map<String,Object>>();
                    for(int k = 0; k < typelist3.size(); k++){
                        if(typelist2.get(j).get("id").toString().equals(typelist3.get(k).get("parentId").toString())){
                            tempList2.add(typelist3.get(k));
                        }
                    }
                    typelist2.get(j).put("children", tempList2);
                }
                typelist1.get(i).put("children", tempList);
            }
        }
        return typelist1;
    }
}
