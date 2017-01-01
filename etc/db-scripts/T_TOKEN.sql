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

Date: 2016-12-12 15:33:59
*/


-- ----------------------------
-- Table structure for T_TOKEN
-- ----------------------------
DROP TABLE [dbo].[T_TOKEN]
GO
CREATE TABLE [dbo].[T_TOKEN] (
[TOKEN_VALUE] varchar(100) NOT NULL ,
[TERMINAL_IP] varchar(50) NULL ,
[ACTIVE] tinyint NULL DEFAULT ((1)) ,
[USERNAME] varchar(50) NULL ,
[PASSWORD] varchar(50) NULL ,
[LAST_MODIFIED] datetime NULL 
)


GO

-- ----------------------------
-- Indexes structure for table T_TOKEN
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table T_TOKEN
-- ----------------------------
ALTER TABLE [dbo].[T_TOKEN] ADD PRIMARY KEY ([TOKEN_VALUE])
GO
