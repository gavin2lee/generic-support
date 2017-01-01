/*
Navicat SQL Server Data Transfer

Source Server         : 10.2.3.102
Source Server Version : 105000
Source Host           : 10.2.3.102:1433
Source Database       : USP
Source Schema         : dbo

Target Server Type    : SQL Server
Target Server Version : 105000
File Encoding         : 65001

Date: 2016-12-12 15:33:45
*/


-- ----------------------------
-- Table structure for T_USER
-- ----------------------------
DROP TABLE [dbo].[T_USER]
GO
CREATE TABLE [dbo].[T_USER] (
[ID] bigint NOT NULL IDENTITY(1,1) ,
[CODE] varchar(20) NULL ,
[USERNAME] varchar(50) NULL ,
[PASSWORD] varchar(100) NULL ,
[NAME] varchar(50) NULL ,
[GENDER] char(1) NULL ,
[MOBILE_PHONE] varchar(50) NULL ,
[TELEPHONE] varchar(50) NULL ,
[EMAIL] varchar(50) NULL ,
[IS_ACTIVE] tinyint NULL DEFAULT ((1)) ,
[IS_LOCKED] tinyint NULL DEFAULT ((0)) ,
[IS_DELETED] tinyint NULL DEFAULT ((0)) ,
[CREATE_AT] datetime NULL ,
[UPDATE_AT] datetime NULL ,
[CREATE_BY_USER_ID] bigint NULL ,
[UPDATE_BY_USER_ID] bigint NULL ,
[DATA_SOURCE] varchar(20) NULL 
)


GO
DBCC CHECKIDENT(N'[dbo].[T_USER]', RESEED, 72)
GO

-- ----------------------------
-- Indexes structure for table T_USER
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table T_USER
-- ----------------------------
ALTER TABLE [dbo].[T_USER] ADD PRIMARY KEY ([ID])
GO
