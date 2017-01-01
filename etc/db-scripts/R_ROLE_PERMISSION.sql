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

Date: 2016-12-12 15:34:33
*/


-- ----------------------------
-- Table structure for R_ROLE_PERMISSION
-- ----------------------------
DROP TABLE [dbo].[R_ROLE_PERMISSION]
GO
CREATE TABLE [dbo].[R_ROLE_PERMISSION] (
[ID] bigint NOT NULL IDENTITY(1,1) ,
[ROLE_ID] bigint NOT NULL ,
[PERMISSION_ID] bigint NOT NULL ,
[IS_DELETED] tinyint NULL DEFAULT ((0)) ,
[CREATE_AT] datetime NULL ,
[UPDATE_AT] datetime NULL ,
[CREATE_BY_USER_ID] bigint NULL ,
[UPDATE_BY_USER_ID] bigint NULL ,
[DATA_SOURCE] varchar(50) NULL 
)


GO
DBCC CHECKIDENT(N'[dbo].[R_ROLE_PERMISSION]', RESEED, 5)
GO

-- ----------------------------
-- Indexes structure for table R_ROLE_PERMISSION
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table R_ROLE_PERMISSION
-- ----------------------------
ALTER TABLE [dbo].[R_ROLE_PERMISSION] ADD PRIMARY KEY ([ID])
GO
