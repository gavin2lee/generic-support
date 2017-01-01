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

Date: 2016-12-12 15:34:25
*/


-- ----------------------------
-- Table structure for R_USER_ROLE
-- ----------------------------
DROP TABLE [dbo].[R_USER_ROLE]
GO
CREATE TABLE [dbo].[R_USER_ROLE] (
[ID] bigint NOT NULL IDENTITY(1,1) ,
[USER_ID] bigint NOT NULL ,
[ROLE_ID] bigint NOT NULL ,
[IS_DELETED] tinyint NULL DEFAULT ((0)) ,
[CREATE_AT] datetime NULL ,
[UPDATE_AT] datetime NULL ,
[CREATE_BY_USER_ID] bigint NULL ,
[UPDATE_BY_USER_ID] bigint NULL ,
[DATA_SOURCE] varchar(50) NULL 
)


GO
DBCC CHECKIDENT(N'[dbo].[R_USER_ROLE]', RESEED, 11)
GO

-- ----------------------------
-- Indexes structure for table R_USER_ROLE
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table R_USER_ROLE
-- ----------------------------
ALTER TABLE [dbo].[R_USER_ROLE] ADD PRIMARY KEY ([ID])
GO
