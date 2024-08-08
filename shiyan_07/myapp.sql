/*
 Navicat Premium Data Transfer

 Source Server         : local
 Source Server Type    : MySQL
 Source Server Version : 80031 (8.0.31)
 Source Host           : localhost:3306
 Source Schema         : myapp

 Target Server Type    : MySQL
 Target Server Version : 80031 (8.0.31)
 File Encoding         : 65001

 Date: 28/07/2024 21:22:42
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category`  (
  `id` int NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of category
-- ----------------------------
INSERT INTO `category` VALUES (1, '学习');
INSERT INTO `category` VALUES (2, '玩耍');
INSERT INTO `category` VALUES (3, '家务');

-- ----------------------------
-- Table structure for todo
-- ----------------------------
DROP TABLE IF EXISTS `todo`;
CREATE TABLE `todo`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `complete` tinyint(1) NOT NULL DEFAULT 0,
  `create_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `uid` int NULL DEFAULT NULL,
  `category_id` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of todo
-- ----------------------------
INSERT INTO `todo` VALUES (1, '貼子', 1, '2024-07-21 16:14:22', '2024-07-28 21:17:30', 2, 2);
INSERT INTO `todo` VALUES (6, '貼子', 0, '2024-07-28 20:24:33', '2024-07-28 21:17:47', 9, 1);
INSERT INTO `todo` VALUES (7, '2', 0, '2024-07-28 20:24:35', '2024-07-28 21:02:46', 9, 1);
INSERT INTO `todo` VALUES (9, '貼子', 0, '2024-07-28 20:24:37', '2024-07-28 21:02:46', 9, 1);
INSERT INTO `todo` VALUES (10, '333', 0, '2024-07-28 20:24:41', '2024-07-28 21:02:47', 9, 1);
INSERT INTO `todo` VALUES (11, '1', 0, '2024-07-28 20:25:43', '2024-07-28 21:02:47', 9, 1);
INSERT INTO `todo` VALUES (12, '测网速', 0, '2024-07-28 20:51:30', '2024-07-28 21:02:47', 9, 1);
INSERT INTO `todo` VALUES (13, '测网速', 0, '2024-07-28 20:51:34', '2024-07-28 21:02:49', 9, 1);
INSERT INTO `todo` VALUES (14, '貼子12121', 0, '2024-07-28 21:17:56', '2024-07-28 21:17:56', 9, 1);
INSERT INTO `todo` VALUES (15, '11', 0, '2024-07-28 21:20:14', '2024-07-28 21:20:14', 9, 1);
INSERT INTO `todo` VALUES (16, '22', 0, '2024-07-28 21:20:15', '2024-07-28 21:20:15', 9, 1);
INSERT INTO `todo` VALUES (17, '222', 0, '2024-07-28 21:20:17', '2024-07-28 21:20:17', 9, 1);
INSERT INTO `todo` VALUES (18, '222', 0, '2024-07-28 21:20:19', '2024-07-28 21:20:19', 9, 1);
INSERT INTO `todo` VALUES (19, '222', 0, '2024-07-28 21:20:20', '2024-07-28 21:20:20', 9, 1);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `password` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `gender` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `age` int NULL DEFAULT NULL,
  `province` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `hobbies` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'admin', 'admin', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `user` VALUES (9, 'admin', 'e10adc3949ba59abbe56e057f20f883e', '男', 19, '宁夏', '1796526712@qq.com', '跳舞 ');

SET FOREIGN_KEY_CHECKS = 1;
