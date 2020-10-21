-- --------------------------------------------------------
-- 主机:                           localhost
-- 服务器版本:                        5.7.23-log - MySQL Community Server (GPL)
-- 服务器操作系统:                      Win64
-- HeidiSQL 版本:                  9.5.0.5196
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- 导出 sailplat_ucs 的数据库结构
CREATE DATABASE IF NOT EXISTS `sailplat_ucs` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `sailplat_ucs`;

-- 导出  表 sailplat_ucs.ucs_client 结构
CREATE TABLE IF NOT EXISTS `ucs_client` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `system_id` bigint(20) DEFAULT NULL,
  `client_code` varchar(48) NOT NULL COMMENT '客户端id',
  `client_secret` varchar(256) NOT NULL COMMENT '客户端密钥',
  `resource_ids` varchar(256) DEFAULT NULL COMMENT '资源集合',
  `scope` varchar(256) NOT NULL COMMENT '授权范围',
  `authorized_grant_types` varchar(256) NOT NULL COMMENT '授权类型',
  `web_server_redirect_uri` varchar(256) DEFAULT NULL COMMENT '回调地址',
  `authorities` varchar(256) DEFAULT NULL COMMENT '权限',
  `access_token_validity` int(11) NOT NULL COMMENT '令牌过期秒数',
  `refresh_token_validity` int(11) NOT NULL COMMENT '刷新令牌过期秒数',
  `additional_information` varchar(4096) DEFAULT NULL COMMENT '附加说明',
  `autoapprove` varchar(256) DEFAULT NULL COMMENT '自动授权',
  `create_user` bigint(64) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(64) DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `status` int(2) NOT NULL COMMENT '状态',
  `is_deleted` int(2) NOT NULL COMMENT '是否已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='客户端表';

-- 正在导出表  sailplat_ucs.ucs_client 的数据：~2 rows (大约)
/*!40000 ALTER TABLE `ucs_client` DISABLE KEYS */;
INSERT INTO `ucs_client` (`id`, `system_id`, `client_code`, `client_secret`, `resource_ids`, `scope`, `authorized_grant_types`, `web_server_redirect_uri`, `authorities`, `access_token_validity`, `refresh_token_validity`, `additional_information`, `autoapprove`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES
	(1123598811738675201, 0, 'sword', 'sword_secret', NULL, 'all', 'refresh_token,password,authorization_code', 'http://localhost:8888', NULL, 3600, 604800, NULL, NULL, 1, '2019-03-24 10:40:55', 1, '2019-03-24 10:40:59', 1, 0),
	(1123598811738675202, 0, 'saber', 'saber_secret', NULL, 'all', 'refresh_token,password,authorization_code', 'http://localhost:8080', NULL, 3600, 604800, NULL, NULL, 1, '2019-03-24 10:42:29', 1, '2019-03-24 10:42:32', 1, 0);
/*!40000 ALTER TABLE `ucs_client` ENABLE KEYS */;

-- 导出  表 sailplat_ucs.ucs_code 结构
CREATE TABLE IF NOT EXISTS `ucs_code` (
  `id` bigint(64) NOT NULL COMMENT '主键',
  `datasource_id` bigint(64) DEFAULT NULL COMMENT '数据源主键',
  `service_name` varchar(64) DEFAULT NULL COMMENT '服务名称',
  `code_name` varchar(64) DEFAULT NULL COMMENT '模块名称',
  `table_name` varchar(64) DEFAULT NULL COMMENT '表名',
  `table_prefix` varchar(64) DEFAULT NULL COMMENT '表前缀',
  `pk_name` varchar(32) DEFAULT NULL COMMENT '主键名',
  `package_name` varchar(500) DEFAULT NULL COMMENT '后端包名',
  `base_mode` int(2) DEFAULT NULL COMMENT '基础业务模式',
  `wrap_mode` int(2) DEFAULT NULL COMMENT '包装器模式',
  `api_path` varchar(2000) DEFAULT NULL COMMENT '后端路径',
  `web_path` varchar(2000) DEFAULT NULL COMMENT '前端路径',
  `is_deleted` int(2) DEFAULT '0' COMMENT '是否已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='代码生成表';

-- 正在导出表  sailplat_ucs.ucs_code 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `ucs_code` DISABLE KEYS */;
/*!40000 ALTER TABLE `ucs_code` ENABLE KEYS */;

-- 导出  表 sailplat_ucs.ucs_datasource 结构
CREATE TABLE IF NOT EXISTS `ucs_datasource` (
  `id` bigint(64) NOT NULL COMMENT '主键',
  `name` varchar(100) DEFAULT NULL COMMENT '名称',
  `driver_class` varchar(100) DEFAULT NULL COMMENT '驱动类',
  `url` varchar(500) DEFAULT NULL COMMENT '连接地址',
  `username` varchar(50) DEFAULT NULL COMMENT '用户名',
  `password` varchar(50) DEFAULT NULL COMMENT '密码',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_user` bigint(64) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(64) DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `status` int(2) DEFAULT NULL COMMENT '状态',
  `is_deleted` int(2) DEFAULT NULL COMMENT '是否已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='数据源配置表';

-- 正在导出表  sailplat_ucs.ucs_datasource 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `ucs_datasource` DISABLE KEYS */;
/*!40000 ALTER TABLE `ucs_datasource` ENABLE KEYS */;

-- 导出  表 sailplat_ucs.ucs_dept 结构
CREATE TABLE IF NOT EXISTS `ucs_dept` (
  `id` bigint(64) NOT NULL COMMENT '主键',
  `enterpriseId` bigint(20) DEFAULT NULL,
  `parent_id` bigint(64) DEFAULT '0' COMMENT '父主键',
  `dept_name` varchar(45) DEFAULT NULL COMMENT '部门名',
  `full_name` varchar(45) DEFAULT NULL COMMENT '部门全称',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `is_deleted` int(2) DEFAULT '0' COMMENT '是否已删除',
  `create_user` bigint(64) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(64) DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `status` int(2) DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='部门表';

-- 正在导出表  sailplat_ucs.ucs_dept 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `ucs_dept` DISABLE KEYS */;
/*!40000 ALTER TABLE `ucs_dept` ENABLE KEYS */;

-- 导出  表 sailplat_ucs.ucs_dict 结构
CREATE TABLE IF NOT EXISTS `ucs_dict` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `system_id` bigint(20) DEFAULT '0' COMMENT '所属系统',
  `parent_id` bigint(20) DEFAULT '0' COMMENT '父主键',
  `code` varchar(255) DEFAULT NULL COMMENT '字典码',
  `dict_key` int(2) DEFAULT NULL COMMENT '字典值',
  `dict_value` varchar(255) DEFAULT NULL COMMENT '字典名称',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `remark` varchar(255) DEFAULT NULL COMMENT '字典备注',
  `is_deleted` int(2) DEFAULT '0' COMMENT '是否已删除',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `FK_DICT_SYSTEM` (`system_id`),
  CONSTRAINT `FK_DICT_SYSTEM` FOREIGN KEY (`system_id`) REFERENCES `ucs_system` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='字典表';

-- 正在导出表  sailplat_ucs.ucs_dict 的数据：~32 rows (大约)
/*!40000 ALTER TABLE `ucs_dict` DISABLE KEYS */;
INSERT INTO `ucs_dict` (`id`, `system_id`, `parent_id`, `code`, `dict_key`, `dict_value`, `sort`, `remark`, `is_deleted`) VALUES
	(1123598814738675201, 0, 0, 'sex', -1, '性别', 1, NULL, 0),
	(1123598814738675202, 0, 1123598814738675201, 'sex', 1, '男', 1, NULL, 0),
	(1123598814738675203, 0, 1123598814738675201, 'sex', 2, '女', 2, NULL, 0),
	(1123598814738675204, 0, 0, 'notice', -1, '通知类型', 2, NULL, 0),
	(1123598814738675205, 0, 1123598814738675204, 'notice', 1, '发布通知', 1, NULL, 0),
	(1123598814738675206, 0, 1123598814738675204, 'notice', 2, '批转通知', 2, NULL, 0),
	(1123598814738675207, 0, 1123598814738675204, 'notice', 3, '转发通知', 3, NULL, 0),
	(1123598814738675208, 0, 1123598814738675204, 'notice', 4, '指示通知', 4, NULL, 0),
	(1123598814738675209, 0, 1123598814738675204, 'notice', 5, '任免通知', 5, NULL, 0),
	(1123598814738675210, 0, 1123598814738675204, 'notice', 6, '事务通知', 6, NULL, 0),
	(1123598814738675211, 0, 0, 'menu_category', -1, '菜单类型', 3, NULL, 0),
	(1123598814738675212, 0, 1123598814738675211, 'menu_category', 1, '菜单', 1, NULL, 0),
	(1123598814738675213, 0, 1123598814738675211, 'menu_category', 2, '按钮', 2, NULL, 0),
	(1123598814738675214, 0, 0, 'button_func', -1, '按钮功能', 4, NULL, 0),
	(1123598814738675215, 0, 1123598814738675214, 'button_func', 1, '工具栏', 1, NULL, 0),
	(1123598814738675216, 0, 1123598814738675214, 'button_func', 2, '操作栏', 2, NULL, 0),
	(1123598814738675217, 0, 1123598814738675214, 'button_func', 3, '工具操作栏', 3, NULL, 0),
	(1123598814738675218, 0, 0, 'yes_no', -1, '是否', 5, NULL, 0),
	(1123598814738675219, 0, 1123598814738675218, 'yes_no', 1, '否', 1, NULL, 0),
	(1123598814738675220, 0, 1123598814738675218, 'yes_no', 2, '是', 2, NULL, 0),
	(1123598814738777220, 0, 0, 'post_category', -1, '岗位类型', 12, NULL, 0),
	(1123598814738777221, 0, 1123598814738777220, 'post_category', 1, '高层', 1, NULL, 0),
	(1123598814738777222, 0, 1123598814738777220, 'post_category', 2, '中层', 2, NULL, 0),
	(1123598814738777223, 0, 1123598814738777220, 'post_category', 3, '基层', 3, NULL, 0),
	(1123598814738777224, 0, 1123598814738777220, 'post_category', 4, '其他', 4, NULL, 0),
	(1123598814738777230, 0, 0, 'region', -1, '行政区划', 13, NULL, 0),
	(1123598814738777231, 0, 1123598814738777230, 'region', 0, '国家', 0, NULL, 0),
	(1123598814738777232, 0, 1123598814738777230, 'region', 1, '省份/直辖市', 1, NULL, 0),
	(1123598814738777233, 0, 1123598814738777230, 'region', 2, '地市', 2, NULL, 0),
	(1123598814738777234, 0, 1123598814738777230, 'region', 3, '区县', 3, NULL, 0),
	(1123598814738777235, 0, 1123598814738777230, 'region', 4, '乡镇', 4, NULL, 0),
	(1123598814738777236, 0, 1123598814738777230, 'region', 5, '村委', 5, NULL, 0);
/*!40000 ALTER TABLE `ucs_dict` ENABLE KEYS */;

-- 导出  表 sailplat_ucs.ucs_enterprise 结构
CREATE TABLE IF NOT EXISTS `ucs_enterprise` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '企业ID',
  `tenant_id` varchar(12) DEFAULT '000000' COMMENT '租户ID',
  `enterprise_name` varchar(255) DEFAULT NULL COMMENT '企业名称',
  `credit_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '统一社会信用代码',
  `industry_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '行业类型',
  `enterprise_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '企业类型',
  `region` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '所在区域',
  `enterprise_size` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '企业规模',
  `charge_person` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '负责人',
  `position` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '负责人职位',
  `phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '负责人电话',
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '负责人邮箱',
  `status` char(2) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT '' COMMENT '状态（0正常 1删除 2停用 3冻结）',
  `remarks` varchar(500) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '备注信息',
  `create_user` bigint(64) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(64) DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `is_deleted` int(2) DEFAULT '0' COMMENT '是否已删除',
  PRIMARY KEY (`id`,`credit_code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=122 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- 正在导出表  sailplat_ucs.ucs_enterprise 的数据：~11 rows (大约)
/*!40000 ALTER TABLE `ucs_enterprise` DISABLE KEYS */;
INSERT INTO `ucs_enterprise` (`id`, `tenant_id`, `enterprise_name`, `credit_code`, `industry_type`, `enterprise_type`, `region`, `enterprise_size`, `charge_person`, `position`, `phone`, `email`, `status`, `remarks`, `create_user`, `create_time`, `update_user`, `update_time`, `is_deleted`) VALUES
	(110, '000002', '中国航天航空科技有限公司', '911101085514239319', '化学纤维制造业', '小型企业', '北京', '50-200人', '张长林', 'COO', '18910359863', 'zcl@linose.com', '', NULL, NULL, NULL, NULL, NULL, 0),
	(111, '000002', '忆久科技', '9110821236547895689', '通用设备制造业', '小型企业', '北京', '50-200人', '张大庆油田', '职位', '17610223858', 'syt@163.com', '-1', '', -1, NULL, 1123598821738675201, '2020-09-03 15:55:34', 0),
	(112, '000002', '中国人大', '911101085514239339', '电力、热力生产和供应业', '微型企业', '中', '500-1000人', '张人大', 'COO', '18910359865', 'a@b.com', NULL, NULL, NULL, NULL, NULL, NULL, 0),
	(113, '000002', '莱诺斯', '124579856325689745', '电器机械和器材制造业', '小型企业', '下水管处', '500-1000人', '从圣诞树', 'cdsc', '19145786589', '133@163.com', NULL, NULL, NULL, NULL, NULL, NULL, 0),
	(114, '000002', '技术公司', '123456789987654321', '电力、热力生产和供应业', '小型企业', '北京', '500-1000人', '张总', '总经理', '15144448888', '123@163.com', NULL, NULL, NULL, NULL, NULL, NULL, 0),
	(115, '000002', '测试测试', '145789653689562368', '非金属矿物制品业', '中型企业', '北京,北京市', '50-200人', '额威风威风', '二次网', '18101037819', '156@163.com', NULL, NULL, NULL, NULL, NULL, NULL, 0),
	(116, '000002', '胖虎公司', '123456789874563210', '汽车制造业', '中型企业', '重庆,重庆市', '50-200人', '胖虎', '总经理', '15544445555', '123@163.com', NULL, NULL, NULL, NULL, NULL, NULL, 0),
	(117, '000002', '给他个', '345322432322134532', '电力、热力生产和供应业', '小型企业', '重庆,重庆市', '500-1000人', '纷纷', '下市场上', '18101037819', 'sas@163.com', NULL, NULL, NULL, NULL, NULL, NULL, 0),
	(118, '000002', '下孙菲菲', '234444444435435334', '化学纤维制造业', '大型企业', '北京,北京市', '10-50人', '是dfvd', '就上次', '18101037819', 'd434@163.com', NULL, NULL, NULL, NULL, NULL, NULL, 0),
	(119, '000002', '二哥提问题', '132652656326564424', '化学纤维制造业', '中型企业', '天津,天津市', '50-200人', 'fxgsdg', '吃不上饭', '18101037819', 'dhe@163.com', NULL, NULL, NULL, NULL, NULL, NULL, 0),
	(120, '000002', '莱诺斯', '1758978971589342789235', '计算机、通讯和其它电子设备制造业', '小型企业', '北京,北京市', '50-200人', '王龙平', '老板', '15000000000', '100000@linose.com', NULL, NULL, NULL, NULL, NULL, NULL, 0);
/*!40000 ALTER TABLE `ucs_enterprise` ENABLE KEYS */;

-- 导出  表 sailplat_ucs.ucs_log_api 结构
CREATE TABLE IF NOT EXISTS `ucs_log_api` (
  `id` bigint(64) NOT NULL COMMENT '编号',
  `service_id` varchar(32) DEFAULT NULL COMMENT '服务ID',
  `enterpriseId` bigint(20) DEFAULT NULL COMMENT '企业ID',
  `server_host` varchar(255) DEFAULT NULL COMMENT '服务器名',
  `server_ip` varchar(255) DEFAULT NULL COMMENT '服务器IP地址',
  `env` varchar(255) DEFAULT NULL COMMENT '服务器环境',
  `type` char(1) DEFAULT '1' COMMENT '日志类型',
  `title` varchar(255) DEFAULT '' COMMENT '日志标题',
  `method` varchar(10) DEFAULT NULL COMMENT '操作方式',
  `request_uri` varchar(255) DEFAULT NULL COMMENT '请求URI',
  `user_agent` varchar(1000) DEFAULT NULL COMMENT '用户代理',
  `remote_ip` varchar(255) DEFAULT NULL COMMENT '操作IP地址',
  `method_class` varchar(255) DEFAULT NULL COMMENT '方法类',
  `method_name` varchar(255) DEFAULT NULL COMMENT '方法名',
  `params` text COMMENT '操作提交的数据',
  `time` varchar(64) DEFAULT NULL COMMENT '执行时间',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='接口日志表';

-- 正在导出表  sailplat_ucs.ucs_log_api 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `ucs_log_api` DISABLE KEYS */;
/*!40000 ALTER TABLE `ucs_log_api` ENABLE KEYS */;

-- 导出  表 sailplat_ucs.ucs_log_error 结构
CREATE TABLE IF NOT EXISTS `ucs_log_error` (
  `id` bigint(64) NOT NULL COMMENT '编号',
  `enterpriseId` bigint(20) DEFAULT NULL,
  `service_id` varchar(32) DEFAULT NULL COMMENT '服务ID',
  `server_host` varchar(255) DEFAULT NULL COMMENT '服务器名',
  `server_ip` varchar(255) DEFAULT NULL COMMENT '服务器IP地址',
  `env` varchar(255) DEFAULT NULL COMMENT '系统环境',
  `method` varchar(10) DEFAULT NULL COMMENT '操作方式',
  `request_uri` varchar(255) DEFAULT NULL COMMENT '请求URI',
  `user_agent` varchar(1000) DEFAULT NULL COMMENT '用户代理',
  `stack_trace` text COMMENT '堆栈',
  `exception_name` varchar(255) DEFAULT NULL COMMENT '异常名',
  `message` text COMMENT '异常信息',
  `line_number` int(11) DEFAULT NULL COMMENT '错误行数',
  `remote_ip` varchar(255) DEFAULT NULL COMMENT '操作IP地址',
  `method_class` varchar(255) DEFAULT NULL COMMENT '方法类',
  `file_name` varchar(1000) DEFAULT NULL COMMENT '文件名',
  `method_name` varchar(255) DEFAULT NULL COMMENT '方法名',
  `params` text COMMENT '操作提交的数据',
  `time` varchar(64) DEFAULT NULL COMMENT '执行时间',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='错误日志表';

-- 正在导出表  sailplat_ucs.ucs_log_error 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `ucs_log_error` DISABLE KEYS */;
/*!40000 ALTER TABLE `ucs_log_error` ENABLE KEYS */;

-- 导出  表 sailplat_ucs.ucs_log_usual 结构
CREATE TABLE IF NOT EXISTS `ucs_log_usual` (
  `id` bigint(64) NOT NULL COMMENT '编号',
  `enterpriseId` bigint(20) DEFAULT NULL,
  `service_id` varchar(32) DEFAULT NULL COMMENT '服务ID',
  `server_host` varchar(255) DEFAULT NULL COMMENT '服务器名',
  `server_ip` varchar(255) DEFAULT NULL COMMENT '服务器IP地址',
  `env` varchar(255) DEFAULT NULL COMMENT '系统环境',
  `log_level` varchar(10) DEFAULT NULL COMMENT '日志级别',
  `log_id` varchar(100) DEFAULT NULL COMMENT '日志业务id',
  `log_data` text COMMENT '日志数据',
  `method` varchar(10) DEFAULT NULL COMMENT '操作方式',
  `request_uri` varchar(255) DEFAULT NULL COMMENT '请求URI',
  `remote_ip` varchar(255) DEFAULT NULL COMMENT '操作IP地址',
  `method_class` varchar(255) DEFAULT NULL COMMENT '方法类',
  `method_name` varchar(255) DEFAULT NULL COMMENT '方法名',
  `user_agent` varchar(1000) DEFAULT NULL COMMENT '用户代理',
  `params` text COMMENT '操作提交的数据',
  `time` datetime DEFAULT NULL COMMENT '执行时间',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='通用日志表';

-- 正在导出表  sailplat_ucs.ucs_log_usual 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `ucs_log_usual` DISABLE KEYS */;
/*!40000 ALTER TABLE `ucs_log_usual` ENABLE KEYS */;

-- 导出  表 sailplat_ucs.ucs_menu 结构
CREATE TABLE IF NOT EXISTS `ucs_menu` (
  `id` bigint(64) NOT NULL COMMENT '主键',
  `parent_id` bigint(64) DEFAULT NULL,
  `enterprise_id` bigint(64) DEFAULT NULL COMMENT '企业ID，企业可以扩展自己的菜单，缺省Null为公共菜单',
  `system_id` bigint(20) DEFAULT NULL COMMENT '系统ID，此菜单属于哪个系统，如果为-1，则为全局菜单',
  `code` varchar(255) DEFAULT NULL COMMENT '菜单编号',
  `name` varchar(255) DEFAULT NULL COMMENT '菜单名称',
  `alias` varchar(255) DEFAULT NULL COMMENT '菜单别名',
  `path` varchar(255) DEFAULT NULL COMMENT '请求地址',
  `source` varchar(255) DEFAULT NULL COMMENT '菜单资源',
  `sort` int(2) DEFAULT NULL COMMENT '排序',
  `category` int(2) DEFAULT NULL COMMENT '菜单类型',
  `admin_flag` int(2) DEFAULT NULL COMMENT '0普通用户，前台注册用户与企业可用 1管理员用户，后台管理员用户可使用',
  `action` int(2) DEFAULT '0' COMMENT '操作按钮类型',
  `is_open` int(2) DEFAULT '1' COMMENT '是否打开新页面',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `is_deleted` int(2) DEFAULT '0' COMMENT '是否已删除',
  `create_user` bigint(64) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(64) DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `status` int(2) DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='菜单表';

-- 正在导出表  sailplat_ucs.ucs_menu 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `ucs_menu` DISABLE KEYS */;
/*!40000 ALTER TABLE `ucs_menu` ENABLE KEYS */;

-- 导出  表 sailplat_ucs.ucs_notice 结构
CREATE TABLE IF NOT EXISTS `ucs_notice` (
  `id` bigint(64) NOT NULL COMMENT '主键',
  `enterpriseId` bigint(20) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL COMMENT '标题',
  `category` int(11) DEFAULT NULL COMMENT '类型',
  `release_time` datetime DEFAULT NULL COMMENT '发布时间',
  `content` varchar(255) DEFAULT NULL COMMENT '内容',
  `create_user` bigint(64) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(64) DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `status` int(2) DEFAULT NULL COMMENT '状态',
  `is_deleted` int(2) DEFAULT NULL COMMENT '是否已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='通知公告表';

-- 正在导出表  sailplat_ucs.ucs_notice 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `ucs_notice` DISABLE KEYS */;
/*!40000 ALTER TABLE `ucs_notice` ENABLE KEYS */;

-- 导出  表 sailplat_ucs.ucs_param 结构
CREATE TABLE IF NOT EXISTS `ucs_param` (
  `id` bigint(64) NOT NULL COMMENT '主键',
  `param_name` varchar(255) DEFAULT NULL COMMENT '参数名',
  `param_key` varchar(255) DEFAULT NULL COMMENT '参数键',
  `param_value` varchar(255) DEFAULT NULL COMMENT '参数值',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_user` bigint(64) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(64) DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `status` int(2) DEFAULT NULL COMMENT '状态',
  `is_deleted` int(2) DEFAULT '0' COMMENT '是否已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='参数表';

-- 正在导出表  sailplat_ucs.ucs_param 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `ucs_param` DISABLE KEYS */;
/*!40000 ALTER TABLE `ucs_param` ENABLE KEYS */;

-- 导出  表 sailplat_ucs.ucs_post 结构
CREATE TABLE IF NOT EXISTS `ucs_post` (
  `id` bigint(64) NOT NULL COMMENT '主键',
  `enterprise_id` bigint(20) DEFAULT NULL,
  `category` int(11) DEFAULT NULL COMMENT '岗位类型',
  `post_code` varchar(12) DEFAULT NULL COMMENT '岗位编号',
  `post_name` varchar(64) DEFAULT NULL COMMENT '岗位名称',
  `sort` int(2) DEFAULT NULL COMMENT '岗位排序',
  `remark` varchar(255) DEFAULT NULL COMMENT '岗位描述',
  `create_user` bigint(64) DEFAULT NULL COMMENT '创建人',
  `create_dept` bigint(64) DEFAULT NULL COMMENT '创建部门',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(64) DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `status` int(2) DEFAULT NULL COMMENT '状态',
  `is_deleted` int(2) DEFAULT NULL COMMENT '是否已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='岗位表';

-- 正在导出表  sailplat_ucs.ucs_post 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `ucs_post` DISABLE KEYS */;
/*!40000 ALTER TABLE `ucs_post` ENABLE KEYS */;

-- 导出  表 sailplat_ucs.ucs_region 结构
CREATE TABLE IF NOT EXISTS `ucs_region` (
  `code` varchar(12) NOT NULL COMMENT '区划编号',
  `parent_code` varchar(12) DEFAULT NULL COMMENT '父区划编号',
  `ancestors` varchar(255) DEFAULT NULL COMMENT '祖区划编号',
  `name` varchar(32) DEFAULT NULL COMMENT '区划名称',
  `province_code` varchar(12) DEFAULT NULL COMMENT '省级区划编号',
  `province_name` varchar(32) DEFAULT NULL COMMENT '省级名称',
  `city_code` varchar(12) DEFAULT NULL COMMENT '市级区划编号',
  `city_name` varchar(32) DEFAULT NULL COMMENT '市级名称',
  `district_code` varchar(12) DEFAULT NULL COMMENT '区级区划编号',
  `district_name` varchar(32) DEFAULT NULL COMMENT '区级名称',
  `town_code` varchar(12) DEFAULT NULL COMMENT '镇级区划编号',
  `town_name` varchar(32) DEFAULT NULL COMMENT '镇级名称',
  `village_code` varchar(12) DEFAULT NULL COMMENT '村级区划编号',
  `village_name` varchar(32) DEFAULT NULL COMMENT '村级名称',
  `level` int(2) DEFAULT NULL COMMENT '层级',
  `sort` int(2) DEFAULT NULL COMMENT '排序',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='行政区划表';

-- 正在导出表  sailplat_ucs.ucs_region 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `ucs_region` DISABLE KEYS */;
/*!40000 ALTER TABLE `ucs_region` ENABLE KEYS */;

-- 导出  表 sailplat_ucs.ucs_role 结构
CREATE TABLE IF NOT EXISTS `ucs_role` (
  `id` bigint(64) NOT NULL COMMENT '主键',
  `enterprise_id` bigint(20) DEFAULT NULL COMMENT '所属企业',
  `role_name` varchar(255) DEFAULT NULL COMMENT '角色名',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `role_alias` varchar(255) DEFAULT NULL COMMENT '角色别名',
  `create_user` bigint(64) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(64) DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `status` int(2) NOT NULL COMMENT '状态',
  `is_deleted` int(2) NOT NULL COMMENT '是否已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色表';

-- 正在导出表  sailplat_ucs.ucs_role 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `ucs_role` DISABLE KEYS */;
/*!40000 ALTER TABLE `ucs_role` ENABLE KEYS */;

-- 导出  表 sailplat_ucs.ucs_role_menu 结构
CREATE TABLE IF NOT EXISTS `ucs_role_menu` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `menu_id` bigint(20) DEFAULT NULL COMMENT '菜单id',
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色id',
  `create_user` bigint(20) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(20) DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `status` int(2) NOT NULL COMMENT '状态',
  `is_deleted` int(2) NOT NULL COMMENT '是否已删除',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `FK_ROLE_MENU_ROLE` (`role_id`),
  CONSTRAINT `FK_ROLE_MENU_ROLE` FOREIGN KEY (`role_id`) REFERENCES `ucs_role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色菜单表';

-- 正在导出表  sailplat_ucs.ucs_role_menu 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `ucs_role_menu` DISABLE KEYS */;
/*!40000 ALTER TABLE `ucs_role_menu` ENABLE KEYS */;

-- 导出  表 sailplat_ucs.ucs_service 结构
CREATE TABLE IF NOT EXISTS `ucs_service` (
  `ID` bigint(20) NOT NULL,
  `service_name` varchar(50) DEFAULT NULL,
  `system_id` bigint(20) DEFAULT NULL COMMENT '此服务由哪个系统提供',
  `service_type` tinyint(2) DEFAULT NULL COMMENT '服务类型，所有人可访问，还是只是授权用户可以使用，授 权用户包括收费用户，只是收费是授 权的前提，是否收费由业务系统实现',
  `create_user` bigint(64) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(64) DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `status` int(2) NOT NULL COMMENT '状态',
  `is_deleted` int(2) NOT NULL COMMENT '是否已删除',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='可提供的服务清单，企业可以通过服务清单';

-- 正在导出表  sailplat_ucs.ucs_service 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `ucs_service` DISABLE KEYS */;
/*!40000 ALTER TABLE `ucs_service` ENABLE KEYS */;

-- 导出  表 sailplat_ucs.ucs_service_enterprise 结构
CREATE TABLE IF NOT EXISTS `ucs_service_enterprise` (
  `ID` bigint(20) NOT NULL,
  `service_id` bigint(20) DEFAULT NULL,
  `enterprise_id` bigint(20) DEFAULT NULL,
  `create_user` bigint(64) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(64) DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `status` int(2) NOT NULL COMMENT '状态',
  `is_deleted` int(2) NOT NULL COMMENT '是否已删除',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='服务企业授权表，表示哪个企业具有此服务的权限，此版本暂不考虑个人用户的服务授权';

-- 正在导出表  sailplat_ucs.ucs_service_enterprise 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `ucs_service_enterprise` DISABLE KEYS */;
/*!40000 ALTER TABLE `ucs_service_enterprise` ENABLE KEYS */;

-- 导出  表 sailplat_ucs.ucs_service_menu 结构
CREATE TABLE IF NOT EXISTS `ucs_service_menu` (
  `ID` bigint(20) DEFAULT NULL,
  `service_id` bigint(20) DEFAULT '0',
  `menu_id` bigint(20) DEFAULT '0',
  `create_user` bigint(64) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(64) DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `status` int(2) NOT NULL COMMENT '状态',
  `is_deleted` int(2) NOT NULL COMMENT '是否已删除'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='服务功能包设定，表明此功能属于哪个服务包，只有享受此服务包的用户才能访问这个功能';

-- 正在导出表  sailplat_ucs.ucs_service_menu 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `ucs_service_menu` DISABLE KEYS */;
/*!40000 ALTER TABLE `ucs_service_menu` ENABLE KEYS */;

-- 导出  表 sailplat_ucs.ucs_system 结构
CREATE TABLE IF NOT EXISTS `ucs_system` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `tenant_id` varchar(48) NOT NULL COMMENT '租户id',
  `system_name` varchar(48) NOT NULL COMMENT '系统名称',
  `create_user` bigint(64) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(64) DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `status` int(2) NOT NULL COMMENT '状态',
  `is_deleted` int(2) NOT NULL COMMENT '是否已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='系统表，租户->系统->企业，关系为，平台可以有多个租户，一个租户可以创建多个系统，一个系统，可以有多个client，一个企业可以入驻到多个系统中。企业可以采购多个服务\\r\\n';

-- 正在导出表  sailplat_ucs.ucs_system 的数据：~4 rows (大约)
/*!40000 ALTER TABLE `ucs_system` DISABLE KEYS */;
INSERT INTO `ucs_system` (`id`, `tenant_id`, `system_name`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES
	(0, '000000', '平台管理系统', NULL, NULL, NULL, NULL, 0, 0),
	(1, '000001', '供应链平台', NULL, NULL, NULL, NULL, 0, 0),
	(2, '000001', 'IOT平台', NULL, NULL, NULL, NULL, 0, 0),
	(3, '000002', '云测平台', NULL, NULL, NULL, NULL, 0, 0);
/*!40000 ALTER TABLE `ucs_system` ENABLE KEYS */;

-- 导出  表 sailplat_ucs.ucs_tenant 结构
CREATE TABLE IF NOT EXISTS `ucs_tenant` (
  `id` bigint(64) NOT NULL COMMENT '主键',
  `tenant_code` varchar(12) NOT NULL COMMENT '租户ID',
  `tenant_name` varchar(50) NOT NULL COMMENT '租户名称',
  `contact_man` varchar(20) DEFAULT NULL COMMENT '联系人',
  `contact_number` varchar(20) DEFAULT NULL COMMENT '联系电话',
  `address` varchar(255) DEFAULT NULL COMMENT '联系地址',
  `create_user` bigint(64) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(64) DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `status` int(2) DEFAULT NULL COMMENT '状态',
  `is_deleted` int(2) DEFAULT '0' COMMENT '是否已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='租户表，每一个租户，代表一个平台，各系统的数据库采用微服务模式，各系统自由维护，此处只做为资料库的模式为大家提供服务';

-- 正在导出表  sailplat_ucs.ucs_tenant 的数据：~3 rows (大约)
/*!40000 ALTER TABLE `ucs_tenant` DISABLE KEYS */;
INSERT INTO `ucs_tenant` (`id`, `tenant_code`, `tenant_name`, `contact_man`, `contact_number`, `address`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES
	(0, '000000', '信帆科技', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0),
	(1, '000001', '寓道科技', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0),
	(2, '000002', '莱诺斯科技', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0);
/*!40000 ALTER TABLE `ucs_tenant` ENABLE KEYS */;

-- 导出  表 sailplat_ucs.ucs_user 结构
CREATE TABLE IF NOT EXISTS `ucs_user` (
  `id` bigint(64) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `nick_name` varchar(12) DEFAULT 'nickname' COMMENT '用户昵称',
  `login_name` varchar(45) DEFAULT NULL COMMENT '账号',
  `password` varchar(45) DEFAULT NULL COMMENT '密码',
  `real_name` varchar(64) DEFAULT NULL COMMENT '真名',
  `user_email` varchar(45) DEFAULT NULL COMMENT '邮箱',
  `user_phone` varchar(45) DEFAULT NULL COMMENT '手机',
  `user_birthday` datetime DEFAULT NULL COMMENT '生日',
  `user_address` varchar(200) DEFAULT '' COMMENT '住址',
  `user_sex` smallint(6) DEFAULT NULL COMMENT '性别',
  `real_status` char(1) DEFAULT '0' COMMENT '实名认证状态:1已认证，0未认证',
  `last_login` varchar(64) DEFAULT NULL COMMENT '上次登陆时间',
  `enterprise_status` char(1) DEFAULT '0' COMMENT '企业认证状态:1已认证，0未认证',
  `safe_code` varchar(256) DEFAULT '' COMMENT '安全凭证',
  `phone_status` char(1) DEFAULT '0' COMMENT '手机认证:0未认证,1已认证',
  `email_status` char(1) DEFAULT '0' COMMENT '邮箱认证:0未认证,1已认证',
  `info_status` varchar(1) DEFAULT '0' COMMENT '账户完善信息:0未完成,1已完成',
  `default_enterprise` bigint(12) DEFAULT NULL COMMENT '缺省的企业ID',
  `head_img` varchar(256) DEFAULT '' COMMENT '头像',
  `status` int(2) DEFAULT NULL COMMENT '状态  1禁用  0启用',
  `create_user` bigint(64) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(64) DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `is_deleted` int(2) DEFAULT '0' COMMENT '是否已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1301788793255452674 DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- 正在导出表  sailplat_ucs.ucs_user 的数据：~9 rows (大约)
/*!40000 ALTER TABLE `ucs_user` DISABLE KEYS */;
INSERT INTO `ucs_user` (`id`, `nick_name`, `login_name`, `password`, `real_name`, `user_email`, `user_phone`, `user_birthday`, `user_address`, `user_sex`, `real_status`, `last_login`, `enterprise_status`, `safe_code`, `phone_status`, `email_status`, `info_status`, `default_enterprise`, `head_img`, `status`, `create_user`, `create_time`, `update_user`, `update_time`, `is_deleted`) VALUES
	(1, 'ceshi22222', 'login12222', '19048575014b19af831777db50e6c6df81078fc9', 'zhenshi12222', NULL, NULL, NULL, NULL, NULL, '0', NULL, '0', NULL, '0', '0', '0', 11, NULL, 1, NULL, '2020-08-28 15:46:02', NULL, '2020-08-28 15:50:25', 0),
	(2, 'xiaohsd234u', 'login1', '10470c3b4b1fed12c3baac014be15fac67c6e815', 'zsdf', NULL, NULL, NULL, NULL, NULL, '0', NULL, '0', NULL, '0', '0', '0', 22, NULL, 1, NULL, '2020-08-28 15:51:34', NULL, '2020-08-28 16:00:09', 0),
	(3, 'xidgsdf', 'locehi1', '10470c3b4b1fed12c3baac014be15fac67c6e815', 'zyertf', NULL, NULL, NULL, NULL, NULL, '0', NULL, '0', NULL, '0', '0', '0', 33, NULL, 1, NULL, '2020-08-28 15:51:52', NULL, '2020-08-28 16:00:09', 0),
	(4, 'nick2', 'user', '10470c3b4b1fed12c3baac014be15fac67c6e815', '李文', 'lw@linose.com', '1829837263', '2020-08-19 15:31:19', '中华大街11号', 0, '0', '1123598816738675201', '0', 'sd423', '0', '0', '0', 44, '/img/ceshi1.jpg', 1, 1123598821738675201, '2020-08-06 15:32:52', 1123598821738675201, '2020-08-16 15:32:58', 0),
	(1123598821738675201, 'nick1', 'admin', '90b9aa7e25f80cf4f64e990b78a9fc5ebd6cecad', '张伟', 'dev@yudorm.com', '88888888', '2018-08-08 00:00:00', '华安与路32号', 1, '0', '1123598816738675201', '0', 'gere32', '0', '0', '0', 111, '/img/ceshi2.jpg', 1, 1123598821738675201, '2018-08-08 00:00:00', 1123598821738675201, '2020-06-25 22:59:15', 0),
	(1301450988557905921, 'nickname', '18311460517', '07543b0b48237ae2fc6a30571727f615f352e516', NULL, NULL, '18311460517', NULL, '', NULL, '0', '1599125070117', '0', '3EE6EA87A57B11A86CE7D491E496CBD9', '0', '0', '0', 0, '', NULL, NULL, '2020-09-03 17:24:30', NULL, NULL, 0),
	(1301451466976997377, 'nickname', '18311460516', '07543b0b48237ae2fc6a30571727f615f352e516', NULL, NULL, '18311460516', NULL, '', NULL, '0', '1599125184183', '0', '60C96785797F450B9028CAEE7D61AF28', '0', '0', '0', 0, '', NULL, NULL, '2020-09-03 17:26:24', NULL, NULL, 0),
	(1301688873479528450, 'nickname', '18311460511', '07543b0b48237ae2fc6a30571727f615f352e516', NULL, NULL, '18311460511', NULL, '', NULL, '0', '1599181786297', '0', 'E27A332645A278885304284FF10C8836', '0', '0', '0', 0, '', NULL, NULL, '2020-09-04 09:09:46', NULL, NULL, 0),
	(1301788793255452673, 'nickname', '18311460510', 'd76ed756c0374727a9316cec7eef0ce57c4e63b0', NULL, NULL, '18311460510', NULL, '', NULL, '0', '1599205609030', '0', '18D55228490486008D26C39C3D5CA01D', '0', '0', '0', 112, '', NULL, NULL, '2020-09-04 15:46:49', NULL, NULL, 0);
/*!40000 ALTER TABLE `ucs_user` ENABLE KEYS */;

-- 导出  表 sailplat_ucs.ucs_user_enterprise 结构
CREATE TABLE IF NOT EXISTS `ucs_user_enterprise` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户-企业关系ID',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `enterprise_id` bigint(20) DEFAULT NULL COMMENT '企业ID',
  `department_id` bigint(20) DEFAULT NULL COMMENT '部门ID',
  `status` int(1) DEFAULT NULL COMMENT '状态（1为管理员 2为普通成员）',
  `createTime` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '加入时间',
  `is_open` char(1) COLLATE utf8_unicode_ci DEFAULT '1' COMMENT '该成员在本企业中的状态0关 ，1开',
  `is_exits` varchar(1) COLLATE utf8_unicode_ci DEFAULT '0' COMMENT '用户在该企业的测试中心中是否添加过:1已添加,0未添加',
  `create_user` bigint(64) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(64) DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `is_deleted` int(2) DEFAULT '0' COMMENT '是否已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=641 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='用户-企业关系表';

-- 正在导出表  sailplat_ucs.ucs_user_enterprise 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `ucs_user_enterprise` DISABLE KEYS */;
/*!40000 ALTER TABLE `ucs_user_enterprise` ENABLE KEYS */;

-- 导出  表 sailplat_ucs.ucs_user_role 结构
CREATE TABLE IF NOT EXISTS `ucs_user_role` (
  `id` bigint(20) NOT NULL,
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色id',
  `create_user` bigint(20) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(20) DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `status` int(2) NOT NULL COMMENT '状态',
  `is_deleted` int(2) NOT NULL COMMENT '是否已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  sailplat_ucs.ucs_user_role 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `ucs_user_role` DISABLE KEYS */;
/*!40000 ALTER TABLE `ucs_user_role` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
