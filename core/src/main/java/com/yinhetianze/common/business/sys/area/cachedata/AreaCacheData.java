package com.yinhetianze.common.business.sys.area.cachedata;

import com.yinhetianze.cachedata.area.AreaCacheDataModel;
import com.yinhetianze.cachedata.area.AreaInfo;
import com.yinhetianze.common.business.sys.area.service.AreaInfoService;
import com.yinhetianze.common.fileupload.alioss.OSSFileUpload;
import com.yinhetianze.core.business.httpresponse.ResponseData;
import com.yinhetianze.core.business.httpresponse.Result;
import com.yinhetianze.core.cachedata.RedisCacheData;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import com.yinhetianze.pojo.task.AreaPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.util.*;

/**
 * 将地区信息缓存起来
 * 格式为：List<AreaInfo>
 *     [
 *          {code(11000), name(北京)，parentId(0)，type(1)，children[
 *              {code(1100100),name(北京市)，parentId(11000),type(2),children[
 *                  code(11001001),name(东城区),parentId(1100100),type(3),children[]
 *              ]}
 *               ......
 *          ]}
 *          ......
 *     ]
 */
@Service
public class AreaCacheData extends RedisCacheData<AreaCacheDataModel>
{
    @Autowired
    private AreaInfoService areaInfoServiceImpl;

    @Autowired
    private OSSFileUpload ossFileUpload;

    @Override
    public AreaCacheDataModel cacheData() throws Exception
    {
        // 获取所有数据库的地区信息
        List<AreaPojo> areaData = areaInfoServiceImpl.getAreaList(new AreaPojo());
        if (CommonUtil.isNotEmpty(areaData))
        {
            AreaCacheDataModel cacheDataModel = new AreaCacheDataModel();
            Map<String, AreaInfo> provinceDataMap = new HashMap<>(); // 省份集
            Map<String, AreaInfo> cityDataMap = new HashMap<>(); // 城市集
            Map<String, AreaInfo> countyDataMap = new HashMap<>(); // 区县集

            AreaInfo ai = null;
            Map<String, AreaInfo> areaInfoMap = new HashMap<>(); // 所有地区集合
            // 将所有地级市信息分类
            for (AreaPojo area : areaData)
            {
                // 按照地级市分级存储数据
                ai = group(area, provinceDataMap, cityDataMap, countyDataMap);

                // 保存到整体列表中，不包含子类地区
                collection(ai, areaInfoMap);
            }

            // 将省份与城市对应关系设置
            setRelation(provinceDataMap, cityDataMap);

            // 将城市与区县地区关系设置
            setRelation(cityDataMap, countyDataMap);

            // 将省份，城市，地县保存到cacheDataModel中
            cacheDataModel.setProvinceMap(provinceDataMap);
            cacheDataModel.setCityMap(cityDataMap);
            cacheDataModel.setCountyMap(countyDataMap);
            cacheDataModel.setProvinceList(copyAreaFromMap(provinceDataMap));
            cacheDataModel.setCityList(copyAreaFromMap(cityDataMap));
            cacheDataModel.setCountyList(copyAreaFromMap(countyDataMap));

            cacheDataModel.setAreaInfoMap(areaInfoMap); // 所有地区单个信息集合，不包含子类地区


            //将 省市区 层级关系数据 上传oss
            if (CommonUtil.isNotEmpty(provinceDataMap))
            {

                //根据 code 排序
                List<String> sortList = new ArrayList();
                Iterator<String> it = provinceDataMap.keySet().iterator();
                while(it.hasNext()){
                    String code = it.next();
                    sortList.add(code);
                }

                Collections.sort(sortList, new Comparator<String>() {
                    @Override
                    public int compare(String o1, String o2) {

                        BigDecimal oo1 = new BigDecimal(o1);
                        BigDecimal oo2 = new BigDecimal(o2);
                        return -1*(oo1.compareTo(oo2));
                    }
                });

                Map<String, AreaInfo> sortDataMap = new TreeMap<>();
                for(String code : sortList){
                    AreaInfo areaInfo = provinceDataMap.get(code);
                    sortDataMap.put(code,areaInfo);
                }

                ResponseData responseData = new ResponseData();
                responseData.setData(sortDataMap.values());
                responseData.setResultInfo(new Result());
                // 将缓存数据推送到oss
                try
                {
                    ByteArrayInputStream bais = new ByteArrayInputStream(CommonUtil.objectToJsonString(responseData).getBytes("utf-8"));
                    Map<String,Object> metaMap = new HashMap<>();
                    metaMap.put("contentType","text/plain");
                    ossFileUpload.fileUpload(bais, "json", "mobile_area_data.json",metaMap);
                }
                catch (Exception e)
                {
                    LoggerUtil.error(AreaCacheData.class, e);
                }
            }
            // 将省份信息返回
            return cacheDataModel;
        }

        // 默认返回空集
        return null;
    }

    private void collection(AreaInfo area, Map<String, AreaInfo> areaInfoMap) throws Exception
    {
        areaInfoMap.put(area.getCode(), area.clone());
    }

    /**
     * 从map格式的地区信息中单独剥离地区
     * @param areaInfoMap
     * @return
     * @throws Exception
     */
    private List<AreaInfo> copyAreaFromMap(Map<String, AreaInfo> areaInfoMap) throws Exception
    {
        List<AreaInfo> areaInfoList = new ArrayList<>();

        if (CommonUtil.isNotEmpty(areaInfoMap))
        {
            Collection<AreaInfo> areas = areaInfoMap.values();
            for (AreaInfo ai : areas)
            {
                areaInfoList.add(ai.clone());
            }
        }

        return areaInfoList;
    }

    /**
     * 将所有地区信息分类整理到省份，城市，区县集合中
     * @param area
     * @param provinceDataMap
     * @param cityDataMap
     * @param countyDataMap
     */
    private AreaInfo group(AreaPojo area, Map<String, AreaInfo> provinceDataMap, Map<String, AreaInfo> cityDataMap, Map<String, AreaInfo> countyDataMap)
    {
        AreaInfo info = createArea(area);
        if (area.getLevelType() == 1)
        {
            // 省份
            provinceDataMap.put(area.getCode(), info);
        }
        else if (area.getLevelType() == 2)
        {
            // 城市
            cityDataMap.put(area.getCode(), info);
        }
        else if (area.getLevelType() == 3)
        {
            // 区县
            countyDataMap.put(area.getCode(), info);
        }
        else
        {
            LoggerUtil.info(AreaCacheData.class, "当前地区信息不属于省市区的划分范围：名称={}，编码={}", new Object[]{area.getName(), area.getCode()});
        }
        return info;
    }

    /**
     * 将上下级关系分类整理并关联
     * @param parents
     * @param childrens
     */
    private void setRelation(Map<String, AreaInfo> parents, Map<String, AreaInfo> childrens)
    {
        // 当父子两级数据都不为空时操作
        if (CommonUtil.isNotEmpty(parents) && CommonUtil.isNotEmpty(childrens))
        {
            // 子集编码集合
            Set<String> childCodes = childrens.keySet();
            AreaInfo ai = null; // 临时地区变量
            // 循环子类
            for (String cc : childCodes)
            {
                // 父类地区
                ai = parents.get(childrens.get(cc).getParentId());
                // 父类地区不为空时，将子类与父类关联起来
                if (CommonUtil.isNotEmpty(ai))
                {
                    // 如果是第一个数据操作，没有子类集合，需要新建子类集合
                    if (CommonUtil.isEmpty(ai.getChildList()))
                    {
                        ai.setChildList(new HashMap<>());
                    }
                    ai.getChildList().put(cc, childrens.get(cc));
                }
                else
                {
                    LoggerUtil.info(AreaCacheData.class, "当前地区信息不属于省市区的划分范围：名称={}，编码={}", new Object[]{childrens.get(cc).getName(), childrens.get(cc).getCode()});
                }
            }

        }
    }

    /**
     * 将AreaPojo类型的area信息转换成AreaInfo类型
     * @param area
     * @return
     */
    private AreaInfo createArea(AreaPojo area)
    {
        AreaInfo areaInfo = new AreaInfo();
        try
        {
            areaInfo.setCode(area.getCode());
            areaInfo.setName(area.getName());
            areaInfo.setParentId(area.getParentId());
            areaInfo.setType(Integer.valueOf(area.getLevelType()));
        }
        catch (Exception e)
        {
            LoggerUtil.error(AreaCacheData.class, e);
        }

        return areaInfo;
    }

}
