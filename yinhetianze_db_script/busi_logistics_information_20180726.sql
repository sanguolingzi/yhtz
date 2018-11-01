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

/*Table structure for table `busi_logistics_information` */

DROP TABLE IF EXISTS `busi_logistics_information`;

CREATE TABLE `busi_logistics_information` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `orders_id` int(10) DEFAULT NULL COMMENT '订单id',
  `express_company` varchar(20) DEFAULT NULL COMMENT '快递公司',
  `express_code` varchar(20) DEFAULT NULL COMMENT '快递公司编码',
  `logistice_code` varchar(30) DEFAULT NULL COMMENT '物流单号',
  `receiver_name` varchar(30) DEFAULT NULL COMMENT '收件人',
  `receiver_mobile` varchar(20) DEFAULT NULL COMMENT '收件人手机',
  `receiver_province` varchar(20) DEFAULT NULL COMMENT '收件省',
  `receiver_city` varchar(20) DEFAULT NULL COMMENT '收件市',
  `receiver_area` varchar(20) DEFAULT NULL COMMENT '收件区/县',
  `receiver_address` varchar(100) DEFAULT NULL COMMENT '收件人详细地址',
  `sender_name` varchar(20) DEFAULT NULL COMMENT '发件人',
  `sender_Mobile` varchar(20) DEFAULT NULL COMMENT '发件人手机',
  `sender_province` varchar(20) DEFAULT NULL COMMENT '发件省',
  `sender_city` varchar(20) DEFAULT NULL COMMENT '发件市',
  `sender_area` varchar(20) DEFAULT NULL COMMENT '发件区/县',
  `sender_address` varchar(100) DEFAULT NULL COMMENT '发件人详细地址',
  `subscription_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '订阅时间',
  `is_subscription` varchar(10) DEFAULT NULL COMMENT 'true:订阅成功 false:订阅失败',
  `subscription_reason` varchar(200) DEFAULT NULL COMMENT '订阅失败原因',
  `estimated_delivery_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '预计到货时间',
  `push_time` timestamp NULL DEFAULT NULL COMMENT '推送时间',
  `push_logistics` text COMMENT '推送物流信息',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `busi_logistics_information` */

insert  into `busi_logistics_information`(`id`,`orders_id`,`express_company`,`express_code`,`logistice_code`,`receiver_name`,`receiver_mobile`,`receiver_province`,`receiver_city`,`receiver_area`,`receiver_address`,`sender_name`,`sender_Mobile`,`sender_province`,`sender_city`,`sender_area`,`sender_address`,`subscription_time`,`is_subscription`,`subscription_reason`,`estimated_delivery_time`,`push_time`,`push_logistics`) values 
(1,10001779,'ZTO','SF','1234561','Yann','15018442396','北京','北京','朝阳区','三里屯街道雅秀大厦','Taylor','15018442396','上海','上海','青浦区','明珠路73号','2018-07-19 10:05:41','true',NULL,'2018-07-23 11:27:37','2018-07-19 10:11:50','[{\"AcceptStation\":\"顺丰速运已收取快件\",\"AcceptTime\":\"2018-07-19 10:11:50\",\"Remark\":\"\"},{\"AcceptStation\":\"货物已经到达深圳\",\"AcceptTime\":\"2018-07-19 10:11:502\",\"Remark\":\"\"},{\"AcceptStation\":\"货物到达福田保税区网点\",\"AcceptTime\":\"2018-07-19 10:11:503\",\"Remark\":\"\"},{\"AcceptStation\":\"货物已经被张三签收了\",\"AcceptTime\":\"2018-07-19 10:11:504\",\"Remark\":\"\"}]'),
(2,10001773,'顺丰速运','SF','1276899944','张','17656438765','北京','北京市','东城区','鸟巢水立方','自营店铺','13333333333','河北省','沧州市','河间市','啊实打实的阿萨打阿萨打阿萨打阿三的','2018-07-19 15:29:44','true',NULL,'2018-07-23 11:27:46',NULL,NULL);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
