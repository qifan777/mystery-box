-- MySQL dump 10.13  Distrib 8.0.29, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: mystery_box
-- ------------------------------------------------------
-- Server version	8.0.29

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `address`
--

DROP TABLE IF EXISTS `address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `address` (
  `id` varchar(36) NOT NULL,
  `created_time` datetime(6) NOT NULL,
  `edited_time` datetime(6) NOT NULL,
  `creator_id` varchar(36) NOT NULL,
  `editor_id` varchar(36) NOT NULL,
  `latitude` double NOT NULL COMMENT '维度',
  `longitude` double NOT NULL COMMENT '经度',
  `details` varchar(255) NOT NULL COMMENT '详细地址',
  `province` varchar(255) NOT NULL COMMENT '省',
  `city` varchar(255) NOT NULL COMMENT '市',
  `district` varchar(255) NOT NULL COMMENT '区',
  `phone_number` varchar(255) NOT NULL COMMENT '手机号',
  `real_name` varchar(255) NOT NULL COMMENT '真实姓名',
  `house_number` varchar(255) NOT NULL COMMENT '门牌号',
  `top` tinyint(1) NOT NULL COMMENT '是否置顶',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='地址表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `address`
--

LOCK TABLES `address` WRITE;
/*!40000 ALTER TABLE `address` DISABLE KEYS */;
INSERT INTO `address` VALUES ('5443f7da984a4f61bc15b8702ec10af1','2024-03-14 10:33:42.068210','2024-03-14 10:33:42.068210','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7',26.08144,119.328431,'福建省福州市晋安区福新中路128号晋安区机关大院2号楼7层','福建省','福州市','晋安区','13656987994','起凡','311',0),('916dd3b50a1c49349d53a1cd18ddcf41','2024-03-14 15:54:08.635695','2024-03-14 15:54:08.635695','ad9bff77beb342d091897e69e0a707dd','ad9bff77beb342d091897e69e0a707dd',26.08135,119.32842,'福建省福州市晋安区','福建省','福州市','晋安区','13656987994','起凡','1123',0),('a20c60c140dd4ac3b5bf29be6aba954d','2024-03-14 10:46:42.327846','2024-03-14 10:46:42.327846','c3a27b84683e4869a38d3c010a16386a','c3a27b84683e4869a38d3c010a16386a',26.08135,119.32842,'福建省福州市晋安区','福建省','福州市','晋安区','12345678901','起凡','311',0),('a2d7c173-3445-4498-8951-39d2cc3010b2','2024-01-25 15:30:58.465746','2024-01-25 18:25:59.037309','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7',26.075455,119.33508,'福建省福州市晋安区五里亭立交桥与连江北路交叉口正南方向100米左右','福建省','福州市','晋安区','13666666666','起凡','1号楼',0),('ef42e042-b54b-4888-aad4-79905f2e49d7','2024-01-25 16:22:49.652859','2024-01-25 18:26:01.917410','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7',26.051222,119.342255,'福建省福州市台江区江滨中大道366号','福建省','福州市','台江区','13666666666','起凡','501',1);
/*!40000 ALTER TABLE `address` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `article`
--

DROP TABLE IF EXISTS `article`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `article` (
  `id` varchar(32) NOT NULL,
  `created_time` datetime(6) NOT NULL,
  `edited_time` datetime(6) NOT NULL,
  `creator_id` varchar(32) NOT NULL,
  `editor_id` varchar(32) NOT NULL,
  `title` varchar(50) NOT NULL COMMENT '标题',
  `cover` varchar(255) NOT NULL COMMENT '封面',
  `summary` varchar(255) NOT NULL COMMENT '摘要',
  `author` varchar(255) NOT NULL COMMENT '作者',
  `content` text NOT NULL COMMENT '内容',
  `unit` varchar(255) NOT NULL COMMENT '单位',
  `likes` int NOT NULL DEFAULT '0' COMMENT '点赞数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `article`
--

LOCK TABLES `article` WRITE;
/*!40000 ALTER TABLE `article` DISABLE KEYS */;
/*!40000 ALTER TABLE `article` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `article_collect`
--

DROP TABLE IF EXISTS `article_collect`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `article_collect` (
  `id` varchar(32) NOT NULL,
  `created_time` datetime(6) NOT NULL,
  `edited_time` datetime(6) NOT NULL,
  `creator_id` varchar(32) NOT NULL,
  `editor_id` varchar(32) NOT NULL,
  `article_id` varchar(32) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `article_collect`
--

LOCK TABLES `article_collect` WRITE;
/*!40000 ALTER TABLE `article_collect` DISABLE KEYS */;
/*!40000 ALTER TABLE `article_collect` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `article_comment`
--

DROP TABLE IF EXISTS `article_comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `article_comment` (
  `id` varchar(32) NOT NULL,
  `created_time` datetime(6) NOT NULL,
  `edited_time` datetime(6) NOT NULL,
  `creator_id` varchar(32) NOT NULL,
  `editor_id` varchar(32) NOT NULL,
  `content` varchar(255) NOT NULL COMMENT '评论内容',
  `article_id` varchar(32) NOT NULL COMMENT '批量文章',
  `parent_id` varchar(32) DEFAULT NULL COMMENT '父亲评论id',
  `parent_user_id` varchar(32) DEFAULT NULL COMMENT '父亲评论的用户id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `article_comment`
--

LOCK TABLES `article_comment` WRITE;
/*!40000 ALTER TABLE `article_comment` DISABLE KEYS */;
/*!40000 ALTER TABLE `article_comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `base_order`
--

DROP TABLE IF EXISTS `base_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `base_order` (
  `id` varchar(36) NOT NULL,
  `created_time` datetime(6) NOT NULL,
  `edited_time` datetime(6) NOT NULL,
  `creator_id` varchar(36) NOT NULL,
  `editor_id` varchar(36) NOT NULL,
  `payment_id` varchar(36) NOT NULL COMMENT '支付订单id',
  `address` varchar(1000) DEFAULT NULL COMMENT '地址详情',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `tracking_number` varchar(50) DEFAULT NULL COMMENT '物流单号',
  `coupon_user_id` varchar(36) DEFAULT NULL COMMENT '用户优惠券id',
  `type` varchar(32) NOT NULL COMMENT '订单类型',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `base_order`
--

LOCK TABLES `base_order` WRITE;
/*!40000 ALTER TABLE `base_order` DISABLE KEYS */;
INSERT INTO `base_order` VALUES ('121a008b0d6b4e52a1cd2e51a96a2cae','2024-03-18 14:19:52.935815','2024-03-18 14:19:52.935815','ad9bff77beb342d091897e69e0a707dd','ad9bff77beb342d091897e69e0a707dd','2c248326b84144deae48bb8a7e2969e5','{\"createdTime\":[2024,3,14,15,54,8],\"editedTime\":[2024,3,14,15,54,8],\"latitude\":26.08135,\"longitude\":119.32842,\"address\":\"福建省福州市晋安区\",\"details\":\"1123\",\"province\":\"福建省\",\"city\":\"福州市\",\"district\":\"晋安区\",\"phoneNumber\":\"13656987994\",\"realName\":\"起凡\",\"top\":false,\"id\":\"916dd3b50a1c49349d53a1cd18ddcf41\"}',NULL,NULL,NULL,'BLIND_BOX_ORDER');
/*!40000 ALTER TABLE `base_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `coupon`
--

DROP TABLE IF EXISTS `coupon`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `coupon` (
  `id` varchar(36) NOT NULL,
  `created_time` datetime(6) NOT NULL,
  `edited_time` datetime(6) NOT NULL,
  `creator_id` varchar(36) NOT NULL,
  `editor_id` varchar(36) NOT NULL,
  `threshold_amount` decimal(10,2) NOT NULL,
  `released_quantity` int NOT NULL,
  `effective_date` datetime(6) NOT NULL,
  `expiration_date` datetime(6) NOT NULL,
  `type` varchar(36) NOT NULL,
  `scope` varchar(36) NOT NULL,
  `amount` decimal(10,2) DEFAULT NULL,
  `discount` decimal(3,2) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `coupon_pk` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `coupon`
--

LOCK TABLES `coupon` WRITE;
/*!40000 ALTER TABLE `coupon` DISABLE KEYS */;
INSERT INTO `coupon` VALUES ('11f61057ff4f4e5d84154eb1a3403b30','2024-02-23 09:35:35.858812','2024-03-01 15:55:08.460549','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7',100.00,111,'2024-02-24 00:00:00.000000','2024-04-01 00:00:00.000000','DISCOUNT','GENERAL',NULL,9.00,'满100打9折'),('863cbb8f8f3940668b0cfce1d916af46','2024-02-23 09:34:29.627801','2024-03-01 17:46:05.797435','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7',100.00,10,'2024-02-14 00:00:00.000000','2024-03-28 00:00:00.000000','REDUCE','GENERAL',5.00,NULL,'满100减5优惠券');
/*!40000 ALTER TABLE `coupon` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `coupon_use_record`
--

DROP TABLE IF EXISTS `coupon_use_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `coupon_use_record` (
  `id` varchar(36) NOT NULL,
  `created_time` datetime(6) NOT NULL,
  `edited_time` datetime(6) NOT NULL,
  `creator_id` varchar(36) NOT NULL,
  `editor_id` varchar(36) NOT NULL,
  `coupon_user_id` varchar(36) NOT NULL,
  `order_id` varchar(36) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `coupon_use_record`
--

LOCK TABLES `coupon_use_record` WRITE;
/*!40000 ALTER TABLE `coupon_use_record` DISABLE KEYS */;
/*!40000 ALTER TABLE `coupon_use_record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `coupon_user_rel`
--

DROP TABLE IF EXISTS `coupon_user_rel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `coupon_user_rel` (
  `id` varchar(36) NOT NULL,
  `created_time` datetime(6) NOT NULL,
  `edited_time` datetime(6) NOT NULL,
  `creator_id` varchar(36) NOT NULL,
  `editor_id` varchar(36) NOT NULL,
  `receive_type` varchar(36) NOT NULL,
  `coupon_id` varchar(36) NOT NULL,
  `status` varchar(36) NOT NULL,
  `user_id` varchar(36) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `coupon_user_rel`
--

LOCK TABLES `coupon_user_rel` WRITE;
/*!40000 ALTER TABLE `coupon_user_rel` DISABLE KEYS */;
INSERT INTO `coupon_user_rel` VALUES ('138651abf04c4da0b2ecc0247a89cff3','2024-02-26 14:01:20.727699','2024-02-26 15:42:22.866240','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','GIFT','11f61057ff4f4e5d84154eb1a3403b30','UNUSED','0f07d638-f1bc-4011-88d8-6dc650ab06a7'),('3076bbf8182248dfb550dcc2771000bf','2024-02-26 15:35:53.313190','2024-02-26 15:35:53.313190','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','GIFT','863cbb8f8f3940668b0cfce1d916af46','UNUSED','0f07d638-f1bc-4011-88d8-6dc650ab06a7'),('6d9b32c572084d3081601a3dafb57346','2024-02-26 15:35:53.291193','2024-02-26 15:35:53.291193','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','GIFT','863cbb8f8f3940668b0cfce1d916af46','UNUSED','9426415c-8254-46dd-95fe-35fb4c307713'),('dee3e19e3acb4167855b6d0390e2a1ea','2024-02-26 15:36:25.414854','2024-02-26 15:42:15.599915','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','GIFT','11f61057ff4f4e5d84154eb1a3403b30','EXPIRED','9426415c-8254-46dd-95fe-35fb4c307713');
/*!40000 ALTER TABLE `coupon_user_rel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dict`
--

DROP TABLE IF EXISTS `dict`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dict` (
  `id` varchar(36) NOT NULL,
  `created_time` datetime(6) NOT NULL,
  `edited_time` datetime(6) NOT NULL,
  `creator_id` varchar(36) NOT NULL,
  `editor_id` varchar(36) NOT NULL,
  `key_id` int NOT NULL,
  `key_en_name` varchar(255) NOT NULL,
  `key_name` varchar(36) NOT NULL,
  `dict_id` int NOT NULL,
  `dict_name` varchar(36) NOT NULL,
  `dict_en_name` varchar(255) NOT NULL,
  `order_num` int NOT NULL,
  `visible` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否可见',
  PRIMARY KEY (`id`),
  UNIQUE KEY `dict_pk` (`key_id`,`dict_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dict`
--

LOCK TABLES `dict` WRITE;
/*!40000 ALTER TABLE `dict` DISABLE KEYS */;
INSERT INTO `dict` VALUES ('13059f87f7c84d319fd3f6038cedea9b','2024-02-22 15:58:22.896688','2024-02-22 15:58:34.067738','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7',0,'DISCOUNT','折扣券',1006,'优惠券类型','COUPON_TYPE',0,1),('1a687b7d9b2447b0aa57e361812dcdf0','2024-01-26 11:10:49.521488','2024-01-26 11:10:49.521488','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7',0,'WE_CHAT_PAY','微信支付',1004,'支付类型','PAY_TYPE',0,1),('1f01fa7bf1624376870d9207735f658d','2024-01-16 09:33:09.151337','2024-01-16 09:33:09.151337','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7',2,'BUTTON','按钮',1002,'菜单类型','MENU_TYPE',2,1),('2a8a74279fb64ecb822c8b22fd493a93','2024-01-26 11:08:34.090534','2024-01-26 11:08:37.984915','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7',4,'CLOSED','已关闭',1003,'商品订单状态','PRODUCT_ORDER_STATUS',0,1),('3860dff47f224dedbc3019cd1b4bc098','2024-01-16 09:30:39.144272','2024-01-16 09:33:15.663135','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7',0,'PAGE','页面',1002,'菜单类型','MENU_TYPE',1,1),('416c90b442e84af1a3f57e321c9c3437','2024-01-16 09:32:28.555205','2024-01-16 09:32:28.555205','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7',1,'DIRECTORY','目录',1002,'菜单类型','MENU_TYPE',0,1),('461a361d073c4574aed1c025e04a81a3','2024-01-26 11:09:32.434369','2024-01-26 11:13:54.428416','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7',5,'REFUNDING','退款中',1003,'商品订单状态','PRODUCT_ORDER_STATUS',0,1),('56e8d93069534f6a875c34d5c26802a5','2024-01-26 11:03:49.162351','2024-01-26 11:04:00.418344','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7',2,'TO_BE_RECEIVED','待收货',1003,'商品订单状态','PRODUCT_ORDER_STATUS',0,1),('5c820b53654545fd844222f7e486fc8e','2024-01-26 10:56:45.364997','2024-01-26 11:02:58.744868','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7',0,'TO_BE_PAID','待付款',1003,'商品订单状态','PRODUCT_ORDER_STATUS',0,1),('5fba34ff760c453a9ce3284ed68710ca','2024-01-14 15:23:55.954376','2024-01-14 15:23:55.954376','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7',2,'PRIVATE','保密',1001,'性别','GENDER',0,1),('7d11e97eb4d7474ca2dab8cb50a32ca8','2024-02-22 15:58:22.896688','2024-02-22 15:58:34.067738','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7',2,'CATEGORY','品类券',1007,'优惠券使用范围','COUPON_SCOPE',0,1),('7d11e97eb4d7474ca2dab8cb50c32ca8','2024-02-22 15:58:22.896688','2024-02-22 15:58:34.067738','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7',1,'PRODUCT','商品券',1007,'优惠券使用范围','COUPON_SCOPE',0,1),('7d11e97eb4d7474ca2dab8cb50c34cab','2024-02-22 15:58:22.896688','2024-02-22 15:58:34.067738','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7',1,'USED','已使用',1008,'优惠券使用状态','COUPON_USE_STATUS',0,1),('7d11e97eb4d7474ca2dab8cb50c34cah','2024-02-22 15:58:22.896688','2024-02-22 15:58:34.067738','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7',0,'GIFT','系统赠送',1009,'优惠券获取方式','COUPON_RECEIVE_TYPE',0,1),('7d11e97eb4d7474ca2dab8cb50c34caz','2024-02-22 15:58:22.896688','2024-02-22 15:58:34.067738','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7',2,'EXPIRED','已过期',1008,'优惠券使用状态','COUPON_USE_STATUS',0,1),('7d11e97eb4d7474ca2dab8cb50c34cc8','2024-02-22 15:58:22.896688','2024-02-22 15:58:34.067738','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7',0,'GENERAL','通用券',1007,'优惠券使用范围','COUPON_SCOPE',0,1),('7d11e97eb4d7474ca2dzb8cb50c34ca1','2024-02-22 15:58:22.896688','2024-02-22 15:58:34.067738','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7',0,'UNUSED','未使用',1008,'优惠券使用状态','COUPON_USE_STATUS',0,1),('a1a13655732845c38cdddc0d41ef5792','2024-01-26 11:06:10.939935','2024-01-26 11:06:16.216645','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7',3,'TO_BE_EVALUATED','待评价',1003,'商品订单状态','PRODUCT_ORDER_STATUS',0,1),('a3caf40ea1ef4a778096f467fb14060e','2024-01-10 10:50:18.555224','2024-01-11 15:49:22.959501','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7',0,'MALE','男',1001,'性别','GENDER',0,1),('b33660610be145f998de5a86753665ce','2024-01-10 13:55:26.468101','2024-01-10 13:55:26.468101','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7',1,'FEMALE','女',1001,'性别','GENDER',1,1),('f0e0c91e7ad249deb9359691d109bd8h','2024-02-21 09:05:02.936174','2024-02-21 09:05:13.494713','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7',0,'PRODUCT','商品',1013,'跳转类型','NAVIGATOR_TYPE',0,1),('f0e0c92e7ad249deb9359691d109bd8h','2024-02-21 09:05:02.936174','2024-02-21 09:05:13.494713','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7',1,'BLIND_BOX','盲盒',1013,'跳转类型','NAVIGATOR_TYPE',0,1),('f0e0c95e7ad249deb9359691d009fd7a','2024-02-18 15:08:27.511795','2024-02-18 15:10:19.268405','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7',6,'TO_BE_CREATE','待创建',1003,'商品订单状态','PRODUCT_ORDER_STATUS',0,1),('f0e0c95e7ad249deb9359691d009fd7c','2024-02-22 15:58:22.896688','2024-02-22 15:58:34.067738','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7',1,'REDUCE','满减券',1006,'优惠券类型','COUPON_TYPE',0,1),('f0e0c95e7ad249deb9359691d109bd8h','2024-02-21 09:05:02.936174','2024-02-21 09:05:13.494713','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7',1,'BLIND_BOX_ORDER','盲盒订单',1012,'订单类型','ORDER_TYPE',0,1),('f0e0c95e7ad249deb9359691d109dd8h','2024-02-21 09:05:02.936174','2024-02-21 09:05:13.494713','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7',0,'PRODUCT_ORDER','商品订单',1012,'订单类型','ORDER_TYPE',0,1),('fc930d380612121791ab809a5be03651','2024-01-26 11:02:08.987958','2024-01-26 11:02:22.277984','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7',0,'NORMAL','正常',1005,'用户状态','USER_STATUS',0,1),('fc930d380612421791ab809a5be03656','2024-01-26 11:02:08.987958','2024-01-26 11:02:22.277984','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7',1,'TO_BE_DELIVERED','待发货',1003,'商品订单状态	','PRODUCT_ORDER_STATUS',0,1),('fc930d380622121791ab809a5be03651','2024-01-26 11:02:08.987958','2024-01-26 11:02:22.277984','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7',1,'BANNED','封禁',1005,'用户状态','USER_STATUS',0,1);
/*!40000 ALTER TABLE `dict` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `feedback`
--

DROP TABLE IF EXISTS `feedback`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `feedback` (
  `id` varchar(36) NOT NULL,
  `created_time` datetime(6) NOT NULL,
  `edited_time` datetime(6) NOT NULL,
  `creator_id` varchar(36) NOT NULL,
  `editor_id` varchar(36) NOT NULL,
  `content` varchar(2000) NOT NULL,
  `pictures` varchar(2000) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `feedback`
--

LOCK TABLES `feedback` WRITE;
/*!40000 ALTER TABLE `feedback` DISABLE KEYS */;
/*!40000 ALTER TABLE `feedback` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `menu`
--

DROP TABLE IF EXISTS `menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `menu` (
  `id` varchar(36) NOT NULL,
  `created_time` datetime(6) NOT NULL,
  `edited_time` datetime(6) NOT NULL,
  `creator_id` varchar(36) NOT NULL,
  `editor_id` varchar(36) NOT NULL,
  `name` varchar(20) NOT NULL,
  `path` varchar(2000) NOT NULL,
  `parent_id` varchar(36) DEFAULT NULL,
  `order_num` int DEFAULT NULL,
  `menu_type` varchar(36) NOT NULL,
  `icon` varchar(255) DEFAULT NULL,
  `visible` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `menu`
--

LOCK TABLES `menu` WRITE;
/*!40000 ALTER TABLE `menu` DISABLE KEYS */;
INSERT INTO `menu` VALUES ('00f2290a-cde5-4e95-9294-4e7599b1a3f5','2024-01-16 11:21:02.655367','2024-01-17 10:12:26.403455','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','权限管理','/permission',NULL,0,'DIRECTORY','Lock',1),('0c30e2f6-8cef-4ee6-999e-a18af97a3a26','2024-01-16 14:33:09.062999','2024-01-17 10:10:02.922860','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','用户','/user','00f2290a-cde5-4e95-9294-4e7599b1a3f5',1,'PAGE','User',1),('16b68322-193d-4be4-b71b-bbc0f18b0d3f','2024-01-16 14:33:42.204639','2024-01-16 20:51:06.437836','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','商品管理','/test',NULL,NULL,'DIRECTORY',NULL,1),('349b39a5-9500-4c22-9972-f22b935aba42','2024-01-17 10:12:54.930459','2024-01-17 10:13:12.046479','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','角色','/role','00f2290a-cde5-4e95-9294-4e7599b1a3f5',2,'PAGE','Avatar',1),('390bcc1517e2476b83071c4c2c0502b8','2024-07-22 15:11:10.521538','2024-07-22 15:11:30.695374','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','类别详情','/product-category-details','16b68322-193d-4be4-b71b-bbc0f18b0d3f',6,'PAGE',NULL,0),('43a710c85f8a43fe9602be9ceed50d1f','2024-02-23 09:33:23.664224','2024-02-23 09:33:23.664224','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','优惠券','/coupon','c9cf9ed3f15d4e20b37c672500311324',0,'PAGE','Ticket',1),('4e965b885e874521ab84c5c114e7a230','2024-03-15 23:21:42.119613','2024-07-22 18:02:50.400013','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','盲盒管理','/mystery-box','7e6a08be19d340a68981f1be6a14ec92',0,'PAGE',NULL,1),('6c0d0f67db5d4418b974c685af43f7e0','2024-02-26 14:09:15.022676','2024-02-26 14:09:15.022676','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','用户优惠券','/coupon-user','c9cf9ed3f15d4e20b37c672500311324',1,'PAGE','User',1),('7e6a08be19d340a68981f1be6a14ec92','2024-03-15 23:21:19.372519','2024-03-15 23:21:19.372519','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','盲盒中心','bind-box-center',NULL,6,'DIRECTORY',NULL,1),('8246938d-50f1-4463-9ff1-8f4097df2b7a','2024-01-18 17:02:00.136884','2024-01-18 17:02:00.136884','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','商品管理','/product','16b68322-193d-4be4-b71b-bbc0f18b0d3f',0,'PAGE','Goods',1),('839c9fd0-8cf0-4070-a50f-16981bf649f8','2024-01-19 11:12:10.495833','2024-01-19 11:12:10.495833','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','商品SKU','/product-sku','16b68322-193d-4be4-b71b-bbc0f18b0d3f',3,'PAGE','ShoppingCartFull',1),('8ea32b3d-90e4-4d76-a777-413917166a32','2024-01-16 11:20:24.133270','2024-01-17 10:13:01.821007','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','菜单','/menu','00f2290a-cde5-4e95-9294-4e7599b1a3f5',3,'PAGE','Menu',1),('9002b4faff164e2fb728afe89601a474','2024-07-24 21:26:26.408181','2024-07-24 21:26:26.408181','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','轮播图详情','/slideshow-details','a350090e-11ff-45d0-88ce-5c3c1b6f9ef4',NULL,'PAGE',NULL,0),('95663185b0e1448ebc746471858ebfe0','2024-07-24 21:26:06.204920','2024-07-24 21:26:06.204920','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','轮播图','/slideshow','a350090e-11ff-45d0-88ce-5c3c1b6f9ef4',NULL,'PAGE',NULL,1),('a350090e-11ff-45d0-88ce-5c3c1b6f9ef4','2024-01-26 10:06:09.672544','2024-01-26 10:06:09.672544','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','系统管理','/system',NULL,0,'DIRECTORY','House',1),('a58c46ccfb634917a1c470e01746410c','2024-07-22 18:03:09.181450','2024-07-22 18:03:09.181450','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','盲盒详情','/mystery-box-details','7e6a08be19d340a68981f1be6a14ec92',NULL,'PAGE',NULL,0),('b361c5efc45a4558a229b7188aa00e61','2024-07-22 09:07:32.077831','2024-07-22 09:07:32.077831','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','商品详情','/product-details','16b68322-193d-4be4-b71b-bbc0f18b0d3f',4,'PAGE',NULL,0),('c32ce807a69c43e8bdf67e27974920ab','2024-02-20 09:28:14.813332','2024-02-20 09:28:14.813332','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','商品订单','/product-order','16b68322-193d-4be4-b71b-bbc0f18b0d3f',0,'PAGE','Tickets',1),('c9cf9ed3f15d4e20b37c672500311324','2024-02-23 09:32:44.008524','2024-02-23 09:32:44.008524','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','优惠券管理','coupon-manage',NULL,4,'DIRECTORY','Ticket',1),('d5510c37-c5e9-48b3-b41b-2a0fa710c94e','2024-01-18 14:36:19.589318','2024-01-18 14:36:19.589318','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','商品类别','/product-category','16b68322-193d-4be4-b71b-bbc0f18b0d3f',0,'PAGE','Memo',1),('fcf86780-d447-4a60-96b8-4f03c4eddc92','2024-01-26 10:07:04.713450','2024-01-26 10:07:04.713450','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','字典','/dict','a350090e-11ff-45d0-88ce-5c3c1b6f9ef4',0,'PAGE','Notebook',1);
/*!40000 ALTER TABLE `menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mystery_box`
--

DROP TABLE IF EXISTS `mystery_box`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mystery_box` (
  `id` varchar(32) NOT NULL,
  `created_time` datetime(6) NOT NULL,
  `edited_time` datetime(6) NOT NULL,
  `creator_id` varchar(32) NOT NULL,
  `editor_id` varchar(32) NOT NULL,
  `name` varchar(32) NOT NULL COMMENT '盲盒名字',
  `details` text NOT NULL COMMENT '盲盒详情',
  `tips` varchar(1000) NOT NULL COMMENT '购买提示',
  `price` decimal(10,2) NOT NULL COMMENT '价格',
  `cover` varchar(255) NOT NULL COMMENT '封面',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='盲盒';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mystery_box`
--

LOCK TABLES `mystery_box` WRITE;
/*!40000 ALTER TABLE `mystery_box` DISABLE KEYS */;
INSERT INTO `mystery_box` VALUES ('defb40ceb03740068907138adecaf1a3','2024-03-16 13:29:45.848413','2024-08-15 14:27:13.115265','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','VOCALOID 唱响未来之声！','33','21',2.00,'https://my-community.oss-cn-qingdao.aliyuncs.com/20240815142657c0e92113-3d4b-46a8-a69e-e5bc3bb4b9cd_28c16457add5ca49707fd437dd56c71c.png@344w_344h.png'),('ebf8f84d60814944a847e0de4bb1d8b4','2024-03-16 13:24:13.168023','2024-07-23 16:06:49.076538','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','测试','1231','231',0.00,'https://my-community.oss-cn-qingdao.aliyuncs.com/20240316132348logo.jpg');
/*!40000 ALTER TABLE `mystery_box` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mystery_box_order`
--

DROP TABLE IF EXISTS `mystery_box_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mystery_box_order` (
  `id` varchar(32) NOT NULL,
  `created_time` datetime(6) NOT NULL,
  `edited_time` datetime(6) NOT NULL,
  `creator_id` varchar(32) NOT NULL,
  `editor_id` varchar(32) NOT NULL,
  `status` varchar(32) NOT NULL COMMENT '订单状态',
  `base_order_id` varchar(32) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mystery_box_order`
--

LOCK TABLES `mystery_box_order` WRITE;
/*!40000 ALTER TABLE `mystery_box_order` DISABLE KEYS */;
INSERT INTO `mystery_box_order` VALUES ('121a008b0d6b4e52a1cd2e51a96a2cae','2024-03-18 14:19:52.961966','2024-03-18 14:19:52.961966','ad9bff77beb342d091897e69e0a707dd','ad9bff77beb342d091897e69e0a707dd','TO_BE_PAID','121a008b0d6b4e52a1cd2e51a96a2cae');
/*!40000 ALTER TABLE `mystery_box_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mystery_box_order_item`
--

DROP TABLE IF EXISTS `mystery_box_order_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mystery_box_order_item` (
  `id` varchar(32) NOT NULL,
  `created_time` datetime(6) NOT NULL,
  `edited_time` datetime(6) NOT NULL,
  `creator_id` varchar(32) NOT NULL,
  `editor_id` varchar(32) NOT NULL,
  `mystery_box_id` varchar(32) NOT NULL,
  `products` varchar(2000) DEFAULT NULL,
  `mystery_box_order_id` varchar(32) NOT NULL,
  `count` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mystery_box_order_item`
--

LOCK TABLES `mystery_box_order_item` WRITE;
/*!40000 ALTER TABLE `mystery_box_order_item` DISABLE KEYS */;
INSERT INTO `mystery_box_order_item` VALUES ('3261390002b34118a127fb9a62a89d1f','2024-03-18 14:19:52.975987','2024-03-18 14:19:52.975987','ad9bff77beb342d091897e69e0a707dd','ad9bff77beb342d091897e69e0a707dd','defb40ceb03740068907138adecaf1a3',NULL,'121a008b0d6b4e52a1cd2e51a96a2cae',1);
/*!40000 ALTER TABLE `mystery_box_order_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mystery_box_product_rel`
--

DROP TABLE IF EXISTS `mystery_box_product_rel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mystery_box_product_rel` (
  `id` varchar(32) NOT NULL,
  `created_time` datetime(6) NOT NULL,
  `edited_time` datetime(6) NOT NULL,
  `creator_id` varchar(32) NOT NULL,
  `editor_id` varchar(32) NOT NULL,
  `mystery_box_id` varchar(32) NOT NULL COMMENT '盲盒',
  `product_id` varchar(32) NOT NULL COMMENT '商品',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='盲盒-商品中间表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mystery_box_product_rel`
--

LOCK TABLES `mystery_box_product_rel` WRITE;
/*!40000 ALTER TABLE `mystery_box_product_rel` DISABLE KEYS */;
INSERT INTO `mystery_box_product_rel` VALUES ('0ef789e54b984696aabd3e1948e0c9f5','2024-08-15 14:25:41.372467','2024-08-15 14:25:41.372467','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','defb40ceb03740068907138adecaf1a3','34932f32cc274ab58b67c97b84b35ecd'),('492a08942df34b3cb73f07e72e5f3fce','2024-07-23 16:06:49.100769','2024-07-23 16:06:49.100769','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','ebf8f84d60814944a847e0de4bb1d8b4','049994406a6246ae8ec3a25b374fc824'),('53bb5ccb13134d43906f25ef4bb7312e','2024-07-23 17:01:30.964098','2024-07-23 17:01:30.964098','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','defb40ceb03740068907138adecaf1a3','b4baa49c9649405cac5af5a583d6c348'),('8ff3630070e54463888c026eeef7afa8','2024-07-23 16:06:49.114944','2024-07-23 16:06:49.114944','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','ebf8f84d60814944a847e0de4bb1d8b4','049994406a6246ae8ec3a25b374fc820'),('9152f1aa0f3145f5a7a4bba70f08167f','2024-07-23 16:06:49.081418','2024-07-23 16:06:49.081418','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','ebf8f84d60814944a847e0de4bb1d8b4','b4baa49c9649405cac5af5a583d6c348'),('abcc07f09c764a248e591aadeaea59b2','2024-07-23 16:06:49.110198','2024-07-23 16:06:49.110198','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','ebf8f84d60814944a847e0de4bb1d8b4','049994406a6246ae3ec3a25b374fc829'),('b0b7c13abc4146dd8e30dd0bc9e9738e','2024-07-23 16:06:49.091521','2024-07-23 16:06:49.091521','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','ebf8f84d60814944a847e0de4bb1d8b4','049994406a6246ae2ec3a25b374fc829'),('cc0e48b1778f4f39b2925d61ee7671c4','2024-07-23 16:06:49.086795','2024-07-23 16:06:49.086795','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','ebf8f84d60814944a847e0de4bb1d8b4','049994406a6246ae1ec3a25b374fc829'),('d0a9094b2c41434fa8843b7dfad224aa','2024-07-23 16:06:49.105476','2024-07-23 16:06:49.105476','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','ebf8f84d60814944a847e0de4bb1d8b4','049994406a6246ae8ez3a25b374fc829'),('e70339adfd98424ea11faa269262970c','2024-07-23 17:01:30.949941','2024-07-23 17:01:30.949941','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','defb40ceb03740068907138adecaf1a3','54a3eb1e81214bb88268c1e24004ee1c'),('e8d27d28e8ff4ce79efc26146f9deec3','2024-07-23 16:06:49.095786','2024-07-23 16:06:49.095786','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','ebf8f84d60814944a847e0de4bb1d8b4','049994406a6246ae8ec3a25b374fc822');
/*!40000 ALTER TABLE `mystery_box_product_rel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payment`
--

DROP TABLE IF EXISTS `payment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `payment` (
  `id` varchar(36) NOT NULL,
  `created_time` datetime(6) NOT NULL,
  `edited_time` datetime(6) NOT NULL,
  `creator_id` varchar(36) NOT NULL,
  `editor_id` varchar(36) NOT NULL,
  `pay_type` varchar(36) NOT NULL,
  `pay_time` datetime DEFAULT NULL,
  `pay_amount` decimal(10,2) NOT NULL,
  `vip_amount` decimal(10,2) NOT NULL,
  `coupon_amount` decimal(10,2) NOT NULL,
  `product_amount` decimal(10,2) NOT NULL,
  `delivery_fee` decimal(10,2) NOT NULL,
  `trade_no` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payment`
--

LOCK TABLES `payment` WRITE;
/*!40000 ALTER TABLE `payment` DISABLE KEYS */;
INSERT INTO `payment` VALUES ('033dfe7f52df4ee999b0a47bad69fa6f','2024-03-14 16:47:12.621484','2024-03-14 16:47:12.621484','ad9bff77beb342d091897e69e0a707dd','ad9bff77beb342d091897e69e0a707dd','WE_CHAT_PAY',NULL,7999.00,0.00,0.00,7999.00,0.00,NULL),('2c248326b84144deae48bb8a7e2969e5','2024-03-18 14:19:52.919817','2024-03-18 14:19:52.919817','ad9bff77beb342d091897e69e0a707dd','ad9bff77beb342d091897e69e0a707dd','WE_CHAT_PAY',NULL,2.00,0.00,0.00,2.00,0.00,NULL),('3173e4233450474c8ea5893c12153591','2024-03-14 16:19:52.053774','2024-03-14 16:19:52.297079','ad9bff77beb342d091897e69e0a707dd','ad9bff77beb342d091897e69e0a707dd','WE_CHAT_PAY',NULL,7999.00,0.00,0.00,7999.00,0.00,NULL),('39d6def553934e74b031afede0933c09','2024-03-14 17:43:14.202842','2024-03-14 17:43:14.202842','ad9bff77beb342d091897e69e0a707dd','ad9bff77beb342d091897e69e0a707dd','WE_CHAT_PAY',NULL,7999.00,0.00,0.00,7999.00,0.00,NULL),('5973c96d6bc944729a1bedd006be0077','2024-02-18 15:50:29.866406','2024-02-18 15:50:29.866406','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','WE_CHAT_PAY',NULL,0.01,0.00,0.00,0.01,0.00,NULL),('6fee967fe61a4729ac092ffcff669134','2024-03-14 16:43:43.333903','2024-03-14 16:43:43.646947','ad9bff77beb342d091897e69e0a707dd','ad9bff77beb342d091897e69e0a707dd','WE_CHAT_PAY',NULL,7999.00,0.00,0.00,7999.00,0.00,NULL),('748761fae8e343b484fd30addd6efdd7','2024-03-15 09:52:20.620995','2024-03-15 09:52:20.620995','ad9bff77beb342d091897e69e0a707dd','ad9bff77beb342d091897e69e0a707dd','WE_CHAT_PAY',NULL,15998.00,0.00,0.00,15998.00,0.00,NULL),('7898af0950d64d8488607485898402d5','2024-02-18 10:50:57.142714','2024-02-18 10:50:57.142714','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','WE_CHAT_PAY',NULL,0.01,0.00,0.00,0.01,0.00,NULL),('7c9d1a4d3fc44ba3b1aae5f74736f4e8','2024-03-15 08:36:20.024898','2024-03-15 08:36:20.024898','ad9bff77beb342d091897e69e0a707dd','ad9bff77beb342d091897e69e0a707dd','WE_CHAT_PAY',NULL,15998.00,0.00,0.00,15998.00,0.00,NULL),('870163c7a21549ba85440f0a86d1d613','2024-03-14 15:51:35.169442','2024-03-14 15:51:35.245116','c3a27b84683e4869a38d3c010a16386a','c3a27b84683e4869a38d3c010a16386a','WE_CHAT_PAY',NULL,7999.00,0.00,0.00,7999.00,0.00,NULL),('89d1146f6d7942ba9f1a026fb3638458','2024-02-07 09:05:54.098899','2024-02-07 09:05:54.098899','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','WE_CHAT_PAY',NULL,1.00,0.00,0.00,0.01,0.00,''),('8ef83397d635438c812e5cb350832d5b','2024-03-14 16:53:59.294872','2024-03-14 16:53:59.294872','ad9bff77beb342d091897e69e0a707dd','ad9bff77beb342d091897e69e0a707dd','WE_CHAT_PAY',NULL,7999.00,0.00,0.00,7999.00,0.00,NULL),('b4125638f7b94687970fa8db30f7522c','2024-03-14 16:40:48.209306','2024-03-14 16:40:48.493666','ad9bff77beb342d091897e69e0a707dd','ad9bff77beb342d091897e69e0a707dd','WE_CHAT_PAY',NULL,7999.00,0.00,0.00,7999.00,0.00,NULL),('b4611b841f7342b1870b986922e8db65','2024-03-01 17:46:21.456667','2024-03-01 17:46:21.456667','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','WE_CHAT_PAY',NULL,7994.00,0.00,5.00,7999.00,0.00,NULL),('b8b0f7e5262340a39707947c67bd605e','2024-03-01 15:54:31.704877','2024-03-01 15:54:31.704877','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','WE_CHAT_PAY',NULL,0.01,0.00,0.00,0.01,0.00,NULL),('bae0c241aba746ac91172f4126762452','2024-03-14 16:31:16.055426','2024-03-14 16:31:16.362303','ad9bff77beb342d091897e69e0a707dd','ad9bff77beb342d091897e69e0a707dd','WE_CHAT_PAY',NULL,7999.00,0.00,0.00,7999.00,0.00,NULL),('be277d8444dd4a668d19555f454a4874','2024-03-14 16:48:24.339420','2024-03-14 16:48:24.339420','ad9bff77beb342d091897e69e0a707dd','ad9bff77beb342d091897e69e0a707dd','WE_CHAT_PAY',NULL,7999.00,0.00,0.00,7999.00,0.00,NULL),('ce6261b000194d93810811e17a713192','2024-03-14 17:35:03.147106','2024-03-14 17:35:03.147106','ad9bff77beb342d091897e69e0a707dd','ad9bff77beb342d091897e69e0a707dd','WE_CHAT_PAY',NULL,7999.00,0.00,0.00,7999.00,0.00,NULL),('d385dfca7e8d4eddbcbe9ffeefc81440','2024-03-14 15:54:19.592794','2024-03-14 15:54:19.706314','ad9bff77beb342d091897e69e0a707dd','ad9bff77beb342d091897e69e0a707dd','WE_CHAT_PAY',NULL,7999.00,0.00,0.00,7999.00,0.00,NULL),('dbcf63c9d8a54637ade17a61f65a513a','2024-02-19 21:58:00.828088','2024-02-19 21:58:00.828088','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','WE_CHAT_PAY',NULL,0.01,0.00,0.00,0.01,0.00,NULL),('e05ba58f93f94542be7624c9471beb12','2024-03-14 16:34:35.179496','2024-03-14 16:34:35.396106','ad9bff77beb342d091897e69e0a707dd','ad9bff77beb342d091897e69e0a707dd','WE_CHAT_PAY',NULL,7999.00,0.00,0.00,7999.00,0.00,NULL),('e8a05ce442344a2a8f6af7f7fc06f8f3','2024-03-14 15:47:30.638465','2024-03-14 15:47:30.681062','c3a27b84683e4869a38d3c010a16386a','c3a27b84683e4869a38d3c010a16386a','WE_CHAT_PAY',NULL,7999.00,0.00,0.00,7999.00,0.00,NULL),('f35fe42738c74691bf9c7dcb5ff21cfc','2024-02-18 15:36:49.774651','2024-02-18 15:36:49.774651','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','WE_CHAT_PAY',NULL,0.01,0.00,0.00,0.01,0.00,NULL);
/*!40000 ALTER TABLE `payment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `id` varchar(36) NOT NULL,
  `created_time` datetime(6) NOT NULL,
  `edited_time` datetime(6) NOT NULL,
  `creator_id` varchar(36) NOT NULL,
  `editor_id` varchar(36) NOT NULL,
  `name` varchar(255) NOT NULL COMMENT '名称',
  `price` decimal(10,2) NOT NULL COMMENT '价格',
  `cover` varchar(255) NOT NULL COMMENT '封面',
  `brand` varchar(255) NOT NULL COMMENT '品牌',
  `category_id` varchar(36) NOT NULL COMMENT '类别id',
  `description` text NOT NULL COMMENT '描述',
  `tags` varchar(255) NOT NULL COMMENT '标签',
  `specifications` text NOT NULL COMMENT '规格',
  `attributes` text NOT NULL COMMENT '属性',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='商品表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES ('049994406a6246ae1ec3a25b374fc829','2024-01-22 16:25:52.694286','2024-01-22 16:25:52.694286','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','狼蛛（AULA） F99客制化机械键盘gasket结构全键热插拔有线蓝牙无线三模办公电竞游戏 星落凝云-收割者轴\n',259.00,'https://img13.360buyimg.com/n7/jfs/t1/249540/23/1636/148199/6593c2c1F5b7c6041/7c04e37a0da39862.jpg','狼蛛（AULA）','9eba1038-5f7d-4440-a1b2-c9bd91ea15b4','华为MateBook D 14 2024笔记本电脑 13代英特尔酷睿处理器/16:10护眼全面屏 i5 16G 512G 皓月银','[\"性能很好\",\"颜值高\"]','[{\"name\":\"屏幕比例\",\"values\":[\"16:10\"]},{\"name\":\"能效等级\",\"values\":[\"一级能效\"]},{\"name\":\"屏幕色域\",\"values\":[\"100%sRGB\"]}]','[{\"name\":\"颜色\",\"values\":[\"皓月银\",\"深空灰\"]},{\"name\":\"型号\",\"values\":[\"I5+16GB+512GB\",\"I7+16GB+1T\"]}]'),('049994406a6246ae2ec3a25b374fc829','2024-01-22 16:25:52.694286','2024-01-22 16:25:52.694286','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','狼蛛（AULA） F99客制化机械键盘gasket结构全键热插拔有线蓝牙无线三模办公电竞游戏 星落凝云-收割者轴\n',259.00,'https://img13.360buyimg.com/n7/jfs/t1/249540/23/1636/148199/6593c2c1F5b7c6041/7c04e37a0da39862.jpg','狼蛛（AULA）','9eba1038-5f7d-4440-a1b2-c9bd91ea15b4','华为MateBook D 14 2024笔记本电脑 13代英特尔酷睿处理器/16:10护眼全面屏 i5 16G 512G 皓月银','[\"性能很好\",\"颜值高\"]','[{\"name\":\"屏幕比例\",\"values\":[\"16:10\"]},{\"name\":\"能效等级\",\"values\":[\"一级能效\"]},{\"name\":\"屏幕色域\",\"values\":[\"100%sRGB\"]}]','[{\"name\":\"颜色\",\"values\":[\"皓月银\",\"深空灰\"]},{\"name\":\"型号\",\"values\":[\"I5+16GB+512GB\",\"I7+16GB+1T\"]}]'),('049994406a6246ae3ec3a25b374fc829','2024-01-19 14:31:27.312976','2024-01-20 14:49:48.728173','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','华为Mate 60 Pro',7999.00,'https://img12.360buyimg.com/n1/s450x450_jfs/t1/235988/15/12433/48078/65a8f4faF38ef4d31/fc8ca7c491173fe4.jpg','华为','a4518fb9-6879-44a5-8ad2-783867ba46d7','4060','[\"性能很好\",\"颜值高\"]','[{\"name\":\"充电功率\",\"values\":[\"80-119W\"]},{\"name\":\"质保期\",\"values\":[\"3年\"]}]','[{\"name\":\"颜色\",\"values\":[\"雅川青\",\"雅丹黑\"]},{\"name\":\"版本\",\"values\":[\"12gb+256gb\",\"12gb+512gb\"]}]'),('049994406a6246ae8ec3a25b374fc820','2024-01-19 14:31:27.312976','2024-01-20 14:49:48.728173','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','华为Mate 60 Pro',7999.00,'https://img12.360buyimg.com/n1/s450x450_jfs/t1/235988/15/12433/48078/65a8f4faF38ef4d31/fc8ca7c491173fe4.jpg','华为','a4518fb9-6879-44a5-8ad2-783867ba46d7','4060','[\"性能很好\",\"颜值高\"]','[{\"name\":\"充电功率\",\"values\":[\"80-119W\"]},{\"name\":\"质保期\",\"values\":[\"3年\"]}]','[{\"name\":\"颜色\",\"values\":[\"雅川青\",\"雅丹黑\"]},{\"name\":\"版本\",\"values\":[\"12gb+256gb\",\"12gb+512gb\"]}]'),('049994406a6246ae8ec3a25b374fc821','2024-01-19 14:31:27.312976','2024-01-20 14:49:48.728173','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','华为Mate 60 Pro',7999.00,'https://img12.360buyimg.com/n1/s450x450_jfs/t1/235988/15/12433/48078/65a8f4faF38ef4d31/fc8ca7c491173fe4.jpg','华为','a4518fb9-6879-44a5-8ad2-783867ba46d7','4060','[\"性能很好\",\"颜值高\"]','[{\"name\":\"充电功率\",\"values\":[\"80-119W\"]},{\"name\":\"质保期\",\"values\":[\"3年\"]}]','[{\"name\":\"颜色\",\"values\":[\"雅川青\",\"雅丹黑\"]},{\"name\":\"版本\",\"values\":[\"12gb+256gb\",\"12gb+512gb\"]}]'),('049994406a6246ae8ec3a25b374fc822','2024-01-22 16:25:52.694286','2024-01-20 14:49:48.728173','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','华为Mate 60 Pro',7999.00,'https://img12.360buyimg.com/n1/s450x450_jfs/t1/235988/15/12433/48078/65a8f4faF38ef4d31/fc8ca7c491173fe4.jpg','华为','a4518fb9-6879-44a5-8ad2-783867ba46d7','4060','[\"性能很好\",\"颜值高\"]','[{\"name\":\"充电功率\",\"values\":[\"80-119W\"]},{\"name\":\"质保期\",\"values\":[\"3年\"]}]','[{\"name\":\"颜色\",\"values\":[\"雅川青\",\"雅丹黑\"]},{\"name\":\"版本\",\"values\":[\"12gb+256gb\",\"12gb+512gb\"]}]'),('049994406a6246ae8ec3a25b374fc823','2024-01-19 14:31:27.312976','2024-01-22 16:25:52.694286','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','华为MateBook D 14 2024笔记本电脑 13代英特尔酷睿处理器/16:10护眼全面屏 i5 16G 512G 皓月银',4199.00,'https://img10.360buyimg.com/n1/jfs/t1/148460/39/40865/58528/65ad1436F567c3e26/9f121abdd7913e20.jpg','华为（HUAWEI）','9eba1038-5f7d-4440-a1b2-c9bd91ea15b4','华为MateBook D 14 2024笔记本电脑 13代英特尔酷睿处理器/16:10护眼全面屏 i5 16G 512G 皓月银','[\"性能很好\",\"颜值高\"]','[{\"name\":\"屏幕比例\",\"values\":[\"16:10\"]},{\"name\":\"能效等级\",\"values\":[\"一级能效\"]},{\"name\":\"屏幕色域\",\"values\":[\"100%sRGB\"]}]','[{\"name\":\"颜色\",\"values\":[\"皓月银\",\"深空灰\"]},{\"name\":\"型号\",\"values\":[\"I5+16GB+512GB\",\"I7+16GB+1T\"]}]'),('049994406a6246ae8ec3a25b374fc824','2024-01-22 16:25:52.694286','2024-01-22 16:25:52.694286','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','华为MateBook D 14 2024笔记本电脑 13代英特尔酷睿处理器/16:10护眼全面屏 i5 16G 512G 皓月银',4199.00,'https://img10.360buyimg.com/n1/jfs/t1/148460/39/40865/58528/65ad1436F567c3e26/9f121abdd7913e20.jpg','华为（HUAWEI）','9eba1038-5f7d-4440-a1b2-c9bd91ea15b4','华为MateBook D 14 2024笔记本电脑 13代英特尔酷睿处理器/16:10护眼全面屏 i5 16G 512G 皓月银','[\"性能很好\",\"颜值高\"]','[{\"name\":\"屏幕比例\",\"values\":[\"16:10\"]},{\"name\":\"能效等级\",\"values\":[\"一级能效\"]},{\"name\":\"屏幕色域\",\"values\":[\"100%sRGB\"]}]','[{\"name\":\"颜色\",\"values\":[\"皓月银\",\"深空灰\"]},{\"name\":\"型号\",\"values\":[\"I5+16GB+512GB\",\"I7+16GB+1T\"]}]'),('049994406a6246ae8ec3a25b374fc829','2024-01-19 14:31:27.312976','2024-01-20 14:49:48.728173','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','华为Mate 60 Pro',7999.00,'https://img12.360buyimg.com/n1/s450x450_jfs/t1/235988/15/12433/48078/65a8f4faF38ef4d31/fc8ca7c491173fe4.jpg','华为','a4518fb9-6879-44a5-8ad2-783867ba46d7','4060','[\"性能很好\",\"颜值高\"]','[{\"name\":\"充电功率\",\"values\":[\"80-119W\"]},{\"name\":\"质保期\",\"values\":[\"3年\"]}]','[{\"name\":\"颜色\",\"values\":[\"雅川青\",\"雅丹黑\"]},{\"name\":\"版本\",\"values\":[\"12gb+256gb\",\"12gb+512gb\"]}]'),('049994406a6246ae8ez3a25b374fc829','2024-01-22 16:25:52.694286','2024-01-22 16:25:52.694286','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','索尼（SONY）PS5 PlayStation5（轻薄版 1TB）数字版 国行PS5游戏机',2999.00,'https://img12.360buyimg.com/n7/jfs/t1/249378/1/3423/71589/65a788faF19f96be1/128abdbb059b0bd9.jpg','索尼（SONY）','9eba1038-5f7d-4440-a1b2-c9bd91ea15b4','华为MateBook D 14 2024笔记本电脑 13代英特尔酷睿处理器/16:10护眼全面屏 i5 16G 512G 皓月银','[\"性能很好\",\"颜值高\"]','[{\"name\":\"屏幕比例\",\"values\":[\"16:10\"]},{\"name\":\"能效等级\",\"values\":[\"一级能效\"]},{\"name\":\"屏幕色域\",\"values\":[\"100%sRGB\"]}]','[{\"name\":\"颜色\",\"values\":[\"皓月银\",\"深空灰\"]},{\"name\":\"型号\",\"values\":[\"I5+16GB+512GB\",\"I7+16GB+1T\"]}]'),('34932f32cc274ab58b67c97b84b35ecd','2024-03-16 15:30:47.519646','2024-08-15 14:26:40.457059','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','FEELALL 星空旋律 软胶挂件 叶月款',15.00,'https://my-community.oss-cn-qingdao.aliyuncs.com/2024081514263873c6fe31-4694-4625-93d9-72ec6abcd5f7_39ceda93f1b594aa6d81dffbefd8b486.png@240w_240h.webp','FEELALL','38dc139dffea4c1c893be26bd0f7f036','FEELALL 星空旋律 软胶挂件 叶月款 1','[\"普通款\"]','[{\"name\":\"材质\",\"values\":[\"PVC\"]},{\"name\":\"尺寸\",\"values\":[\"长39.7mm-40.8mm,宽37.5mm-51.2mm（不同款式有差异，请以实物为准）\"]},{\"name\":\"官方价\",\"values\":[\"15日元\"]}]','[]'),('54a3eb1e81214bb88268c1e24004ee1c','2024-03-16 15:27:59.250737','2024-08-15 14:26:23.261925','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','CRYPTON 初音未来 雪未来 毛绒玩偶',245.00,'https://my-community.oss-cn-qingdao.aliyuncs.com/20240815142620ae585aea-768f-4047-ae2e-24e5410c9598_7059967cf48438fa9035adc8795524b8.png@800w_800h.webp','CRYPTON','38dc139dffea4c1c893be26bd0f7f036','CRYPTON 初音未来 雪未来 毛绒玩偶','[\"隐藏款\"]','[{\"name\":\"面料\",\"values\":[\"棉\"]},{\"name\":\"填充物\",\"values\":[\"聚酯纤维\"]},{\"name\":\"尺寸\",\"values\":[\"长约23cm\"]},{\"name\":\"官方价\",\"values\":[\"4000日元\"]}]','[]'),('b4baa49c9649405cac5af5a583d6c348','2024-03-16 15:06:45.133626','2024-03-16 15:59:38.219657','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','SSF 初音未来 RAGE 世界计划 2020Ver. 手办               ',2220.00,'http://localhost/static/c92c9fa5-cfcb-4d9b-9f79-513d0e842237_105b1c14c3092904b9713c01a626b1c1.png@800w_800h.webp','SSF','38dc139dffea4c1c893be26bd0f7f036','无','[\"超神款\"]','[{\"name\":\"尺寸\",\"values\":[\"高约305mm/宽约311mm/深约239mm\"]},{\"name\":\"比例\",\"values\":[\"1/7\"]},{\"name\":\"材质\",\"values\":[\"PVC\"]},{\"name\":\"原型制作\",\"values\":[\"Design COCO（Art Director：CHIGA）\"]},{\"name\":\"画师\",\"values\":[\"村上ゆいち\"]}]','[]');
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_category`
--

DROP TABLE IF EXISTS `product_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_category` (
  `id` varchar(36) NOT NULL,
  `created_time` datetime(6) NOT NULL,
  `edited_time` datetime(6) NOT NULL,
  `creator_id` varchar(36) NOT NULL,
  `editor_id` varchar(36) NOT NULL,
  `name` varchar(50) NOT NULL COMMENT '类别名称',
  `parent_id` varchar(36) DEFAULT NULL COMMENT '父亲类别id',
  `icon` varchar(100) DEFAULT NULL COMMENT '类别图标',
  `description` text NOT NULL COMMENT '描述',
  `sort_order` int NOT NULL COMMENT '排序号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='商品类别';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_category`
--

LOCK TABLES `product_category` WRITE;
/*!40000 ALTER TABLE `product_category` DISABLE KEYS */;
INSERT INTO `product_category` VALUES ('38dc139dffea4c1c893be26bd0f7f036','2024-03-16 15:08:36.842972','2024-07-22 15:12:10.605873','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','手办','a4518fb9-6879-44a5-8ad2-783867ba46d7','https://my-community.oss-cn-qingdao.aliyuncs.com/20240722151201起凡.jpg','手办',1),('51eac0d9-a653-4edb-b156-5a7d96648196','2024-01-18 14:40:58.923272','2024-01-18 14:40:58.923272','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','显卡','9eba1038-5f7d-4440-a1b2-c9bd91ea15b4','','显卡',1),('9eba1038-5f7d-4440-a1b2-c9bd91ea15b4','2024-01-18 14:39:20.619126','2024-01-18 14:39:20.619126','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','电脑',NULL,'','电脑',0),('a4518fb9-6879-44a5-8ad2-783867ba46d7','2024-01-18 14:40:06.789083','2024-01-18 14:40:06.789083','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','内存条','9eba1038-5f7d-4440-a1b2-c9bd91ea15b4','','内存条',1);
/*!40000 ALTER TABLE `product_category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_order`
--

DROP TABLE IF EXISTS `product_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_order` (
  `id` varchar(36) NOT NULL,
  `created_time` datetime(6) NOT NULL,
  `edited_time` datetime(6) NOT NULL,
  `creator_id` varchar(36) NOT NULL,
  `editor_id` varchar(36) NOT NULL,
  `payment_id` varchar(36) NOT NULL,
  `address_id` varchar(36) NOT NULL,
  `status` varchar(36) NOT NULL,
  `remark` text,
  `tracking_number` varchar(50) DEFAULT NULL,
  `coupon_user_id` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_order`
--

LOCK TABLES `product_order` WRITE;
/*!40000 ALTER TABLE `product_order` DISABLE KEYS */;
INSERT INTO `product_order` VALUES ('00bd4d25b25e4e33b69838a6557e9c12','2024-03-14 16:40:48.225302','2024-03-14 16:40:48.496848','ad9bff77beb342d091897e69e0a707dd','ad9bff77beb342d091897e69e0a707dd','b4125638f7b94687970fa8db30f7522c','916dd3b50a1c49349d53a1cd18ddcf41','TO_BE_PAID','',NULL,NULL),('00ec58ac17214916bde3816ab46d53d3','2024-03-14 15:47:30.642474','2024-03-14 15:47:30.684063','c3a27b84683e4869a38d3c010a16386a','c3a27b84683e4869a38d3c010a16386a','e8a05ce442344a2a8f6af7f7fc06f8f3','a20c60c140dd4ac3b5bf29be6aba954d','TO_BE_PAID','',NULL,NULL),('0565260b12fb40418c472761f997d833','2024-02-07 09:05:54.114409','2024-02-20 16:03:24.468172','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','89d1146f6d7942ba9f1a026fb3638458','ef42e042-b54b-4888-aad4-79905f2e49d7','TO_BE_RECEIVED','','YT7442137959572',NULL),('0a6ce9cca8ff4c7a8b3cc1b9530461ea','2024-03-14 16:34:35.190494','2024-03-14 16:34:35.405106','ad9bff77beb342d091897e69e0a707dd','ad9bff77beb342d091897e69e0a707dd','e05ba58f93f94542be7624c9471beb12','916dd3b50a1c49349d53a1cd18ddcf41','TO_BE_PAID','',NULL,NULL),('0cd0b367ad4a47e791f7a9e3a1aaadd1','2024-03-14 16:53:59.314140','2024-03-14 16:53:59.314140','ad9bff77beb342d091897e69e0a707dd','ad9bff77beb342d091897e69e0a707dd','8ef83397d635438c812e5cb350832d5b','916dd3b50a1c49349d53a1cd18ddcf41','TO_BE_PAID','',NULL,NULL),('1a867a0724d549038900ad97e192a468','2024-02-18 15:36:49.787650','2024-02-20 16:13:39.765585','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','f35fe42738c74691bf9c7dcb5ff21cfc','ef42e042-b54b-4888-aad4-79905f2e49d7','TO_BE_RECEIVED','','YT7442137959572',NULL),('38fd7f354aa44c9d86d2399193364dd8','2024-03-14 16:19:52.065951','2024-03-14 16:19:52.302081','ad9bff77beb342d091897e69e0a707dd','ad9bff77beb342d091897e69e0a707dd','3173e4233450474c8ea5893c12153591','916dd3b50a1c49349d53a1cd18ddcf41','TO_BE_PAID','',NULL,NULL),('3d7dbeaccbed452f92f353abbd0bb2fe','2024-03-15 09:52:20.634505','2024-03-15 09:52:20.634505','ad9bff77beb342d091897e69e0a707dd','ad9bff77beb342d091897e69e0a707dd','748761fae8e343b484fd30addd6efdd7','916dd3b50a1c49349d53a1cd18ddcf41','TO_BE_PAID','',NULL,NULL),('48e26936a0514ff691164f6b608e8fa8','2024-03-14 17:43:14.220363','2024-03-14 17:43:14.220363','ad9bff77beb342d091897e69e0a707dd','ad9bff77beb342d091897e69e0a707dd','39d6def553934e74b031afede0933c09','916dd3b50a1c49349d53a1cd18ddcf41','TO_BE_PAID','',NULL,NULL),('4cb9020163b841fca1691862ff575bf6','2024-03-14 16:47:12.635054','2024-03-14 16:47:12.635054','ad9bff77beb342d091897e69e0a707dd','ad9bff77beb342d091897e69e0a707dd','033dfe7f52df4ee999b0a47bad69fa6f','916dd3b50a1c49349d53a1cd18ddcf41','TO_BE_PAID','',NULL,NULL),('6f7bfba6062c4154920b8f8a57c3629b','2024-03-14 17:35:03.160104','2024-03-14 17:35:03.160104','ad9bff77beb342d091897e69e0a707dd','ad9bff77beb342d091897e69e0a707dd','ce6261b000194d93810811e17a713192','916dd3b50a1c49349d53a1cd18ddcf41','TO_BE_PAID','',NULL,NULL),('71e4132bc6304e2b8b3e0e5da0098ff8','2024-03-14 15:54:19.597794','2024-03-14 15:54:19.710581','ad9bff77beb342d091897e69e0a707dd','ad9bff77beb342d091897e69e0a707dd','d385dfca7e8d4eddbcbe9ffeefc81440','916dd3b50a1c49349d53a1cd18ddcf41','TO_BE_PAID','',NULL,NULL),('80b4f0c9d760406c9f9ed7924f1d4c14','2024-02-18 15:50:29.880407','2024-02-18 15:50:29.880407','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','5973c96d6bc944729a1bedd006be0077','ef42e042-b54b-4888-aad4-79905f2e49d7','TO_BE_PAID','',NULL,NULL),('87acf07b3ada48629d4089995a84ad06','2024-03-14 16:31:16.073088','2024-03-14 16:31:16.367872','ad9bff77beb342d091897e69e0a707dd','ad9bff77beb342d091897e69e0a707dd','bae0c241aba746ac91172f4126762452','916dd3b50a1c49349d53a1cd18ddcf41','TO_BE_PAID','',NULL,NULL),('9723bc129b0f406794d0ae6189ba2057','2024-02-18 10:50:57.157233','2024-02-18 15:33:00.603908','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','7898af0950d64d8488607485898402d5','ef42e042-b54b-4888-aad4-79905f2e49d7','CLOSED','',NULL,NULL),('9f9f487cec4b401cb89a769afdcc4049','2024-02-19 21:58:00.842349','2024-02-19 21:58:00.842349','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','dbcf63c9d8a54637ade17a61f65a513a','ef42e042-b54b-4888-aad4-79905f2e49d7','TO_BE_PAID','',NULL,NULL),('bc1c1e9380c14918bc0a2dec28525be9','2024-03-01 17:46:21.483693','2024-03-01 17:46:21.483693','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','b4611b841f7342b1870b986922e8db65','ef42e042-b54b-4888-aad4-79905f2e49d7','TO_BE_PAID','',NULL,'3076bbf8182248dfb550dcc2771000bf'),('cadaa29ba5774b859a05089ac74a64ce','2024-03-14 16:48:24.353422','2024-03-14 16:48:24.353422','ad9bff77beb342d091897e69e0a707dd','ad9bff77beb342d091897e69e0a707dd','be277d8444dd4a668d19555f454a4874','916dd3b50a1c49349d53a1cd18ddcf41','TO_BE_PAID','',NULL,NULL),('d90b67bc8a8c42dcb75610b7a2a30194','2024-03-15 08:36:20.074439','2024-03-15 08:36:20.074439','ad9bff77beb342d091897e69e0a707dd','ad9bff77beb342d091897e69e0a707dd','7c9d1a4d3fc44ba3b1aae5f74736f4e8','916dd3b50a1c49349d53a1cd18ddcf41','TO_BE_PAID','',NULL,NULL),('f3786417ead44ea9b80c2c8f7c1bb380','2024-03-01 15:54:31.715485','2024-03-01 15:54:31.715485','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','b8b0f7e5262340a39707947c67bd605e','ef42e042-b54b-4888-aad4-79905f2e49d7','TO_BE_PAID','',NULL,NULL),('f543cc020d444ccfbf234122d866e02c','2024-03-14 15:51:35.175481','2024-03-14 15:51:35.248123','c3a27b84683e4869a38d3c010a16386a','c3a27b84683e4869a38d3c010a16386a','870163c7a21549ba85440f0a86d1d613','a20c60c140dd4ac3b5bf29be6aba954d','TO_BE_PAID','',NULL,NULL),('fcca95920ee74743a6e887e7b8df9712','2024-03-14 16:43:43.351121','2024-03-14 16:43:43.650949','ad9bff77beb342d091897e69e0a707dd','ad9bff77beb342d091897e69e0a707dd','6fee967fe61a4729ac092ffcff669134','916dd3b50a1c49349d53a1cd18ddcf41','TO_BE_PAID','',NULL,NULL);
/*!40000 ALTER TABLE `product_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_order_item`
--

DROP TABLE IF EXISTS `product_order_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_order_item` (
  `id` varchar(36) NOT NULL,
  `created_time` datetime(6) NOT NULL,
  `edited_time` datetime(6) NOT NULL,
  `creator_id` varchar(36) NOT NULL,
  `editor_id` varchar(36) NOT NULL,
  `product_order_id` varchar(36) NOT NULL,
  `product_sku_id` varchar(36) NOT NULL,
  `count` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_order_item`
--

LOCK TABLES `product_order_item` WRITE;
/*!40000 ALTER TABLE `product_order_item` DISABLE KEYS */;
INSERT INTO `product_order_item` VALUES ('11c57f674593409b875a580f05eb9031','2024-03-01 17:46:21.522916','2024-03-01 17:46:21.522916','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','bc1c1e9380c14918bc0a2dec28525be9','8fbe2304-26da-4b18-9654-517ee3d0aa4e',1),('2ee9b05f9bd14213a8cbd09a6b8db8cd','2024-03-14 16:40:48.244946','2024-03-14 16:40:48.500582','ad9bff77beb342d091897e69e0a707dd','ad9bff77beb342d091897e69e0a707dd','00bd4d25b25e4e33b69838a6557e9c12','8fbe2304-26da-4b18-9654-517ee3d0aa4e',1),('30f05cc48b7c44dfaadaee7836c0da3c','2024-02-07 09:05:54.124921','2024-02-18 09:58:50.390114','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0565260b12fb40418c472761f997d833','aa8c50ad-7ea7-407f-adbb-d84daa9aa66b',1),('33767114a4964544be06a99c56e0ce3d','2024-02-18 15:36:49.797649','2024-02-18 15:49:42.199027','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','1a867a0724d549038900ad97e192a468','8fbe2304-26da-4b18-9654-517ee3d0aa4e',1),('35fb2aacde624ac696948a66dfeb863a','2024-03-14 15:54:19.601794','2024-03-14 15:54:19.715001','ad9bff77beb342d091897e69e0a707dd','ad9bff77beb342d091897e69e0a707dd','71e4132bc6304e2b8b3e0e5da0098ff8','8fbe2304-26da-4b18-9654-517ee3d0aa4e',1),('40271bb7a3f8425fb25228799908e35e','2024-03-01 15:54:31.722130','2024-03-01 15:54:31.722130','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','f3786417ead44ea9b80c2c8f7c1bb380','8fbe2304-26da-4b18-9654-517ee3d0aa4e',1),('49521f96d0714cea80093205cda3a100','2024-03-15 09:52:20.646510','2024-03-15 09:52:20.646510','ad9bff77beb342d091897e69e0a707dd','ad9bff77beb342d091897e69e0a707dd','3d7dbeaccbed452f92f353abbd0bb2fe','8fbe2304-26da-4b18-9654-517ee3d0aa4e',2),('4bb1d52b999b4fa9856bc6f4775ea68e','2024-03-14 16:19:52.076464','2024-03-14 16:19:52.310079','ad9bff77beb342d091897e69e0a707dd','ad9bff77beb342d091897e69e0a707dd','38fd7f354aa44c9d86d2399193364dd8','8fbe2304-26da-4b18-9654-517ee3d0aa4e',1),('4d1e842a9c74432490bd7c77cae40808','2024-03-14 15:47:30.648897','2024-03-14 15:47:30.687430','c3a27b84683e4869a38d3c010a16386a','c3a27b84683e4869a38d3c010a16386a','00ec58ac17214916bde3816ab46d53d3','8fbe2304-26da-4b18-9654-517ee3d0aa4e',1),('53aac8ee204a42f88227c2a0f66c2000','2024-03-14 17:35:03.174132','2024-03-14 17:35:03.174132','ad9bff77beb342d091897e69e0a707dd','ad9bff77beb342d091897e69e0a707dd','6f7bfba6062c4154920b8f8a57c3629b','8fbe2304-26da-4b18-9654-517ee3d0aa4e',1),('54b8ab2ba4264661a6bb51e8ccf9e160','2024-03-14 17:43:14.233878','2024-03-14 17:43:14.233878','ad9bff77beb342d091897e69e0a707dd','ad9bff77beb342d091897e69e0a707dd','48e26936a0514ff691164f6b608e8fa8','8fbe2304-26da-4b18-9654-517ee3d0aa4e',1),('5c0ca09e6ea94cfaaebfa49a2e838f75','2024-03-14 16:31:16.085583','2024-03-14 16:31:16.371821','ad9bff77beb342d091897e69e0a707dd','ad9bff77beb342d091897e69e0a707dd','87acf07b3ada48629d4089995a84ad06','8fbe2304-26da-4b18-9654-517ee3d0aa4e',1),('63fed7f01bec449ca85acd0d36d1151e','2024-02-19 21:58:00.852825','2024-02-19 21:58:00.852825','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','9f9f487cec4b401cb89a769afdcc4049','8fbe2304-26da-4b18-9654-517ee3d0aa4e',1),('849997c896914e4bb5fc448c5c7c0ce6','2024-02-18 15:50:29.893409','2024-02-18 15:50:29.893409','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','80b4f0c9d760406c9f9ed7924f1d4c14','8fbe2304-26da-4b18-9654-517ee3d0aa4e',1),('91169746b22041b8bc8e0e9d8adbd09d','2024-02-07 09:05:54.131434','2024-02-18 09:58:50.397115','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0565260b12fb40418c472761f997d833','8fbe2304-26da-4b18-9654-517ee3d0aa4e',1),('93f4c16fe79e4d34ad80d2341966a243','2024-03-14 16:43:43.369162','2024-03-14 16:43:43.654477','ad9bff77beb342d091897e69e0a707dd','ad9bff77beb342d091897e69e0a707dd','fcca95920ee74743a6e887e7b8df9712','8fbe2304-26da-4b18-9654-517ee3d0aa4e',1),('a45435fe271b463199cd39432a9cdb51','2024-03-14 16:53:59.331164','2024-03-14 16:53:59.331164','ad9bff77beb342d091897e69e0a707dd','ad9bff77beb342d091897e69e0a707dd','0cd0b367ad4a47e791f7a9e3a1aaadd1','8fbe2304-26da-4b18-9654-517ee3d0aa4e',1),('be1db56a97be481da4e16e7d4497b2a4','2024-03-14 16:47:12.648057','2024-03-14 16:47:12.648057','ad9bff77beb342d091897e69e0a707dd','ad9bff77beb342d091897e69e0a707dd','4cb9020163b841fca1691862ff575bf6','8fbe2304-26da-4b18-9654-517ee3d0aa4e',1),('c2aea72dcb9646949f69d6e7760e0f82','2024-03-14 16:34:35.198497','2024-03-14 16:34:35.413624','ad9bff77beb342d091897e69e0a707dd','ad9bff77beb342d091897e69e0a707dd','0a6ce9cca8ff4c7a8b3cc1b9530461ea','8fbe2304-26da-4b18-9654-517ee3d0aa4e',1),('cec0332da35540a98cfcfa51e3893bc6','2024-03-15 08:36:20.101954','2024-03-15 08:36:20.101954','ad9bff77beb342d091897e69e0a707dd','ad9bff77beb342d091897e69e0a707dd','d90b67bc8a8c42dcb75610b7a2a30194','8fbe2304-26da-4b18-9654-517ee3d0aa4e',2),('e558d28d11694a629e83dbc01cc64327','2024-02-18 10:50:57.167740','2024-02-18 15:33:00.615428','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','9723bc129b0f406794d0ae6189ba2057','8fbe2304-26da-4b18-9654-517ee3d0aa4e',1),('eb708793113c48c08f0ebd1906944cb8','2024-03-14 15:51:35.179479','2024-03-14 15:51:35.251124','c3a27b84683e4869a38d3c010a16386a','c3a27b84683e4869a38d3c010a16386a','f543cc020d444ccfbf234122d866e02c','8fbe2304-26da-4b18-9654-517ee3d0aa4e',1),('ec5c5c78b6594bfc96693c673acb0257','2024-03-14 16:48:24.365937','2024-03-14 16:48:24.365937','ad9bff77beb342d091897e69e0a707dd','ad9bff77beb342d091897e69e0a707dd','cadaa29ba5774b859a05089ac74a64ce','8fbe2304-26da-4b18-9654-517ee3d0aa4e',1);
/*!40000 ALTER TABLE `product_order_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_sku`
--

DROP TABLE IF EXISTS `product_sku`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_sku` (
  `id` varchar(36) NOT NULL,
  `created_time` datetime(6) NOT NULL,
  `edited_time` datetime(6) NOT NULL,
  `creator_id` varchar(36) NOT NULL,
  `editor_id` varchar(36) NOT NULL,
  `values` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `product_id` varchar(255) NOT NULL,
  `cover` varchar(255) DEFAULT NULL,
  `price` decimal(38,2) DEFAULT NULL,
  `stock` int DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_sku`
--

LOCK TABLES `product_sku` WRITE;
/*!40000 ALTER TABLE `product_sku` DISABLE KEYS */;
INSERT INTO `product_sku` VALUES ('14b496e7-9538-4428-84ae-3f64e55bab0e','2024-01-24 09:41:39.266530','2024-01-24 09:41:39.266530','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','[\"皓月银\",\"I5+16GB+512GB\"]','华为MateBook D 14 2024笔记本电脑 13代英特尔酷睿处理器/16:10护眼全面屏 i5 16G 512G 皓月银','fcd61ee7-ff1f-448c-8131-05c6dfea64b8','https://img10.360buyimg.com/n1/jfs/t1/148460/39/40865/58528/65ad1436F567c3e26/9f121abdd7913e20.jpg',4199.00,13,'华为MateBook D 14 2024笔记本电脑 13代英特尔酷睿处理器/16:10护眼全面屏 i5 16G 512G 皓月银'),('25bf6840-482e-4808-988f-7acec21703ea','2024-01-24 09:41:59.466059','2024-01-24 09:41:59.466059','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','[\"皓月银\",\"I5+16GB+512GB\"]','华为MateBook D 14 2024笔记本电脑 13代英特尔酷睿处理器/16:10护眼全面屏 i5 16G 512G 皓月银','fcd61ee7-ff1f-448c-1131-05c6dfea64b7','https://img10.360buyimg.com/n1/jfs/t1/148460/39/40865/58528/65ad1436F567c3e26/9f121abdd7913e20.jpg',4199.00,4,'华为MateBook D 14 2024笔记本电脑 13代英特尔酷睿处理器/16:10护眼全面屏 i5 16G 512G 皓月银'),('4e24ecdd-cd7c-4b31-83ef-a7fda9a699a4','2024-01-24 09:41:39.351579','2024-01-24 09:41:39.351579','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','[\"深空灰\",\"I7+16GB+1T\"]','华为MateBook D 14 2024笔记本电脑 13代英特尔酷睿处理器/16:10护眼全面屏 i5 16G 512G 皓月银','fcd61ee7-ff1f-448c-8131-05c6dfea64b8','https://img10.360buyimg.com/n1/jfs/t1/148460/39/40865/58528/65ad1436F567c3e26/9f121abdd7913e20.jpg',4199.00,13,'华为MateBook D 14 2024笔记本电脑 13代英特尔酷睿处理器/16:10护眼全面屏 i5 16G 512G 皓月银'),('6dfbceeb-9a6b-4902-a896-be75b5238019','2024-01-23 09:10:57.906429','2024-01-23 09:13:57.814821','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','[\"深空灰\",\"I7+16GB+1T\"]','索尼（SONY）PS5 PlayStation5（轻薄版 1TB）数字版 国行PS5游戏机','fcd61ee7-ff1f-448c-8131-05c6dfea14b8','https://img12.360buyimg.com/n7/jfs/t1/249378/1/3423/71589/65a788faF19f96be1/128abdbb059b0bd9.jpg',3002.00,0,'华为MateBook D 14 2024笔记本电脑 13代英特尔酷睿处理器/16:10护眼全面屏 i5 16G 512G 皓月银'),('746aa96e-3873-4165-9fba-798274c76080','2024-01-19 15:01:05.412403','2024-01-19 15:51:59.842536','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','[\"雅丹黑\",\"12gb+512gb\"]','华为Mate 60 Pro','04999440-6a62-46ae-8ec3-a25b374fc821','https://img12.360buyimg.com/n1/s450x450_jfs/t1/235988/15/12433/48078/65a8f4faF38ef4d31/fc8ca7c491173fe4.jpg',7999.00,1000,'40602'),('8fbe2304-26da-4b18-9654-517ee3d0aa4e','2024-01-19 15:01:05.387563','2024-02-18 15:49:42.195844','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','[\"雅川青\",\"12gb+256gb\"]','华为Mate 60 Pro','04999440-6a62-46ae-8ec3-a25b374fc821','https://img12.360buyimg.com/n1/s450x450_jfs/t1/235988/15/12433/48078/65a8f4faF38ef4d31/fc8ca7c491173fe4.jpg',7999.00,979,'4060'),('95f86932-de63-465b-b8d4-3c67f08ea94a','2024-01-23 09:10:57.896919','2024-01-23 09:14:04.021355','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','[\"深空灰\",\"I5+16GB+512GB\"]','索尼（SONY）PS5 PlayStation5（轻薄版 1TB）数字版 国行PS5游戏机','fcd61ee7-ff1f-448c-8131-05c6dfea14b8','https://img12.360buyimg.com/n7/jfs/t1/249378/1/3423/71589/65a788faF19f96be1/128abdbb059b0bd9.jpg',4500.00,0,'华为MateBook D 14 2024笔记本电脑 13代英特尔酷睿处理器/16:10护眼全面屏 i5 16G 512G 皓月银'),('9fd6c8e4-bcb2-4ac6-93b4-23feaeb802cc','2024-01-24 09:41:59.487426','2024-01-24 09:41:59.487426','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','[\"深空灰\",\"I5+16GB+512GB\"]','华为MateBook D 14 2024笔记本电脑 13代英特尔酷睿处理器/16:10护眼全面屏 i5 16G 512G 皓月银','fcd61ee7-ff1f-448c-1131-05c6dfea64b7','https://img10.360buyimg.com/n1/jfs/t1/148460/39/40865/58528/65ad1436F567c3e26/9f121abdd7913e20.jpg',4199.00,4,'华为MateBook D 14 2024笔记本电脑 13代英特尔酷睿处理器/16:10护眼全面屏 i5 16G 512G 皓月银'),('aa8c50ad-7ea7-407f-adbb-d84daa9aa66b','2024-01-23 09:10:57.867081','2024-02-18 09:58:50.386106','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','[\"皓月银\",\"I5+16GB+512GB\"]','索尼（SONY）PS5 PlayStation5（轻薄版 1TB）数字版 国行PS5游戏机','fcd61ee7-ff1f-448c-8131-05c6dfea14b8','https://img12.360buyimg.com/n7/jfs/t1/249378/1/3423/71589/65a788faF19f96be1/128abdbb059b0bd9.jpg',2999.00,0,'华为MateBook D 14 2024笔记本电脑 13代英特尔酷睿处理器/16:10护眼全面屏 i5 16G 512G 皓月银'),('b51e1a2a-54f3-4d55-95a1-1ef5693519a3','2024-01-23 09:10:57.885625','2024-01-23 09:14:10.238193','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','[\"皓月银\",\"I7+16GB+1T\"]','索尼（SONY）PS5 PlayStation5（轻薄版 1TB）数字版 国行PS5游戏机','fcd61ee7-ff1f-448c-8131-05c6dfea14b8','https://img12.360buyimg.com/n7/jfs/t1/249378/1/3423/71589/65a788faF19f96be1/128abdbb059b0bd9.jpg',23333.00,0,'华为MateBook D 14 2024笔记本电脑 13代英特尔酷睿处理器/16:10护眼全面屏 i5 16G 512G 皓月银'),('b54cc44c-adfe-4d75-ae06-6d778c2d876e','2024-01-24 09:41:39.332214','2024-01-24 09:41:39.332214','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','[\"皓月银\",\"I7+16GB+1T\"]','华为MateBook D 14 2024笔记本电脑 13代英特尔酷睿处理器/16:10护眼全面屏 i5 16G 512G 皓月银','fcd61ee7-ff1f-448c-8131-05c6dfea64b8','https://img10.360buyimg.com/n1/jfs/t1/148460/39/40865/58528/65ad1436F567c3e26/9f121abdd7913e20.jpg',4199.00,13,'华为MateBook D 14 2024笔记本电脑 13代英特尔酷睿处理器/16:10护眼全面屏 i5 16G 512G 皓月银'),('d8446ec8-832b-467d-9a8d-3c08a9e4be4c','2024-01-24 09:41:59.496656','2024-01-24 09:41:59.496656','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','[\"深空灰\",\"I7+16GB+1T\"]','华为MateBook D 14 2024笔记本电脑 13代英特尔酷睿处理器/16:10护眼全面屏 i5 16G 512G 皓月银','fcd61ee7-ff1f-448c-1131-05c6dfea64b7','https://img10.360buyimg.com/n1/jfs/t1/148460/39/40865/58528/65ad1436F567c3e26/9f121abdd7913e20.jpg',4199.00,4,'华为MateBook D 14 2024笔记本电脑 13代英特尔酷睿处理器/16:10护眼全面屏 i5 16G 512G 皓月银'),('df62bb69-c79e-448e-bfbb-415b9ead9c01','2024-01-19 15:01:05.396403','2024-01-19 15:01:05.396403','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','[\"雅川青\",\"12gb+512gb\"]','华为Mate 60 Pro','04999440-6a62-46ae-8ec3-a25b374fc821','https://img12.360buyimg.com/n1/s450x450_jfs/t1/235988/15/12433/48078/65a8f4faF38ef4d31/fc8ca7c491173fe4.jpg',7999.00,1000,'4060'),('f2f212ac-c3ab-4d1c-97d2-aab0e5a11315','2024-01-19 15:01:05.404406','2024-01-19 15:01:05.404406','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','[\"雅丹黑\",\"12gb+256gb\"]','华为Mate 60 Pro','04999440-6a62-46ae-8ec3-a25b374fc821','https://img12.360buyimg.com/n1/s450x450_jfs/t1/235988/15/12433/48078/65a8f4faF38ef4d31/fc8ca7c491173fe4.jpg',7999.00,1000,'4060'),('f9ca2d0d-870a-49da-a765-5d0f2dc366cc','2024-01-24 09:41:59.475569','2024-01-24 09:41:59.475569','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','[\"皓月银\",\"I7+16GB+1T\"]','华为MateBook D 14 2024笔记本电脑 13代英特尔酷睿处理器/16:10护眼全面屏 i5 16G 512G 皓月银','fcd61ee7-ff1f-448c-1131-05c6dfea64b7','https://img10.360buyimg.com/n1/jfs/t1/148460/39/40865/58528/65ad1436F567c3e26/9f121abdd7913e20.jpg',4199.00,4,'华为MateBook D 14 2024笔记本电脑 13代英特尔酷睿处理器/16:10护眼全面屏 i5 16G 512G 皓月银'),('fbcb1044-e2f5-4e79-ba28-58fedefa4270','2024-01-24 09:41:39.342415','2024-01-24 09:41:39.342415','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','[\"深空灰\",\"I5+16GB+512GB\"]','华为MateBook D 14 2024笔记本电脑 13代英特尔酷睿处理器/16:10护眼全面屏 i5 16G 512G 皓月银','fcd61ee7-ff1f-448c-8131-05c6dfea64b8','https://img10.360buyimg.com/n1/jfs/t1/148460/39/40865/58528/65ad1436F567c3e26/9f121abdd7913e20.jpg',4199.00,13,'华为MateBook D 14 2024笔记本电脑 13代英特尔酷睿处理器/16:10护眼全面屏 i5 16G 512G 皓月银');
/*!40000 ALTER TABLE `product_sku` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `id` varchar(36) NOT NULL,
  `created_time` datetime(6) NOT NULL,
  `edited_time` datetime(6) NOT NULL,
  `creator_id` varchar(36) NOT NULL,
  `editor_id` varchar(36) NOT NULL,
  `name` varchar(36) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `role_pk` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES ('4a83f185-30bb-4464-9e68-239698e89a5e','2024-01-15 14:43:26.118566','2024-01-18 20:52:02.010268','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','普通用户'),('5f785900-d317-4210-979d-d17a40ba8ecc','2024-01-15 14:22:57.274513','2024-08-15 14:32:39.908909','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','管理员'),('da8163bd-67c0-4263-b075-e39d7e595ca9','2024-01-16 15:51:42.833408','2024-01-16 20:52:07.588413','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','测试23');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role_menu_rel`
--

DROP TABLE IF EXISTS `role_menu_rel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role_menu_rel` (
  `id` varchar(36) NOT NULL,
  `created_time` datetime(6) NOT NULL,
  `edited_time` datetime(6) NOT NULL,
  `creator_id` varchar(36) NOT NULL,
  `editor_id` varchar(36) NOT NULL,
  `role_id` varchar(36) NOT NULL,
  `menu_id` varchar(36) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `role_id` (`role_id`,`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role_menu_rel`
--

LOCK TABLES `role_menu_rel` WRITE;
/*!40000 ALTER TABLE `role_menu_rel` DISABLE KEYS */;
INSERT INTO `role_menu_rel` VALUES ('04c2af000e0343bd899e5353552a92b7','2024-07-22 18:03:15.384307','2024-07-22 18:03:15.384307','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','5f785900-d317-4210-979d-d17a40ba8ecc','a58c46ccfb634917a1c470e01746410c'),('0a9bd59886174b0c9597ad7dd35fdb57','2024-02-23 09:33:36.113671','2024-02-23 09:33:36.113671','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','5f785900-d317-4210-979d-d17a40ba8ecc','43a710c85f8a43fe9602be9ceed50d1f'),('0dea9143-96a7-4ada-9158-84df2d9a0d8a','2024-01-17 10:37:02.842166','2024-01-17 10:37:02.842166','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','5f785900-d317-4210-979d-d17a40ba8ecc','349b39a5-9500-4c22-9972-f22b935aba42'),('12ee642c326e46e4908adfe51d1fe7dc','2024-08-15 14:32:39.929660','2024-08-15 14:32:39.929660','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','5f785900-d317-4210-979d-d17a40ba8ecc','9002b4faff164e2fb728afe89601a474'),('18f8e839-65cd-44cf-8e20-675ab562ff72','2024-01-16 15:51:42.848283','2024-01-16 15:51:42.848283','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','da8163bd-67c0-4263-b075-e39d7e595ca9','16b68322-193d-4be4-b71b-bbc0f18b0d3f'),('1a0e54d4-f1de-4e54-aac1-14f96e6892a9','2024-01-17 09:13:36.299660','2024-01-17 09:13:36.299660','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','5f785900-d317-4210-979d-d17a40ba8ecc','16b68322-193d-4be4-b71b-bbc0f18b0d3f'),('260a568c2c01431a8b89f9b19a530d67','2024-02-23 09:33:36.108159','2024-02-23 09:33:36.108159','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','5f785900-d317-4210-979d-d17a40ba8ecc','c9cf9ed3f15d4e20b37c672500311324'),('2934352c-742d-4041-b577-ac4d5be0af3e','2024-01-18 20:27:46.570354','2024-01-18 20:27:46.570354','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','4a83f185-30bb-4464-9e68-239698e89a5e','16b68322-193d-4be4-b71b-bbc0f18b0d3f'),('4648de11-03d3-4a68-b5e3-a1076fb7ca33','2024-01-18 17:02:15.666791','2024-01-18 17:02:15.666791','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','5f785900-d317-4210-979d-d17a40ba8ecc','8246938d-50f1-4463-9ff1-8f4097df2b7a'),('4977d1b0-2e43-4457-9a9c-e99043d3b9ef','2024-01-17 09:13:36.304465','2024-01-17 09:13:36.304465','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','5f785900-d317-4210-979d-d17a40ba8ecc','00f2290a-cde5-4e95-9294-4e7599b1a3f5'),('4a8a37da-0132-4d03-93eb-727661ce81b3','2024-01-19 11:12:29.119931','2024-01-19 11:12:29.119931','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','5f785900-d317-4210-979d-d17a40ba8ecc','839c9fd0-8cf0-4070-a50f-16981bf649f8'),('680532bf-c941-4cbc-88a1-47a55283f1a9','2024-01-16 15:51:42.853546','2024-01-16 15:51:42.853546','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','da8163bd-67c0-4263-b075-e39d7e595ca9','8ea32b3d-90e4-4d76-a777-413917166a32'),('766ef9efcdad4df399bbf28bb159a90e','2024-07-22 15:11:21.505526','2024-07-22 15:11:21.505526','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','5f785900-d317-4210-979d-d17a40ba8ecc','390bcc1517e2476b83071c4c2c0502b8'),('82768ea3-66f2-4447-9528-9c45f617ef4b','2024-01-18 14:36:40.298395','2024-01-18 14:36:40.298395','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','5f785900-d317-4210-979d-d17a40ba8ecc','d5510c37-c5e9-48b3-b41b-2a0fa710c94e'),('9155676edfae4b8fb1710214d33463c7','2024-02-20 09:28:31.647348','2024-02-20 09:28:31.647348','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','5f785900-d317-4210-979d-d17a40ba8ecc','c32ce807a69c43e8bdf67e27974920ab'),('9594627e-8c2f-45c7-91c3-ce1c0f9e93c7','2024-01-26 10:07:13.134646','2024-01-26 10:07:13.134646','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','5f785900-d317-4210-979d-d17a40ba8ecc','fcf86780-d447-4a60-96b8-4f03c4eddc92'),('9c47cfc6-8333-4cac-bc0e-a027f5dbacd6','2024-01-17 09:13:40.638499','2024-01-17 09:13:40.637983','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','5f785900-d317-4210-979d-d17a40ba8ecc','8ea32b3d-90e4-4d76-a777-413917166a32'),('9cc88468-9cf8-400a-beb2-239aaea1bc76','2024-01-18 20:27:46.574487','2024-01-18 20:27:46.574487','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','4a83f185-30bb-4464-9e68-239698e89a5e','8246938d-50f1-4463-9ff1-8f4097df2b7a'),('9d8c50cdc2274948b1368a42aefee3b3','2024-03-15 23:21:55.204659','2024-03-15 23:21:55.204659','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','5f785900-d317-4210-979d-d17a40ba8ecc','7e6a08be19d340a68981f1be6a14ec92'),('be1ad609-7c29-4f15-af1b-fcb2443e62c0','2024-01-26 10:07:13.130646','2024-01-26 10:07:13.130646','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','5f785900-d317-4210-979d-d17a40ba8ecc','a350090e-11ff-45d0-88ce-5c3c1b6f9ef4'),('beb24e5692294f91bd91c285cbec6e83','2024-07-22 09:09:02.559547','2024-07-22 09:09:02.559547','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','5f785900-d317-4210-979d-d17a40ba8ecc','b361c5efc45a4558a229b7188aa00e61'),('c0966b54-bc59-4963-9c3d-d3d68a8b2ce0','2024-01-17 16:42:52.109438','2024-01-17 16:42:52.109438','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','5f785900-d317-4210-979d-d17a40ba8ecc','0c30e2f6-8cef-4ee6-999e-a18af97a3a26'),('c6b42a81ad7c495d9cccb41a9cf37202','2024-03-15 23:21:55.209895','2024-03-15 23:21:55.209895','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','5f785900-d317-4210-979d-d17a40ba8ecc','4e965b885e874521ab84c5c114e7a230'),('c960432c-a191-4630-9902-540cefb992f2','2024-01-16 20:51:34.270707','2024-01-16 20:51:34.270707','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','da8163bd-67c0-4263-b075-e39d7e595ca9','00f2290a-cde5-4e95-9294-4e7599b1a3f5'),('d3f5aa75-dbf7-40b9-986c-dde2edf14b08','2024-01-16 20:51:45.829499','2024-01-16 20:51:45.829499','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','da8163bd-67c0-4263-b075-e39d7e595ca9','0c30e2f6-8cef-4ee6-999e-a18af97a3a26'),('dc447935-53ac-4181-95b1-5b6c6fe52900','2024-01-18 20:27:46.578579','2024-01-18 20:27:46.578579','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','4a83f185-30bb-4464-9e68-239698e89a5e','d5510c37-c5e9-48b3-b41b-2a0fa710c94e'),('f4d3ea50fa2a4586b3cb7bc020acc51d','2024-08-15 14:32:39.946384','2024-08-15 14:32:39.946384','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','5f785900-d317-4210-979d-d17a40ba8ecc','95663185b0e1448ebc746471858ebfe0'),('fbdb4bb46d51411b819733fe1d283ad4','2024-02-26 14:09:22.813874','2024-02-26 14:09:22.813874','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','5f785900-d317-4210-979d-d17a40ba8ecc','6c0d0f67db5d4418b974c685af43f7e0');
/*!40000 ALTER TABLE `role_menu_rel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `slideshow`
--

DROP TABLE IF EXISTS `slideshow`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `slideshow` (
  `id` varchar(36) NOT NULL,
  `created_time` datetime(6) NOT NULL,
  `edited_time` datetime(6) NOT NULL,
  `creator_id` varchar(36) NOT NULL,
  `editor_id` varchar(36) NOT NULL,
  `picture` varchar(255) NOT NULL COMMENT '公告图片',
  `content` varchar(255) NOT NULL COMMENT '公告内容',
  `navigator_id` varchar(32) DEFAULT NULL,
  `navigator_type` varchar(32) DEFAULT NULL,
  `sort` int NOT NULL,
  `valid` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `slideshow`
--

LOCK TABLES `slideshow` WRITE;
/*!40000 ALTER TABLE `slideshow` DISABLE KEYS */;
INSERT INTO `slideshow` VALUES ('4205c56fa205499b812e7bdcd443528e','2024-08-15 14:33:16.913373','2024-08-15 14:33:16.913373','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','https://my-community.oss-cn-qingdao.aliyuncs.com/20240815143252c0e92113-3d4b-46a8-a69e-e5bc3bb4b9cd_28c16457add5ca49707fd437dd56c71c.png@344w_344h.png','盲盒测试',NULL,'BLIND_BOX',0,1);
/*!40000 ALTER TABLE `slideshow` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` varchar(36) NOT NULL,
  `created_time` datetime(6) NOT NULL,
  `edited_time` datetime(6) NOT NULL,
  `nickname` varchar(20) DEFAULT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  `gender` varchar(36) DEFAULT NULL,
  `phone` varchar(20) NOT NULL,
  `password` varchar(100) NOT NULL,
  `status` varchar(32) NOT NULL COMMENT '用户状态',
  PRIMARY KEY (`id`),
  UNIQUE KEY `phone` (`phone`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('0f07d638f1bc401188d86dc650ab06a7','2024-01-10 10:48:02.758735','2024-03-14 16:43:43.635947','起凡1','https://my-community.oss-cn-qingdao.aliyuncs.com/20240110212158起凡.jpg','PRIVATE','13656987994','$2a$10$pl/GmO3mDaqWjBtmfXzppOFQwnW/jlinORR6.83Lo7QdTuU4uh5AG','NORMAL'),('4b2cdd272a3d4507a38b05fef41ef4f0','2024-03-14 10:40:44.266051','2024-03-14 16:43:43.574057','起凡2',NULL,'MALE','1365698799','123456','NORMAL'),('9426415c-8254-46dd-95fe-35fb4c307713','2024-01-18 20:25:42.052245','2024-01-18 20:27:01.505872','默认用户',NULL,'FEMALE','13656987995','$2a$10$xp1LPOARpDbUPhNLtjfQMeDWgWS3KRM/7wB4pbya1c9IxVECh.Dv2','NORMAL'),('ad9bff77beb342d091897e69e0a707dd','2024-03-14 15:50:09.144352','2024-03-14 16:43:43.633433','起凡3',NULL,'MALE','1365698790','123456','NORMAL'),('c3a27b84683e4869a38d3c010a16386a','2024-03-14 10:41:45.351397','2024-03-14 16:43:43.441625','起凡3',NULL,'MALE','1365698791','123456','NORMAL');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_role_rel`
--

DROP TABLE IF EXISTS `user_role_rel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_role_rel` (
  `id` varchar(36) NOT NULL,
  `created_time` datetime(6) NOT NULL,
  `edited_time` datetime(6) NOT NULL,
  `creator_id` varchar(36) NOT NULL,
  `editor_id` varchar(36) NOT NULL,
  `role_id` varchar(36) NOT NULL,
  `user_id` varchar(36) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `role_id` (`role_id`,`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_role_rel`
--

LOCK TABLES `user_role_rel` WRITE;
/*!40000 ALTER TABLE `user_role_rel` DISABLE KEYS */;
INSERT INTO `user_role_rel` VALUES ('3b595f1e-50b1-454d-b0d7-4cdb084ef2bc','2024-01-18 20:27:01.511897','2024-01-18 20:27:01.511897','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','4a83f185-30bb-4464-9e68-239698e89a5e','0f07d638f1bc401188d86dc650ab06a7'),('c61c1c7f-e845-4e6d-93f4-26f9d76a82fa','2024-01-17 08:44:26.417028','2024-01-17 08:44:26.417028','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','5f785900-d317-4210-979d-d17a40ba8ecc','0f07d638f1bc401188d86dc650ab06a7');
/*!40000 ALTER TABLE `user_role_rel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_we_chat`
--

DROP TABLE IF EXISTS `user_we_chat`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_we_chat` (
  `id` varchar(36) NOT NULL,
  `created_time` datetime(6) NOT NULL,
  `edited_time` datetime(6) NOT NULL,
  `open_id` varchar(30) NOT NULL,
  `user_id` varchar(36) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `open_id` (`open_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_we_chat`
--

LOCK TABLES `user_we_chat` WRITE;
/*!40000 ALTER TABLE `user_we_chat` DISABLE KEYS */;
INSERT INTO `user_we_chat` VALUES ('ed3aad89-221f-4fe5-9443-b649b13bfb40','2024-01-22 16:11:50.056072','2024-01-22 16:11:50.056072','oEheF5USRu6Y3qWjpb3wJPBfuejw','ad9bff77beb342d091897e69e0a707dd');
/*!40000 ALTER TABLE `user_we_chat` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-08-15 16:47:55