-- 以下脚本于 2018-10-20 10:06执行 begin
CREATE TABLE `game_register_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `p_game_id` int(11) DEFAULT NULL COMMENT '推荐人gameId',
  `game_id` int(11) DEFAULT NULL COMMENT '被推荐人gameId',
  `status` tinyint(2) DEFAULT NULL COMMENT '是否成功 (0不成功 1成功)',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
-- 以下脚本于 2018-10-20 10:06执行 end


