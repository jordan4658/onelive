/*
 Navicat Premium Data Transfer

 Source Server         : dev-mysql8
 Source Server Type    : MySQL
 Source Server Version : 80023
 Source Host           : database-1.cpbp5ehdkqcm.ap-northeast-2.rds.amazonaws.com:3306
 Source Schema         : onelive

 Target Server Type    : MySQL
 Target Server Version : 80023
 File Encoding         : 65001

 Date: 17/01/2022 17:24:30
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_bus_parameter
-- ----------------------------
DROP TABLE IF EXISTS `sys_bus_parameter`;
CREATE TABLE `sys_bus_parameter`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '业务参数ID',
  `param_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '业务名称',
  `param_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '业务参数代码',
  `p_param_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '业务参数父代码',
  `param_value` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '业务参数值',
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '参数说明',
  `status` int NULL DEFAULT NULL COMMENT '系统参数启用状态 0启用 9未启用',
  `sortby` int NOT NULL DEFAULT 1 COMMENT '排序权重',
  `is_delete` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除',
  `create_user` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_user` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '最后修改人',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `merchant_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '0' COMMENT '商户code值，默认值为0',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_code_un`(`param_code`) USING BTREE COMMENT '参数code唯一索引'
) ENGINE = InnoDB AUTO_INCREMENT = 524 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '业务参数' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_bus_parameter
-- ----------------------------
INSERT INTO `sys_bus_parameter` VALUES (1, '第三方回调网关地址', 'PAY_CALLBACK_HTTP', '', 'http://54.180.119.120:4040', '', 0, 1, 0, '1', '2021-12-29 18:07:50', '1', '2022-01-07 15:37:28', '0');
INSERT INTO `sys_bus_parameter` VALUES (3, '平台金币单位标识', 'PLATFORM_GOLD_UNIT', '', 'G', '平台金币单位标识', 0, 1, 0, '1', '2022-01-07 20:35:01', '', '2022-01-07 20:53:24', '0');
INSERT INTO `sys_bus_parameter` VALUES (4, '金币兑换银豆汇率', 'GOLD_SILVER_EXCHANGE', '', '10', '', 0, 1, 0, '', '2022-01-07 21:17:30', '', '2022-01-07 21:17:30', '0');
INSERT INTO `sys_bus_parameter` VALUES (5, '平台银豆单位标识', 'PLATFORM_SILVER_UNIT', '', 'S', '', 0, 1, 0, '', '2022-01-07 22:21:17', '', '2022-01-07 22:21:20', '0');
INSERT INTO `sys_bus_parameter` VALUES (7, '充值', '1', 'TRANSACTION_TYPE', '1,15,16', '用户充值（所有充值显示这个）', 0, 1, 0, '', '2022-01-12 13:43:26', '', '2022-01-12 17:06:46', '0');
INSERT INTO `sys_bus_parameter` VALUES (8, '提现', '2', 'TRANSACTION_TYPE', '3,4,7', '提现', 0, 1, 0, '', '2022-01-12 13:43:44', '', '2022-01-12 17:06:49', '0');
INSERT INTO `sys_bus_parameter` VALUES (9, '礼物', '3', 'TRANSACTION_TYPE', '25,26,29', '直播打赏主播消费', 0, 1, 0, '', '2022-01-12 13:43:44', '', '2022-01-12 17:06:52', '0');
INSERT INTO `sys_bus_parameter` VALUES (10, '注单', '4', 'TRANSACTION_TYPE', '21', '下注', 0, 1, 0, '', '2022-01-12 13:43:44', '', '2022-01-12 17:06:54', '0');
INSERT INTO `sys_bus_parameter` VALUES (11, '中奖', '5', 'TRANSACTION_TYPE', '22', '中奖', 0, 1, 0, '', '2022-01-12 13:43:44', '', '2022-01-12 17:06:55', '0');
INSERT INTO `sys_bus_parameter` VALUES (12, '其他', '6', 'TRANSACTION_TYPE', '10,11', '提现退回、人工存入', 0, 1, 0, '', '2022-01-12 13:43:44', '', '2022-01-12 17:06:57', '0');
INSERT INTO `sys_bus_parameter` VALUES (13, '优惠', '7', 'TRANSACTION_TYPE', '8', '充值奖励', 0, 1, 0, '', '2022-01-12 13:43:44', '', '2022-01-12 17:06:58', '0');
INSERT INTO `sys_bus_parameter` VALUES (14, '活动', '8', 'TRANSACTION_TYPE', '9', '红包', 0, 1, 0, '', '2022-01-12 13:43:44', '', '2022-01-12 17:06:59', '0');
INSERT INTO `sys_bus_parameter` VALUES (15, '兑换', '9', 'TRANSACTION_TYPE', '28', '银豆', 0, 1, 0, '', '2022-01-12 13:43:44', '', '2022-01-12 17:07:01', '0');
INSERT INTO `sys_bus_parameter` VALUES (16, '全部', '0', 'TRANSACTION_TYPE', '1,15,16,28,9,8,10,11,22,21,25,26,29,3,4,7', '全部类型的列表', 0, 1, 0, '', '2022-01-12 13:43:44', '', '2022-01-13 19:13:09', '0');
INSERT INTO `sys_bus_parameter` VALUES (476, '', 'AbBank', 'banklist', 'AbBank', '', 0, 0, 0, '', '2021-04-13 18:40:53', '', '2021-04-20 03:41:10', '0');
INSERT INTO `sys_bus_parameter` VALUES (477, '', 'Acb', 'banklist', 'Acb', '', 0, 0, 0, '', '2021-04-13 18:40:53', '', '2021-04-20 03:41:10', '0');
INSERT INTO `sys_bus_parameter` VALUES (478, '', 'AgriBank', 'banklist', 'AgriBank', '', 0, 0, 0, '', '2021-04-13 18:40:53', '', '2021-04-20 03:41:10', '0');
INSERT INTO `sys_bus_parameter` VALUES (479, '', 'Baca', 'banklist', 'Baca', '', 0, 0, 0, '', '2021-04-13 18:40:53', '', '2021-04-20 03:41:10', '0');
INSERT INTO `sys_bus_parameter` VALUES (480, '', 'Baoviet', 'banklist', 'Baoviet', '', 0, 0, 0, '', '2021-04-13 18:40:53', '', '2021-04-20 03:41:10', '0');
INSERT INTO `sys_bus_parameter` VALUES (481, '', 'Bidv', 'banklist', 'Bidv', '', 0, 0, 0, '', '2021-04-13 18:40:53', '', '2021-04-20 03:41:10', '0');
INSERT INTO `sys_bus_parameter` VALUES (482, '', 'Cimb', 'banklist', 'Cimb', '', 0, 0, 0, '', '2021-04-13 18:40:53', '', '2021-04-20 03:41:10', '0');
INSERT INTO `sys_bus_parameter` VALUES (483, '', 'CoopBank', 'banklist', 'CoopBank', '', 0, 0, 0, '', '2021-04-13 18:40:53', '', '2021-04-20 03:41:10', '0');
INSERT INTO `sys_bus_parameter` VALUES (484, '', 'DongA', 'banklist', 'DongA', '', 0, 0, 0, '', '2021-04-13 18:40:53', '', '2021-04-20 03:41:10', '0');
INSERT INTO `sys_bus_parameter` VALUES (485, '', 'EximBank', 'banklist', 'EximBank', '', 0, 0, 0, '', '2021-04-13 18:40:53', '', '2021-04-20 03:41:10', '0');
INSERT INTO `sys_bus_parameter` VALUES (486, '', 'GpBank', 'banklist', 'GpBank', '', 0, 0, 0, '', '2021-04-13 18:40:53', '', '2021-04-20 03:41:10', '0');
INSERT INTO `sys_bus_parameter` VALUES (487, '', 'HDBank', 'banklist', 'HDBank', '', 0, 0, 0, '', '2021-04-13 18:40:53', '', '2021-04-20 03:41:10', '0');
INSERT INTO `sys_bus_parameter` VALUES (488, '', 'Hong_leong_Bank', 'banklist', 'Hong_leong_Bank', '', 0, 0, 0, '', '2021-04-13 18:40:53', '', '2021-04-20 03:41:10', '0');
INSERT INTO `sys_bus_parameter` VALUES (489, '', 'lbkBank', 'banklist', 'lbkBank', '', 0, 0, 0, '', '2021-04-13 18:40:53', '', '2021-04-20 03:41:10', '0');
INSERT INTO `sys_bus_parameter` VALUES (490, '', 'Lab', 'banklist', 'Lab', '', 0, 0, 0, '', '2021-04-13 18:40:53', '', '2021-04-20 03:41:10', '0');
INSERT INTO `sys_bus_parameter` VALUES (491, '', 'Jcb', 'banklist', 'Jcb', '', 0, 0, 0, '', '2021-04-13 18:40:53', '', '2021-04-20 03:41:10', '0');
INSERT INTO `sys_bus_parameter` VALUES (492, '', 'Kienlong', 'banklist', 'Kienlong', '', 0, 0, 0, '', '2021-04-13 18:40:53', '', '2021-04-20 03:41:10', '0');
INSERT INTO `sys_bus_parameter` VALUES (493, '', 'LienvietpostBank', 'banklist', 'LienvietpostBank', '', 0, 0, 0, '', '2021-04-13 18:40:53', '', '2021-04-20 03:41:10', '0');
INSERT INTO `sys_bus_parameter` VALUES (494, '', 'MaritimeBank', 'banklist', 'MaritimeBank', '', 0, 0, 0, '', '2021-04-13 18:40:53', '', '2021-04-20 03:41:10', '0');
INSERT INTO `sys_bus_parameter` VALUES (495, '', 'Master', 'banklist', 'Master', '', 0, 0, 0, '', '2021-04-13 18:40:53', '', '2021-04-20 03:41:10', '0');
INSERT INTO `sys_bus_parameter` VALUES (496, '', 'Mb', 'banklist', 'Mb', '', 0, 0, 0, '', '2021-04-13 18:40:53', '', '2021-04-20 03:41:10', '0');
INSERT INTO `sys_bus_parameter` VALUES (497, '', 'NamABank', 'banklist', 'NamABank', '', 0, 0, 0, '', '2021-04-13 18:40:53', '', '2021-04-20 03:41:10', '0');
INSERT INTO `sys_bus_parameter` VALUES (498, '', 'NaviBank', 'banklist', 'NaviBank', '', 0, 0, 0, '', '2021-04-13 18:40:53', '', '2021-04-20 03:41:10', '0');
INSERT INTO `sys_bus_parameter` VALUES (499, '', 'Ncb', 'banklist', 'Ncb', '', 0, 0, 0, '', '2021-04-13 18:40:53', '', '2021-04-20 03:41:10', '0');
INSERT INTO `sys_bus_parameter` VALUES (500, '', 'Ocb', 'banklist', 'Ocb', '', 0, 0, 0, '', '2021-04-13 18:40:53', '', '2021-04-20 03:41:10', '0');
INSERT INTO `sys_bus_parameter` VALUES (501, '', 'OceanBank', 'banklist', 'OceanBank', '', 0, 0, 0, '', '2021-04-13 18:40:53', '', '2021-04-20 03:41:10', '0');
INSERT INTO `sys_bus_parameter` VALUES (502, '', 'PgBank', 'banklist', 'PgBank', '', 0, 0, 0, '', '2021-04-13 18:40:53', '', '2021-04-20 03:41:10', '0');
INSERT INTO `sys_bus_parameter` VALUES (503, '', 'PublicBank', 'banklist', 'PublicBank', '', 0, 0, 0, '', '2021-04-13 18:40:53', '', '2021-04-20 03:41:10', '0');
INSERT INTO `sys_bus_parameter` VALUES (504, '', 'PVComBank', 'banklist', 'PVComBank', '', 0, 0, 0, '', '2021-04-13 18:40:53', '', '2021-04-20 03:41:10', '0');
INSERT INTO `sys_bus_parameter` VALUES (505, '', 'SacomBank', 'banklist', 'SacomBank', '', 0, 0, 0, '', '2021-04-13 18:40:53', '', '2021-04-20 03:41:10', '0');
INSERT INTO `sys_bus_parameter` VALUES (506, '', 'SaigonBank', 'banklist', 'SaigonBank', '', 0, 0, 0, '', '2021-04-13 18:40:53', '', '2021-04-20 03:41:10', '0');
INSERT INTO `sys_bus_parameter` VALUES (507, '', 'Scb', 'banklist', 'Scb', '', 0, 0, 0, '', '2021-04-13 18:40:53', '', '2021-04-20 03:41:10', '0');
INSERT INTO `sys_bus_parameter` VALUES (508, '', 'SeaBank', 'banklist', 'SeaBank', '', 0, 0, 0, '', '2021-04-13 18:40:53', '', '2021-04-20 03:41:10', '0');
INSERT INTO `sys_bus_parameter` VALUES (509, '', 'SHB', 'banklist', 'SHB', '', 0, 0, 0, '', '2021-04-13 18:40:53', '', '2021-04-20 03:41:10', '0');
INSERT INTO `sys_bus_parameter` VALUES (510, '', 'Shinhan', 'banklist', 'Shinhan', '', 0, 0, 0, '', '2021-04-13 18:40:53', '', '2021-04-20 03:41:10', '0');
INSERT INTO `sys_bus_parameter` VALUES (511, '', 'TechcomBank', 'banklist', 'TechcomBank', '', 0, 0, 0, '', '2021-04-13 18:40:53', '', '2021-04-20 03:41:10', '0');
INSERT INTO `sys_bus_parameter` VALUES (512, '', 'TPBank', 'banklist', 'TPBank', '', 0, 0, 0, '', '2021-04-13 18:40:53', '', '2021-04-20 03:41:10', '0');
INSERT INTO `sys_bus_parameter` VALUES (513, '', 'Uob', 'banklist', 'Uob', '', 0, 0, 0, '', '2021-04-13 18:40:53', '', '2021-04-20 03:41:10', '0');
INSERT INTO `sys_bus_parameter` VALUES (514, '', 'VIB', 'banklist', 'VIB', '', 0, 0, 0, '', '2021-04-13 18:40:53', '', '2021-04-20 03:41:10', '0');
INSERT INTO `sys_bus_parameter` VALUES (515, '', 'Vieta', 'banklist', 'Vieta', '', 0, 0, 0, '', '2021-04-13 18:40:53', '', '2021-04-20 03:41:10', '0');
INSERT INTO `sys_bus_parameter` VALUES (516, '', 'VietBank', 'banklist', 'VietBank', '', 0, 0, 0, '', '2021-04-13 18:40:53', '', '2021-04-20 03:41:10', '0');
INSERT INTO `sys_bus_parameter` VALUES (517, '', 'VietcapitalBank', 'banklist', 'VietcapitalBank', '', 0, 0, 0, '', '2021-04-13 18:40:53', '', '2021-04-20 03:41:10', '0');
INSERT INTO `sys_bus_parameter` VALUES (518, '', 'VietcomBank', 'banklist', 'VietcomBank', '', 0, 0, 0, '', '2021-04-13 18:40:53', '', '2021-04-20 03:41:10', '0');
INSERT INTO `sys_bus_parameter` VALUES (519, '', 'VietinBank', 'banklist', 'VietinBank', '', 0, 0, 0, '', '2021-04-13 18:40:53', '', '2021-04-20 03:41:10', '0');
INSERT INTO `sys_bus_parameter` VALUES (520, '', 'Visa', 'banklist', 'Visa', '', 0, 0, 0, '', '2021-04-13 18:40:53', '', '2021-04-20 03:41:10', '0');
INSERT INTO `sys_bus_parameter` VALUES (521, '', 'VPBank', 'banklist', 'VPBank', '', 0, 0, 0, '', '2021-04-13 18:40:53', '', '2021-04-20 03:41:10', '0');
INSERT INTO `sys_bus_parameter` VALUES (522, '', 'Vrb', 'banklist', 'Vrb', '', 0, 0, 0, '', '2021-04-13 18:40:53', '', '2021-04-20 03:41:10', '0');
INSERT INTO `sys_bus_parameter` VALUES (523, '', 'Nab', 'banklist', 'Nab', '', 0, 0, 0, '', '2021-04-13 18:40:53', '', '2021-04-20 03:41:10', '0');

SET FOREIGN_KEY_CHECKS = 1;
