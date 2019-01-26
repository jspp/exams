/*
SQLyog Enterprise v12.09 (64 bit)
MySQL - 5.6.25-log : Database - student_db
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`student_db` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `student_db`;

/*Table structure for table `students` */

DROP TABLE IF EXISTS `students`;

CREATE TABLE `students` (
  `ROW_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `STU_NUM` varchar(20) DEFAULT NULL COMMENT '学号',
  `STU_NAME` varchar(12) DEFAULT NULL COMMENT '名称',
  `STU_SEX` char(1) DEFAULT '男' COMMENT '性别',
  `STU_AGE` tinyint(4) DEFAULT NULL COMMENT '年龄',
  `STU_ADDRESS` varchar(128) DEFAULT NULL COMMENT '籍贯',
  `STU_SUBJECT` varchar(128) DEFAULT NULL COMMENT '专业',
  PRIMARY KEY (`ROW_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=10005 DEFAULT CHARSET=utf8mb4;

/*Data for the table `students` */

insert  into `students`(`ROW_ID`,`STU_NUM`,`STU_NAME`,`STU_SEX`,`STU_AGE`,`STU_ADDRESS`,`STU_SUBJECT`) values (10000,'10000','张三','男',21,'陕西西安','电子商务'),(10002,'10002','小明','男',30,'湖南长沙','国际贸易'),(10003,'10003','韩梅梅','女',25,'陕西安康','英语'),(10004,'12311','1','2',3,'42','55');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
