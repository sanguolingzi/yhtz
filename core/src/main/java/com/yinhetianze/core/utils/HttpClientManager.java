package com.yinhetianze.core.utils;

import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * httpclient客户端管理工具类
 *
 * MAX_CONNECTION_NUM：最大连接数，默认500
 * MAX_PER_ROUTE：单个路由最大连接数，默认20
 * IDLE_TIMEOUT：闲置连接关闭时间，默认300秒，单位毫秒
 * REQUEST_TIMEOUT：服务器请求超时时间，默认60秒，单位毫秒
 * RESPONSE_TIMEOUT：服务器响应超时时间，默认60秒，单位毫秒
 *
 */
@Service
public class HttpClientManager
{
    /**
     * 等待服务器响应的超时时间，默认1分钟，单位毫秒
     */
    @Value("${REQUEST_TIMEOUT:60000}")
    private Integer requestTimeout;

    /**
     * 请求连接服务器的超时时间，默认1分钟，单位毫秒
     */
    @Value("${RESPONSE_TIMEOUT:60000}")
    private Integer responseTimeout;

    /**
     * 连接池最大连接数，默认500个
     */
    @Value("${MAX_CONNECTION_NUM:500}")
    private Integer maxConnectionNum;

    /**
     * 每个路由连接的最大连接数，默认20个
     */
    @Value("${MAX_PER_ROUTE:20}")
    private Integer maxPerRouteNum;

    /**
     * 闲置连接，默认5分钟后失效，单位毫秒
     */
    @Value("${IDLE_TIMEOUT:300000}")
    private Integer idleTimeout;

    private static PoolingHttpClientConnectionManager manager;

    private static Object lock = new Object();

    public HttpClientManager()
    {
    }

    /**
     * 内部获取httpclient连接池管理器
     * @return
     */
    private PoolingHttpClientConnectionManager getManager()
    {
        // 判断是否为空，为空则进行构建
        if (CommonUtil.isEmpty(manager))
        {
            // 同步锁定操作
            synchronized (lock)
            {
                // 保证第一次操作时同步进程已经排队lock，不再创建第二个manager
                if (CommonUtil.isEmpty(manager))
                {
                    manager = new PoolingHttpClientConnectionManager();

                    SSLContextBuilder sslContextBuilder = new SSLContextBuilder();
                    try
                    {
                        sslContextBuilder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
                        SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(
                                sslContextBuilder.build());
                        // 支持http和https请求协议
                        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                                .register("https", socketFactory)
                                .register("http", new PlainConnectionSocketFactory())
                                .build();

                        // 连接池属性设置，最大连接数，单个路由最大连接数，闲置连接关闭时间等
                        manager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
                        if (CommonUtil.isNotEmpty(maxConnectionNum))
                        {
                            manager.setMaxTotal(maxConnectionNum);
                        }
                        if (CommonUtil.isNotEmpty(maxPerRouteNum))
                        {
                            manager.setDefaultMaxPerRoute(maxPerRouteNum);
                        }
                        if (CommonUtil.isNotEmpty(idleTimeout))
                        {
                            manager.closeIdleConnections(idleTimeout, TimeUnit.MILLISECONDS);
                        }
                    }
                    catch (Exception e)
                    {
                        LoggerUtil.error(HttpClientManager.class, "Init PoolingHttpClientConnectionManager Error : ", new Object[]{e});
                        manager = null;
                    }
                }
            }
        }

        return manager;
    }

    /**
     * 获取httpclient请求配置
     * @param proxy
     * @return
     */
    public RequestConfig getConfig(HttpHost proxy)
    {
        // 请求超时时间和响应超时时间
        RequestConfig.Builder builder = RequestConfig.custom();
        if (CommonUtil.isNotEmpty(requestTimeout))
        {
            builder = builder.setConnectTimeout(requestTimeout);
        }
        if (CommonUtil.isNotEmpty(responseTimeout))
        {
            builder = builder.setSocketTimeout(responseTimeout);
        }

        // 是否设置代理
        if (CommonUtil.isNotEmpty(proxy))
        {
            builder = builder.setProxy(proxy);
        }

        return builder.build();
    }

    /**
     * 获取httpclient对象
     * @return
     */
    public CloseableHttpClient getHttpClient()
    {
        return getHttpClient(null);
    }

    /**
     * 获取http对象，使用指定的主机对象
     * @param proxy 主机对象
     * @return
     */
    public CloseableHttpClient getHttpClient(HttpHost proxy)
    {
        CloseableHttpClient client = null;

        // 连接池如果不为空，则使用连接池，否则使用默认httpclient连接
        if (CommonUtil.isNotEmpty(getManager()))
        {
            HttpClientBuilder builder = HttpClients.custom().setConnectionManager(getManager());

            RequestConfig config = getConfig(proxy);
            if (CommonUtil.isNotEmpty(config))
            {
                builder = builder.setDefaultRequestConfig(config);
            }

            client = builder.build();
        }
        else
        {
            client = HttpClients.createDefault();
        }

        return client;
    }

}
