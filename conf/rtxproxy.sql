/*
Navicat SQLite Data Transfer

Source Server         : RTXProxy
Source Server Version : 30808
Source Host           : :0

Target Server Type    : SQLite
Target Server Version : 30808
File Encoding         : 65001

Date: 2017-10-26 16:25:56
*/

PRAGMA foreign_keys = OFF;

-- ----------------------------
-- Table structure for t_message
-- ----------------------------
DROP TABLE IF EXISTS "main"."t_message";
CREATE TABLE "t_message" (
"id"  INTEGER NOT NULL,
"gmt_create"  TEXT,
"gmt_modified"  TEXT,
"title"  TEXT,
"msg"  TEXT,
"receiver"  TEXT,
"b_sent"  INTEGER,
PRIMARY KEY ("id" ASC)
);

-- ----------------------------
-- Indexes structure for table t_message
-- ----------------------------
CREATE INDEX "main"."idx_b_sent_gmt_modified"
ON "t_message" ("b_sent" ASC, "gmt_modified" ASC);
