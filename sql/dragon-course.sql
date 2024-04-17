CREATE TABLE `course_extra`(
                               `id` BIGINT NOT NULL COMMENT '主键，课程id',
                               `charge` VARCHAR(32) NOT NULL COMMENT '收费规则，对应数据字典',
                               `price` DOUBLE(10, 2) NULL DEFAULT 0 COMMENT '现价',
                               `original_price` DOUBLE(10, 2) NULL DEFAULT 0 COMMENT '原价',
                               `teachers` VARCHAR(64) NOT NULL COMMENT '教师id列表，形如[1,2]',
                               `email` VARCHAR(64) NOT NULL,
                               `qq` VARCHAR(64) NOT NULL,
                               `wechat` VARCHAR(64) NOT NULL,
                               `phone` VARCHAR(64) NOT NULL,
                               PRIMARY KEY(`id`)
);
CREATE TABLE `teacher`(
                          `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
                          `name` VARCHAR(32) NOT NULL,
                          `position` VARCHAR(64) NOT NULL,
                          `introduction` VARCHAR(1024) NOT NULL,
                          `photograph` VARCHAR(1024) NOT NULL
);
CREATE TABLE `course_base`(
                              `id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '主键',
                              `company_id` BIGINT NOT NULL COMMENT '机构ID',
                              `company_name` VARCHAR(255) NOT NULL COMMENT '机构名称',
                              `name` VARCHAR(100) NOT NULL COMMENT '课程名称',
                              `tags` VARCHAR(50) NOT NULL COMMENT '课程标签',
                              `mt` VARCHAR(20) NOT NULL COMMENT '大分类',
                              `st` VARCHAR(20) NOT NULL COMMENT '小分类',
                              `description` TEXT NULL COMMENT '课程介绍',
                              `pic` VARCHAR(500) NULL COMMENT '课程图片',
                              `create_date` DATETIME NULL COMMENT '创建时间',
                              `update_date` DATETIME NULL COMMENT '修改时间',
                              `status` VARCHAR(10) NOT NULL DEFAULT '1' COMMENT '未发布,审核中,审核不通过,审核通过,已发布',
                              `audit_mind` VARCHAR(255) NULL COMMENT '审核意见'
);
CREATE TABLE `course_publish`(
                                 `id` BIGINT NOT NULL COMMENT '主键',
                                 `company_id` BIGINT NOT NULL COMMENT '机构ID',
                                 `company_name` VARCHAR(255) NULL COMMENT '公司名称',
                                 `name` VARCHAR(100) NOT NULL COMMENT '课程名称',
                                 `tags` VARCHAR(32) NULL COMMENT '标签',
                                 `mt` VARCHAR(20) NOT NULL COMMENT '大分类',
                                 `mt_name` VARCHAR(255) NOT NULL COMMENT '大分类名称',
                                 `st` VARCHAR(20) NOT NULL COMMENT '小分类',
                                 `st_name` VARCHAR(255) NOT NULL COMMENT '小分类名称',
                                 `pic` VARCHAR(500) NOT NULL COMMENT '课程图片',
                                 `description` TEXT NULL COMMENT '课程介绍',
                                 `extra` TEXT NULL COMMENT '课程额外信息，json格式',
                                 `teachplan` TEXT NULL COMMENT '所有课程计划，json格式',
                                 `teachers` TEXT NULL COMMENT '教师信息列表，json格式',
                                 `update_date` DATETIME NULL COMMENT '发布时间',
                                 PRIMARY KEY(`id`)
);
CREATE TABLE `teachplan`(
                            `id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                            `pname` VARCHAR(64) NOT NULL COMMENT '课程计划名称',
                            `parentid` BIGINT NOT NULL COMMENT '课程计划父级Id',
                            `grade` SMALLINT NOT NULL COMMENT '层级，分为1、2级',
                            `description` VARCHAR(500) NULL COMMENT '章节及课程时介绍',
                            `orderby` INT NOT NULL DEFAULT 0 COMMENT '排序字段',
                            `course_id` BIGINT NOT NULL COMMENT '课程标识',
                            `teachplan_id` BIGINT NULL COMMENT '媒资id',
                            `is_preview` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否支持试学或预览（试看）'
);
CREATE TABLE `mq_message_history`(
                                     `id` BIGINT NOT NULL COMMENT '消息id',
                                     `message_type` VARCHAR(32) NOT NULL COMMENT '消息类型代码',
                                     `business_key1` VARCHAR(64) NULL COMMENT '关联业务信息',
                                     `business_key2` VARCHAR(255) NULL COMMENT '关联业务信息',
                                     `business_key3` VARCHAR(512) NULL COMMENT '关联业务信息',
                                     `execute_num` INT UNSIGNED NULL COMMENT '通知次数',
                                     `state` INT UNSIGNED NULL COMMENT '处理状态，0:初始，1:成功，2:失败',
                                     `returnfailure_date` DATETIME NULL COMMENT '回复失败时间',
                                     `returnsuccess_date` DATETIME NULL COMMENT '回复成功时间',
                                     `returnfailure_msg` VARCHAR(255) NULL COMMENT '回复失败内容',
                                     `execute_date` DATETIME NULL COMMENT '最近通知时间',
                                     `stage_state1` CHAR(1) NULL,
                                     `stage_state2` CHAR(1) NULL,
                                     `stage_state3` CHAR(1) NULL,
                                     `stage_state4` CHAR(1) NULL,
                                     PRIMARY KEY(`id`)
);
CREATE TABLE `mq_message`(
                             `id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '消息id',
                             `message_type` VARCHAR(32) NOT NULL COMMENT '消息类型代码: course_publish ,  media_test',
                             `business_key1` VARCHAR(64) NULL COMMENT '关联业务信息',
                             `business_key2` VARCHAR(255) NULL COMMENT '关联业务信息',
                             `business_key3` VARCHAR(512) NULL COMMENT '关联业务信息',
                             `execute_num` INT UNSIGNED NOT NULL DEFAULT 0 COMMENT '通知次数',
                             `state` CHAR(1) NOT NULL DEFAULT 0 COMMENT '处理状态，0:初始，1:成功',
                             `returnfailure_date` DATETIME NULL COMMENT '回复失败时间',
                             `returnsuccess_date` DATETIME NULL COMMENT '回复成功时间',
                             `returnfailure_msg` VARCHAR(2048) NULL COMMENT '回复失败内容',
                             `execute_date` DATETIME NULL COMMENT '最近通知时间',
                             `stage_state1` CHAR(1) NOT NULL DEFAULT 0 COMMENT '阶段1处理状态, 0:初始，1:成功',
                             `stage_state2` CHAR(1) NOT NULL DEFAULT 0 COMMENT '阶段2处理状态, 0:初始，1:成功',
                             `stage_state3` CHAR(1) NOT NULL DEFAULT 0 COMMENT '阶段3处理状态, 0:初始，1:成功',
                             `stage_state4` CHAR(1) NOT NULL DEFAULT 0 COMMENT '阶段4处理状态, 0:初始，1:成功'
);
CREATE TABLE `course_category`(
                                  `id` VARCHAR(20) NOT NULL COMMENT '主键',
                                  `name` VARCHAR(32) NOT NULL COMMENT '分类名称',
                                  `parentid` VARCHAR(20) NOT NULL DEFAULT '0' COMMENT '父结点id（第一级的父节点是0，自关联字段id）',
                                  `orderby` INT NOT NULL DEFAULT 0 COMMENT '排序字段',
                                  `is_leaf` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否叶子',
                                  PRIMARY KEY(`id`)
);
