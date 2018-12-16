/*
Navicat MySQL Data Transfer

Source Server         : mysql-VPS服
Source Server Version : 50721
Source Host           : 95.163.207.33:3306
Source Database       : votedb

Target Server Type    : MYSQL
Target Server Version : 50721
File Encoding         : 65001

Date: 2018-12-16 18:43:23
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for vote
-- ----------------------------
DROP TABLE IF EXISTS `vote`;
CREATE TABLE `vote` (
  `subject` text NOT NULL COMMENT '题目',
  `type` int(2) NOT NULL COMMENT '0单选题 1多选',
  `subject_id` varchar(64) NOT NULL COMMENT '题目id',
  `create_date` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建日期',
  `expiry_date` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '失效日期',
  `update_date` timestamp NULL DEFAULT NULL,
  `sum_user` int(64) unsigned zerofill DEFAULT NULL COMMENT '统计人数',
  `sum_vote` int(64) unsigned zerofill DEFAULT NULL COMMENT '统计票数'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
