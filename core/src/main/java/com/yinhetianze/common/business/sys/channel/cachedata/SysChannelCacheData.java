package com.yinhetianze.common.business.sys.channel.cachedata;

import com.yinhetianze.common.business.sys.channel.service.SysChannelInfoService;
import com.yinhetianze.core.cachedata.RedisCacheData;
import com.yinhetianze.core.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 渠道信息的缓存数据，按照
 * channelCode={ChannelInfoObject}的格式保存集合
 */
@Service
public class SysChannelCacheData extends RedisCacheData<HashMap<String, Map<String, Object>>>
{
    @Autowired
    private SysChannelInfoService sysChannelInfoServiceImpl;

    @Override
    public HashMap<String, Map<String, Object>> cacheData() throws Exception
    {
        HashMap<String, Map<String, Object>> cacheData = new HashMap<>();
        List<Map<String, Object>> channelList = sysChannelInfoServiceImpl.getChannelInfoList(new HashMap<>());
        if (CommonUtil.isNotEmpty(channelList))
        {
            for (Map<String, Object> channel : channelList)
            {
                cacheData.put(String.valueOf(channel.get("channelCode")), channel);
            }
        }

        return cacheData;
    }
}
