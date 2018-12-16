/*
Navicat MySQL Data Transfer

Source Server         : mysql-VPS服
Source Server Version : 50721
Source Host           : 95.163.207.33:3306
Source Database       : votedb

Target Server Type    : MYSQL
Target Server Version : 50721
File Encoding         : 65001

Date: 2018-12-16 18:43:02
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `user_id` varchar(32) NOT NULL COMMENT '用户id',
  `user_name` varchar(20) NOT NULL COMMENT '用户名',
  `phone` varchar(11) DEFAULT '' COMMENT '手机号',
  `password` varchar(50) NOT NULL DEFAULT '123456' COMMENT '密码',
  `avatar` varchar(255) DEFAULT NULL COMMENT '头像',
  `gender` int(2) DEFAULT NULL COMMENT '性别,0未知，1男，2女，3lesbian,4gay',
  `create_time` timestamp NULL DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `school` varchar(100) DEFAULT NULL COMMENT '学校',
  `type` int(2) DEFAULT NULL COMMENT '0普通用户 1管理员',
  PRIMARY KEY (`user_id`),
  KEY `id_index` (`user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
