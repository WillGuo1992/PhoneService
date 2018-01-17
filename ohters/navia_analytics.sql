/*
SQLyog Ultimate v11.13 (64 bit)
MySQL - 5.7.18 : Database - navia_analytics
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`navia_analytics` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `navia_analytics`;

/*Table structure for table `analyse_day` */

DROP TABLE IF EXISTS `analyse_day`;

CREATE TABLE `analyse_day` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `analy_time` datetime DEFAULT NULL COMMENT '统计时间',
  `time` datetime DEFAULT NULL COMMENT 'mac时间',
  `g_id` int(7) unsigned NOT NULL DEFAULT '0' COMMENT '组ID',
  `area_id` int(11) NOT NULL DEFAULT '-1' COMMENT '区域ID',
  `duli_mac_count` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '独立mac数',
  `indoor_mac_count` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '室内个数',
  `his_max_count` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '历史在最大独立mac数',
  `indoor_his_max_count` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '历史室内最大值',
  `rpt_count` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '熟客数',
  `indoor_rpt_count` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '室内熟客',
  PRIMARY KEY (`id`),
  KEY `g_id_index` (`g_id`),
  KEY `time` (`time`),
  KEY `area_id` (`area_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7033 DEFAULT CHARSET=utf8;

/*Data for the table `analyse_day` */

/*Table structure for table `analyse_day_mac` */

DROP TABLE IF EXISTS `analyse_day_mac`;

CREATE TABLE `analyse_day_mac` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ap_mac` varchar(45) DEFAULT NULL,
  `g_id` int(11) DEFAULT NULL,
  `area_id` int(11) NOT NULL DEFAULT '-1',
  `time` datetime DEFAULT NULL,
  `mac` varchar(45) DEFAULT NULL,
  `analy_id` int(11) DEFAULT NULL,
  `count` int(11) DEFAULT '0',
  `time_line` text,
  `time_limit` int(11) DEFAULT '0',
  `vendor` varchar(100) DEFAULT NULL,
  `type` enum('1','2','3') DEFAULT NULL,
  `indoor_count` int(11) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `g_id_index` (`g_id`),
  KEY `time` (`time`),
  KEY `ap_mac` (`ap_mac`),
  KEY `area_id` (`area_id`),
  KEY `analy_id` (`analy_id`)
) ENGINE=InnoDB AUTO_INCREMENT=34034449 DEFAULT CHARSET=utf8;

/*Data for the table `analyse_day_mac` */

/*Table structure for table `analyse_mac_time` */

DROP TABLE IF EXISTS `analyse_mac_time`;

CREATE TABLE `analyse_mac_time` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `analy_id` int(10) unsigned NOT NULL,
  `g_id` int(10) NOT NULL,
  `area_id` int(10) NOT NULL DEFAULT '-1',
  `mac` varchar(50) NOT NULL,
  `a_time` datetime NOT NULL COMMENT '来的时间',
  `d_time` datetime NOT NULL COMMENT '离开时间',
  `dur_time` int(10) NOT NULL COMMENT '停留时间',
  `time_line` text,
  `signal_line` text,
  `indoor` tinyint(1) DEFAULT NULL COMMENT '是否是室内',
  `max_signal` int(5) DEFAULT NULL COMMENT '最强信号',
  `min_distance` float DEFAULT NULL COMMENT '最近距离',
  PRIMARY KEY (`id`),
  KEY `analy_id` (`analy_id`),
  KEY `g_id` (`g_id`),
  KEY `area_id` (`area_id`)
) ENGINE=MyISAM AUTO_INCREMENT=48941377 DEFAULT CHARSET=utf8;

/*Data for the table `analyse_mac_time` */

/*Table structure for table `analyse_mon` */

DROP TABLE IF EXISTS `analyse_mon`;

CREATE TABLE `analyse_mon` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `g_id` int(11) unsigned DEFAULT NULL,
  `time` datetime DEFAULT NULL,
  `analy_time` datetime DEFAULT NULL,
  `duli_mac_count` int(11) unsigned DEFAULT '0',
  `indoor_mac_count` int(11) unsigned DEFAULT '0',
  `rpt_count` int(11) unsigned DEFAULT '0',
  `indoor_rpt_count` int(11) unsigned DEFAULT '0',
  `area_id` int(11) NOT NULL DEFAULT '-1',
  PRIMARY KEY (`id`),
  KEY `g_id_index` (`g_id`),
  KEY `time` (`time`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;

/*Data for the table `analyse_mon` */

/*Table structure for table `analyse_mon_mac` */

DROP TABLE IF EXISTS `analyse_mon_mac`;

CREATE TABLE `analyse_mon_mac` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `g_id` int(11) DEFAULT NULL,
  `time` datetime DEFAULT NULL,
  `ap_mac` varchar(45) DEFAULT NULL,
  `mac` varchar(45) DEFAULT NULL,
  `analy_id` int(11) DEFAULT NULL,
  `vendor` varchar(100) DEFAULT NULL,
  `type` enum('1','2','3') DEFAULT NULL,
  `area_id` int(11) NOT NULL DEFAULT '-1',
  PRIMARY KEY (`id`),
  KEY `g_id_index` (`g_id`),
  KEY `time` (`time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `analyse_mon_mac` */

/*Table structure for table `analyse_vendor` */

DROP TABLE IF EXISTS `analyse_vendor`;

CREATE TABLE `analyse_vendor` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `g_id` int(10) unsigned DEFAULT NULL,
  `area_id` int(10) NOT NULL DEFAULT '-1',
  `v_time` datetime DEFAULT NULL,
  `vendor` varchar(35) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `count` int(10) unsigned NOT NULL DEFAULT '0',
  `type` enum('1','2','3') DEFAULT NULL COMMENT '1:mobile;2:PC;3:wifi',
  `indoor_count` int(10) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `g_id` (`g_id`),
  KEY `area_id` (`area_id`)
) ENGINE=InnoDB AUTO_INCREMENT=283627 DEFAULT CHARSET=utf8;

/*Data for the table `analyse_vendor` */

/*Table structure for table `analyse_week` */

DROP TABLE IF EXISTS `analyse_week`;

CREATE TABLE `analyse_week` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `g_id` int(11) unsigned DEFAULT NULL,
  `time` datetime DEFAULT NULL,
  `analy_time` datetime DEFAULT NULL,
  `duli_mac_count` int(11) unsigned DEFAULT '0',
  `indoor_mac_count` int(11) unsigned DEFAULT '0',
  `rpt_count` int(11) unsigned DEFAULT '0',
  `indoor_rpt_count` int(11) unsigned DEFAULT '0',
  `area_id` int(11) NOT NULL DEFAULT '-1',
  PRIMARY KEY (`id`),
  KEY `g_id_index` (`g_id`),
  KEY `time` (`time`)
) ENGINE=InnoDB AUTO_INCREMENT=431 DEFAULT CHARSET=utf8 COMMENT='周统计表';

/*Data for the table `analyse_week` */

/*Table structure for table `analyse_week_mac` */

DROP TABLE IF EXISTS `analyse_week_mac`;

CREATE TABLE `analyse_week_mac` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `g_id` int(11) DEFAULT NULL,
  `time` datetime DEFAULT NULL,
  `ap_mac` varchar(45) DEFAULT NULL,
  `mac` varchar(45) DEFAULT NULL,
  `analy_id` int(11) DEFAULT NULL,
  `count` int(11) DEFAULT NULL,
  `vendor` varchar(100) DEFAULT NULL,
  `type` enum('1','2','3') DEFAULT NULL,
  `area_id` int(11) NOT NULL DEFAULT '-1',
  PRIMARY KEY (`id`),
  KEY `g_id_index` (`g_id`),
  KEY `time` (`time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `analyse_week_mac` */

/*Table structure for table `ap_meta` */

DROP TABLE IF EXISTS `ap_meta`;

CREATE TABLE `ap_meta` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `model` varchar(50) NOT NULL COMMENT '设备类型',
  `p_tx` int(11) unsigned NOT NULL COMMENT '发射功率',
  `g_tx` int(11) unsigned NOT NULL COMMENT '增益',
  PRIMARY KEY (`id`),
  UNIQUE KEY `model` (`model`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `ap_meta` */

/*Table structure for table `meta_group` */

DROP TABLE IF EXISTS `meta_group`;

CREATE TABLE `meta_group` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL,
  `area_size` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '区域大小,只有叶级节点有',
  `parent` int(10) NOT NULL DEFAULT '0' COMMENT '父亲',
  `leaf` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否叶子节点',
  `hom_n` int(11) DEFAULT '3' COMMENT '室内环境参数, (2-5之间, 默认:3)',
  `type` int(5) unsigned NOT NULL DEFAULT '0' COMMENT '0:默认 1:branch 2:floor 3:area',
  `enable` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '1:可用(默认)  0:不可用',
  PRIMARY KEY (`id`),
  KEY `type` (`type`),
  KEY `leaf` (`leaf`)
) ENGINE=InnoDB AUTO_INCREMENT=133 DEFAULT CHARSET=utf8;

/*Data for the table `meta_group` */

/*Table structure for table `refer_ap` */

DROP TABLE IF EXISTS `refer_ap`;

CREATE TABLE `refer_ap` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `g_id` int(11) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `mac` varchar(50) NOT NULL,
  `position` enum('door','center','wall') NOT NULL,
  `map_id` int(10) DEFAULT '0',
  `map_cm_x` int(10) DEFAULT '0',
  `map_cm_y` int(10) DEFAULT '0',
  `model` varchar(20) DEFAULT NULL,
  `serial_num` varchar(15) NOT NULL,
  `enable` tinyint(2) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `mac` (`mac`),
  KEY `g_id` (`g_id`)
) ENGINE=InnoDB AUTO_INCREMENT=351 DEFAULT CHARSET=utf8 COMMENT='设备';

/*Data for the table `refer_ap` */

/*Table structure for table `refer_map` */

DROP TABLE IF EXISTS `refer_map`;

CREATE TABLE `refer_map` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `g_id` int(10) unsigned NOT NULL,
  `name` varchar(50) DEFAULT NULL COMMENT '名字',
  `cm_w` int(8) unsigned DEFAULT NULL COMMENT '实际宽度:cm',
  `cm_h` int(8) unsigned DEFAULT NULL COMMENT '实际高度:cm',
  `px_w` int(8) unsigned DEFAULT NULL COMMENT '像素宽',
  `px_h` int(8) unsigned DEFAULT NULL COMMENT '像素高',
  PRIMARY KEY (`id`),
  KEY `g_id` (`g_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='分析数据需要的参考数据';

/*Data for the table `refer_map` */

/*Table structure for table `refer_map_area` */

DROP TABLE IF EXISTS `refer_map_area`;

CREATE TABLE `refer_map_area` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL DEFAULT '0' COMMENT '块名字',
  `map_id` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '所在地图的ID',
  `g_id` int(10) unsigned DEFAULT NULL COMMENT 'gourp.id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8 COMMENT='地图标记出来的关注区域';

/*Data for the table `refer_map_area` */

/*Table structure for table `refer_map_area_point` */

DROP TABLE IF EXISTS `refer_map_area_point`;

CREATE TABLE `refer_map_area_point` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `cm_x` int(11) unsigned DEFAULT NULL COMMENT '坐标:x',
  `cm_y` int(11) unsigned DEFAULT NULL COMMENT '坐标:y',
  `map_id` int(11) unsigned DEFAULT NULL COMMENT 'map id',
  `area_id` int(11) unsigned DEFAULT NULL COMMENT '所在块区',
  `sort` int(11) unsigned DEFAULT NULL COMMENT '顺序',
  PRIMARY KEY (`id`),
  KEY `map_id` (`map_id`),
  KEY `area_id` (`area_id`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8 COMMENT='块的点路径';

/*Data for the table `refer_map_area_point` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
