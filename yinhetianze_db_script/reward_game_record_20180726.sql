

CREATE TABLE `reward_game_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `game_id` int(11) DEFAULT NULL COMMENT '游戏id',
  `customer_id` int(11) DEFAULT NULL COMMENT '用户id',
  `game_amount` decimal(10,0) DEFAULT NULL COMMENT '奖励游戏币',
  `bankroll_serial` int(11) DEFAULT NULL COMMENT '奖励币流水记录id',
  `game_task_id` int(11) DEFAULT NULL COMMENT '游戏任务id',
  `game_type_id` int(11) DEFAULT NULL COMMENT '游戏类型id',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
