/*
Navicat MySQL Data Transfer

Source Server         : mysql-VPS服
Source Server Version : 50721
Source Host           : 95.163.207.33:3306
Source Database       : votedb

Target Server Type    : MYSQL
Target Server Version : 50721
File Encoding         : 65001

Date: 2018-12-16 18:43:36
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for vote_options
-- ----------------------------
DROP TABLE IF EXISTS `vote_options`;
CREATE TABLE `vote_options` (
  `option_str` varchar(255) NOT NULL COMMENT '选项',
  `subject_id` varchar(64) NOT NULL COMMENT '题目id',
  `option_index` varchar(10) DEFAULT NULL,
  `create_date` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建日期',
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `count` int(11) unsigned zerofill DEFAULT NULL,
  `percent` varchar(10) DEFAULT NULL COMMENT '比例',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=105 DEFAULT CHARSET=utf8;
