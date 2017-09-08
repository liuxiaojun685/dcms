/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50711
Source Host           : localhost:3306
Source Database       : dcms_db

Target Server Type    : MYSQL
Target Server Version : 50711
File Encoding         : 65001

Date: 2017-05-04 12:18:28
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `Admin`
-- ----------------------------
DROP TABLE IF EXISTS `Admin`;
CREATE TABLE `Admin` (
  `pkAid` varchar(40) NOT NULL COMMENT '管理员ID',
  `fkParentId` varchar(40) DEFAULT NULL COMMENT '父管理员ID',
  `account` varchar(30) DEFAULT '' COMMENT '登录名',
  `name` varchar(30) DEFAULT '' COMMENT '显示名称',
  `passwd` varchar(256) DEFAULT '' COMMENT '密码',
  `adminType` int(11) DEFAULT '0' COMMENT '类型 1安全审计员 2系统管理员 3安全保密管理员',
  `createFrom` int(11) DEFAULT '0' COMMENT '来源 1预置 2派生',
  `derive` int(11) DEFAULT '0' COMMENT '是否能派生或取消权限 1是 0否',
  `adminState` int(11) DEFAULT '0' COMMENT '是否删除 1是 0否',
  `description` varchar(256) DEFAULT '' COMMENT '角色描述',
  `createTime` bigint(20) DEFAULT '0' COMMENT '创建时间',
  PRIMARY KEY (`pkAid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of Admin
-- ----------------------------
INSERT INTO `Admin` VALUES ('aid-logadmin', null, 'logadmin', '安全审计员', '26005150f2f901f322447765a5753cb8', '1', '1', '1', '0', '负责系统日志查询、审计、导出日志等审计类操作', '0');
INSERT INTO `Admin` VALUES ('aid-secadmin', null, 'secadmin', '安全保密管理员', '3547415bab861284dbf866f57e038c6c', '3', '1', '1', '0', '负责系统定级、密钥管理、流程管理等核心安全配置', '0');
INSERT INTO `Admin` VALUES ('aid-sysadmin', null, 'sysadmin', '系统管理员', '116e81f8f96e60fe013a6cd824ce171c', '2', '1', '1', '0', '负责系统环境配置、组织机构及用户配置等基础配置', '0');

-- ----------------------------
-- Table structure for `AdminLog`
-- ----------------------------
DROP TABLE IF EXISTS `AdminLog`;
CREATE TABLE `AdminLog` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fkAid` varchar(40) DEFAULT NULL COMMENT '操作管理员ID',
  `createTime` bigint(20) DEFAULT '0' COMMENT '入库时间',
  `operateTime` bigint(20) DEFAULT '0' COMMENT '操作时间',
  `operateType` varchar(256) DEFAULT '' COMMENT '操作类型',
  `operateDescription` varchar(1024) DEFAULT '' COMMENT '操作详情',
  `operateResult` int(11) DEFAULT '0' COMMENT '操作结果',
  `ip` varchar(40) DEFAULT '' COMMENT '操作机器IP地址',
  `reserve` varchar(1024) DEFAULT '' COMMENT '预留',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of AdminLog
-- ----------------------------

-- ----------------------------
-- Table structure for `BackupHistory`
-- ----------------------------
DROP TABLE IF EXISTS `BackupHistory`;
CREATE TABLE `BackupHistory` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fkLocationId` int(11) DEFAULT NULL COMMENT '备份位置ID',
  `createTime` bigint(20) DEFAULT '0' COMMENT '备份时间',
  `description` varchar(1024) DEFAULT '' COMMENT '备份描述',
  `createFrom` int(11) DEFAULT '0' COMMENT '备份方式 1手动 2自动',
  `fileName` varchar(256) DEFAULT '' COMMENT '备份文件名 backup_01.sql',
  `fileSize` bigint(20) DEFAULT '0' COMMENT '备份文件大小 字节',
  `fileMd5` varchar(40) DEFAULT '' COMMENT '备份文件MD5',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of BackupHistory
-- ----------------------------

-- ----------------------------
-- Table structure for `Business`
-- ----------------------------
DROP TABLE IF EXISTS `Business`;
CREATE TABLE `Business` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fkParentId` int(11) DEFAULT NULL COMMENT '上级部门ID',
  `name` varchar(256) DEFAULT '' COMMENT '业务名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of Business
-- ----------------------------

-- ----------------------------
-- Table structure for `Client`
-- ----------------------------
DROP TABLE IF EXISTS `Client`;
CREATE TABLE `Client` (
  `pkCid` varchar(40) NOT NULL COMMENT '终端ID',
  `fkFirstLoginUid` varchar(40) DEFAULT NULL COMMENT '首次登陆用户ID',
  `fkLastLoginUid` varchar(40) DEFAULT NULL COMMENT '末次登陆用户ID',
  `pcName` varchar(256) DEFAULT '' COMMENT '机器名',
  `clientLevel` int(11) DEFAULT '0' COMMENT '密级 0公开 1内部 2秘密 3机密 4绝密',
  `ip` varchar(40) DEFAULT '' COMMENT '机器IP',
  `mac` varchar(20) DEFAULT '' COMMENT '机器MAC',
  `osType` varchar(256) DEFAULT '' COMMENT '操作系统类型',
  `versionType` int(11) DEFAULT '0' COMMENT '终端版本类型 1网络版 2单机版',
  `versionCode` int(11) DEFAULT '0' COMMENT '终端版本号',
  `versionName` varchar(256) DEFAULT '' COMMENT '终端版本名',
  `heartbeatTime` bigint(20) DEFAULT '0' COMMENT '终端心跳时间',
  `firstLoginTime` bigint(20) DEFAULT '0' COMMENT '首次登录日期',
  `lastLoginTime` bigint(20) DEFAULT '0' COMMENT '末次登录日期',
  `description` varchar(1024) DEFAULT '' COMMENT '终端信息详情',
  `clientState` int(11) DEFAULT '0' COMMENT '终端状态 1已删除 4已锁定',
  PRIMARY KEY (`pkCid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of Client
-- ----------------------------

-- ----------------------------
-- Table structure for `ClientLevelAccessPolicy`
-- ----------------------------
DROP TABLE IF EXISTS `ClientLevelAccessPolicy`;
CREATE TABLE `ClientLevelAccessPolicy` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userLevel` int(11) DEFAULT '0' COMMENT '用户密级 1一般 2重要 3核心',
  `clientLevel` int(11) DEFAULT '0' COMMENT '终端密级 0公开 1内部 2秘密 3机密 4绝密',
  `enable` int(11) DEFAULT '0' COMMENT '是否允许访问 1是 0否',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ClientLevelAccessPolicy
-- ----------------------------
INSERT INTO `ClientLevelAccessPolicy` VALUES ('1', '1', '0', '1');
INSERT INTO `ClientLevelAccessPolicy` VALUES ('2', '2', '0', '1');
INSERT INTO `ClientLevelAccessPolicy` VALUES ('3', '3', '0', '1');
INSERT INTO `ClientLevelAccessPolicy` VALUES ('4', '1', '1', '1');
INSERT INTO `ClientLevelAccessPolicy` VALUES ('5', '2', '1', '1');
INSERT INTO `ClientLevelAccessPolicy` VALUES ('6', '3', '1', '1');
INSERT INTO `ClientLevelAccessPolicy` VALUES ('7', '1', '2', '1');
INSERT INTO `ClientLevelAccessPolicy` VALUES ('8', '2', '2', '1');
INSERT INTO `ClientLevelAccessPolicy` VALUES ('9', '3', '2', '1');
INSERT INTO `ClientLevelAccessPolicy` VALUES ('10', '1', '3', '0');
INSERT INTO `ClientLevelAccessPolicy` VALUES ('11', '2', '3', '1');
INSERT INTO `ClientLevelAccessPolicy` VALUES ('12', '3', '3', '1');
INSERT INTO `ClientLevelAccessPolicy` VALUES ('13', '1', '4', '0');
INSERT INTO `ClientLevelAccessPolicy` VALUES ('14', '2', '4', '0');
INSERT INTO `ClientLevelAccessPolicy` VALUES ('15', '3', '4', '1');

-- ----------------------------
-- Table structure for `ClientLog`
-- ----------------------------
DROP TABLE IF EXISTS `ClientLog`;
CREATE TABLE `ClientLog` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fkUid` varchar(40) DEFAULT NULL COMMENT '终端用户ID',
  `fkCid` varchar(40) DEFAULT NULL COMMENT '终端ID',
  `createTime` bigint(20) DEFAULT '0' COMMENT '操作时间',
  `ip` varchar(40) DEFAULT '' COMMENT '终端机器IP地址',
  `reserve` varchar(1024) DEFAULT '' COMMENT '预留',
  `operateType` varchar(256) DEFAULT '' COMMENT '操作类型 文件起草 阅读 打印 编辑 截屏 内容复制 文件复制 删除 另存 发送 定密 签发 变更 解密 解绑 授权',
  `operateDescription` varchar(1024) DEFAULT '' COMMENT '操作详情 用户[xxx]登陆失败，原因账号或密码错误。',
  `operateResult` int(11) DEFAULT '0' COMMENT '操作结果 1成功 2失败',
  `fkSrcFid` varchar(40) DEFAULT NULL COMMENT '操作源文件fid',
  `srcName` varchar(40) DEFAULT '' COMMENT '操作源文件名',
  `desName` varchar(40) DEFAULT '' COMMENT '操作目标文件名',
  `operateWay` varchar(256) DEFAULT '' COMMENT '操作方法 内容粘贴 U盘拷贝 光盘刻录 网络共享 邮件发送',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ClientLog
-- ----------------------------

-- ----------------------------
-- Table structure for `ClientPatch`
-- ----------------------------
DROP TABLE IF EXISTS `ClientPatch`;
CREATE TABLE `ClientPatch` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fkLocationId` int(11) DEFAULT NULL COMMENT '补丁位置ID',
  `createTime` bigint(20) DEFAULT '0' COMMENT '创建日期',
  `description` varchar(1024) DEFAULT '' COMMENT '补丁描述',
  `name` varchar(256) DEFAULT '' COMMENT '补丁文件名 xxx.exe',
  `fileSize` bigint(20) DEFAULT '0' COMMENT '补丁文件大小',
  `fileMd5` varchar(40) DEFAULT '' COMMENT '补丁文件MD5',
  `versionCode` int(11) DEFAULT '0' COMMENT '版本号',
  `versionName` varchar(256) DEFAULT '' COMMENT '版本名',
  `osType` varchar(256) DEFAULT '' COMMENT '操作系统类型',
  `versionType` int(11) DEFAULT '0' COMMENT '版本类别 1网络版 2单机版',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ClientPatch
-- ----------------------------

-- ----------------------------
-- Table structure for `ClientRequestLog`
-- ----------------------------
DROP TABLE IF EXISTS `ClientRequestLog`;
CREATE TABLE `ClientRequestLog` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fkUid` varchar(40) DEFAULT NULL COMMENT '操作用户ID',
  `fkCid` varchar(40) DEFAULT NULL COMMENT '操作用户使用的终端ID',
  `createTime` bigint(20) DEFAULT '0' COMMENT '入库时间',
  `operateTime` bigint(20) DEFAULT '0' COMMENT '操作时间',
  `operateType` varchar(256) DEFAULT '' COMMENT '操作类型 登陆 注销 修改密码 策略更新 文件下载',
  `operateDescription` varchar(1024) DEFAULT '' COMMENT '操作详情 用户[xxx]登陆失败，原因账号或密码错误。',
  `operateResult` int(11) DEFAULT '0' COMMENT '操作结果 1成功 2失败',
  `ip` varchar(40) DEFAULT '' COMMENT '操作机器IP地址',
  `reserve` varchar(1024) DEFAULT '' COMMENT '预留',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ClientRequestLog
-- ----------------------------

-- ----------------------------
-- Table structure for `ClientUninstallPasswd`
-- ----------------------------
DROP TABLE IF EXISTS `ClientUninstallPasswd`;
CREATE TABLE `ClientUninstallPasswd` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `passwd` varchar(256) DEFAULT '' COMMENT '终端卸载密码 明文显示前前后两位，其他用*代替',
  `createTime` bigint(20) DEFAULT '0' COMMENT '创建日期',
  `description` varchar(1024) DEFAULT '' COMMENT '创建密码描述',
  `syncNum` int(11) DEFAULT '0' COMMENT '已同步密码的终端数',
  `unsyncNum` int(11) DEFAULT '0' COMMENT '未同步密码的终端数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ClientUninstallPasswd
-- ----------------------------
INSERT INTO `ClientUninstallPasswd` VALUES ('1', 'MTIzNDU2Nzg=', '0', '初始卸载密码', null, null);

-- ----------------------------
-- Table structure for `Component`
-- ----------------------------
DROP TABLE IF EXISTS `Component`;
CREATE TABLE `Component` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(256) DEFAULT '' COMMENT '组件名称',
  `description` varchar(1024) DEFAULT '' COMMENT '组件描述',
  `enable` int(11) DEFAULT '0' COMMENT '组件是否启用 1是 0否',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of Component
-- ----------------------------
INSERT INTO `Component` VALUES ('1', '日志服务', '提供日志类数据库统一采集、展示，根据日志配置文件进行提供相应的功能服务', '1');
INSERT INTO `Component` VALUES ('2', '资源告警服务', '对系统资源的异常情况进行告警，并提供告警信息查看、处理和订阅服务', '1');

-- ----------------------------
-- Table structure for `Department`
-- ----------------------------
DROP TABLE IF EXISTS `Department`;
CREATE TABLE `Department` (
  `pkDid` varchar(40) NOT NULL COMMENT '部门ID',
  `fkParentId` varchar(40) DEFAULT NULL COMMENT '上级部门ID',
  `name` varchar(256) DEFAULT '' COMMENT '部门名称',
  `description` varchar(1024) DEFAULT '' COMMENT '部门描述',
  `departmentState` int(11) DEFAULT '0' COMMENT '部门状态 1已删除',
  `root` int(11) DEFAULT '0' COMMENT '是否根部门 1是 0否',
  PRIMARY KEY (`pkDid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of Department
-- ----------------------------
INSERT INTO `Department` VALUES ('did-root', null, '公司', '', '0', '1');

-- ----------------------------
-- Table structure for `File`
-- ----------------------------
DROP TABLE IF EXISTS `File`;
CREATE TABLE `File` (
  `pkFid` varchar(40) NOT NULL COMMENT '文件ID',
  `fkParentFid` varchar(40) DEFAULT NULL COMMENT '父文件ID',
  `fkOrigFid` varchar(40) DEFAULT NULL COMMENT '祖文件ID',
  `fkFileCreateUid` varchar(40) DEFAULT NULL COMMENT '文件起草人用户ID',
  `fkFileLevelDecideUid` varchar(40) DEFAULT NULL COMMENT '定密责任人用户ID(末次)',
  `fkFileDispatchUid` varchar(40) DEFAULT NULL COMMENT '文件签发人用户ID',
  `fkLocationId` int(11) DEFAULT NULL COMMENT '文件位置ID',
  `fileState` int(11) DEFAULT '0' COMMENT '定密状态 1预定密 2正式定密 3文件签发 4密级变更 5文件解密',
  `name` varchar(256) DEFAULT '' COMMENT '文件名称带后缀',
  `suffix` varchar(256) DEFAULT '' COMMENT '文件名后缀',
  `markVersion` int(11) DEFAULT '0' COMMENT '标志版本',
  `urgency` int(11) DEFAULT '0' COMMENT '重要或紧急程度',
  `fileLevel` int(11) DEFAULT '0' COMMENT '文件密级 0公开 1内部 2秘密 3机密 4绝密',
  `validPeriod` varchar(20) DEFAULT '' COMMENT '保密期限 周期 格式yymmdd',
  `durationType` int(11) DEFAULT '0' COMMENT '保密期限 类型 0不限 1长期 2期限 3解密日期 4条件',
  `durationDescription` varchar(1024) DEFAULT '' COMMENT '保密期限 描述',
  `fileCreateTime` bigint(20) DEFAULT '0' COMMENT '文件起草日期',
  `fileLevelDecideTime` bigint(20) DEFAULT '0' COMMENT '正式定密日期',
  `fileDispatchTime` bigint(20) DEFAULT '0' COMMENT '文件签发日期',
  `fileLevelChangeTime` bigint(20) DEFAULT '0' COMMENT '文件密级变更日期(末次)',
  `fileDecryptTime` bigint(20) DEFAULT '0' COMMENT '文件解密日期',
  `description` varchar(1024) DEFAULT '' COMMENT '文件属性详情',
  `majorUnit` varchar(256) DEFAULT '' COMMENT '主定密单位 JSONObject{unitNo,name}',
  `minorUnit` varchar(4096) DEFAULT '' COMMENT '辅助定密单位 JSONArray[unitNo,name]',
  `basis` TEXT COMMENT '定密依据 JSON[basisLevel,basisContent]',
  `basisType` int(11) DEFAULT '0' COMMENT '定密依据类型 0确定性定密 1不明确事项 2无权定密事项 3派生定密',
  `basisDesc` varchar(4096) DEFAULT '' COMMENT '定密依据描述',
  `fileSize` bigint(20) DEFAULT '0' COMMENT '文件大小 字节',
  `fileMd5` varchar(40) DEFAULT '' COMMENT '文件MD5',
  `fileDispatchExpect` varchar(1024) DEFAULT '' COMMENT '知悉范围描述',
  `issueNumber` varchar(50) DEFAULT '' COMMENT '文件文号',
  `docNumber` int(11) DEFAULT '0' COMMENT '文件份号',
  `duplicationAmount` int(11) DEFAULT '0' COMMENT '文件份数 分发总数量',
  `fileExtension` varchar(4096) DEFAULT '' COMMENT '文件其他扩展属性',
  `contentRead` int(11) DEFAULT '0' COMMENT '是否允许内容阅读 1是 0否',
  `contentPrint` int(11) DEFAULT '0' COMMENT '是否允许内容打印 1是 0否',
  `contentPrintHideWater` int(11) DEFAULT '0' COMMENT '是否允许打印隐藏水印 1是 0否',
  `contentModify` int(11) DEFAULT '0' COMMENT '是否允许内容修改 1是 0否',
  `contentScreenShot` int(11) DEFAULT '0' COMMENT '是否允许内容截屏 1是 0否',
  `contentCopy` int(11) DEFAULT '0' COMMENT '是否允许内容拷贝 粘贴约束 1是 0否',
  `fileCopy` int(11) DEFAULT '0' COMMENT '是否允许文件拷贝 1是 0否',
  `fileSaveCopy` int(11) DEFAULT '0' COMMENT '是否允许文件另存副本 1是 0否',
  `history` varchar(4096) DEFAULT '' COMMENT '文件管理历史 JSONObject{time,uid,levelAltered,durationAltered,levelBefore,durationBefore,description}',
  `business` varchar(1024) DEFAULT '' COMMENT '业务属性',
  `outOfDate` int(11) DEFAULT '0' COMMENT '是否过期 1是 0否',
  PRIMARY KEY (`pkFid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of File
-- ----------------------------

-- ----------------------------
-- Table structure for `FileAccessScope`
-- ----------------------------
DROP TABLE IF EXISTS `FileAccessScope`;
CREATE TABLE `FileAccessScope` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fkFid` varchar(40) DEFAULT NULL COMMENT '文件ID',
  `unitNo` varchar(40) NOT NULL COMMENT '定密单位编号',
  `fkUid` varchar(40) DEFAULT NULL COMMENT '文件分发用户ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of FileAccessScope
-- ----------------------------

-- ----------------------------
-- Table structure for `FileAnalysis`
-- ----------------------------
DROP TABLE IF EXISTS `FileAnalysis`;
CREATE TABLE `FileAnalysis` (
  `analysisId` varchar(40) NOT NULL COMMENT '分析ID',
  `fkFid` varchar(40) DEFAULT NULL COMMENT '文件ID',
  `md5` varchar(40) DEFAULT '' COMMENT '文件MD5',
  `fileName` varchar(256) DEFAULT '' COMMENT '文件名',
  `filePath` varchar(1024) DEFAULT '' COMMENT '文件路径',
  `keywordResult` TEXT COMMENT '检测结果',
  `description` varchar(256) DEFAULT '' COMMENT '描述建议',
  `execState` int(11) DEFAULT '0' COMMENT '执行状态 0未执行 1正在执行 2已完成 3执行失败',
  PRIMARY KEY (`analysisId`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of FileAnalysis
-- ----------------------------

-- ----------------------------
-- Table structure for `FileDRM`
-- ----------------------------
DROP TABLE IF EXISTS `FileDRM`;
CREATE TABLE `FileDRM` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fkFid` varchar(40) DEFAULT NULL COMMENT '文件ID',
  `fkVarId` varchar(40) DEFAULT NULL COMMENT '用户ID/组ID/部门ID',
  `varIdType` int(11) DEFAULT '0' COMMENT 'varId类型 1用户ID 2组ID 3部门ID',
  `contentRead` int(11) DEFAULT '0' COMMENT '是否允许内容阅读 1是 0否',
  `contentPrint` int(11) DEFAULT '0' COMMENT '是否允许内容打印 1是 0否',
  `contentPrintHideWater` int(11) DEFAULT '0' COMMENT '是否允许打印隐藏水印 1是 0否',
  `contentModify` int(11) DEFAULT '0' COMMENT '是否允许内容修改 1是 0否',
  `contentScreenShot` int(11) DEFAULT '0' COMMENT '是否允许内容截屏 1是 0否',
  `contentCopy` int(11) DEFAULT '0' COMMENT '是否允许内容拷贝=粘贴约束 1是 0否',
  `fileCopy` int(11) DEFAULT '0' COMMENT '是否允许文件拷贝 1是 0否',
  `fileSaveCopy` int(11) DEFAULT '0' COMMENT '是否允许文件另存副本 1是 0否',
  `fileAuthorization` int(11) DEFAULT '0' COMMENT '是否允许文件授权 1是 0否 2允许提审批',
  `fileLevelDecide` int(11) DEFAULT '0' COMMENT '是否允许文件定密 1是 0否 2允许提审批',
  `fileLevelChange` int(11) DEFAULT '0' COMMENT '是否允许密级变更 1是 0否 2允许提审批',
  `fileDispatch` int(11) DEFAULT '0' COMMENT '是否允许文件分发 1是 0否 2允许提审批',
  `fileDecrypt` int(11) DEFAULT '0' COMMENT '是否允许文件解密 1是 0否 2允许提审批',
  `fileUnbunding` int(11) DEFAULT '0' COMMENT '是否允许文件解绑 1是 0否 2允许提审批',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of FileDRM
-- ----------------------------

-- ----------------------------
-- Table structure for `FileLevelAccessPolicy`
-- ----------------------------
DROP TABLE IF EXISTS `FileLevelAccessPolicy`;
CREATE TABLE `FileLevelAccessPolicy` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fileLevel` int(11) DEFAULT '0' COMMENT '文件密级 0公开 1内部 2秘密 3机密 4绝密',
  `userLevel` int(11) DEFAULT '0' COMMENT '用户密级 1一般 2重要 3核心',
  `clientLevel` int(11) DEFAULT '0' COMMENT '(字段废弃)终端密级 0公开 1内部 2秘密 3机密 4绝密',
  `enable` int(11) DEFAULT '0' COMMENT '是否允许访问 1是 0否',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of FileLevelAccessPolicy
-- ----------------------------
INSERT INTO `FileLevelAccessPolicy` VALUES ('1', '0', '1', '0', '1');
INSERT INTO `FileLevelAccessPolicy` VALUES ('2', '0', '2', '0', '1');
INSERT INTO `FileLevelAccessPolicy` VALUES ('3', '0', '3', '0', '1');
INSERT INTO `FileLevelAccessPolicy` VALUES ('4', '1', '1', '0', '1');
INSERT INTO `FileLevelAccessPolicy` VALUES ('5', '1', '2', '0', '1');
INSERT INTO `FileLevelAccessPolicy` VALUES ('6', '1', '3', '0', '1');
INSERT INTO `FileLevelAccessPolicy` VALUES ('7', '2', '1', '0', '1');
INSERT INTO `FileLevelAccessPolicy` VALUES ('8', '2', '2', '0', '1');
INSERT INTO `FileLevelAccessPolicy` VALUES ('9', '2', '3', '0', '1');
INSERT INTO `FileLevelAccessPolicy` VALUES ('10', '3', '1', '0', '0');
INSERT INTO `FileLevelAccessPolicy` VALUES ('11', '3', '2', '0', '1');
INSERT INTO `FileLevelAccessPolicy` VALUES ('12', '3', '3', '0', '1');
INSERT INTO `FileLevelAccessPolicy` VALUES ('13', '4', '1', '0', '0');
INSERT INTO `FileLevelAccessPolicy` VALUES ('14', '4', '2', '0', '0');
INSERT INTO `FileLevelAccessPolicy` VALUES ('15', '4', '3', '0', '1');

-- ----------------------------
-- Table structure for `FileLevelChangeHistory`
-- ----------------------------
DROP TABLE IF EXISTS `FileLevelChangeHistory`;
CREATE TABLE `FileLevelChangeHistory` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fkFid` varchar(40) DEFAULT NULL COMMENT '文件ID',
  `fkUid` varchar(40) DEFAULT NULL COMMENT '密级变更申请人用户ID',
  `fkFileLevelDecideUid` varchar(40) DEFAULT NULL COMMENT '定密责任人用户ID',
  `lastLevel` int(11) DEFAULT '0' COMMENT '变更前密级 0公开 1内部 2秘密 3机密 4绝密',
  `description` varchar(1024) DEFAULT '' COMMENT '密级变更描述',
  `fileLevelChangeTime` bigint(20) DEFAULT '0' COMMENT '文件密级变更日期',
  `validPeriod` varchar(20) DEFAULT '' COMMENT '保密期限 周期 格式yymmdd',
  `lastDurationType` int(11) DEFAULT '0' COMMENT '保密期限 类型 0不限 1长期 2期限 3解密日期 4条件',
  `lastDurationDescription` varchar(1024) DEFAULT '' COMMENT '保密期限 描述 单位毫秒',
  `lastFileLevelDecideTime` bigint(20) DEFAULT '0' COMMENT '保密期限 正式定密日期',
  `lastFileDecryptTime` bigint(20) DEFAULT '0' COMMENT '保密期限 文件解密日期',
  `levelAltered` int(11) DEFAULT '0' COMMENT '是否变更密级 1是 0否',
  `durationAltered` int(11) DEFAULT '0' COMMENT '是否变更保密期限 1是 0否',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of FileLevelChangeHistory
-- ----------------------------

-- ----------------------------
-- Table structure for `FileLevelDecideBasis`
-- ----------------------------
DROP TABLE IF EXISTS `FileLevelDecideBasis`;
CREATE TABLE `FileLevelDecideBasis` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `basisType` int(11) DEFAULT '0' COMMENT '定密依据类型 0确定性 1不明确 2无权定密 3派生',
  `basisLevel` int(11) DEFAULT '0' COMMENT '文件密级 0公开 1内部 2秘密 3机密 4绝密',
  `basisItem` varchar(1024) DEFAULT '' COMMENT '定密依据事项',
  `basisClass` varchar(256) DEFAULT '' COMMENT '定密依据业务分类',
  `basisName` varchar(256) DEFAULT '' COMMENT '定密依据依据名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of FileLevelDecideBasis
-- ----------------------------

-- ----------------------------
-- Table structure for `FileLevelDecidePolicy`
-- ----------------------------
DROP TABLE IF EXISTS `FileLevelDecidePolicy`;
CREATE TABLE `FileLevelDecidePolicy` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fileState` int(11) DEFAULT '0' COMMENT '定密状态 1预定密 2正式定密 3文件签发 4密级变更 5文件解密',
  `fileLevel` int(11) DEFAULT '0' COMMENT '文件密级 0公开 1内部 2秘密 3机密 4绝密',
  `roleType` int(11) DEFAULT '0' COMMENT '定密角色类型 1文件起草人 2定密责任人 3文件签发人 4分发使用人',
  `contentRead` int(11) DEFAULT '0' COMMENT '是否允许内容阅读 1是 0否',
  `contentPrint` int(11) DEFAULT '0' COMMENT '是否允许内容打印 1是 0否',
  `contentPrintHideWater` int(11) DEFAULT '0' COMMENT '是否允许打印隐藏水印 1是 0否',
  `contentModify` int(11) DEFAULT '0' COMMENT '是否允许内容修改 1是 0否',
  `contentScreenShot` int(11) DEFAULT '0' COMMENT '是否允许内容截屏 1是 0否',
  `contentCopy` int(11) DEFAULT '0' COMMENT '是否允许内容拷贝=粘贴约束 1是 0否',
  `fileCopy` int(11) DEFAULT '0' COMMENT '是否允许文件拷贝 1是 0否',
  `fileSaveCopy` int(11) DEFAULT '0' COMMENT '是否允许文件另存副本 1是 0否',
  `fileAuthorization` int(11) DEFAULT '0' COMMENT '是否允许文件授权 1是 0否 2允许提审批',
  `fileLevelDecide` int(11) DEFAULT '0' COMMENT '是否允许文件定密 1是 0否 2允许提审批',
  `fileLevelChange` int(11) DEFAULT '0' COMMENT '是否允许密级变更 1是 0否 2允许提审批',
  `fileDispatch` int(11) DEFAULT '0' COMMENT '是否允许文件分发 1是 0否 2允许提审批',
  `fileDecrypt` int(11) DEFAULT '0' COMMENT '是否允许文件解密 1是 0否 2允许提审批',
  `fileUnbunding` int(11) DEFAULT '0' COMMENT '是否允许文件解绑 1是 0否 2允许提审批',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of FileLevelDecidePolicy
-- ----------------------------
INSERT INTO `FileLevelDecidePolicy` VALUES ('1', '1', '0', '1', '1', '1', '0', '1', '1', '1', '1', '1', '1', '2', '0', '0', '0', '2');
INSERT INTO `FileLevelDecidePolicy` VALUES ('2', '1', '1', '1', '1', '1', '0', '1', '1', '1', '1', '1', '1', '2', '0', '0', '0', '2');
INSERT INTO `FileLevelDecidePolicy` VALUES ('3', '1', '2', '1', '1', '1', '0', '1', '1', '1', '1', '1', '1', '2', '0', '0', '0', '2');
INSERT INTO `FileLevelDecidePolicy` VALUES ('4', '1', '3', '1', '1', '1', '0', '1', '1', '1', '1', '1', '1', '2', '0', '0', '0', '2');
INSERT INTO `FileLevelDecidePolicy` VALUES ('5', '1', '4', '1', '1', '1', '0', '1', '0', '0', '0', '0', '1', '2', '0', '0', '0', '2');
INSERT INTO `FileLevelDecidePolicy` VALUES ('6', '2', '0', '1', '1', '0', '0', '0', '1', '0', '1', '1', '0', '0', '0', '0', '0', '2');
INSERT INTO `FileLevelDecidePolicy` VALUES ('7', '2', '1', '1', '1', '0', '0', '0', '1', '0', '1', '1', '0', '0', '0', '0', '0', '2');
INSERT INTO `FileLevelDecidePolicy` VALUES ('8', '2', '2', '1', '1', '0', '0', '0', '1', '0', '1', '1', '0', '0', '0', '0', '0', '2');
INSERT INTO `FileLevelDecidePolicy` VALUES ('9', '2', '3', '1', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '2');
INSERT INTO `FileLevelDecidePolicy` VALUES ('10', '2', '4', '1', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '2');
INSERT INTO `FileLevelDecidePolicy` VALUES ('11', '3', '0', '1', '1', '0', '0', '0', '1', '0', '1', '1', '0', '0', '2', '0', '2', '2');
INSERT INTO `FileLevelDecidePolicy` VALUES ('12', '3', '1', '1', '1', '0', '0', '0', '1', '0', '1', '1', '0', '0', '2', '0', '2', '2');
INSERT INTO `FileLevelDecidePolicy` VALUES ('13', '3', '2', '1', '1', '0', '0', '0', '1', '0', '1', '1', '0', '0', '2', '0', '2', '2');
INSERT INTO `FileLevelDecidePolicy` VALUES ('14', '3', '3', '1', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '2', '0', '2', '2');
INSERT INTO `FileLevelDecidePolicy` VALUES ('15', '3', '4', '1', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '2', '0', '2', '2');
INSERT INTO `FileLevelDecidePolicy` VALUES ('16', '4', '0', '1', '1', '0', '0', '0', '1', '0', '1', '1', '0', '0', '2', '0', '2', '2');
INSERT INTO `FileLevelDecidePolicy` VALUES ('17', '4', '1', '1', '1', '0', '0', '0', '1', '0', '1', '1', '0', '0', '2', '0', '2', '2');
INSERT INTO `FileLevelDecidePolicy` VALUES ('18', '4', '2', '1', '1', '0', '0', '0', '1', '0', '1', '1', '0', '0', '2', '0', '2', '2');
INSERT INTO `FileLevelDecidePolicy` VALUES ('19', '4', '3', '1', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '2', '0', '2', '2');
INSERT INTO `FileLevelDecidePolicy` VALUES ('20', '4', '4', '1', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '2', '0', '2', '2');
INSERT INTO `FileLevelDecidePolicy` VALUES ('21', '5', '0', '1', '1', '0', '0', '0', '1', '1', '1', '1', '0', '0', '0', '0', '0', '2');
INSERT INTO `FileLevelDecidePolicy` VALUES ('22', '5', '1', '1', '1', '0', '0', '0', '1', '1', '1', '1', '0', '0', '0', '0', '0', '2');
INSERT INTO `FileLevelDecidePolicy` VALUES ('23', '5', '2', '1', '1', '0', '0', '1', '1', '1', '1', '1', '0', '0', '0', '0', '0', '2');
INSERT INTO `FileLevelDecidePolicy` VALUES ('24', '5', '3', '1', '1', '0', '0', '1', '1', '0', '0', '0', '0', '0', '0', '0', '0', '2');
INSERT INTO `FileLevelDecidePolicy` VALUES ('25', '5', '4', '1', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '2');
INSERT INTO `FileLevelDecidePolicy` VALUES ('26', '1', '0', '2', '1', '1', '0', '1', '1', '1', '1', '1', '0', '3', '0', '0', '0', '3');
INSERT INTO `FileLevelDecidePolicy` VALUES ('27', '1', '1', '2', '1', '1', '0', '1', '1', '1', '1', '1', '0', '3', '0', '0', '0', '3');
INSERT INTO `FileLevelDecidePolicy` VALUES ('28', '1', '2', '2', '1', '1', '0', '1', '1', '1', '1', '1', '0', '3', '0', '0', '0', '3');
INSERT INTO `FileLevelDecidePolicy` VALUES ('29', '1', '3', '2', '1', '1', '0', '1', '1', '1', '1', '1', '0', '3', '0', '0', '0', '3');
INSERT INTO `FileLevelDecidePolicy` VALUES ('30', '1', '4', '2', '1', '1', '0', '1', '1', '1', '1', '1', '0', '3', '0', '0', '0', '3');
INSERT INTO `FileLevelDecidePolicy` VALUES ('31', '2', '0', '2', '1', '1', '0', '1', '1', '1', '1', '1', '0', '3', '0', '2', '0', '3');
INSERT INTO `FileLevelDecidePolicy` VALUES ('32', '2', '1', '2', '1', '1', '0', '1', '1', '1', '1', '1', '0', '3', '0', '2', '0', '3');
INSERT INTO `FileLevelDecidePolicy` VALUES ('33', '2', '2', '2', '1', '1', '0', '1', '1', '1', '1', '1', '0', '3', '0', '2', '0', '3');
INSERT INTO `FileLevelDecidePolicy` VALUES ('34', '2', '3', '2', '1', '1', '0', '1', '1', '1', '1', '1', '0', '3', '0', '2', '0', '3');
INSERT INTO `FileLevelDecidePolicy` VALUES ('35', '2', '4', '2', '1', '1', '0', '1', '1', '1', '1', '1', '0', '3', '0', '2', '0', '3');
INSERT INTO `FileLevelDecidePolicy` VALUES ('36', '3', '0', '2', '1', '1', '0', '1', '1', '1', '1', '1', '0', '0', '3', '0', '3', '3');
INSERT INTO `FileLevelDecidePolicy` VALUES ('37', '3', '1', '2', '1', '1', '0', '1', '1', '1', '1', '1', '0', '0', '3', '0', '3', '3');
INSERT INTO `FileLevelDecidePolicy` VALUES ('38', '3', '2', '2', '1', '1', '0', '1', '1', '1', '1', '1', '0', '0', '3', '0', '3', '3');
INSERT INTO `FileLevelDecidePolicy` VALUES ('39', '3', '3', '2', '1', '1', '0', '1', '1', '1', '1', '1', '0', '0', '3', '0', '3', '3');
INSERT INTO `FileLevelDecidePolicy` VALUES ('40', '3', '4', '2', '1', '1', '0', '1', '1', '1', '1', '1', '0', '0', '3', '0', '3', '3');
INSERT INTO `FileLevelDecidePolicy` VALUES ('41', '4', '0', '2', '1', '1', '0', '1', '1', '1', '1', '1', '0', '0', '3', '0', '3', '3');
INSERT INTO `FileLevelDecidePolicy` VALUES ('42', '4', '1', '2', '1', '1', '0', '1', '1', '1', '1', '1', '0', '0', '3', '0', '3', '3');
INSERT INTO `FileLevelDecidePolicy` VALUES ('43', '4', '2', '2', '1', '1', '0', '1', '1', '1', '1', '1', '0', '0', '3', '0', '3', '3');
INSERT INTO `FileLevelDecidePolicy` VALUES ('44', '4', '3', '2', '1', '1', '0', '1', '1', '1', '1', '1', '0', '0', '3', '0', '3', '3');
INSERT INTO `FileLevelDecidePolicy` VALUES ('45', '4', '4', '2', '1', '1', '0', '1', '1', '1', '1', '1', '0', '0', '3', '0', '3', '3');
INSERT INTO `FileLevelDecidePolicy` VALUES ('46', '5', '0', '2', '1', '1', '0', '0', '1', '1', '1', '1', '0', '0', '0', '0', '0', '3');
INSERT INTO `FileLevelDecidePolicy` VALUES ('47', '5', '1', '2', '1', '1', '0', '0', '1', '1', '1', '1', '0', '0', '0', '0', '0', '3');
INSERT INTO `FileLevelDecidePolicy` VALUES ('48', '5', '2', '2', '1', '1', '0', '0', '1', '1', '1', '1', '0', '0', '0', '0', '0', '3');
INSERT INTO `FileLevelDecidePolicy` VALUES ('49', '5', '3', '2', '1', '1', '0', '0', '1', '1', '1', '1', '0', '0', '0', '0', '0', '3');
INSERT INTO `FileLevelDecidePolicy` VALUES ('50', '5', '4', '2', '1', '1', '0', '0', '1', '1', '1', '1', '0', '0', '0', '0', '0', '3');
INSERT INTO `FileLevelDecidePolicy` VALUES ('51', '1', '0', '3', '1', '1', '0', '0', '1', '0', '0', '0', '1', '2', '0', '0', '0', '2');
INSERT INTO `FileLevelDecidePolicy` VALUES ('52', '1', '1', '3', '1', '1', '0', '0', '1', '0', '0', '0', '1', '2', '0', '0', '0', '2');
INSERT INTO `FileLevelDecidePolicy` VALUES ('53', '1', '2', '3', '1', '1', '0', '0', '1', '0', '0', '0', '1', '2', '0', '0', '0', '2');
INSERT INTO `FileLevelDecidePolicy` VALUES ('54', '1', '3', '3', '1', '1', '0', '0', '1', '0', '0', '0', '1', '2', '0', '0', '0', '2');
INSERT INTO `FileLevelDecidePolicy` VALUES ('55', '1', '4', '3', '1', '1', '0', '0', '1', '0', '0', '0', '1', '2', '0', '0', '0', '2');
INSERT INTO `FileLevelDecidePolicy` VALUES ('56', '2', '0', '3', '1', '1', '0', '0', '1', '0', '0', '0', '1', '0', '0', '3', '0', '2');
INSERT INTO `FileLevelDecidePolicy` VALUES ('57', '2', '1', '3', '1', '1', '0', '0', '1', '0', '0', '0', '1', '0', '0', '3', '0', '2');
INSERT INTO `FileLevelDecidePolicy` VALUES ('58', '2', '2', '3', '1', '1', '0', '0', '1', '0', '0', '0', '1', '0', '0', '3', '0', '2');
INSERT INTO `FileLevelDecidePolicy` VALUES ('59', '2', '3', '3', '1', '1', '0', '0', '1', '0', '0', '0', '1', '0', '0', '3', '0', '2');
INSERT INTO `FileLevelDecidePolicy` VALUES ('60', '2', '4', '3', '1', '1', '0', '0', '1', '0', '0', '0', '1', '0', '0', '3', '0', '2');
INSERT INTO `FileLevelDecidePolicy` VALUES ('61', '3', '0', '3', '1', '1', '0', '0', '1', '0', '0', '0', '1', '0', '2', '3', '2', '2');
INSERT INTO `FileLevelDecidePolicy` VALUES ('62', '3', '1', '3', '1', '1', '0', '0', '1', '0', '0', '0', '1', '0', '2', '3', '2', '2');
INSERT INTO `FileLevelDecidePolicy` VALUES ('63', '3', '2', '3', '1', '1', '0', '0', '1', '0', '0', '0', '1', '0', '2', '3', '2', '2');
INSERT INTO `FileLevelDecidePolicy` VALUES ('64', '3', '3', '3', '1', '1', '0', '0', '1', '0', '0', '0', '1', '0', '2', '3', '2', '2');
INSERT INTO `FileLevelDecidePolicy` VALUES ('65', '3', '4', '3', '1', '1', '0', '0', '1', '0', '0', '0', '1', '0', '2', '3', '2', '2');
INSERT INTO `FileLevelDecidePolicy` VALUES ('66', '4', '0', '3', '1', '1', '0', '0', '1', '0', '0', '0', '1', '0', '2', '3', '2', '2');
INSERT INTO `FileLevelDecidePolicy` VALUES ('67', '4', '1', '3', '1', '1', '0', '0', '1', '0', '0', '0', '1', '0', '2', '3', '2', '2');
INSERT INTO `FileLevelDecidePolicy` VALUES ('68', '4', '2', '3', '1', '1', '0', '0', '1', '0', '0', '0', '1', '0', '2', '3', '2', '2');
INSERT INTO `FileLevelDecidePolicy` VALUES ('69', '4', '3', '3', '1', '1', '0', '0', '1', '0', '0', '0', '1', '0', '2', '3', '2', '2');
INSERT INTO `FileLevelDecidePolicy` VALUES ('70', '4', '4', '3', '1', '1', '0', '0', '1', '0', '0', '0', '1', '0', '2', '3', '2', '2');
INSERT INTO `FileLevelDecidePolicy` VALUES ('71', '5', '0', '3', '1', '1', '0', '0', '1', '0', '0', '0', '1', '0', '0', '0', '0', '2');
INSERT INTO `FileLevelDecidePolicy` VALUES ('72', '5', '1', '3', '1', '1', '0', '0', '1', '0', '0', '0', '1', '0', '0', '0', '0', '2');
INSERT INTO `FileLevelDecidePolicy` VALUES ('73', '5', '2', '3', '1', '1', '0', '0', '1', '0', '0', '0', '1', '0', '0', '0', '0', '2');
INSERT INTO `FileLevelDecidePolicy` VALUES ('74', '5', '3', '3', '1', '1', '0', '0', '1', '0', '0', '0', '1', '0', '0', '0', '0', '2');
INSERT INTO `FileLevelDecidePolicy` VALUES ('75', '5', '4', '3', '1', '1', '0', '0', '1', '0', '0', '0', '1', '0', '0', '0', '0', '2');
INSERT INTO `FileLevelDecidePolicy` VALUES ('76', '1', '0', '4', '1', '1', '0', '0', '0', '0', '0', '0', '0', '2', '0', '0', '0', '2');
INSERT INTO `FileLevelDecidePolicy` VALUES ('77', '1', '1', '4', '1', '1', '0', '0', '0', '0', '0', '0', '0', '2', '0', '0', '0', '2');
INSERT INTO `FileLevelDecidePolicy` VALUES ('78', '1', '2', '4', '1', '1', '0', '0', '0', '0', '0', '0', '0', '2', '0', '0', '0', '2');
INSERT INTO `FileLevelDecidePolicy` VALUES ('79', '1', '3', '4', '1', '0', '0', '0', '0', '0', '0', '0', '0', '2', '0', '0', '0', '2');
INSERT INTO `FileLevelDecidePolicy` VALUES ('80', '1', '4', '4', '1', '0', '0', '0', '0', '0', '0', '0', '0', '2', '0', '0', '0', '2');
INSERT INTO `FileLevelDecidePolicy` VALUES ('81', '2', '0', '4', '1', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '2');
INSERT INTO `FileLevelDecidePolicy` VALUES ('82', '2', '1', '4', '1', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '2');
INSERT INTO `FileLevelDecidePolicy` VALUES ('83', '2', '2', '4', '1', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '2');
INSERT INTO `FileLevelDecidePolicy` VALUES ('84', '2', '3', '4', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '2');
INSERT INTO `FileLevelDecidePolicy` VALUES ('85', '2', '4', '4', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '2');
INSERT INTO `FileLevelDecidePolicy` VALUES ('86', '3', '0', '4', '1', '1', '0', '0', '0', '0', '0', '0', '0', '0', '2', '0', '2', '2');
INSERT INTO `FileLevelDecidePolicy` VALUES ('87', '3', '1', '4', '1', '1', '0', '0', '0', '0', '0', '0', '0', '0', '2', '0', '2', '2');
INSERT INTO `FileLevelDecidePolicy` VALUES ('88', '3', '2', '4', '1', '1', '0', '0', '0', '0', '0', '0', '0', '0', '2', '0', '2', '2');
INSERT INTO `FileLevelDecidePolicy` VALUES ('89', '3', '3', '4', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '2', '0', '2', '2');
INSERT INTO `FileLevelDecidePolicy` VALUES ('90', '3', '4', '4', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '2', '0', '2', '2');
INSERT INTO `FileLevelDecidePolicy` VALUES ('91', '4', '0', '4', '1', '1', '0', '0', '0', '0', '0', '0', '0', '0', '2', '0', '2', '2');
INSERT INTO `FileLevelDecidePolicy` VALUES ('92', '4', '1', '4', '1', '1', '0', '0', '0', '0', '0', '0', '0', '0', '2', '0', '2', '2');
INSERT INTO `FileLevelDecidePolicy` VALUES ('93', '4', '2', '4', '1', '1', '0', '0', '0', '0', '0', '0', '0', '0', '2', '0', '2', '2');
INSERT INTO `FileLevelDecidePolicy` VALUES ('94', '4', '3', '4', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '2', '0', '2', '2');
INSERT INTO `FileLevelDecidePolicy` VALUES ('95', '4', '4', '4', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '2', '0', '2', '2');
INSERT INTO `FileLevelDecidePolicy` VALUES ('96', '5', '0', '4', '1', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '2');
INSERT INTO `FileLevelDecidePolicy` VALUES ('97', '5', '1', '4', '1', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '2');
INSERT INTO `FileLevelDecidePolicy` VALUES ('98', '5', '2', '4', '1', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '2');
INSERT INTO `FileLevelDecidePolicy` VALUES ('99', '5', '3', '4', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '2');
INSERT INTO `FileLevelDecidePolicy` VALUES ('100', '5', '4', '4', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '2');

-- ----------------------------
-- Table structure for `FileLevelDecideUnit`
-- ----------------------------
DROP TABLE IF EXISTS `FileLevelDecideUnit`;
CREATE TABLE `FileLevelDecideUnit` (
  `unitNo` varchar(40) NOT NULL COMMENT '定密单位编号',
  `name` varchar(256) DEFAULT '' COMMENT '定密单位名称',
  `description` varchar(1024) DEFAULT '' COMMENT '定密单位描述',
  `major` int(11) DEFAULT '0' COMMENT '是否是主要定密单位 1是 0否 主定密单位只有一个',
  PRIMARY KEY (`unitNo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of FileLevelDecideUnit
-- ----------------------------
INSERT INTO `FileLevelDecideUnit` VALUES ('10001', '主定密单位', '主定密单位', '1');

-- ----------------------------
-- Table structure for `FileLevelManagementLog`
-- ----------------------------
DROP TABLE IF EXISTS `FileLevelManagementLog`;
CREATE TABLE `FileLevelManagementLog` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fkUid` varchar(40) DEFAULT NULL COMMENT '操作用户ID',
  `createTime` bigint(20) DEFAULT '0' COMMENT '入库时间',
  `operateTime` bigint(20) DEFAULT '0' COMMENT '操作时间',
  `operateType` varchar(256) DEFAULT '' COMMENT '操作类型',
  `operateDescription` varchar(1024) DEFAULT '' COMMENT '操作详情',
  `operateResult` int(11) DEFAULT '0' COMMENT '操作结果',
  `ip` varchar(40) DEFAULT '' COMMENT '操作机器IP地址',
  `reserve` varchar(1024) DEFAULT '' COMMENT '预留',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of FileLevelManagementLog
-- ----------------------------

-- ----------------------------
-- Table structure for `FileLevelValidPeriod`
-- ----------------------------
DROP TABLE IF EXISTS `FileLevelValidPeriod`;
CREATE TABLE `FileLevelValidPeriod` (
  `filelevel` int(11) NOT NULL COMMENT '文件密级 0公开 1内部 2秘密 3机密 4绝密',
  `validPeriod` varchar(20) DEFAULT '' COMMENT '保密期限 周期 格式yymmdd',
  `isDesensity` int(11) DEFAULT '0' COMMENT '该密级是否进行脱敏 0否 1是',
  `showWatermark` int(11) DEFAULT '0' COMMENT '该密级是否是否显示屏幕水印 0否 1是',
  `keyword` TEXT COMMENT '密点分析关键词，|分割',
  PRIMARY KEY (`filelevel`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of FileLevelValidPeriod
-- ----------------------------
INSERT INTO `FileLevelValidPeriod` VALUES ('0', '000000', '0', '0', '');
INSERT INTO `FileLevelValidPeriod` VALUES ('1', '000000', '0', '0', '');
INSERT INTO `FileLevelValidPeriod` VALUES ('2', '100000', '1', '0', '');
INSERT INTO `FileLevelValidPeriod` VALUES ('3', '200000', '1', '0', '');
INSERT INTO `FileLevelValidPeriod` VALUES ('4', '300000', '1', '0', '');

-- ----------------------------
-- Table structure for `Group`
-- ----------------------------
DROP TABLE IF EXISTS `Group`;
CREATE TABLE `Group` (
  `pkGid` varchar(40) NOT NULL COMMENT '组ID',
  `fkDid` varchar(40) DEFAULT NULL COMMENT '所属部门ID',
  `name` varchar(256) DEFAULT '' COMMENT '组名称',
  `description` varchar(1024) DEFAULT '' COMMENT '组描述',
  `createTime` bigint(20) DEFAULT '0' COMMENT '创建日期',
  `groupState` int(11) DEFAULT '0' COMMENT '状态 1已删除',
  PRIMARY KEY (`pkGid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of Group
-- ----------------------------

-- ----------------------------
-- Table structure for `License`
-- ----------------------------
DROP TABLE IF EXISTS `License`;
CREATE TABLE `License` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `createTime` bigint(20) DEFAULT '0' COMMENT '导入日期',
  `license` varchar(2048) NOT NULL COMMENT '授权码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of License
-- ----------------------------

-- ----------------------------
-- Table structure for `MarkKey`
-- ----------------------------
DROP TABLE IF EXISTS `MarkKey`;
CREATE TABLE `MarkKey` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fkAid` varchar(40) DEFAULT NULL COMMENT '创建人管理员ID',
  `pubKey` varchar(1024) DEFAULT '' COMMENT '密标公钥',
  `priKey` varchar(1024) DEFAULT '' COMMENT '密标私钥',
  `markVersion` int(11) DEFAULT '0' COMMENT '密标密钥版本',
  `keyId` varchar(40) DEFAULT '' COMMENT '密钥ID',
  `keyName` varchar(1024) DEFAULT '' COMMENT '密钥名称',
  `keyLength` int(11) DEFAULT '0' COMMENT '密钥长度',
  `createTime` bigint(20) DEFAULT '0' COMMENT '创建日期',
  `createFrom` int(11) DEFAULT '0' COMMENT '来源 0系统预置 1手动创建 2导入创建',
  `enable` int(11) DEFAULT '0' COMMENT '密钥是否启用 1是 0否',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of MarkKey
-- ----------------------------
INSERT INTO `MarkKey` VALUES ('1', 'aid-secadmin', 'MUMxREU0QzBGNzI0NUM5MTUyQkE0OTVDRDg3OTcwNTg=', 'MUMxREU0QzBGNzI0NUM5MTUyQkE0OTVDRDg3OTcwNTg=', '1', 'f66c03392d934e9581a165cc8b20d71d', '系统预置密钥', '32', '0', '0', '1');

-- ----------------------------
-- Table structure for `MarkKeyHistory`
-- ----------------------------
DROP TABLE IF EXISTS `MarkKeyHistory`;
CREATE TABLE `MarkKeyHistory` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `keyId` varchar(40) DEFAULT '' COMMENT '密钥ID',
  `keyName` varchar(1024) DEFAULT '' COMMENT '密钥名称',
  `createTime` bigint(20) DEFAULT '0' COMMENT '入库日期',
  `enableTime` bigint(20) DEFAULT '0' COMMENT '启用日期',
  `disableTime` bigint(20) DEFAULT '0' COMMENT '禁用日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of MarkKeyHistory
-- ----------------------------
INSERT INTO `MarkKeyHistory` VALUES ('1', 'f66c03392d934e9581a165cc8b20d71d', '系统预置密钥', '0', '0', '0');

-- ----------------------------
-- Table structure for `Middleware`
-- ----------------------------
DROP TABLE IF EXISTS `Middleware`;
CREATE TABLE `Middleware` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(265) DEFAULT '' COMMENT '中间件名称',
  `description` varchar(1024) DEFAULT '' COMMENT '中间件描述',
  `enable` int(11) DEFAULT '0' COMMENT '中间件是否启用(被动显示) 1是 0否',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of Middleware
-- ----------------------------
INSERT INTO `Middleware` VALUES ('1', '密级文件属性查询', '提供方法获取文件的密标属性信息，如文件标识、秘密等级等', '0');
INSERT INTO `Middleware` VALUES ('2', '文件标定密', '提供方法将文件标密或去密标', '0');
INSERT INTO `Middleware` VALUES ('3', '文件流转', '提供方法与密标平台对文件的分发范围数据做同步，以保证用户可以即时获取到被流转文件的访问策略。', '0');
INSERT INTO `Middleware` VALUES ('4', '用户标识映射', '提供方法与密标平台对用户标识做映射', '0');

-- ----------------------------
-- Table structure for `MiddlewareACL`
-- ----------------------------
DROP TABLE IF EXISTS `MiddlewareACL`;
CREATE TABLE `MiddlewareACL` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fkMiddlewareId` int(11) DEFAULT NULL COMMENT '中间件ID',
  `ip` varchar(40) DEFAULT '' COMMENT '第三方应用所在IP',
  `enable` int(11) DEFAULT '0' COMMENT '是否启用 1是 0否',
  `passwdEnable` int(11) DEFAULT '0' COMMENT '是否启用授权口令 1是 0否',
  `passwd` varchar(256) DEFAULT '' COMMENT '口令',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of MiddlewareACL
-- ----------------------------

-- ----------------------------
-- Table structure for `RiskLevelPolicy`
-- ----------------------------
DROP TABLE IF EXISTS `RiskLevelPolicy`;
CREATE TABLE `RiskLevelPolicy` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `operateType` varchar(256) DEFAULT '' COMMENT '操作类型',
  `riskLevel` int(11) DEFAULT '0' COMMENT '风险等级 1低 2中 3高',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of RiskLevelPolicy
-- ----------------------------
INSERT INTO `RiskLevelPolicy` VALUES ('1', '阅读', '1');
INSERT INTO `RiskLevelPolicy` VALUES ('2', '打印', '3');
INSERT INTO `RiskLevelPolicy` VALUES ('3', '编辑', '2');
INSERT INTO `RiskLevelPolicy` VALUES ('4', '截屏', '3');
INSERT INTO `RiskLevelPolicy` VALUES ('5', '拷贝', '2');
INSERT INTO `RiskLevelPolicy` VALUES ('6', '粘贴', '2');
INSERT INTO `RiskLevelPolicy` VALUES ('7', '删除', '1');
INSERT INTO `RiskLevelPolicy` VALUES ('8', '另存', '2');
INSERT INTO `RiskLevelPolicy` VALUES ('9', '改名', '2');
INSERT INTO `RiskLevelPolicy` VALUES ('10', '发送', '3');
INSERT INTO `RiskLevelPolicy` VALUES ('11', '预定密', '1');
INSERT INTO `RiskLevelPolicy` VALUES ('12', '正式定密', '1');
INSERT INTO `RiskLevelPolicy` VALUES ('13', '文件签发', '1');
INSERT INTO `RiskLevelPolicy` VALUES ('14', '密级变更', '1');
INSERT INTO `RiskLevelPolicy` VALUES ('15', '文件解密', '1');
INSERT INTO `RiskLevelPolicy` VALUES ('16', '密文还原', '1');
INSERT INTO `RiskLevelPolicy` VALUES ('17', '正式定密流程申请', '1');
INSERT INTO `RiskLevelPolicy` VALUES ('18', '文件签发流程申请', '1');
INSERT INTO `RiskLevelPolicy` VALUES ('19', '密级变更流程申请', '1');
INSERT INTO `RiskLevelPolicy` VALUES ('20', '文件解密流程申请', '1');
INSERT INTO `RiskLevelPolicy` VALUES ('21', '密文还原流程申请', '1');
INSERT INTO `RiskLevelPolicy` VALUES ('22', '正式定密流程审批', '1');
INSERT INTO `RiskLevelPolicy` VALUES ('23', '文件签发流程审批', '1');
INSERT INTO `RiskLevelPolicy` VALUES ('24', '密级变更流程审批', '1');
INSERT INTO `RiskLevelPolicy` VALUES ('25', '文件解密流程审批', '1');
INSERT INTO `RiskLevelPolicy` VALUES ('26', '密文还原流程审批', '1');
INSERT INTO `RiskLevelPolicy` VALUES ('27', '登录', '1');
INSERT INTO `RiskLevelPolicy` VALUES ('28', '注销', '1');
INSERT INTO `RiskLevelPolicy` VALUES ('29', '修改密码', '2');
INSERT INTO `RiskLevelPolicy` VALUES ('30', '重置用户密码', '2');
INSERT INTO `RiskLevelPolicy` VALUES ('31', '用户与组织管理', '2');
INSERT INTO `RiskLevelPolicy` VALUES ('32', '终端管理', '1');
INSERT INTO `RiskLevelPolicy` VALUES ('33', '用户角色管理', '2');
INSERT INTO `RiskLevelPolicy` VALUES ('34', '定密策略管理', '2');
INSERT INTO `RiskLevelPolicy` VALUES ('35', '配置管理', '1');
INSERT INTO `RiskLevelPolicy` VALUES ('36', '授权许可', '1');
INSERT INTO `RiskLevelPolicy` VALUES ('37', '组件管理', '1');
INSERT INTO `RiskLevelPolicy` VALUES ('38', '备份管理', '1');
INSERT INTO `RiskLevelPolicy` VALUES ('39', '集成管理', '1');
INSERT INTO `RiskLevelPolicy` VALUES ('40', '策略同步', '1');
INSERT INTO `RiskLevelPolicy` VALUES ('41', '附件下载', '2');
INSERT INTO `RiskLevelPolicy` VALUES ('42', '日志导出', '1');
INSERT INTO `RiskLevelPolicy` VALUES ('43', '日志归档', '1');

-- ----------------------------
-- Table structure for `Role`
-- ----------------------------
DROP TABLE IF EXISTS `Role`;
CREATE TABLE `Role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fkUid` varchar(40) DEFAULT NULL COMMENT '用户uid',
  `fkAgentUid` varchar(40) DEFAULT NULL COMMENT '代理人uid',
  `fkAgentInvalidTime` bigint(20) DEFAULT '0' COMMENT '代理人到期时间',
  `fileLevel` int(11) DEFAULT '0' COMMENT '操作密级 0公开 1内部 2秘密 3机密 4绝密 多选可位与操作',
  `roleType` int(11) DEFAULT '0' COMMENT '角色类型 2定密责任人 3文件签发人 4分发使用人 7签入人(特权) 8签出人(特权)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of Role
-- ----------------------------

-- ----------------------------
-- Table structure for `RoleScope`
-- ----------------------------
DROP TABLE IF EXISTS `RoleScope`;
CREATE TABLE `RoleScope` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fkRoleId` int(11) DEFAULT NULL COMMENT '角色信息ID',
  `fkVarId` varchar(40) DEFAULT NULL COMMENT '用户ID/组ID/部门ID',
  `varType` int(11) DEFAULT '0' COMMENT 'varId类型 1用户ID 2组ID 3部门ID',
  `varName` varchar(256) DEFAULT '' COMMENT '组织名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of RoleScope
-- ----------------------------

-- ----------------------------
-- Table structure for `StatisticsFile`
-- ----------------------------
DROP TABLE IF EXISTS `StatisticsFile`;
CREATE TABLE `StatisticsFile` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `createTime` bigint(20) DEFAULT '0' COMMENT '统计时间',
  `classifiedNum` bigint(20) DEFAULT '0' COMMENT '正式定密数量',
  `issuedNum` bigint(20) DEFAULT '0' COMMENT '文件签发数量',
  `classifiedChangeNum` bigint(20) DEFAULT '0' COMMENT '密级变更数量',
  `declassifiedNum` bigint(20) DEFAULT '0' COMMENT '文件解密数量',
  `outsourceNum` bigint(20) DEFAULT '0' COMMENT '文件外发数量',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of StatisticsFile
-- ----------------------------
INSERT INTO `StatisticsFile` VALUES ('1', '1483200000000', '1', '0', '0', '0', '0');
INSERT INTO `StatisticsFile` VALUES ('2', '1483200000000', '2', '0', '0', '0', '0');
INSERT INTO `StatisticsFile` VALUES ('3', '1483200000000', '1', '0', '0', '0', '0');
INSERT INTO `StatisticsFile` VALUES ('4', '1483200000000', '2', '0', '0', '0', '0');
INSERT INTO `StatisticsFile` VALUES ('5', '1485878400000', '3', '0', '0', '0', '0');
INSERT INTO `StatisticsFile` VALUES ('6', '1485878400000', '3', '0', '0', '0', '0');
INSERT INTO `StatisticsFile` VALUES ('7', '1485878400000', '4', '0', '0', '0', '0');
INSERT INTO `StatisticsFile` VALUES ('8', '1485878400000', '3', '0', '0', '0', '0');
INSERT INTO `StatisticsFile` VALUES ('9', '1488297600000', '5', '0', '0', '0', '0');
INSERT INTO `StatisticsFile` VALUES ('10', '1488297600000', '5', '0', '0', '0', '0');
INSERT INTO `StatisticsFile` VALUES ('11', '1488297600000', '6', '0', '0', '0', '0');
INSERT INTO `StatisticsFile` VALUES ('12', '1488297600000', '7', '0', '0', '0', '0');
INSERT INTO `StatisticsFile` VALUES ('13', '1490976000000', '1', '0', '0', '0', '0');
INSERT INTO `StatisticsFile` VALUES ('14', '1491062400000', '5', '0', '0', '0', '0');
INSERT INTO `StatisticsFile` VALUES ('15', '1491148800000', '8', '0', '0', '0', '0');

-- ----------------------------
-- Table structure for `StorageLocation`
-- ----------------------------
DROP TABLE IF EXISTS `StorageLocation`;
CREATE TABLE `StorageLocation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `domainName` varchar(256) DEFAULT '' COMMENT '域名 如果有域名使用域名',
  `ip` varchar(256) DEFAULT '' COMMENT 'IP 没有域名使用IP localhost表示本地',
  `port` varchar(256) DEFAULT '' COMMENT '端口',
  `filePath` varchar(256) DEFAULT '' COMMENT '路径',
  `protocol` varchar(256) DEFAULT '' COMMENT '协议 LOCAL HTTP HTTPS FTP..',
  `account` varchar(256) DEFAULT '' COMMENT '账号',
  `passwd` varchar(256) DEFAULT '' COMMENT '密码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of StorageLocation
-- ----------------------------
INSERT INTO `StorageLocation` VALUES ('1', '', '', '', 'C:\\DCMS\\cache', 'local', '', '');
INSERT INTO `StorageLocation` VALUES ('2', '', '', '', 'C:\\DCMS\\backup', 'local', '', '');
INSERT INTO `StorageLocation` VALUES ('3', '', '', '', 'C:\\DCMS\\patch', 'local', '', '');
INSERT INTO `StorageLocation` VALUES ('4', '', '', '', 'C:\\DCMS\\log', 'local', '', '');
INSERT INTO `StorageLocation` VALUES ('5', '', '', '', 'C:\\DCMS\\file0', 'local', '', '');
INSERT INTO `StorageLocation` VALUES ('6', '', '', '', 'C:\\DCMS\\file1', 'local', '', '');
INSERT INTO `StorageLocation` VALUES ('7', '', '', '', 'C:\\DCMS\\file2', 'local', '', '');
INSERT INTO `StorageLocation` VALUES ('8', '', '', '', 'C:\\DCMS\\file3', 'local', '', '');
INSERT INTO `StorageLocation` VALUES ('9', '', '', '', 'C:\\DCMS\\file4', 'local', '', '');

-- ----------------------------
-- Table structure for `SystemConfig`
-- ----------------------------
DROP TABLE IF EXISTS `SystemConfig`;
CREATE TABLE `SystemConfig` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fkBackupLocationId` int(11) DEFAULT NULL COMMENT '备份存储位置ID',
  `fkFileLevel1LocationId` int(11) DEFAULT NULL COMMENT '公开文件上传存储位置ID',
  `fkFileLevel2LocationId` int(11) DEFAULT NULL COMMENT '内部文件上传存储位置ID',
  `fkFileLevel3LocationId` int(11) DEFAULT NULL COMMENT '秘密文件上传存储位置ID',
  `fkFileLevel4LocationId` int(11) DEFAULT NULL COMMENT '机密文件上传存储位置ID',
  `fkFileLevel5LocationId` int(11) DEFAULT NULL COMMENT '绝密文件上传存储位置ID',
  `fkPatchLocationId` int(11) DEFAULT NULL COMMENT '补丁上传存储位置ID',
  `fkLogLocationId` int(11) DEFAULT NULL COMMENT '日志归档存储位置ID',
  `fkAdAuthLocationId` int(11) DEFAULT NULL COMMENT '认证服务器位置ID',
  `systemTopLevel` int(11) DEFAULT '0' COMMENT '系统定级 0公开 1内部 2秘密 3机密 4绝密',
  `autoBackupEnable` int(11) DEFAULT '0' COMMENT '是否自动备份 1是 0否',
  `mailSenderAccount` varchar(256) DEFAULT '' COMMENT '邮件告警发件人邮箱',
  `mailSenderPasswd` varchar(256) DEFAULT '' COMMENT '邮件告警发件人密码',
  `mailSenderSmtpAddr` varchar(256) DEFAULT '' COMMENT '邮件告警SMTP服务器地址',
  `mailSenderSmtpPort` varchar(256) DEFAULT '' COMMENT '邮件告警SMTP服务器端口号',
  `mailSenderSSLEnable` int(11) DEFAULT '0' COMMENT '邮件告警SMTP启用SSL',
  `mailRecvResourceAccount` varchar(1024) DEFAULT '' COMMENT '系统资源告警收件邮箱 ;号分割',
  `mailRecvResourceThreshold` varchar(256) DEFAULT '' COMMENT '系统资源告警阈值 JSON',
  `mailRecvLogStorageAddr` varchar(1024) DEFAULT '' COMMENT '日志存储容量告警收件邮箱 ;号分割',
  `mailRecvLogStorageThreshold` varchar(256) DEFAULT '' COMMENT '日志存储容量告警阈值 JSON',
  `mailRecvFileStorageAddr` varchar(1024) DEFAULT '' COMMENT '文件存储容量告警收件邮箱 ;号分割',
  `mailRecvFileStorageThreshold` varchar(256) DEFAULT '' COMMENT '文件存储容量告警阈值 JSON',
  `localAuthEnable` int(11) DEFAULT '0' COMMENT '本地认证是否启用 1是 0否',
  `localAuthPasswdMin` int(11) DEFAULT '0' COMMENT '本地认证密码最小长度',
  `localAuthPasswdMax` int(11) DEFAULT '0' COMMENT '本地认证密码最大长度',
  `localAuthPasswdContainsNumber` int(11) DEFAULT '0' COMMENT '密码复杂度(数字) 1必须包含 0可不包含',
  `localAuthPasswdContainsLetter` int(11) DEFAULT '0' COMMENT '密码复杂度(字母大小写) 1必须包含 0可不包含',
  `localAuthPasswdContainsSpecial` int(11) DEFAULT '0' COMMENT '密码复杂度(特殊字符) 1必须包含 0可不包含',
  `localAuthPasswdLockThreshold` int(11) DEFAULT '0' COMMENT '密码连续错误次数阈值',
  `localAuthPasswdLockTime` bigint(20) DEFAULT '0' COMMENT '密码错误锁定时间设置 毫秒',
  `localAuthPasswdForceChange` int(11) DEFAULT '0' COMMENT '是否强制定期修改密码 1是 0否',
  `localAuthPasswdPeriod` bigint(20) DEFAULT '0' COMMENT '定期修改密码时间设置 毫秒',
  `localAuthUnlockByAdmin` int(11) DEFAULT '0' COMMENT '是否必须管理员解锁 1是 0否',
  `localAuthInitPasswd` varchar(256) DEFAULT '' COMMENT '新用户初始密码',
  `localAuthInitPasswdForceChange` int(11) DEFAULT '0' COMMENT '是否强制修改初始密码 1是 0否',
  `adAuthEnable` int(11) DEFAULT '0' COMMENT 'AD认证是否启用 1是 0否',
  `adAuthDn` varchar(256) DEFAULT '' COMMENT '连接账号全DN',
  `adAuthAutoSync` int(11) DEFAULT '0' COMMENT '是否启用AD自动同步 1是 0否',
  `adAuthSyncRoot` varchar(256) DEFAULT '' COMMENT '同步根DN',
  `watermarkContent` varchar(256) DEFAULT '' COMMENT '水印内容 “内部资料，严禁传阅！”',
  `watermarkDispUser` int(11) DEFAULT '0' COMMENT '是否显示用户名 1是 0否',
  `watermarkDispDepartment` int(11) DEFAULT '0' COMMENT '是否显示所属部门 1是 0否',
  `watermarkDispIp` int(11) DEFAULT '0' COMMENT '是否显示终端IP 1是 0否',
  `watermarkDispPcName` int(11) DEFAULT '0' COMMENT '是否显示终端机器名 1是 0否',
  `watermarkDispPrintTime` int(11) DEFAULT '0' COMMENT '是否显示打印时间 1是 0否',
  `watermarkDispAngle` int(11) DEFAULT '0' COMMENT '设置水印显示角度 1水平 2对角',
  `watermarkDispAlpha` int(11) DEFAULT '0' COMMENT '设置水印透明度 0-100',
  `watermarkDispTextSize` int(11) DEFAULT '0' COMMENT '设置水印字体大小',
  `watermarkDispTextColor` varchar(256) DEFAULT '#FF0000FF' COMMENT '设置水印字体颜色 #RRGGBBAA',
  `watermarkDispAlign` int(11) DEFAULT '0' COMMENT '设置水印对其方式 1居中 2平铺',
  `watermarkContentJson4Print` varchar(2048) DEFAULT '' COMMENT '水印具体信息',
  `watermarkContentJson4Screen` varchar(2048) DEFAULT '' COMMENT '水印具体信息',
  `masterServerIp` varchar(256) DEFAULT '' COMMENT '主服务器地址',
  `masterServerPort` varchar(256) DEFAULT '' COMMENT '主服务器端口',
  `reserveServerIp` varchar(256) DEFAULT '' COMMENT '备用服务器地址',
  `reserveServerPort` varchar(256) DEFAULT '' COMMENT '备用服务器端口',
  `timerMailAlarmStartTime` bigint(20) DEFAULT '0' COMMENT '邮件告警定时配置起始时间',
  `timerMailAlarmPeriod` bigint(20) DEFAULT '0' COMMENT '邮件告警定时配置周期 毫秒',
  `timerBackupStartTime` bigint(20) DEFAULT '0' COMMENT '数据库备份定时配置起始时间',
  `timerBackupPerriod` bigint(20) DEFAULT '0' COMMENT '数据库备份定时配置周期 毫秒',
  `timerClientLogStartTime` bigint(20) DEFAULT '0' COMMENT '日志归档定时配置起始时间',
  `timerClientLogPeriod` bigint(20) DEFAULT '0' COMMENT '日志归档定时配置周期 毫秒',
  `timerDirSyncStartTime` bigint(20) DEFAULT '0' COMMENT '目录同步定时配置起始时间',
  `timerDirSyncPeriod` bigint(20) DEFAULT '0' COMMENT '目录同步定时配置周期 毫秒',
  `timerAutoDecodeStartTime` bigint(20) DEFAULT '0' COMMENT '文件自动解密定时配置起始时间',
  `timerAutoDecodePeriod` bigint(20) DEFAULT '0' COMMENT '文件自动解密定时配置周期 毫秒',
  `timerFileStatisticStartTime` bigint(20) DEFAULT '0' COMMENT '密级文件统计定时配置起始时间',
  `timerFileStatisticPeriod` bigint(20) DEFAULT '0' COMMENT '密级文件统计定时配置周期 毫秒',
  `timerCancelApproverStartTime` bigint(20) DEFAULT '0' COMMENT '取消审批代理人权限定时配置起始时间',
  `timerCancelApproverPeriod` bigint(20) DEFAULT '0' COMMENT '取消审批代理人权限定时配置周期 毫秒',
  `organizationCode` varchar(40) DEFAULT '' COMMENT '组织机构代码',
  `logArchiveKeepTime` varchar(256) DEFAULT '' COMMENT '日志归档保留周期配置 JSON{riskLevel1, riskLevel2, riskLevel3} 单位月',
  `autoLogArchiveEnable` int(11) DEFAULT '0' COMMENT '是否自动日志归档 1是 0否',
  `preclassifiedForce` int(11) DEFAULT '0' COMMENT '是否强制预定密 1是 0否',
  `advancedControlStrategy` varchar(256) DEFAULT '' COMMENT '高级管控策略配置 JSON数据',
  `classifiedWhiteList` varchar(4096) DEFAULT '' COMMENT '标密白名单 |分割',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of SystemConfig
-- ----------------------------
INSERT INTO `SystemConfig` VALUES ('1', '2', '5', '6', '7', '8', '9', '3', '4', null, '4', '1', '', '', 'smtp.163.com', '465', '1', null, '{\"cpuFree\":[99],\"hdFree\":[5],\"memFree\":[5]}', null, '{\"free\":[5]}', null, '{\"free\":[5]}', '1', '6', '20', '1', '1', '0', '5', '600000', '0', '2592000000', '0', 'ZGNtc0AxMjM=', '0', '0', '', '0', '', '涉密文件，禁止传阅', '1', '0', '0', '0', '1', '1', '15', '124', '#FF00FFF22', '1', '', '', '', '', '', '', '0', '86400000', '0', '86400000', '0', '7776000000', '0', '86400000', '0', '86400000', '0', '86400000', '0', '86400000', '', '', '0', '0', '', '*\APPDATA\*|*\COOKIES\*|*\TEMPORARY INTERNET FILE\*|*\LANDESK\*|*\LOTUS\NOTES\*|*\RECYCLER\*|*\SYMANTEC\*|*\TEMPORARY INTERNET FILES\*|*\MICROSOFT\WORD\STARTUP\*|*\MSOHTMLCLIP*\*|*\Shared Memory\*|*\MICROSOFT\TEMPLATES\*|*\KINGSOFT\*TEMPLATES\*|*\MICROSOFT\WORD\STARTUP\*');

-- ----------------------------
-- Table structure for `Token`
-- ----------------------------
DROP TABLE IF EXISTS `Token`;
CREATE TABLE `Token` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fkUid` varchar(40) DEFAULT NULL COMMENT '用户ID',
  `fkCid` varchar(40) DEFAULT NULL COMMENT '终端ID',
  `fkAid` varchar(40) DEFAULT NULL COMMENT '管理员ID',
  `token` varchar(256) DEFAULT '' COMMENT '身份标识码',
  `createTime` bigint(20) DEFAULT '0' COMMENT '创建时间',
  `heartbeatTime` bigint(20) DEFAULT '0' COMMENT '失效时间',
  `loginFrom` int(11) DEFAULT '0' COMMENT '登陆方式 1Web 2终端',
  `ip` varchar(40) DEFAULT '' COMMENT '登陆IP',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of Token
-- ----------------------------

-- ----------------------------
-- Table structure for `TrustedApplication`
-- ----------------------------
DROP TABLE IF EXISTS `TrustedApplication`;
CREATE TABLE `TrustedApplication` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `processName` varchar(1024) DEFAULT '' COMMENT '可信应用程序进程名 xxx.exe',
  `description` varchar(256) DEFAULT '' COMMENT '可信应用程序描述',
  `extensionName` varchar(1024) DEFAULT '' COMMENT '可信应用程序扩展名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of TrustedApplication
-- ----------------------------
INSERT INTO `TrustedApplication` VALUES ('1', 'WINWORD.EXE|EXCEL.EXE|POWERPNT.EXE', 'Office办公', '*.DOC|*.DOCX|*.XLS|*.XLSX|*.PPT|*.PPTX|*.XML|*.DOT|*.DOTX|*.PDF|*.XPS|*.ODT|*.RTF|*.HTML|*.HTM');
INSERT INTO `TrustedApplication` VALUES ('2', 'NOTEPAD.EXE|WORDPAD.EXE|NOTRPAD++.EXE', '文本类', '*.TXT|*.RTF|*.DOC|*.DOCX');
INSERT INTO `TrustedApplication` VALUES ('3', 'ACROBAT.EXE|ACRORD32.EXE|ACRODIST.EXE|ACROBAT ELEMENTS.EXE|MSPVIEW.EXE|FOXITR~1.EXE|FOXIT READER.EXE|FOXITREADER.EXE', 'PDF', '*.PDF|*.DOC|*.DOCX');
INSERT INTO `TrustedApplication` VALUES ('4', 'MSPAINT.EXE', '图片类', '*.BMP|*.PNG|*.JPG|*.JPEG|*.JPE|*.PNG|*.TIFF|*.TIF|*.ICO|*.DIB|*.EMF');
INSERT INTO `TrustedApplication` VALUES ('5', 'WPS.EXE|ET.EXE|WPP.EXE|ACROBAT.EXE|ACRORD32.EXE|ACRODIST.EXE|ACROBAT ELEMENTS.EXE|MSPVIEW.EXE|FOXITR~1.EXE|FOXIT READER.EXE|FOXITREADER.EXE', 'WPS软件', '*.DOC|*.DOCX|*.XLS|*.XLSX|*.PPT|*.PPTX|*.XML|*.WPS|*.ET|*.DPS|*.PDF|*.HTML|*.HTM');

-- ----------------------------
-- Table structure for `User`
-- ----------------------------
DROP TABLE IF EXISTS `User`;
CREATE TABLE `User` (
  `pkUid` varchar(40) NOT NULL COMMENT '用户ID',
  `fkCreateAid` varchar(40) DEFAULT NULL COMMENT '创建者管理员ID',
  `fkLastLoginCid` varchar(40) DEFAULT NULL COMMENT '末次登录终端ID',
  `account` varchar(256) DEFAULT '' COMMENT '登录名',
  `name` varchar(256) DEFAULT '' COMMENT '显示姓名',
  `gender` int(11) DEFAULT '0' COMMENT '性别 1男 2女',
  `phone` varchar(256) DEFAULT '' COMMENT '电话',
  `mail` varchar(256) DEFAULT '' COMMENT '邮箱',
  `position` varchar(256) DEFAULT '' COMMENT '职务',
  `passwd` varchar(256) DEFAULT '' COMMENT '密码',
  `userLevel` int(11) DEFAULT '0' COMMENT '用户密级 1一般 2重要 3核心',
  `userState` int(11) DEFAULT '0' COMMENT '状态 1已删除或隐藏(中间件) 4已锁定',
  `passwdState` int(11) DEFAULT '0' COMMENT '密码状态 1初始密码未修改 2密码已修改',
  `createFrom` int(11) DEFAULT '0' COMMENT '来源 1手动创建 2导入创建 3同步创建 4中间件用户',
  `createTime` bigint(20) DEFAULT '0' COMMENT '用户创建日期',
  `firstLoginTime` bigint(20) DEFAULT '0' COMMENT '首次登录日期',
  `lastLoginTime` bigint(20) DEFAULT '0' COMMENT '末次登录日期',
  `lastLoginType` int(11) DEFAULT '0' COMMENT '末次登陆方式 1平台 2终端',
  `lastLoginIp` varchar(40) DEFAULT '' COMMENT '末次登陆IP地址',
  `errLoginCount` int(11) DEFAULT '0' COMMENT '连续错误登陆次数 成功后清零',
  `unlockTime` bigint(20) DEFAULT '0' COMMENT '用户解锁日期',
  `lastPasswdChangeTime` bigint(20) DEFAULT '0' COMMENT '用户上次修改密码日期',
  `heartbeatTime` bigint(20) DEFAULT '0' COMMENT '用户心跳时间',
  PRIMARY KEY (`pkUid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of User
-- ----------------------------

-- ----------------------------
-- Table structure for `UserMessage`
-- ----------------------------
DROP TABLE IF EXISTS `UserMessage`;
CREATE TABLE `UserMessage` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fkUid` varchar(40) DEFAULT NULL COMMENT '用户ID',
  `message` varchar(4096) DEFAULT '' COMMENT '消息',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of UserMessage
-- ----------------------------

-- ----------------------------
-- Table structure for `UserMessageHistory`
-- ----------------------------
DROP TABLE IF EXISTS `UserMessageHistory`;
CREATE TABLE `UserMessageHistory` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fkUid` varchar(40) DEFAULT NULL COMMENT '用户ID',
  `msgType` varchar(256) DEFAULT '' COMMENT '消息类型',
  `message` varchar(2048) DEFAULT '' COMMENT '消息内容',
  `url` varchar(256) DEFAULT '' COMMENT '消息URL',
  `createTime` bigint(20) DEFAULT '0' COMMENT '产生时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of UserMessageHistory
-- ----------------------------

-- ----------------------------
-- Table structure for `UserToDepartment`
-- ----------------------------
DROP TABLE IF EXISTS `UserToDepartment`;
CREATE TABLE `UserToDepartment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fkUid` varchar(40) DEFAULT NULL COMMENT '用户ID',
  `fkDid` varchar(40) DEFAULT NULL COMMENT '部门ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of UserToDepartment
-- ----------------------------

-- ----------------------------
-- Table structure for `UserToGroup`
-- ----------------------------
DROP TABLE IF EXISTS `UserToGroup`;
CREATE TABLE `UserToGroup` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fkUid` varchar(40) DEFAULT NULL COMMENT '用户ID',
  `fkGid` varchar(40) DEFAULT NULL COMMENT '组ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of UserToGroup
-- ----------------------------

-- ----------------------------
-- Table structure for `Workflow`
-- ----------------------------
DROP TABLE IF EXISTS `Workflow`;
CREATE TABLE `Workflow` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `workFlowType` int(11) DEFAULT '0' COMMENT '流程名称 2正式定密审核 3文件签发审核 4密级变更审核 5文件解密审核',
  `fkSrcFid` varchar(40) DEFAULT NULL COMMENT '源文件ID',
  `fkDesFid` varchar(40) DEFAULT NULL COMMENT '目标文件ID，如定密流程结束后修改文件头产生的新文件',
  `applyFileLevel` int(11) DEFAULT '0' COMMENT '申请的文件密级 0公开 1内部 2秘密 3机密 4绝密',
  `applyValidPeriod` varchar(20) DEFAULT '' COMMENT '申请的保密期限 周期 格式yymmdd',
  `durationType` int(11) DEFAULT '0' COMMENT '保密期限 类型 0不限 1长期 2期限 3解密日期 4条件',
  `durationDescription` varchar(1024) DEFAULT '' COMMENT '申请的保密期限 描述 单位毫秒',
  `fileDecryptTime` bigint(20) DEFAULT '0' COMMENT '申请的保密期限 文件解密日期',
  `applyBasis` TEXT COMMENT '申请的定密依据 JSON[level,content]',
  `applyBasisType` int(11) DEFAULT '0' COMMENT '申请的定密依据类型 0确定性定密 1不明确事项 2无权定密事项 3派生定密',
  `applyBasisDesc` varchar(4096) DEFAULT '' COMMENT '申请的定密依据描述',
  `applyMajorUnit` varchar(256) DEFAULT '' COMMENT '申请的辅助定密单位',
  `applyMinorUnit` varchar(4096) DEFAULT '' COMMENT '申请的辅助定密单位 JSONArray[string]',
  `applyDispatch` TEXT COMMENT '申请的知悉范围，JSONArray[unitNo,uid]',
  `applyDispatchDesc` varchar(1024) DEFAULT '' COMMENT '申请的知悉范围描述',
  `flowTrack` varchar(1024) DEFAULT NULL COMMENT '流程轨迹详情,流程完成时从WorkflowTrack移动数据到此字段 JSON[序号，责任人，审批状态，意见，日期]',
  `flowState` int(11) DEFAULT '0' COMMENT '流程状态 1已完成 0未完成',
  `createTime` bigint(20) DEFAULT '0' COMMENT '发起时间',
  `fkCreateUid` varchar(40) DEFAULT NULL COMMENT '发起人uid',
  `createComment` varchar(256) DEFAULT '' COMMENT '发起理由',
  `totalStep` int(11) DEFAULT '0' COMMENT '审批总级数',
  `currentStep` int(11) DEFAULT '0' COMMENT '当前待审批级数',
  `permission` varchar(1024) DEFAULT '' COMMENT '权限JSON',
  `issueNumber` varchar(50) DEFAULT '' COMMENT '文件文号',
  `docNumber` int(11) DEFAULT '0' COMMENT '文件份号',
  `duplicationAmount` int(11) DEFAULT '0' COMMENT '文件份数 分发总数量',
  `markVersion` int(11) DEFAULT '0' COMMENT '标志版本',
  `urgency` int(11) DEFAULT '0' COMMENT '重要或紧急程度',
  `fileName` varchar(256) DEFAULT '文件名称带后缀',
  `flowResult` int(11) DEFAULT '0' COMMENT '流程处理结果 0处理中 1通过 2拒绝',
  `business` varchar(1024) DEFAULT '' COMMENT '业务属性',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of Workflow
-- ----------------------------

-- ----------------------------
-- Table structure for `WorkflowPolicy`
-- ----------------------------
DROP TABLE IF EXISTS `WorkflowPolicy`;
CREATE TABLE `WorkflowPolicy` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `workFlowType` int(11) DEFAULT '0' COMMENT '流程类型 2正式定密审核 3文件签发审核 4密级变更审核 5文件解密审核',
  `fileLevel` int(11) DEFAULT '0' COMMENT '文件密级 0公开 1内部 2秘密 3机密 4绝密',
  `createTime` bigint(20) DEFAULT '0' COMMENT '入库时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of WorkflowPolicy
-- ----------------------------
INSERT INTO `WorkflowPolicy` VALUES ('1', '2', '0', '0');
INSERT INTO `WorkflowPolicy` VALUES ('2', '2', '1', '0');
INSERT INTO `WorkflowPolicy` VALUES ('3', '2', '2', '0');
INSERT INTO `WorkflowPolicy` VALUES ('4', '2', '3', '0');
INSERT INTO `WorkflowPolicy` VALUES ('5', '2', '4', '0');
INSERT INTO `WorkflowPolicy` VALUES ('6', '3', '0', '0');
INSERT INTO `WorkflowPolicy` VALUES ('7', '3', '1', '0');
INSERT INTO `WorkflowPolicy` VALUES ('8', '3', '2', '0');
INSERT INTO `WorkflowPolicy` VALUES ('9', '3', '3', '0');
INSERT INTO `WorkflowPolicy` VALUES ('10', '3', '4', '0');
INSERT INTO `WorkflowPolicy` VALUES ('11', '4', '0', '0');
INSERT INTO `WorkflowPolicy` VALUES ('12', '4', '1', '0');
INSERT INTO `WorkflowPolicy` VALUES ('13', '4', '2', '0');
INSERT INTO `WorkflowPolicy` VALUES ('14', '4', '3', '0');
INSERT INTO `WorkflowPolicy` VALUES ('15', '4', '4', '0');
INSERT INTO `WorkflowPolicy` VALUES ('16', '5', '0', '0');
INSERT INTO `WorkflowPolicy` VALUES ('17', '5', '1', '0');
INSERT INTO `WorkflowPolicy` VALUES ('18', '5', '2', '0');
INSERT INTO `WorkflowPolicy` VALUES ('19', '5', '3', '0');
INSERT INTO `WorkflowPolicy` VALUES ('20', '5', '4', '0');

-- ----------------------------
-- Table structure for `WorkflowPolicyStep`
-- ----------------------------
DROP TABLE IF EXISTS `WorkflowPolicyStep`;
CREATE TABLE `WorkflowPolicyStep` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fkRoleId` int(11) DEFAULT NULL COMMENT '责任人角色信息ID 只有签发流程由签发人审核，其他均由定密责任人审核。',
  `step` int(11) DEFAULT '0' COMMENT '审核级数(步骤) 1一级 2二级 3三级',
  `fkWorkFlowPolicyId` int(11) DEFAULT '0' COMMENT '流程策略ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of WorkflowPolicyStep
-- ----------------------------

-- ----------------------------
-- Table structure for `WorkflowTrack`
-- ----------------------------
DROP TABLE IF EXISTS `WorkflowTrack`;
CREATE TABLE `WorkflowTrack` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fkWorkFlowId` int(11) DEFAULT NULL COMMENT '流程ID',
  `fkApproveUid` varchar(40) DEFAULT NULL COMMENT '审批人uid',
  `approveStep` int(11) DEFAULT '0' COMMENT '审批级数(次序号)',
  `approveState` int(11) DEFAULT '0' COMMENT '审批状态 0未审批 1通过 2否决',
  `approveComment` varchar(256) DEFAULT '' COMMENT '审批意见',
  `approveTime` bigint(20) DEFAULT '0' COMMENT '审批时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of WorkflowTrack
-- ----------------------------
