/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50122
Source Host           : localhost:3306
Source Database       : itv

Target Server Type    : MYSQL
Target Server Version : 50122
File Encoding         : 65001

Date: 2013-06-27 10:30:36
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `movie`
-- ----------------------------
DROP TABLE IF EXISTS `movie`;
CREATE TABLE `movie` (
  `id` varchar(16) NOT NULL,
  `name` varchar(32) DEFAULT NULL,
  `director` varchar(20) DEFAULT NULL,
  `actor` varchar(127) DEFAULT NULL,
  `area` varchar(16) DEFAULT NULL,
  `supplierUrl` varchar(40) DEFAULT NULL,
  `year` varchar(10) DEFAULT NULL,
  `duration` varchar(16) DEFAULT NULL,
  `value` float(4,0) DEFAULT NULL,
  `less` varchar(1024) DEFAULT NULL,
  `ratingCount` int(4) DEFAULT NULL,
  `suppliesCount` int(4) DEFAULT NULL,
  `supplies` varchar(1024) DEFAULT NULL,
  `imgUrl` varchar(125) DEFAULT NULL,
  `language` varchar(4) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `name` (`name`) USING BTREE,
  KEY `daoyan` (`director`) USING BTREE,
  KEY `zhuyan` (`actor`) USING BTREE,
  KEY `supplierUrl` (`supplierUrl`) USING HASH
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of movie
-- ----------------------------

-- ----------------------------
-- Table structure for `movie_focus_map`
-- ----------------------------
DROP TABLE IF EXISTS `movie_focus_map`;
CREATE TABLE `movie_focus_map` (
  `id` varchar(16) NOT NULL DEFAULT '',
  `name` varchar(32) DEFAULT NULL,
  `supplierUrl` varchar(60) DEFAULT NULL,
  `bigImgUrl` varchar(125) DEFAULT NULL,
  `miniImgUrl` varchar(125) DEFAULT NULL,
  `text` varchar(64) DEFAULT NULL,
  `type` int(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of movie_focus_map
-- ----------------------------
