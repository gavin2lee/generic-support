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

Date: 2016-12-12 15:34:18
*/


-- ----------------------------
-- Table structure for T_PERMISSION
-- ----------------------------
DROP TABLE [dbo].[T_PERMISSION]
GO
CREATE TABLE [dbo].[T_PERMISSION] (
[ID] bigint NOT NULL IDENTITY(1,1) ,
[CODE] varchar(50) NULL ,
[NAME] varchar(50) NULL ,
[LABEL] nvarchar(50) NULL ,
[DESCRIPTION] nvarchar(50) NULL ,
[IS_DELETED] tinyint NULL DEFAULT ((0)) 
)


GO
DBCC CHECKIDENT(N'[dbo].[T_PERMISSION]', RESEED, 3)
GO

-- ----------------------------
-- Indexes structure for table T_PERMISSION
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table T_PERMISSION
-- ----------------------------
ALTER TABLE [dbo].[T_PERMISSION] ADD PRIMARY KEY ([ID])
GO
