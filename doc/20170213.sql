/*
MySQL Backup
Source Server Version: 5.6.24
Source Database: com.zwc
Date: 2017/2/13 17:45:18
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
--  Table structure for `tb_job`
-- ----------------------------
DROP TABLE IF EXISTS `tb_job`;
CREATE TABLE `tb_job` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `jobName` varchar(255) DEFAULT NULL,
  `corn` varchar(255) DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `tb_member`
-- ----------------------------
DROP TABLE IF EXISTS `tb_member`;
CREATE TABLE `tb_member` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `phone` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `displayname` varchar(255) DEFAULT NULL,
  `img_url` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `sex` int(10) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `status` int(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `tb_members`
-- ----------------------------
DROP TABLE IF EXISTS `tb_members`;
CREATE TABLE `tb_members` (
  `Id` bigint(20) NOT NULL AUTO_INCREMENT,
  `Phone` varchar(255) NOT NULL,
  `UserName` varchar(255) NOT NULL,
  `Imgurl` varchar(1000) DEFAULT NULL,
  `PassWord` varchar(255) NOT NULL,
  `Address` varchar(500) DEFAULT NULL,
  `Role` int(11) DEFAULT NULL,
  `CreateTime` datetime NOT NULL,
  `UpdateTime` datetime NOT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `tb_member_role`
-- ----------------------------
DROP TABLE IF EXISTS `tb_member_role`;
CREATE TABLE `tb_member_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `memberid` bigint(20) DEFAULT NULL,
  `roleids` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `tb_perm`
-- ----------------------------
DROP TABLE IF EXISTS `tb_perm`;
CREATE TABLE `tb_perm` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parentid` bigint(20) DEFAULT NULL,
  `permtype` int(10) DEFAULT NULL,
  `displayname` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `tb_role`
-- ----------------------------
DROP TABLE IF EXISTS `tb_role`;
CREATE TABLE `tb_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `rolename` varchar(255) DEFAULT NULL,
  `displayname` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `tb_role_perm`
-- ----------------------------
DROP TABLE IF EXISTS `tb_role_perm`;
CREATE TABLE `tb_role_perm` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `roleid` bigint(20) DEFAULT NULL,
  `permids` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records 
-- ----------------------------
INSERT INTO `tb_job` VALUES ('1','zhengJob','0/20 * * * * ?','2017-01-18 15:17:10',NULL), ('2','SecondJob','0/15 * * * * ?','2017-01-18 16:53:30','secondJob');
INSERT INTO `tb_member` VALUES ('8','13099998888','wangwu','王五',NULL,'e10adc3949ba59abbe56e057f20f883e',NULL,'1','2016-08-31 15:36:28',NULL,'','1'), ('9','13059758695','13059758695','张三',NULL,'e10adc3949ba59abbe56e057f20f883e',NULL,'1','2016-08-31 15:46:38',NULL,'超级管理员','1'), ('10','13088889999','lisi','李四',NULL,'e10adc3949ba59abbe56e057f20f883e',NULL,'1','2016-09-08 16:09:51',NULL,'李四','1');
INSERT INTO `tb_members` VALUES ('1','13059758695','admin','/pic/20160317/20160317731.png','123456','古墩路','1','2016-03-30 15:45:27','2016-03-30 16:03:45'), ('3','13011112222','李四1','/pic/20160318/20160318174.png','123456','古墩路','2','2016-03-30 15:45:27','2016-04-06 16:07:03'), ('6','222','111','','111','111','2','2016-04-18 11:09:41','2016-04-18 11:41:44'), ('7','2222','2222','','22222','2222','2','2016-04-19 16:16:09','2016-04-19 16:16:09'), ('8','333','333','','3333','333','2','2016-04-19 16:16:23','2016-04-19 16:16:23'), ('9','3333','33333','','33333','33333','2','2016-04-19 16:16:36','2016-04-19 16:16:36'), ('12','1','1','','1','1','2','2016-04-21 11:12:34','2016-04-21 11:12:34');
INSERT INTO `tb_member_role` VALUES ('8','8','45,','2016-08-31 15:36:28'), ('9','9','45,','2016-08-31 15:46:38'), ('10','10','46,','2016-09-08 16:09:51');
INSERT INTO `tb_perm` VALUES ('19','0','1','管理员管理','&#xe62d;',NULL,'2016-08-28 15:22:54','2016-08-28 15:22:54'), ('20','0','1','任务管理','','','2017-01-18 16:59:25','2017-01-18 17:01:50'), ('21','0','1','系统管理','&#xe62e;',NULL,'2016-08-28 15:23:29','2016-08-28 15:23:29'), ('22','19','2','角色管理','/role/index',NULL,'2016-08-28 15:24:02','2016-08-28 15:24:02'), ('23','19','2','权限管理','/perm/index',NULL,'2016-08-28 15:24:20','2016-08-28 15:24:20'), ('24','19','2','管理员列表','/admin/index',NULL,'2016-08-28 15:24:36','2016-08-28 15:24:36'), ('25','23','3','添加权限','/perm/add','添加权限','2016-08-29 16:10:03','2016-08-29 17:28:20'), ('26','23','3','修改权限','/perm/edit','','2016-08-31 15:45:39','2016-08-31 15:45:39'), ('27','24','3','管理员添加','/admin/add','管理员添加','2016-09-08 10:11:02','2016-09-08 10:11:02'), ('28','22','3','角色修改','/role/edit','角色修改','2016-09-08 10:12:13','2016-09-08 16:22:20'), ('29','22','3','重置管理员','/role/resetadmin','重置管理员','2016-09-08 11:10:22','2016-09-08 16:14:43'), ('31','22','3','角色删除','/role/delete','角色删除','2016-09-08 16:22:54','2016-09-08 16:22:54'), ('32','23','3','权限删除','/perm/delete','权限删除','2016-09-08 16:25:51','2016-09-08 16:25:51'), ('33','22','3','添加角色','/role/add','添加角色','2016-09-08 16:27:41','2016-09-08 16:27:41'), ('34','20','2','任务列表','/job/index','任务列表','2017-01-18 13:54:18','2017-01-18 13:54:18'), ('35','34','3','添加任务','/job/add','','2017-01-18 16:43:00','2017-01-18 16:43:00'), ('38','20','2','任务日志列表','/job/log','/job/log','2017-01-18 21:14:34','2017-01-18 21:14:34'), ('39','34','3','任务命令','/job/command','任务命令','2017-01-20 14:16:30','2017-01-20 14:16:30'), ('40','34','3','删除任务','/job/delete','删除任务','2017-01-22 10:51:08','2017-01-22 10:51:08'), ('41','24','3','管理员修改','/admin/edit','管理员修改','2017-02-05 15:04:56','2017-02-05 15:04:56'), ('42','0','1','业务消息监控管理','','业务消息监控管理','2017-02-13 14:43:23','2017-02-13 14:43:23'), ('43','42','2','业务消息监控列表','/businessmq/index','业务消息监控列表','2017-02-13 14:44:22','2017-02-13 16:31:08'), ('44','42','2','业务消息日志列表','/businessmq/log','业务消息日志列表','2017-02-13 16:31:46','2017-02-13 16:31:46');
INSERT INTO `tb_role` VALUES ('45','admin','admin','2016-08-31 15:34:00','2016-08-31 15:34:00'), ('46','test1','test1','2016-09-08 15:56:24','2016-09-08 15:56:24');
INSERT INTO `tb_role_perm` VALUES ('39','45','19,20,21,22,23,24,25,26,27,28,29,31,32,33,34,35,38,39,40,41,42,43,44','2016-08-31 15:34:03','2017-02-13 16:31:52'), ('40','46','14,19,22,28,23,25,24,','2016-09-08 15:56:29',NULL);
