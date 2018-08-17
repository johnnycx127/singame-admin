/*---------------------------create user--------------------------------*/
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '员工编码 根据年份以及ID生成',
  `password` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '密码',
  `name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '员工姓名',
  `gender` int(11) DEFAULT NULL COMMENT '性别 0-未知 1-男 2-女',
  `status` int(11) DEFAULT NULL COMMENT '员工状态 0-未知 1-可用 2-冻结 3-注销',
  `departmentId` bigint(20) DEFAULT NULL COMMENT '部门ID',
  `is_department_master` int(11) DEFAULT NULL COMMENT '是否为部门主管',
  `position` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '职位',
  `created_by` bigint(20) DEFAULT NULL COMMENT '创建者',
  `updated_by` bigint(20) DEFAULT NULL COMMENT '修改者',
  `created_at` datetime DEFAULT NULL COMMENT '创建日期',
  `updated_at` datetime DEFAULT NULL COMMENT '更新日期',
  `version` int(11) DEFAULT NULL COMMENT '版本, 乐观锁',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_code_index` (`code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*---------------------------end create user----------------------------*/

/*---------------------------create department--------------------------*/
CREATE TABLE `department` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `pid` bigint(20) DEFAULT NULL COMMENT '父ID',
  `name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '名称',
  `describtion` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*---------------------------end create department--------------------------*/

/*---------------------------create permission------------------------------*/
CREATE TABLE `permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '编码',
  `resouce` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '资源',
  `action` int(11) DEFAULT NULL COMMENT '行为 0-未知 1-读取 2-写入',
  `name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '名称',
  `discribtion` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '描述',
  `created_by` bigint(20) DEFAULT NULL COMMENT '创建人',
  `updated_by` bigint(20) DEFAULT NULL COMMENT '修改人',
  `removed_by` bigint(20) DEFAULT NULL COMMENT '删除人',
  `created_at` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_at` datetime DEFAULT NULL COMMENT '修改时间',
  `removed_at` datetime DEFAULT NULL COMMENT '删除时间',
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `permission_code_uniqu_index` (`code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*---------------------------end create permission--------------------------*/

/*---------------------------create role------------------------------------*/
CREATE TABLE `role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '名称',
  `created_by` bigint(20) DEFAULT NULL,
  `updated_by` bigint(20) DEFAULT NULL,
  `removed_by` bigint(20) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `removed_at` datetime DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*---------------------------end create role-------------------------------*/

/*---------------------------create user_role------------------------------------*/
CREATE TABLE `user_role` (
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `user_role_user_id_index` (`user_id`) USING BTREE,
  KEY `user_role_role_id_index` (`role_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*---------------------------end create user_role---------------------------------*/

/*---------------------------create role_permission------------------------------------*/
CREATE TABLE `role_permission` (
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `permission_id` bigint(20) NOT NULL COMMENT '权限ID',
  PRIMARY KEY (`role_id`,`permission_id`),
  KEY `role_permission_role_id_index` (`role_id`) USING BTREE,
  KEY `role_permission_permission_id_index` (`permission_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*---------------------------end create role_permission---------------------------------*/