create table `dragon-discuss`.discuss
(
    id          bigint                  not null
        primary key,
    course_id   bigint                  not null comment '课程id',
    course_name varchar(100)            not null comment '课程名称',
    company_id  bigint                  not null comment '机构id',
    user_id     bigint                  not null comment '用户id',
    user_name   varchar(30)             not null comment '用户名称',
    avatar      varchar(500)            null comment '头像地址url',
    learn_time  int                     null comment '评论时用户学习时长/分钟',
    content     varchar(255) default '' not null comment '评论内容',
    star        decimal(2, 1)           not null comment '评分',
    status      tinyint                 not null comment '评论状态。1正常2申请屏蔽3屏蔽',
    create_time datetime                null comment '创建时间'
)
    comment '课程评论表';

create index discuss_course_id_index
    on `dragon-discuss`.discuss (course_id);

create index discuss_user_id_index
    on `dragon-discuss`.discuss (user_id);

create table `dragon-discuss`.discuss_statistics
(
    id            bigint                            not null
        primary key,
    course_id     bigint                            not null comment '课程id',
    course_name   varchar(100)                      not null comment '课程名称',
    pic           varchar(500)                      not null comment '课程图片',
    company_id    bigint                            not null comment '机构id',
    discuss_count int            default 0          not null comment '评论数量',
    star          decimal(10, 8) default 0.00000000 not null comment '评分-高精度'
)
    comment '评论统计表，机构用';

create index discuss_statistics_company_id_index
    on `dragon-discuss`.discuss_statistics (company_id);

