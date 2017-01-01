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

Date: 2016-12-12 15:34:09
*/


-- ----------------------------
-- Table structure for T_ROLE
-- ----------------------------
DROP TABLE [dbo].[T_ROLE]
GO
CREATE TABLE [dbo].[T_ROLE] (
[ID] bigint NOT NULL IDENTITY(1,1) ,
[CODE] varchar(20) NULL ,
[NAME] varchar(50) NULL ,
[LABEL] nvarchar(50) NULL ,
[DESCRIPTION] nvarchar(100) NULL ,
[IS_DELETED] tinyint NULL DEFAULT ((0)) ,
[CREATE_AT] datetime NULL ,
[UPDATE_AT] datetime NULL ,
[CREATE_BY_USER_ID] bigint NULL ,
[UPDATE_BY_USER_ID] bigint NULL ,
[DATA_SOURCE] varchar(50) NULL 
)


GO
DBCC CHECKIDENT(N'[dbo].[T_ROLE]', RESEED, 3)
GO

-- ----------------------------
-- Indexes structure for table T_ROLE
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table T_ROLE
-- ----------------------------
ALTER TABLE [dbo].[T_ROLE] ADD PRIMARY KEY ([ID])
GO
