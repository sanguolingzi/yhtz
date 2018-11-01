DROP TABLE busi_listener_server;

CREATE TABLE busi_listener_server(
    id INT AUTO_INCREMENT PRIMARY KEY,
    server_name VARCHAR(50) NOT NULL COMMENT '服务名称',
    server_ip VARCHAR(20) NOT NULL COMMENT '服务器ip地址',
    server_status SMALLINT(2) UNSIGNED ZEROFILL COMMENT '服务状态，0=等待，1=运行，2=停止',
    server_port INT(5) DEFAULT 80 COMMENT '服务端口',
    regist_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '第一次添加到队列的时间，仅一次',
    last_shutdown_time DATETIME COMMENT '最近一次停服时间',
    last_startup_time DATETIME COMMENT '最近一次启动时间',
    UNIQUE KEY `server_name` (`server_name`,`server_ip`,`server_port`)
) ENGINE = INNODB DEFAULT CHARSET utf8 COMMENT '服务地址列表';