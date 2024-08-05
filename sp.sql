/*用户基本信息 */
DROP TABLE IF EXISTS `sp_car_user`;

CREATE TABLE `sp_car_user` (
  `user_id` bigint NOT NULL COMMENT 'id',
  `mobile` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '手机号',
  `nickname` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '用户昵称',
  `user_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户编号',
  `head_image` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '用户头像',
  `password` varchar(225) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '密码',
  `pay_password` varchar(225) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '交易密码',
  `user_sex` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '性别 0未知  1男   2女',
  `cert_id` bigint unsigned NOT NULL DEFAULT '0' COMMENT '实名认证 ID',
  `level_id` int unsigned NOT NULL DEFAULT '0' COMMENT '会员级别 ID',
  `balance` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '余额',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '备注',
  `status` tinyint unsigned NOT NULL DEFAULT '1' COMMENT '状态 1正常 2禁止登陆',
  `food` decimal(20,7) DEFAULT '0.0000000' COMMENT '代币',
  `is_mine` tinyint(1) DEFAULT '0' COMMENT ' 0 未赠送初始矿场          1已赠送初始矿场',
  `wechat` varchar(20) DEFAULT NULL COMMENT '微信',
  `qq` varchar(20) DEFAULT NULL COMMENT 'qq',
  `register_ip` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '注册ip',
  `register_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
  `source` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '注册来源',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` int DEFAULT '0' COMMENT '逻辑删除',
  PRIMARY KEY (`user_id`) USING BTREE,
  KEY `mobile` (`mobile`) USING BTREE,
  KEY `cert_id` (`cert_id`) USING BTREE,
  KEY `level_id` (`level_id`) USING BTREE,
  KEY `status` (`status`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=COMPACT COMMENT='用户表';



/*物流公司 */
DROP TABLE IF EXISTS `sp_delivery`;

CREATE TABLE `sp_delivery` (
  `dvy_id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `dvy_name` varchar(50) NOT NULL DEFAULT '' COMMENT '物流公司名称',
  `company_home_url` varchar(255) DEFAULT NULL COMMENT '公司主页',
  `query_url` varchar(520) NOT NULL COMMENT '物流查询接口',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` int DEFAULT '0' COMMENT '逻辑删除',
  PRIMARY KEY (`dvy_id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb3 COMMENT='物流公司';


insert  into `sp_delivery`(`dvy_id`,`dvy_name`,`company_home_url`,`query_url`) values
  (14,'顺丰快递公司','http://www.sf-express.com','http://www.kuaidi100.com/query?type=shunfeng&postid={dvyFlowId}&id=11'),
  (15,'申通快递公司','http://www.sto-express.com','http://www.kuaidi100.com/query?type=shentong&postid={dvyFlowId}&id=11'),
  (16,'中通速递','http://www.zto.cn','http://www.kuaidi100.com/query?type=zhongtong&postid={dvyFlowId}&id=11'),
  (17,'安信达','http://www.anxinda.com','http://www.kuaidi100.com/query?type=anxindakuaixi&postid={dvyFlowId}&id=11'),
  (18,'EMS','http://www.ems.com.cn','http://www.kuaidi100.com/query?type=ems&postid={dvyFlowId}&id=11'),
  (19,'凡客如风达','http://www.rufengda.com','http://www.kuaidi100.com/query?type=rufengda&postid={dvyFlowId}&id=11'),
  (20,'汇通快递','http://www.htky365.com','http://www.kuaidi100.com/query?type=huitongkuaidi&postid={dvyFlowId}&id=11'),
  (21,'天天快递','http://www.ttkdex.com','http://www.kuaidi100.com/query?type=tiantian&postid={dvyFlowId}&id=11'),
  (22,'佳吉快运','http://www.jiaji.com','http://www.kuaidi100.com/query?type=jiajiwuliu&postid={dvyFlowId}&id=11'),
  (23,'速尔快递','http://www.sure56.com','http://www.kuaidi100.com/query?type=suer&postid={dvyFlowId}&id=11'),
  (24,'信丰物流','http://www.xf-express.com.cn','http://www.kuaidi100.com/query?type=xinfengwuliu&postid={dvyFlowId}&id=11'),
  (25,'韵达快递','http://www.yundaex.com','http://www.kuaidi100.com/query?type=yunda&postid={dvyFlowId}&id=11'),
  (26,'优速快递','http://www.uc56.com','http://www.kuaidi100.com/query?type=youshuwuliu&postid={dvyFlowId}&id=11'),
  (27,'中邮物流','http://www.cnpl.com.cn','http://www.kuaidi100.com/query?type=zhongyouwuliu&postid={dvyFlowId}&id=11'),
  (28,'圆通快递','http://www.yto.net.cn','http://www.kuaidi100.com/query?type=yuantong&postid={dvyFlowId}&id=11'),
  (29,'宅急送','http://www.zjs.com.cn','http://www.kuaidi100.com/query?type=zhaijisong&postid={dvyFlowId}&id=11');


/*物流公司 */
DROP TABLE IF EXISTS `sp_user_addr`;

CREATE TABLE `sp_user_addr` (
  `addr_id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` bigint NOT NULL DEFAULT '0' COMMENT '用户ID',
  `receiver` varchar(50) DEFAULT NULL COMMENT '收货人',
  `province_id` bigint DEFAULT NULL COMMENT '省ID',
  `province` varchar(100) DEFAULT NULL COMMENT '省',
  `city` varchar(20) DEFAULT NULL COMMENT '城市',
  `city_id` bigint DEFAULT NULL COMMENT '城市ID',
  `area` varchar(20) DEFAULT NULL COMMENT '区',
  `area_id` bigint DEFAULT NULL COMMENT '区ID',
  `post_code` varchar(15) DEFAULT NULL COMMENT '邮编',
  `addr` varchar(1000) DEFAULT NULL COMMENT '地址',
  `mobile` varchar(20) DEFAULT NULL COMMENT '手机',
  `status` int NOT NULL COMMENT '状态,1正常，0无效',
  `common_addr` int NOT NULL DEFAULT '0' COMMENT '是否默认地址 1是',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` int DEFAULT '0' COMMENT '逻辑删除',
  PRIMARY KEY (`addr_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3 COMMENT='用户地址';




DROP TABLE IF EXISTS `sp_invitation_relations`;

CREATE TABLE `sp_invitation_relations` (
 `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
 `invited_user_id` bigint DEFAULT NULL COMMENT '被邀请用户的用户ID',
 `inviter_user_id` bigint DEFAULT NULL COMMENT '直接邀请者的用户ID',
 `secondary_inviter_user_id` int DEFAULT NULL COMMENT '间接邀请者的用户ID，即邀请者的上级邀请者',
 `level` int DEFAULT NULL COMMENT '邀请层级，1表示一级邀请，2表示二级邀请',
 `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
 PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;