create table `dragon-course`.course_base
(
    id             bigint auto_increment comment '主键'
        primary key,
    company_id     bigint                      not null comment '机构ID',
    name           varchar(100)                not null comment '课程名称',
    mt             varchar(20)                 not null comment '大分类',
    st             varchar(20)                 not null comment '小分类',
    pic            varchar(500)                null comment '课程图片',
    charge         tinyint(1)     default 0    not null comment '是否收费',
    original_price decimal(10, 2) default 0.00 null comment '原价',
    price          decimal(10, 2) default 0.00 null comment '现价',
    star           decimal(2, 1)  default 5.0  null comment '评分',
    status         tinyint        default 1    not null comment 'UNPUBLISHED(1, "未发布"),     UNDER_REVIEW(2, "审核中"),     REVIEW_FAILED(3, "审核不通过"),     REVIEW_PASSED(4, "审核通过")',
    mind           varchar(255)                null comment '审核意见',
    create_time    datetime                    null comment '创建时间',
    update_time    datetime                    null comment '修改时间'
);

create index course_base_company_id_index
    on `dragon-course`.course_base (company_id);

create table `dragon-course`.course_category
(
    id          bigint                  not null comment '主键'
        primary key,
    name        varchar(32)             not null comment '分类名称',
    parentid    bigint       default 0  not null comment '父结点id（第一级的父节点是0，自关联字段id）',
    orderby     int          default 0  not null comment '排序字段',
    description varchar(255) default '' not null comment '描述',
    is_leaf     tinyint(1)   default 0  not null comment '是否叶子'
);

create table `dragon-course`.course_extra
(
    id           bigint                  not null comment '主键，课程id'
        primary key,
    company_name varchar(128) default '' not null comment '机构名称',
    tags         varchar(128) default '' not null comment '课程标签，用符号,分割',
    description  text                    null comment '课程介绍',
    teachers     varchar(64)             null comment '教师id列表，形如[''''1'''',''''2'''']',
    email        varchar(64)             not null,
    qq           varchar(64)             null,
    wechat       varchar(64)             null,
    phone        varchar(64)             null
);

create table `dragon-course`.course_publish
(
    id         bigint not null comment '主键-课程id'
        primary key,
    company_id bigint not null comment '机构ID',
    info       text   not null comment '课程所有信息'
);

create table `dragon-course`.mq_message
(
    id                 bigint auto_increment comment '消息id'
        primary key,
    message_type       varchar(32)              not null comment '消息类型代码: course_publish ,  media_test',
    business_key1      varchar(64)              null comment '关联业务信息',
    business_key2      varchar(255)             null comment '关联业务信息',
    business_key3      varchar(512)             null comment '关联业务信息',
    execute_num        int unsigned default '0' not null comment '通知次数',
    state              char         default '0' not null comment '处理状态，0:初始，1:成功',
    returnfailure_date datetime                 null comment '回复失败时间',
    returnsuccess_date datetime                 null comment '回复成功时间',
    returnfailure_msg  varchar(2048)            null comment '回复失败内容',
    execute_date       datetime                 null comment '最近通知时间',
    stage_state1       char         default '0' not null comment '阶段1处理状态, 0:初始，1:成功',
    stage_state2       char         default '0' not null comment '阶段2处理状态, 0:初始，1:成功',
    stage_state3       char         default '0' not null comment '阶段3处理状态, 0:初始，1:成功',
    stage_state4       char         default '0' not null comment '阶段4处理状态, 0:初始，1:成功'
);

create table `dragon-course`.mq_message_history
(
    id                 bigint       not null comment '消息id'
        primary key,
    message_type       varchar(32)  not null comment '消息类型代码',
    business_key1      varchar(64)  null comment '关联业务信息',
    business_key2      varchar(255) null comment '关联业务信息',
    business_key3      varchar(512) null comment '关联业务信息',
    execute_num        int unsigned null comment '通知次数',
    state              int unsigned null comment '处理状态，0:初始，1:成功，2:失败',
    returnfailure_date datetime     null comment '回复失败时间',
    returnsuccess_date datetime     null comment '回复成功时间',
    returnfailure_msg  varchar(255) null comment '回复失败内容',
    execute_date       datetime     null comment '最近通知时间',
    stage_state1       char         null,
    stage_state2       char         null,
    stage_state3       char         null,
    stage_state4       char         null
);

create table `dragon-course`.teacher
(
    id           bigint unsigned auto_increment comment '教师ID'
        primary key,
    company_id   bigint        not null comment '所属机构id',
    name         varchar(32)   not null comment '教师姓名',
    position     varchar(64)   not null comment '教师职位',
    introduction varchar(1024) not null comment '教师介绍',
    photograph   varchar(1024) not null comment '教师头像'
);

create index teacher_company_id_index
    on `dragon-course`.teacher (company_id);

create table `dragon-course`.teachplan
(
    id          varchar(36)             not null comment 'UUID'
        primary key,
    pname       varchar(64)             not null comment '课程计划名称',
    parentid    varchar(36)             null comment '课程计划父级Id，大章节写"0"',
    grade       smallint                not null comment '层级，分为1、2级',
    orderby     int          default 0  not null comment '排序字段',
    course_id   bigint                  not null comment '课程标识',
    media_id    varchar(64)  default '' not null comment '媒资id',
    media_name  varchar(255) default '' not null comment '媒资名称',
    is_preview  tinyint(1)   default 0  not null comment '是否支持试学或预览（试看）',
    description varchar(500)            null comment '章节及课程时介绍'
);

create index teachplan_course_id_index
    on `dragon-course`.teachplan (course_id);

