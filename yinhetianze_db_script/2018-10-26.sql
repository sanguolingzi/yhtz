--图片存储路径
 INSERT INTO busi_sys_sysproperties(p_name,p_value,is_work,p_module,p_description)
VALUES('advertisementImgPath','/advertisementImgPath',1,3,'广告图片存储路径');

--广告个数表
CREATE TABLE busi_sys_advertisement(
    id INT AUTO_INCREMENT PRIMARY KEY,
    advertisement_name VARCHAR(100) COMMENT '广告名称',
    is_show SMALLINT(2) DEFAULT 1 COMMENT '是否首页显示，0显示，1不显示，默认1不显示',
    sort SMALLINT(6) COMMENT '排序编号',
    del_flag SMALLINT(2) DEFAULT 0 COMMENT '是否删除，1已删除，0未删除，默认0未删除',
    create_user INT(11) COMMENT '创建人',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_user INT(11) COMMENT '更新操作人',
    update_time DATETIME COMMENT '更新时间'
) ENGINE = INNODB DEFAULT CHARSET UTF8 COMMENT '广告表';

--广告详情
CREATE TABLE busi_sys_advertisement_detail(
    id INT AUTO_INCREMENT PRIMARY KEY,
    advertisement_id INT(11) COMMENT '广告ID',
    advertisement_detail_img VARCHAR(200) COMMENT '广告详情图片',
    advertisement_detail_link VARCHAR(100) COMMENT '广告详情链接',
    link_markup SMALLINT(6) DEFAULT '0' COMMENT '链接标记 0=商品详情 1=商品三级分类列表 2=搜索页面 3=商品搜索详情页面 4=公告详情 默认0',
    link_parameter VARCHAR(200) DEFAULT NULL COMMENT '链接参数',
    is_show SMALLINT(2) DEFAULT 1 COMMENT '是否首页显示，0显示，1不显示，默认1不显示',
    sort SMALLINT(6) COMMENT '排序编号',
    del_flag SMALLINT(2) DEFAULT 0 COMMENT '是否删除，1已删除，0未删除，默认0未删除',
    create_user INT(11) COMMENT '创建人',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_user INT(11) COMMENT '更新操作人',
    update_time DATETIME COMMENT '更新时间'
) ENGINE = INNODB DEFAULT CHARSET UTF8 COMMENT '广告详情表';
-- 以上脚本于 2018-10-31 14：06执行 end