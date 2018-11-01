package com.yinhetianze.pojo.listener;

import java.util.Date;
import javax.persistence.*;

@Table(name = "busi_listener_server")
public class ListenerServerPojo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 服务名称
     */
    @Column(name = "server_name")
    private String serverName;

    /**
     * 服务器ip地址
     */
    @Column(name = "server_ip")
    private String serverIp;

    /**
     * 服务端口
     */
    @Column(name = "server_port")
    private Integer port;

    /**
     * 服务状态
     */
    @Column(name = "server_status")
    private Short serverStatus;

    /**
     * 第一次添加到队列的时间，仅一次
     */
    @Column(name = "regist_time")
    private Date registTime;

    /**
     * 最近一次停服时间
     */
    @Column(name = "last_shutdown_time")
    private Date lastShutdownTime;

    /**
     * 最近一次启动时间
     */
    @Column(name = "last_startup_time")
    private Date lastStartupTime;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取服务名称
     *
     * @return server_name - 服务名称
     */
    public String getServerName() {
        return serverName;
    }

    /**
     * 设置服务名称
     *
     * @param serverName 服务名称
     */
    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    /**
     * 获取服务器ip地址
     *
     * @return server_ip - 服务器ip地址
     */
    public String getServerIp() {
        return serverIp;
    }

    /**
     * 设置服务器ip地址
     *
     * @param serverIp 服务器ip地址
     */
    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

    /**
     * 获取服务状态
     *
     * @return server_status - 服务状态
     */
    public Short getServerStatus() {
        return serverStatus;
    }

    /**
     * 设置服务状态
     *
     * @param serverStatus 服务状态
     */
    public void setServerStatus(Short serverStatus) {
        this.serverStatus = serverStatus;
    }

    /**
     * 获取第一次添加到队列的时间，仅一次
     *
     * @return regist_time - 第一次添加到队列的时间，仅一次
     */
    public Date getRegistTime() {
        return registTime;
    }

    /**
     * 设置第一次添加到队列的时间，仅一次
     *
     * @param registTime 第一次添加到队列的时间，仅一次
     */
    public void setRegistTime(Date registTime) {
        this.registTime = registTime;
    }

    /**
     * 获取最近一次停服时间
     *
     * @return last_shutdown_time - 最近一次停服时间
     */
    public Date getLastShutdownTime() {
        return lastShutdownTime;
    }

    /**
     * 设置最近一次停服时间
     *
     * @param lastShutdownTime 最近一次停服时间
     */
    public void setLastShutdownTime(Date lastShutdownTime) {
        this.lastShutdownTime = lastShutdownTime;
    }

    /**
     * 获取最近一次启动时间
     *
     * @return last_startup_time - 最近一次启动时间
     */
    public Date getLastStartupTime() {
        return lastStartupTime;
    }

    /**
     * 设置最近一次启动时间
     *
     * @param lastStartupTime 最近一次启动时间
     */
    public void setLastStartupTime(Date lastStartupTime) {
        this.lastStartupTime = lastStartupTime;
    }

    public Integer getPort()
    {
        return port;
    }

    public void setPort(Integer port)
    {
        this.port = port;
    }
}