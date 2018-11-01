/*
SQLyog Ultimate v12.2.6 (64 bit)
MySQL - 5.7.21 : Database - devyhtz
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`devyhtz` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `devyhtz`;

/*Table structure for table `busi_express_code` */

DROP TABLE IF EXISTS `busi_express_code`;

CREATE TABLE `busi_express_code` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `express_company` varchar(20) DEFAULT NULL COMMENT '快递公司',
  `express_code` varchar(20) DEFAULT NULL COMMENT '编码',
  `express_track` varchar(1) DEFAULT NULL COMMENT '轨迹查询 0.不支持 1.支持',
  `express_single` varchar(1) DEFAULT NULL COMMENT '电子面单 0.不支持 1.支持',
  `express_delivery` varchar(1) DEFAULT NULL COMMENT '预约取件 0.不支持 1.支持',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

/*Data for the table `busi_express_code` */

insert  into `busi_express_code`(`id`,`express_company`,`express_code`,`express_track`,`express_single`,`express_delivery`) values 
(1,'顺丰速运','SF','1','1','1'),
(2,'百世快递','HTKY','1','1','1'),
(3,'中通快递','ZTO','1','1','1'),
(4,'申通快递','STO','1','1','0'),
(5,'圆通速递','YTO','1','1','1'),
(6,'韵达速递','YD','1','1','1'),
(7,'邮政快递包裹','YZPY','1','1','0'),
(8,'EMS','EMS','1','1','0'),
(9,'天天快递','HHTT','1','1','0'),
(10,'优速快递','UC','1','1','1'),
(11,'德邦快递','DBL','1','1','1'),
(12,'快捷快递','FAST','1','1','0'),
(13,'宅急送','ZJS','1','1','0'),
(14,'TNT快递','TNT','1','0','0'),
(15,'UPS','UPS','1','0','0');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
