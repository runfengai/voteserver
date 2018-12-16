/*
Navicat MySQL Data Transfer

Source Server         : mysql-VPS服
Source Server Version : 50721
Source Host           : 95.163.207.33:3306
Source Database       : votedb

Target Server Type    : MYSQL
Target Server Version : 50721
File Encoding         : 65001

Date: 2018-12-16 18:46:50
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for vote_user
-- ----------------------------
DROP TABLE IF EXISTS `vote_user`;
CREATE TABLE `vote_user` (
  `user_id` varchar(64) NOT NULL,
  `subject_id` varchar(64) NOT NULL,
  `option_id` bigint(11) DEFAULT NULL,
  `create_date` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
