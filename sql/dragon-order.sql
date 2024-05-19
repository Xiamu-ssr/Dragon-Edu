create table `dragon-order`.order_statistics
(
    id         bigint         not null
        primary key,
    company_id bigint         not null comment '机构id',
    sale_num   int            not null comment '销售量',
    sale       decimal(10, 2) not null comment '销售额',
    stat_date  date           not null comment '日期'
)
    comment '订单统计';

create index order_statistics_company_id__index
    on `dragon-order`.order_statistics (company_id, stat_date);

create table `dragon-order`.order_table
(
    id          bigint                      not null
        primary key,
    course_id   bigint                      not null comment '课程id',
    course_name varchar(100)                null comment '课程名称',
    company_id  bigint                      not null comment '机构id',
    user_id     bigint                      not null comment '用户id',
    create_time datetime                    not null comment '创建日期',
    price       decimal(10, 2) default 0.00 not null comment '金额',
    status      tinyint                     not null comment '订单状态：1未支付、2支付成功、3支付失败。',
    remarks     varchar(255)   default '无' not null comment '备注'
);

create index order_table_company_id_status_index
    on `dragon-order`.order_table (company_id, status);

create index order_table_user_id_status_index
    on `dragon-order`.order_table (user_id, status);

create table `dragon-order`.sale_data
(
    id         bigint                      not null
        primary key,
    company_id bigint                      not null comment '公司id',
    sale_num   bigint         default 0    not null comment '销售总量',
    revenue    decimal(12, 2) default 0.00 not null comment '销售总额'
)
    comment '销售总量统计';

