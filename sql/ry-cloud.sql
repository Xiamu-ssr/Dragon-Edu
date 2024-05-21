-- ----------------------------
-- 第三方平台授权表
-- ----------------------------
drop table if exists sys_social;
create table sys_social
(
    id                 bigint           not null        comment '主键',
    user_id            bigint           not null        comment '用户ID',
    tenant_id          varchar(20)      default null    comment '租户id',
    auth_id            varchar(255)     not null        comment '平台+平台唯一id',
    source             varchar(255)     not null        comment '用户来源',
    open_id            varchar(255)     default null    comment '平台编号唯一id',
    user_name          varchar(30)      not null        comment '登录账号',
    nick_name          varchar(30)      default ''      comment '用户昵称',
    email              varchar(255)     default ''      comment '用户邮箱',
    avatar             varchar(500)     default ''      comment '头像地址',
    access_token       varchar(255)     not null        comment '用户的授权令牌',
    expire_in          int              default null    comment '用户的授权令牌的有效期，部分平台可能没有',
    refresh_token      varchar(255)     default null    comment '刷新令牌，部分平台可能没有',
    access_code        varchar(255)     default null    comment '平台的授权信息，部分平台可能没有',
    union_id           varchar(255)     default null    comment '用户的 unionid',
    scope              varchar(255)     default null    comment '授予的权限，部分平台可能没有',
    token_type         varchar(255)     default null    comment '个别平台的授权信息，部分平台可能没有',
    id_token           varchar(255)     default null    comment 'id token，部分平台可能没有',
    mac_algorithm      varchar(255)     default null    comment '小米平台用户的附带属性，部分平台可能没有',
    mac_key            varchar(255)     default null    comment '小米平台用户的附带属性，部分平台可能没有',
    code               varchar(255)     default null    comment '用户的授权code，部分平台可能没有',
    oauth_token        varchar(255)     default null    comment 'Twitter平台用户的附带属性，部分平台可能没有',
    oauth_token_secret varchar(255)     default null    comment 'Twitter平台用户的附带属性，部分平台可能没有',
    create_dept        bigint(20)                       comment '创建部门',
    create_by          bigint(20)                       comment '创建者',
    create_time        datetime                         comment '创建时间',
    update_by          bigint(20)                       comment '更新者',
    update_time        datetime                         comment '更新时间',
    del_flag           char(1)          default '0'     comment '删除标志（0代表存在 2代表删除）',
    PRIMARY KEY (id)
) engine=innodb comment = '社会化关系表';

-- ----------------------------
-- 租户表
-- ----------------------------
drop table if exists sys_tenant;
create table sys_tenant
(
    id                bigint(20)    not null        comment 'id',
    tenant_id         varchar(20)   not null        comment '租户编号',
    contact_user_name varchar(20)                   comment '联系人',
    contact_phone     varchar(20)                   comment '联系电话',
    company_name      varchar(50)                   comment '企业名称',
    license_number    varchar(30)                   comment '统一社会信用代码',
    address           varchar(200)                  comment '地址',
    intro             varchar(200)                  comment '企业简介',
    domain            varchar(200)                  comment '域名',
    remark            varchar(200)                  comment '备注',
    package_id        bigint(20)                    comment '租户套餐编号',
    expire_time       datetime                      comment '过期时间',
    account_count     int           default -1      comment '用户数量（-1不限制）',
    status            char(1)       default '0'     comment '租户状态（0正常 1停用）',
    del_flag          char(1)       default '0'     comment '删除标志（0代表存在 2代表删除）',
    create_dept       bigint(20)                    comment '创建部门',
    create_by         bigint(20)                    comment '创建者',
    create_time       datetime                      comment '创建时间',
    update_by         bigint(20)                    comment '更新者',
    update_time       datetime                      comment '更新时间',
    primary key (id)
) engine=innodb comment = '租户表';


-- ----------------------------
-- 初始化-租户表数据
-- ----------------------------

insert into sys_tenant values(1, '000000', '管理组', '15888888888', 'XXX有限公司', NULL, NULL, '多租户通用后台管理管理系统', NULL, NULL, NULL, NULL, -1, '0', '0', 103, 1, sysdate(), NULL, NULL);


-- ----------------------------
-- 租户套餐表
-- ----------------------------
drop table if exists sys_tenant_package;
create table sys_tenant_package (
    package_id              bigint(20)     not null    comment '租户套餐id',
    package_name            varchar(20)                comment '套餐名称',
    menu_ids                varchar(3000)              comment '关联菜单id',
    remark                  varchar(200)               comment '备注',
    menu_check_strictly     tinyint(1)     default 1   comment '菜单树选择项是否关联显示',
    status                  char(1)        default '0' comment '状态（0正常 1停用）',
    del_flag                char(1)        default '0' comment '删除标志（0代表存在 2代表删除）',
    create_dept             bigint(20)                 comment '创建部门',
    create_by               bigint(20)                 comment '创建者',
    create_time             datetime                   comment '创建时间',
    update_by               bigint(20)                 comment '更新者',
    update_time             datetime                   comment '更新时间',
    primary key (package_id)
) engine=innodb comment = '租户套餐表';


-- ----------------------------
-- 1、部门表
-- ----------------------------
drop table if exists sys_dept;
create table sys_dept (
  dept_id           bigint(20)      not null                   comment '部门id',
  tenant_id         varchar(20)     default '000000'           comment '租户编号',
  parent_id         bigint(20)      default 0                  comment '父部门id',
  ancestors         varchar(500)    default ''                 comment '祖级列表',
  dept_name         varchar(30)     default ''                 comment '部门名称',
  order_num         int(4)          default 0                  comment '显示顺序',
  leader            bigint(20)      default null               comment '负责人',
  phone             varchar(11)     default null               comment '联系电话',
  email             varchar(50)     default null               comment '邮箱',
  status            char(1)         default '0'                comment '部门状态（0正常 1停用）',
  del_flag          char(1)         default '0'                comment '删除标志（0代表存在 2代表删除）',
  create_dept       bigint(20)      default null               comment '创建部门',
  create_by         bigint(20)      default null               comment '创建者',
  create_time       datetime                                   comment '创建时间',
  update_by         bigint(20)      default null               comment '更新者',
  update_time       datetime                                   comment '更新时间',
  primary key (dept_id)
) engine=innodb comment = '部门表';

-- ----------------------------
-- 初始化-部门表数据
-- ----------------------------

INSERT INTO `ry-cloud`.sys_dept (dept_id, tenant_id, parent_id, ancestors, dept_name, order_num, leader, phone, email, status, del_flag, create_dept, create_by, create_time, update_by, update_time) VALUES (100, '000000', 0, '0', '根目录', 0, null, '15888888888', 'xxx@qq.com', '0', '0', 103, 1, '2024-03-06 14:54:11', 1, '2024-03-07 22:57:39');
INSERT INTO `ry-cloud`.sys_dept (dept_id, tenant_id, parent_id, ancestors, dept_name, order_num, leader, phone, email, status, del_flag, create_dept, create_by, create_time, update_by, update_time) VALUES (101, '000000', 100, '0,100', '总公司', 1, null, '15888888888', 'xxx@qq.com', '0', '0', 103, 1, '2024-03-06 14:54:11', 1, '2024-04-05 22:31:16');
INSERT INTO `ry-cloud`.sys_dept (dept_id, tenant_id, parent_id, ancestors, dept_name, order_num, leader, phone, email, status, del_flag, create_dept, create_by, create_time, update_by, update_time) VALUES (102, '000000', 100, '0,100', '长沙分公司', 2, null, '15888888888', 'xxx@qq.com', '0', '2', 103, 1, '2024-03-06 14:54:11', null, null);
INSERT INTO `ry-cloud`.sys_dept (dept_id, tenant_id, parent_id, ancestors, dept_name, order_num, leader, phone, email, status, del_flag, create_dept, create_by, create_time, update_by, update_time) VALUES (103, '000000', 101, '0,100,101', '研发部门', 1, 1, '15888888888', 'xxx@qq.com', '0', '0', 103, 1, '2024-03-06 14:54:11', null, null);
INSERT INTO `ry-cloud`.sys_dept (dept_id, tenant_id, parent_id, ancestors, dept_name, order_num, leader, phone, email, status, del_flag, create_dept, create_by, create_time, update_by, update_time) VALUES (104, '000000', 101, '0,100,101', '市场部门', 2, null, '15888888888', 'xxx@qq.com', '0', '2', 103, 1, '2024-03-06 14:54:11', null, null);
INSERT INTO `ry-cloud`.sys_dept (dept_id, tenant_id, parent_id, ancestors, dept_name, order_num, leader, phone, email, status, del_flag, create_dept, create_by, create_time, update_by, update_time) VALUES (105, '000000', 101, '0,100,101', '测试部门', 3, null, '15888888888', 'xxx@qq.com', '0', '0', 103, 1, '2024-03-06 14:54:11', null, null);
INSERT INTO `ry-cloud`.sys_dept (dept_id, tenant_id, parent_id, ancestors, dept_name, order_num, leader, phone, email, status, del_flag, create_dept, create_by, create_time, update_by, update_time) VALUES (106, '000000', 101, '0,100,101', '财务部门', 4, null, '15888888888', 'xxx@qq.com', '0', '2', 103, 1, '2024-03-06 14:54:11', null, null);
INSERT INTO `ry-cloud`.sys_dept (dept_id, tenant_id, parent_id, ancestors, dept_name, order_num, leader, phone, email, status, del_flag, create_dept, create_by, create_time, update_by, update_time) VALUES (107, '000000', 101, '0,100,101', '运营部门', 5, null, '15888888888', 'xxx@qq.com', '0', '0', 103, 1, '2024-03-06 14:54:11', 1, '2024-04-05 22:31:42');
INSERT INTO `ry-cloud`.sys_dept (dept_id, tenant_id, parent_id, ancestors, dept_name, order_num, leader, phone, email, status, del_flag, create_dept, create_by, create_time, update_by, update_time) VALUES (108, '000000', 102, '0,100,102', '市场部门', 1, null, '15888888888', 'xxx@qq.com', '0', '2', 103, 1, '2024-03-06 14:54:11', null, null);
INSERT INTO `ry-cloud`.sys_dept (dept_id, tenant_id, parent_id, ancestors, dept_name, order_num, leader, phone, email, status, del_flag, create_dept, create_by, create_time, update_by, update_time) VALUES (109, '000000', 102, '0,100,102', '财务部门', 2, null, '15888888888', 'xxx@qq.com', '0', '2', 103, 1, '2024-03-06 14:54:11', null, null);
INSERT INTO `ry-cloud`.sys_dept (dept_id, tenant_id, parent_id, ancestors, dept_name, order_num, leader, phone, email, status, del_flag, create_dept, create_by, create_time, update_by, update_time) VALUES (1765753747772293122, '000000', 100, '0,100', '外部机构', 3, null, null, null, '0', '0', 103, 1, '2024-03-07 22:57:54', 1, '2024-03-07 22:57:54');
INSERT INTO `ry-cloud`.sys_dept (dept_id, tenant_id, parent_id, ancestors, dept_name, order_num, leader, phone, email, status, del_flag, create_dept, create_by, create_time, update_by, update_time) VALUES (1765753855255527426, '000000', 100, '0,100', '外部用户', 4, null, null, null, '0', '0', 103, 1, '2024-03-07 22:58:20', 1, '2024-03-07 22:58:20');

-- ----------------------------
-- 2、用户信息表
-- ----------------------------
drop table if exists sys_user;
create table sys_user (
  user_id           bigint(20)      not null                   comment '用户ID',
  tenant_id         varchar(20)     default '000000'           comment '租户编号',
  dept_id           bigint(20)      default null               comment '部门ID',
  user_name         varchar(30)     not null                   comment '用户账号',
  nick_name         varchar(30)     not null                   comment '用户昵称',
  user_type         varchar(10)     default 'sys_user'         comment '用户类型（sys_user系统用户）',
  email             varchar(50)     default ''                 comment '用户邮箱',
  phonenumber       varchar(11)     default ''                 comment '手机号码',
  sex               char(1)         default '0'                comment '用户性别（0男 1女 2未知）',
  avatar            bigint(20)                                 comment '头像地址',
  password          varchar(100)    default ''                 comment '密码',
  status            char(1)         default '0'                comment '帐号状态（0正常 1停用）',
  del_flag          char(1)         default '0'                comment '删除标志（0代表存在 2代表删除）',
  login_ip          varchar(128)    default ''                 comment '最后登录IP',
  login_date        datetime                                   comment '最后登录时间',
  create_dept       bigint(20)      default null               comment '创建部门',
  create_by         bigint(20)      default null               comment '创建者',
  create_time       datetime                                   comment '创建时间',
  update_by         bigint(20)      default null               comment '更新者',
  update_time       datetime                                   comment '更新时间',
  remark            varchar(500)    default null               comment '备注',
  primary key (user_id)
) engine=innodb comment = '用户信息表';

-- ----------------------------
-- 初始化-用户信息表数据
-- ----------------------------
insert into sys_user values(1, '000000', 103, 'admin', '疯狂的狮子Li', 'sys_user', 'crazyLionLi@163.com', '15888888888', '1', null, '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '0', '0', '127.0.0.1', sysdate(), 103, 1, sysdate(), null, null, '管理员');
insert into sys_user values(2, '000000', 105, 'lionli', '疯狂的狮子Li', 'sys_user', 'crazyLionLi@qq.com',  '15666666666', '1', null, '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '0', '0', '127.0.0.1', sysdate(), 103, 1, sysdate(), null, null, '测试员');


-- ----------------------------
-- 3、岗位信息表
-- ----------------------------
drop table if exists sys_post;
create table sys_post
(
  post_id       bigint(20)      not null                   comment '岗位ID',
  tenant_id     varchar(20)     default '000000'           comment '租户编号',
  post_code     varchar(64)     not null                   comment '岗位编码',
  post_name     varchar(50)     not null                   comment '岗位名称',
  post_sort     int(4)          not null                   comment '显示顺序',
  status        char(1)         not null                   comment '状态（0正常 1停用）',
  create_dept   bigint(20)      default null               comment '创建部门',
  create_by     bigint(20)      default null               comment '创建者',
  create_time   datetime                                   comment '创建时间',
  update_by     bigint(20)      default null               comment '更新者',
  update_time   datetime                                   comment '更新时间',
  remark        varchar(500)    default null               comment '备注',
  primary key (post_id)
) engine=innodb comment = '岗位信息表';

-- ----------------------------
-- 初始化-岗位信息表数据
-- ----------------------------
insert into sys_post values(1, '000000', 'ceo',  '董事长',    1, '0', 103, 1, sysdate(), null, null, '');
insert into sys_post values(2, '000000', 'se',   '项目经理',  2, '0', 103, 1, sysdate(), null, null, '');
insert into sys_post values(3, '000000', 'hr',   '人力资源',  3, '0', 103, 1, sysdate(), null, null, '');
insert into sys_post values(4, '000000', 'user', '普通员工',  4, '0', 103, 1, sysdate(), null, null, '');


-- ----------------------------
-- 4、角色信息表
-- ----------------------------
drop table if exists sys_role;
create table sys_role (
  role_id              bigint(20)      not null                   comment '角色ID',
  tenant_id            varchar(20)     default '000000'           comment '租户编号',
  role_name            varchar(30)     not null                   comment '角色名称',
  role_key             varchar(100)    not null                   comment '角色权限字符串',
  role_sort            int(4)          not null                   comment '显示顺序',
  data_scope           char(1)         default '1'                comment '数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）',
  menu_check_strictly  tinyint(1)      default 1                  comment '菜单树选择项是否关联显示',
  dept_check_strictly  tinyint(1)      default 1                  comment '部门树选择项是否关联显示',
  status               char(1)         not null                   comment '角色状态（0正常 1停用）',
  del_flag             char(1)         default '0'                comment '删除标志（0代表存在 2代表删除）',
  create_dept          bigint(20)      default null               comment '创建部门',
  create_by            bigint(20)      default null               comment '创建者',
  create_time          datetime                                   comment '创建时间',
  update_by            bigint(20)      default null               comment '更新者',
  update_time          datetime                                   comment '更新时间',
  remark               varchar(500)    default null               comment '备注',
  primary key (role_id)
) engine=innodb comment = '角色信息表';

-- ----------------------------
-- 初始化-角色信息表数据
-- ----------------------------
INSERT INTO `ry-cloud`.sys_role (role_id, tenant_id, role_name, role_key, role_sort, data_scope, menu_check_strictly, dept_check_strictly, status, del_flag, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1, '000000', '超级管理员', 'superadmin', 1, '1', 1, 1, '0', '0', 103, 1, '2024-03-06 14:54:11', null, null, '超级管理员');
INSERT INTO `ry-cloud`.sys_role (role_id, tenant_id, role_name, role_key, role_sort, data_scope, menu_check_strictly, dept_check_strictly, status, del_flag, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (2, '000000', '普通角色', 'common', 2, '2', 1, 1, '0', '0', 103, 1, '2024-03-06 14:54:11', null, null, '普通角色');
INSERT INTO `ry-cloud`.sys_role (role_id, tenant_id, role_name, role_key, role_sort, data_scope, menu_check_strictly, dept_check_strictly, status, del_flag, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (3, '000000', '本部门及以下', 'test1', 3, '4', 1, 1, '0', '2', 103, 1, '2024-03-06 14:55:08', 1, null, null);
INSERT INTO `ry-cloud`.sys_role (role_id, tenant_id, role_name, role_key, role_sort, data_scope, menu_check_strictly, dept_check_strictly, status, del_flag, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (4, '000000', '仅本人', 'test2', 4, '5', 1, 1, '0', '2', 103, 1, '2024-03-06 14:55:08', 1, null, null);
INSERT INTO `ry-cloud`.sys_role (role_id, tenant_id, role_name, role_key, role_sort, data_scope, menu_check_strictly, dept_check_strictly, status, del_flag, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1765752906382327810, '000000', '机构端', 'organization', 11, '1', 1, 1, '0', '0', 103, 1, '2024-03-07 22:54:34', 1, '2024-05-04 17:11:23', '');
INSERT INTO `ry-cloud`.sys_role (role_id, tenant_id, role_name, role_key, role_sort, data_scope, menu_check_strictly, dept_check_strictly, status, del_flag, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1776256902792491010, '000000', '普通用户', 'consumer', 10, '1', 1, 1, '0', '0', 103, 1, '2024-04-05 22:33:42', 1, '2024-04-26 20:04:54', '');
INSERT INTO `ry-cloud`.sys_role (role_id, tenant_id, role_name, role_key, role_sort, data_scope, menu_check_strictly, dept_check_strictly, status, del_flag, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1776257072934432769, '000000', '运营端', 'operator', 12, '1', 1, 1, '0', '0', 103, 1, '2024-04-05 22:34:22', 1, '2024-05-10 15:59:40', '');
INSERT INTO `ry-cloud`.sys_role (role_id, tenant_id, role_name, role_key, role_sort, data_scope, menu_check_strictly, dept_check_strictly, status, del_flag, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1776257299984691201, '000000', '开发端', 'developer', 13, '1', 1, 1, '0', '0', 103, 1, '2024-04-05 22:35:16', 1, '2024-05-10 17:08:12', '');


-- ----------------------------
-- 5、菜单权限表
-- ----------------------------
drop table if exists sys_menu;
create table sys_menu (
  menu_id           bigint(20)      not null                   comment '菜单ID',
  menu_name         varchar(50)     not null                   comment '菜单名称',
  parent_id         bigint(20)      default 0                  comment '父菜单ID',
  order_num         int(4)          default 0                  comment '显示顺序',
  path              varchar(200)    default ''                 comment '路由地址',
  component         varchar(255)    default null               comment '组件路径',
  query_param       varchar(255)    default null               comment '路由参数',
  is_frame          int(1)          default 1                  comment '是否为外链（0是 1否）',
  is_cache          int(1)          default 0                  comment '是否缓存（0缓存 1不缓存）',
  menu_type         char(1)         default ''                 comment '菜单类型（M目录 C菜单 F按钮）',
  visible           char(1)         default 0                  comment '显示状态（0显示 1隐藏）',
  status            char(1)         default 0                  comment '菜单状态（0正常 1停用）',
  perms             varchar(100)    default null               comment '权限标识',
  icon              varchar(100)    default '#'                comment '菜单图标',
  create_dept       bigint(20)      default null               comment '创建部门',
  create_by         bigint(20)      default null               comment '创建者',
  create_time       datetime                                   comment '创建时间',
  update_by         bigint(20)      default null               comment '更新者',
  update_time       datetime                                   comment '更新时间',
  remark            varchar(500)    default ''                 comment '备注',
  primary key (menu_id)
) engine=innodb comment = '菜单权限表';

-- ----------------------------
-- 初始化-菜单信息表数据
-- ----------------------------
INSERT INTO `ry-cloud`.sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query_param, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1, '系统管理', 0, 1, 'system', null, '', 1, 0, 'M', '0', '0', '', 'system', 103, 1, '2024-03-06 14:54:12', null, null, '系统管理目录');
INSERT INTO `ry-cloud`.sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query_param, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (2, '系统监控', 0, 3, 'monitor', null, '', 1, 0, 'M', '0', '0', '', 'monitor', 103, 1, '2024-03-06 14:54:12', null, null, '系统监控目录');
INSERT INTO `ry-cloud`.sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query_param, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (3, '系统工具', 0, 4, 'tool', null, '', 1, 0, 'M', '0', '0', '', 'tool', 103, 1, '2024-03-06 14:54:12', null, null, '系统工具目录');
INSERT INTO `ry-cloud`.sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query_param, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (4, 'PLUS官网', 0, 5, 'https://gitee.com/dromara/RuoYi-Cloud-Plus', null, '', 0, 0, 'M', '0', '1', '', 'guide', 103, 1, '2024-03-06 14:54:12', 1, '2024-03-07 22:25:29', 'RuoYi-Cloud-Plus官网地址');
INSERT INTO `ry-cloud`.sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query_param, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (5, '测试菜单', 0, 5, 'demo', null, null, 1, 0, 'M', '0', '1', null, 'star', 103, 1, '2024-03-06 14:55:08', 1, '2024-03-07 22:25:20', '');
INSERT INTO `ry-cloud`.sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query_param, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (6, '租户管理', 0, 2, 'tenant', null, '', 1, 0, 'M', '0', '1', '', 'chart', 103, 1, '2024-03-06 14:54:12', 1, '2024-03-07 22:25:42', '租户管理目录');
INSERT INTO `ry-cloud`.sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query_param, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (100, '用户管理', 1, 1, 'user', 'system/user/index', '', 1, 0, 'C', '0', '0', 'system:user:list', 'user', 103, 1, '2024-03-06 14:54:12', null, null, '用户管理菜单');
INSERT INTO `ry-cloud`.sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query_param, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (101, '角色管理', 1, 2, 'role', 'system/role/index', '', 1, 0, 'C', '0', '0', 'system:role:list', 'peoples', 103, 1, '2024-03-06 14:54:12', null, null, '角色管理菜单');
INSERT INTO `ry-cloud`.sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query_param, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (102, '菜单管理', 1, 3, 'menu', 'system/menu/index', '', 1, 0, 'C', '0', '0', 'system:menu:list', 'tree-table', 103, 1, '2024-03-06 14:54:12', null, null, '菜单管理菜单');
INSERT INTO `ry-cloud`.sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query_param, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (103, '部门管理', 1, 4, 'dept', 'system/dept/index', '', 1, 0, 'C', '0', '0', 'system:dept:list', 'tree', 103, 1, '2024-03-06 14:54:12', null, null, '部门管理菜单');
INSERT INTO `ry-cloud`.sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query_param, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (104, '岗位管理', 1, 5, 'post', 'system/post/index', '', 1, 0, 'C', '0', '1', 'system:post:list', 'post', 103, 1, '2024-03-06 14:54:12', 1, '2024-04-05 22:36:38', '岗位管理菜单');
INSERT INTO `ry-cloud`.sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query_param, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (105, '字典管理', 1, 6, 'dict', 'system/dict/index', '', 1, 0, 'C', '0', '0', 'system:dict:list', 'dict', 103, 1, '2024-03-06 14:54:12', null, null, '字典管理菜单');
INSERT INTO `ry-cloud`.sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query_param, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (106, '参数设置', 1, 7, 'config', 'system/config/index', '', 1, 0, 'C', '0', '0', 'system:config:list', 'edit', 103, 1, '2024-03-06 14:54:12', null, null, '参数设置菜单');
INSERT INTO `ry-cloud`.sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query_param, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (107, '通知公告', 1, 8, 'notice', 'system/notice/index', '', 1, 0, 'C', '0', '0', 'system:notice:list', 'message', 103, 1, '2024-03-06 14:54:12', null, null, '通知公告菜单');
INSERT INTO `ry-cloud`.sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query_param, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (108, '日志管理', 1, 9, 'log', '', '', 1, 0, 'M', '0', '0', '', 'log', 103, 1, '2024-03-06 14:54:12', null, null, '日志管理菜单');
INSERT INTO `ry-cloud`.sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query_param, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (109, '在线用户', 2, 1, 'online', 'monitor/online/index', '', 1, 0, 'C', '0', '0', 'monitor:online:list', 'online', 103, 1, '2024-03-06 14:54:12', null, null, '在线用户菜单');
INSERT INTO `ry-cloud`.sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query_param, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (110, 'PowerJob控制台', 2, 2, 'http://localhost:7700', '', '', 0, 0, 'C', '0', '0', 'monitor:job:list', 'job', 103, 1, '2024-03-06 14:54:12', null, null, '定时任务菜单');
INSERT INTO `ry-cloud`.sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query_param, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (111, 'Sentinel控制台', 2, 3, 'http://localhost:8718', '', '', 0, 0, 'C', '0', '0', 'monitor:sentinel:list', 'sentinel', 103, 1, '2024-03-06 14:54:12', null, null, '流量控制菜单');
INSERT INTO `ry-cloud`.sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query_param, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (112, 'Nacos控制台', 2, 4, 'http://localhost:8848/nacos', '', '', 0, 0, 'C', '0', '0', 'monitor:nacos:list', 'nacos', 103, 1, '2024-03-06 14:54:12', null, null, '服务治理菜单');
INSERT INTO `ry-cloud`.sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query_param, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (113, 'Admin控制台', 2, 5, 'http://localhost:9100/login', '', '', 0, 0, 'C', '0', '0', 'monitor:server:list', 'server', 103, 1, '2024-03-06 14:54:12', null, null, '服务监控菜单');
INSERT INTO `ry-cloud`.sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query_param, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (114, '表单构建', 3, 1, 'build', 'tool/build/index', '', 1, 0, 'C', '0', '0', 'tool:build:list', 'build', 103, 1, '2024-03-06 14:54:12', null, null, '表单构建菜单');
INSERT INTO `ry-cloud`.sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query_param, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (115, '代码生成', 3, 2, 'gen', 'tool/gen/index', '', 1, 0, 'C', '0', '0', 'tool:gen:list', 'code', 103, 1, '2024-03-06 14:54:12', null, null, '代码生成菜单');
INSERT INTO `ry-cloud`.sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query_param, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (118, '文件管理', 1, 10, 'oss', 'system/oss/index', '', 1, 0, 'C', '0', '0', 'system:oss:list', 'upload', 103, 1, '2024-03-06 14:54:12', null, null, '文件管理菜单');
INSERT INTO `ry-cloud`.sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query_param, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (121, '租户管理', 6, 1, 'tenant', 'system/tenant/index', '', 1, 0, 'C', '0', '0', 'system:tenant:list', 'list', 103, 1, '2024-03-06 14:54:12', null, null, '租户管理菜单');
INSERT INTO `ry-cloud`.sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query_param, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (122, '租户套餐管理', 6, 2, 'tenantPackage', 'system/tenantPackage/index', '', 1, 0, 'C', '0', '0', 'system:tenantPackage:list', 'form', 103, 1, '2024-03-06 14:54:12', null, null, '租户套餐管理菜单');
INSERT INTO `ry-cloud`.sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query_param, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (123, '客户端管理', 1, 11, 'client', 'system/client/index', '', 1, 0, 'C', '0', '0', 'system:client:list', 'international', 103, 1, '2024-03-06 14:54:12', null, null, '客户端管理菜单');
INSERT INTO `ry-cloud`.sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query_param, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (124, '缓存监控', 2, 1, 'cache', 'monitor/cache/index', '', 1, 0, 'C', '0', '0', 'monitor:cache:list', 'redis', 103, 1, '2024-03-06 14:54:12', null, null, '缓存监控');
INSERT INTO `ry-cloud`.sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query_param, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (500, '操作日志', 108, 1, 'operlog', 'monitor/operlog/index', '', 1, 0, 'C', '0', '0', 'monitor:operlog:list', 'form', 103, 1, '2024-03-06 14:54:12', null, null, '操作日志菜单');
INSERT INTO `ry-cloud`.sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query_param, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (501, '登录日志', 108, 2, 'logininfor', 'monitor/logininfor/index', '', 1, 0, 'C', '0', '0', 'monitor:logininfor:list', 'logininfor', 103, 1, '2024-03-06 14:54:12', null, null, '登录日志菜单');
INSERT INTO `ry-cloud`.sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query_param, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1001, '用户查询', 100, 1, '', '', '', 1, 0, 'F', '0', '0', 'system:user:query', '#', 103, 1, '2024-03-06 14:54:12', null, null, '');
INSERT INTO `ry-cloud`.sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query_param, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1002, '用户新增', 100, 2, '', '', '', 1, 0, 'F', '0', '0', 'system:user:add', '#', 103, 1, '2024-03-06 14:54:12', null, null, '');
INSERT INTO `ry-cloud`.sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query_param, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1003, '用户修改', 100, 3, '', '', '', 1, 0, 'F', '0', '0', 'system:user:edit', '#', 103, 1, '2024-03-06 14:54:12', null, null, '');
INSERT INTO `ry-cloud`.sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query_param, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1004, '用户删除', 100, 4, '', '', '', 1, 0, 'F', '0', '0', 'system:user:remove', '#', 103, 1, '2024-03-06 14:54:12', null, null, '');
INSERT INTO `ry-cloud`.sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query_param, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1005, '用户导出', 100, 5, '', '', '', 1, 0, 'F', '0', '0', 'system:user:export', '#', 103, 1, '2024-03-06 14:54:12', null, null, '');
INSERT INTO `ry-cloud`.sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query_param, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1006, '用户导入', 100, 6, '', '', '', 1, 0, 'F', '0', '0', 'system:user:import', '#', 103, 1, '2024-03-06 14:54:12', null, null, '');
INSERT INTO `ry-cloud`.sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query_param, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1007, '重置密码', 100, 7, '', '', '', 1, 0, 'F', '0', '0', 'system:user:resetPwd', '#', 103, 1, '2024-03-06 14:54:12', null, null, '');
INSERT INTO `ry-cloud`.sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query_param, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1008, '角色查询', 101, 1, '', '', '', 1, 0, 'F', '0', '0', 'system:role:query', '#', 103, 1, '2024-03-06 14:54:12', null, null, '');
INSERT INTO `ry-cloud`.sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query_param, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1009, '角色新增', 101, 2, '', '', '', 1, 0, 'F', '0', '0', 'system:role:add', '#', 103, 1, '2024-03-06 14:54:12', null, null, '');
INSERT INTO `ry-cloud`.sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query_param, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1010, '角色修改', 101, 3, '', '', '', 1, 0, 'F', '0', '0', 'system:role:edit', '#', 103, 1, '2024-03-06 14:54:12', null, null, '');
INSERT INTO `ry-cloud`.sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query_param, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1011, '角色删除', 101, 4, '', '', '', 1, 0, 'F', '0', '0', 'system:role:remove', '#', 103, 1, '2024-03-06 14:54:12', null, null, '');
INSERT INTO `ry-cloud`.sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query_param, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1012, '角色导出', 101, 5, '', '', '', 1, 0, 'F', '0', '0', 'system:role:export', '#', 103, 1, '2024-03-06 14:54:12', null, null, '');
INSERT INTO `ry-cloud`.sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query_param, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1013, '菜单查询', 102, 1, '', '', '', 1, 0, 'F', '0', '0', 'system:menu:query', '#', 103, 1, '2024-03-06 14:54:12', null, null, '');
INSERT INTO `ry-cloud`.sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query_param, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1014, '菜单新增', 102, 2, '', '', '', 1, 0, 'F', '0', '0', 'system:menu:add', '#', 103, 1, '2024-03-06 14:54:12', null, null, '');
INSERT INTO `ry-cloud`.sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query_param, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1015, '菜单修改', 102, 3, '', '', '', 1, 0, 'F', '0', '0', 'system:menu:edit', '#', 103, 1, '2024-03-06 14:54:12', null, null, '');
INSERT INTO `ry-cloud`.sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query_param, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1016, '菜单删除', 102, 4, '', '', '', 1, 0, 'F', '0', '0', 'system:menu:remove', '#', 103, 1, '2024-03-06 14:54:12', null, null, '');
INSERT INTO `ry-cloud`.sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query_param, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1017, '部门查询', 103, 1, '', '', '', 1, 0, 'F', '0', '0', 'system:dept:query', '#', 103, 1, '2024-03-06 14:54:12', null, null, '');
INSERT INTO `ry-cloud`.sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query_param, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1018, '部门新增', 103, 2, '', '', '', 1, 0, 'F', '0', '0', 'system:dept:add', '#', 103, 1, '2024-03-06 14:54:12', null, null, '');
INSERT INTO `ry-cloud`.sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query_param, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1019, '部门修改', 103, 3, '', '', '', 1, 0, 'F', '0', '0', 'system:dept:edit', '#', 103, 1, '2024-03-06 14:54:12', null, null, '');
INSERT INTO `ry-cloud`.sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query_param, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1020, '部门删除', 103, 4, '', '', '', 1, 0, 'F', '0', '0', 'system:dept:remove', '#', 103, 1, '2024-03-06 14:54:12', null, null, '');
INSERT INTO `ry-cloud`.sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query_param, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1021, '岗位查询', 104, 1, '', '', '', 1, 0, 'F', '0', '0', 'system:post:query', '#', 103, 1, '2024-03-06 14:54:12', null, null, '');
INSERT INTO `ry-cloud`.sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query_param, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1022, '岗位新增', 104, 2, '', '', '', 1, 0, 'F', '0', '0', 'system:post:add', '#', 103, 1, '2024-03-06 14:54:12', null, null, '');
INSERT INTO `ry-cloud`.sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query_param, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1023, '岗位修改', 104, 3, '', '', '', 1, 0, 'F', '0', '0', 'system:post:edit', '#', 103, 1, '2024-03-06 14:54:12', null, null, '');
INSERT INTO `ry-cloud`.sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query_param, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1024, '岗位删除', 104, 4, '', '', '', 1, 0, 'F', '0', '0', 'system:post:remove', '#', 103, 1, '2024-03-06 14:54:12', null, null, '');
INSERT INTO `ry-cloud`.sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query_param, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1025, '岗位导出', 104, 5, '', '', '', 1, 0, 'F', '0', '0', 'system:post:export', '#', 103, 1, '2024-03-06 14:54:12', null, null, '');
INSERT INTO `ry-cloud`.sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query_param, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1026, '字典查询', 105, 1, '#', '', '', 1, 0, 'F', '0', '0', 'system:dict:query', '#', 103, 1, '2024-03-06 14:54:12', null, null, '');
INSERT INTO `ry-cloud`.sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query_param, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1027, '字典新增', 105, 2, '#', '', '', 1, 0, 'F', '0', '0', 'system:dict:add', '#', 103, 1, '2024-03-06 14:54:12', null, null, '');
INSERT INTO `ry-cloud`.sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query_param, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1028, '字典修改', 105, 3, '#', '', '', 1, 0, 'F', '0', '0', 'system:dict:edit', '#', 103, 1, '2024-03-06 14:54:12', null, null, '');
INSERT INTO `ry-cloud`.sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query_param, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1029, '字典删除', 105, 4, '#', '', '', 1, 0, 'F', '0', '0', 'system:dict:remove', '#', 103, 1, '2024-03-06 14:54:12', null, null, '');
INSERT INTO `ry-cloud`.sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query_param, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1030, '字典导出', 105, 5, '#', '', '', 1, 0, 'F', '0', '0', 'system:dict:export', '#', 103, 1, '2024-03-06 14:54:12', null, null, '');
INSERT INTO `ry-cloud`.sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query_param, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1031, '参数查询', 106, 1, '#', '', '', 1, 0, 'F', '0', '0', 'system:config:query', '#', 103, 1, '2024-03-06 14:54:12', null, null, '');
INSERT INTO `ry-cloud`.sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query_param, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1032, '参数新增', 106, 2, '#', '', '', 1, 0, 'F', '0', '0', 'system:config:add', '#', 103, 1, '2024-03-06 14:54:12', null, null, '');
INSERT INTO `ry-cloud`.sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query_param, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1033, '参数修改', 106, 3, '#', '', '', 1, 0, 'F', '0', '0', 'system:config:edit', '#', 103, 1, '2024-03-06 14:54:12', null, null, '');
INSERT INTO `ry-cloud`.sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query_param, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1034, '参数删除', 106, 4, '#', '', '', 1, 0, 'F', '0', '0', 'system:config:remove', '#', 103, 1, '2024-03-06 14:54:12', null, null, '');
INSERT INTO `ry-cloud`.sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query_param, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1035, '参数导出', 106, 5, '#', '', '', 1, 0, 'F', '0', '0', 'system:config:export', '#', 103, 1, '2024-03-06 14:54:12', null, null, '');
INSERT INTO `ry-cloud`.sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query_param, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1036, '公告查询', 107, 1, '#', '', '', 1, 0, 'F', '0', '0', 'system:notice:query', '#', 103, 1, '2024-03-06 14:54:12', null, null, '');
INSERT INTO `ry-cloud`.sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query_param, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1037, '公告新增', 107, 2, '#', '', '', 1, 0, 'F', '0', '0', 'system:notice:add', '#', 103, 1, '2024-03-06 14:54:12', null, null, '');
INSERT INTO `ry-cloud`.sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query_param, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1038, '公告修改', 107, 3, '#', '', '', 1, 0, 'F', '0', '0', 'system:notice:edit', '#', 103, 1, '2024-03-06 14:54:12', null, null, '');
INSERT INTO `ry-cloud`.sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query_param, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1039, '公告删除', 107, 4, '#', '', '', 1, 0, 'F', '0', '0', 'system:notice:remove', '#', 103, 1, '2024-03-06 14:54:12', null, null, '');
INSERT INTO `ry-cloud`.sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query_param, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1040, '操作查询', 500, 1, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:operlog:query', '#', 103, 1, '2024-03-06 14:54:12', null, null, '');
INSERT INTO `ry-cloud`.sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query_param, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1041, '操作删除', 500, 2, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:operlog:remove', '#', 103, 1, '2024-03-06 14:54:12', null, null, '');
INSERT INTO `ry-cloud`.sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query_param, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1042, '日志导出', 500, 4, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:operlog:export', '#', 103, 1, '2024-03-06 14:54:12', null, null, '');
INSERT INTO `ry-cloud`.sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query_param, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1043, '登录查询', 501, 1, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:logininfor:query', '#', 103, 1, '2024-03-06 14:54:12', null, null, '');
INSERT INTO `ry-cloud`.sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query_param, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1044, '登录删除', 501, 2, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:logininfor:remove', '#', 103, 1, '2024-03-06 14:54:12', null, null, '');
INSERT INTO `ry-cloud`.sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query_param, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1045, '日志导出', 501, 3, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:logininfor:export', '#', 103, 1, '2024-03-06 14:54:12', null, null, '');
INSERT INTO `ry-cloud`.sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query_param, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1046, '在线查询', 109, 1, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:online:query', '#', 103, 1, '2024-03-06 14:54:12', null, null, '');
INSERT INTO `ry-cloud`.sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query_param, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1047, '批量强退', 109, 2, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:online:batchLogout', '#', 103, 1, '2024-03-06 14:54:12', null, null, '');
INSERT INTO `ry-cloud`.sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query_param, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1048, '单条强退', 109, 3, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:online:forceLogout', '#', 103, 1, '2024-03-06 14:54:12', null, null, '');
INSERT INTO `ry-cloud`.sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query_param, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1050, '账户解锁', 501, 4, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:logininfor:unlock', '#', 103, 1, '2024-03-06 14:54:12', null, null, '');
INSERT INTO `ry-cloud`.sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query_param, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1055, '生成查询', 115, 1, '#', '', '', 1, 0, 'F', '0', '0', 'tool:gen:query', '#', 103, 1, '2024-03-06 14:54:12', null, null, '');
INSERT INTO `ry-cloud`.sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query_param, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1056, '生成修改', 115, 2, '#', '', '', 1, 0, 'F', '0', '0', 'tool:gen:edit', '#', 103, 1, '2024-03-06 14:54:12', null, null, '');
INSERT INTO `ry-cloud`.sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query_param, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1057, '生成删除', 115, 3, '#', '', '', 1, 0, 'F', '0', '0', 'tool:gen:remove', '#', 103, 1, '2024-03-06 14:54:12', null, null, '');
INSERT INTO `ry-cloud`.sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query_param, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1058, '导入代码', 115, 2, '#', '', '', 1, 0, 'F', '0', '0', 'tool:gen:import', '#', 103, 1, '2024-03-06 14:54:12', null, null, '');
INSERT INTO `ry-cloud`.sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query_param, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1059, '预览代码', 115, 4, '#', '', '', 1, 0, 'F', '0', '0', 'tool:gen:preview', '#', 103, 1, '2024-03-06 14:54:12', null, null, '');
INSERT INTO `ry-cloud`.sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query_param, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1060, '生成代码', 115, 5, '#', '', '', 1, 0, 'F', '0', '0', 'tool:gen:code', '#', 103, 1, '2024-03-06 14:54:12', null, null, '');
INSERT INTO `ry-cloud`.sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query_param, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1061, '客户端管理查询', 123, 1, '#', '', '', 1, 0, 'F', '0', '0', 'system:client:query', '#', 103, 1, '2024-03-06 14:54:12', null, null, '');
INSERT INTO `ry-cloud`.sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query_param, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1062, '客户端管理新增', 123, 2, '#', '', '', 1, 0, 'F', '0', '0', 'system:client:add', '#', 103, 1, '2024-03-06 14:54:12', null, null, '');
INSERT INTO `ry-cloud`.sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query_param, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1063, '客户端管理修改', 123, 3, '#', '', '', 1, 0, 'F', '0', '0', 'system:client:edit', '#', 103, 1, '2024-03-06 14:54:12', null, null, '');
INSERT INTO `ry-cloud`.sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query_param, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1064, '客户端管理删除', 123, 4, '#', '', '', 1, 0, 'F', '0', '0', 'system:client:remove', '#', 103, 1, '2024-03-06 14:54:12', null, null, '');
INSERT INTO `ry-cloud`.sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query_param, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1065, '客户端管理导出', 123, 5, '#', '', '', 1, 0, 'F', '0', '0', 'system:client:export', '#', 103, 1, '2024-03-06 14:54:12', null, null, '');
INSERT INTO `ry-cloud`.sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query_param, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1500, '测试单表', 5, 1, 'demo', 'demo/demo/index', null, 1, 0, 'C', '0', '0', 'demo:demo:list', '#', 103, 1, '2024-03-06 14:55:08', null, null, '测试单表菜单');
INSERT INTO `ry-cloud`.sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query_param, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1501, '测试单表查询', 1500, 1, '#', '', null, 1, 0, 'F', '0', '0', 'demo:demo:query', '#', 103, 1, '2024-03-06 14:55:08', null, null, '');
INSERT INTO `ry-cloud`.sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query_param, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1502, '测试单表新增', 1500, 2, '#', '', null, 1, 0, 'F', '0', '0', 'demo:demo:add', '#', 103, 1, '2024-03-06 14:55:08', null, null, '');
INSERT INTO `ry-cloud`.sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query_param, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1503, '测试单表修改', 1500, 3, '#', '', null, 1, 0, 'F', '0', '0', 'demo:demo:edit', '#', 103, 1, '2024-03-06 14:55:08', null, null, '');
INSERT INTO `ry-cloud`.sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query_param, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1504, '测试单表删除', 1500, 4, '#', '', null, 1, 0, 'F', '0', '0', 'demo:demo:remove', '#', 103, 1, '2024-03-06 14:55:08', null, null, '');
INSERT INTO `ry-cloud`.sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query_param, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1505, '测试单表导出', 1500, 5, '#', '', null, 1, 0, 'F', '0', '0', 'demo:demo:export', '#', 103, 1, '2024-03-06 14:55:08', null, null, '');
INSERT INTO `ry-cloud`.sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query_param, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1506, '测试树表', 5, 1, 'tree', 'demo/tree/index', null, 1, 0, 'C', '0', '0', 'demo:tree:list', '#', 103, 1, '2024-03-06 14:55:08', null, null, '测试树表菜单');
INSERT INTO `ry-cloud`.sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query_param, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1507, '测试树表查询', 1506, 1, '#', '', null, 1, 0, 'F', '0', '0', 'demo:tree:query', '#', 103, 1, '2024-03-06 14:55:08', null, null, '');
INSERT INTO `ry-cloud`.sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query_param, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1508, '测试树表新增', 1506, 2, '#', '', null, 1, 0, 'F', '0', '0', 'demo:tree:add', '#', 103, 1, '2024-03-06 14:55:08', null, null, '');
INSERT INTO `ry-cloud`.sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query_param, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1509, '测试树表修改', 1506, 3, '#', '', null, 1, 0, 'F', '0', '0', 'demo:tree:edit', '#', 103, 1, '2024-03-06 14:55:08', null, null, '');
INSERT INTO `ry-cloud`.sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query_param, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1510, '测试树表删除', 1506, 4, '#', '', null, 1, 0, 'F', '0', '0', 'demo:tree:remove', '#', 103, 1, '2024-03-06 14:55:08', null, null, '');
INSERT INTO `ry-cloud`.sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query_param, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1511, '测试树表导出', 1506, 5, '#', '', null, 1, 0, 'F', '0', '0', 'demo:tree:export', '#', 103, 1, '2024-03-06 14:55:08', null, null, '');
INSERT INTO `ry-cloud`.sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query_param, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1600, '文件查询', 118, 1, '#', '', '', 1, 0, 'F', '0', '0', 'system:oss:query', '#', 103, 1, '2024-03-06 14:54:12', null, null, '');
INSERT INTO `ry-cloud`.sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query_param, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1601, '文件上传', 118, 2, '#', '', '', 1, 0, 'F', '0', '0', 'system:oss:upload', '#', 103, 1, '2024-03-06 14:54:12', null, null, '');
INSERT INTO `ry-cloud`.sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query_param, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1602, '文件下载', 118, 3, '#', '', '', 1, 0, 'F', '0', '0', 'system:oss:download', '#', 103, 1, '2024-03-06 14:54:12', null, null, '');
INSERT INTO `ry-cloud`.sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query_param, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1603, '文件删除', 118, 4, '#', '', '', 1, 0, 'F', '0', '0', 'system:oss:remove', '#', 103, 1, '2024-03-06 14:54:12', null, null, '');
INSERT INTO `ry-cloud`.sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query_param, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1606, '租户查询', 121, 1, '#', '', '', 1, 0, 'F', '0', '0', 'system:tenant:query', '#', 103, 1, '2024-03-06 14:54:12', null, null, '');
INSERT INTO `ry-cloud`.sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query_param, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1607, '租户新增', 121, 2, '#', '', '', 1, 0, 'F', '0', '0', 'system:tenant:add', '#', 103, 1, '2024-03-06 14:54:12', null, null, '');
INSERT INTO `ry-cloud`.sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query_param, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1608, '租户修改', 121, 3, '#', '', '', 1, 0, 'F', '0', '0', 'system:tenant:edit', '#', 103, 1, '2024-03-06 14:54:12', null, null, '');
INSERT INTO `ry-cloud`.sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query_param, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1609, '租户删除', 121, 4, '#', '', '', 1, 0, 'F', '0', '0', 'system:tenant:remove', '#', 103, 1, '2024-03-06 14:54:12', null, null, '');
INSERT INTO `ry-cloud`.sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query_param, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1610, '租户导出', 121, 5, '#', '', '', 1, 0, 'F', '0', '0', 'system:tenant:export', '#', 103, 1, '2024-03-06 14:54:12', null, null, '');
INSERT INTO `ry-cloud`.sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query_param, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1611, '租户套餐查询', 122, 1, '#', '', '', 1, 0, 'F', '0', '0', 'system:tenantPackage:query', '#', 103, 1, '2024-03-06 14:54:12', null, null, '');
INSERT INTO `ry-cloud`.sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query_param, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1612, '租户套餐新增', 122, 2, '#', '', '', 1, 0, 'F', '0', '0', 'system:tenantPackage:add', '#', 103, 1, '2024-03-06 14:54:12', null, null, '');
INSERT INTO `ry-cloud`.sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query_param, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1613, '租户套餐修改', 122, 3, '#', '', '', 1, 0, 'F', '0', '0', 'system:tenantPackage:edit', '#', 103, 1, '2024-03-06 14:54:12', null, null, '');
INSERT INTO `ry-cloud`.sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query_param, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1614, '租户套餐删除', 122, 4, '#', '', '', 1, 0, 'F', '0', '0', 'system:tenantPackage:remove', '#', 103, 1, '2024-03-06 14:54:12', null, null, '');
INSERT INTO `ry-cloud`.sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query_param, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1615, '租户套餐导出', 122, 5, '#', '', '', 1, 0, 'F', '0', '0', 'system:tenantPackage:export', '#', 103, 1, '2024-03-06 14:54:12', null, null, '');
INSERT INTO `ry-cloud`.sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query_param, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1620, '配置列表', 118, 5, '#', '', '', 1, 0, 'F', '0', '0', 'system:ossConfig:list', '#', 103, 1, '2024-03-06 14:54:12', null, null, '');
INSERT INTO `ry-cloud`.sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query_param, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1621, '配置添加', 118, 6, '#', '', '', 1, 0, 'F', '0', '0', 'system:ossConfig:add', '#', 103, 1, '2024-03-06 14:54:12', null, null, '');
INSERT INTO `ry-cloud`.sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query_param, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1622, '配置编辑', 118, 6, '#', '', '', 1, 0, 'F', '0', '0', 'system:ossConfig:edit', '#', 103, 1, '2024-03-06 14:54:12', null, null, '');
INSERT INTO `ry-cloud`.sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query_param, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1623, '配置删除', 118, 6, '#', '', '', 1, 0, 'F', '0', '0', 'system:ossConfig:remove', '#', 103, 1, '2024-03-06 14:54:12', null, null, '');
INSERT INTO `ry-cloud`.sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query_param, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1765748102306217985, '课程管理', 0, 101, 'courseManagement', 'organization/courseManagement/index', null, 1, 0, 'C', '0', '0', null, 'button', 103, 1, '2024-03-07 22:35:28', 1, '2024-03-07 22:35:28', '');
INSERT INTO `ry-cloud`.sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query_param, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1765750471618215938, '媒资管理', 0, 102, 'mediaManagement', 'organization/mediaManagement/index', null, 1, 0, 'C', '0', '0', null, 'button', 103, 1, '2024-03-07 22:44:53', 1, '2024-03-07 22:45:14', '');
INSERT INTO `ry-cloud`.sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query_param, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1765751660271386625, '评论管理', 0, 103, 'discussMgt', 'organization/discussMgt/index', null, 1, 0, 'C', '0', '0', null, 'button', 103, 1, '2024-03-07 22:49:37', 1, '2024-05-04 20:07:14', '');
INSERT INTO `ry-cloud`.sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query_param, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1765752069346050049, '订单管理', 0, 104, 'orderManagement', 'organization/orderManagement/index', null, 1, 0, 'C', '0', '0', null, 'button', 103, 1, '2024-03-07 22:51:14', 1, '2024-03-07 22:51:14', '');
INSERT INTO `ry-cloud`.sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query_param, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1765752588034654210, '数据分析', 0, 105, 'dataAnalysis', 'organization/dataAnalysis/index', null, 1, 0, 'C', '0', '0', null, 'button', 103, 1, '2024-03-07 22:53:18', 1, '2024-03-07 22:53:18', '');
INSERT INTO `ry-cloud`.sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query_param, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1769247292298739713, '教师管理', 0, 106, 'teacherManagement', 'organization/teacherManagement/index', null, 1, 0, 'C', '0', '0', null, 'button', 103, 1, '2024-03-17 14:20:00', 1, '2024-03-17 14:20:20', '');
INSERT INTO `ry-cloud`.sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query_param, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1773553672308404225, '分类管理', 0, 201, 'categoryMgt', 'operator/categoryMgt/index', null, 1, 0, 'C', '0', '0', '', 'chart', 103, 1, '2024-03-29 11:32:01', 1, '2024-03-29 15:54:43', '');
INSERT INTO `ry-cloud`.sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query_param, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1774786262642323457, '字典生成枚举类', 105, 6, '', null, null, 1, 0, 'F', '0', '0', 'system:dict:enum', '', 103, 1, '2024-04-01 21:09:54', 1, '2024-04-01 21:09:54', '');
INSERT INTO `ry-cloud`.sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query_param, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1776255093659152385, '课程审核', 0, 202, 'courseAudit', 'operator/courseAudit/index', null, 1, 0, 'C', '0', '0', null, 'chart', 103, 1, '2024-04-05 22:26:30', 1, '2024-04-05 22:26:43', '');
INSERT INTO `ry-cloud`.sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query_param, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1776255233673408514, '媒资审核', 0, 203, 'mediaAudit', 'operator/mediaAudit/index', null, 1, 0, 'C', '0', '0', null, 'chart', 103, 1, '2024-04-05 22:27:04', 1, '2024-04-05 22:27:04', '');
INSERT INTO `ry-cloud`.sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query_param, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1778410600671047682, '热门管理', 0, 204, 'hotCourseMgt', 'operator/hotCourseMgt/index', null, 1, 0, 'C', '0', '0', null, 'chart', 103, 1, '2024-04-11 21:11:43', 1, '2024-04-11 21:12:10', '');
INSERT INTO `ry-cloud`.sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query_param, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1786683849485848578, '课程评论管理', 0, 107, 'singleCourseDiscussMgt', 'organization/discussMgt/singleCourseDiscussMgt', null, 1, 0, 'C', '1', '0', null, 'button', 103, 1, '2024-05-04 17:06:39', 1, '2024-05-04 20:07:22', '');
INSERT INTO `ry-cloud`.sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query_param, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1786729771934679042, '评论审核', 0, 205, 'discussAudit', 'operator/discussAudit/index', null, 1, 0, 'C', '0', '0', null, 'chart', 103, 1, '2024-05-04 20:09:08', 1, '2024-05-04 20:12:54', '');


-- ----------------------------
-- 6、用户和角色关联表  用户N-1角色
-- ----------------------------
drop table if exists sys_user_role;
create table sys_user_role (
  user_id   bigint(20) not null comment '用户ID',
  role_id   bigint(20) not null comment '角色ID',
  primary key(user_id, role_id)
) engine=innodb comment = '用户和角色关联表';

-- ----------------------------
-- 初始化-用户和角色关联表数据
-- ----------------------------
insert into sys_user_role values ('1', '1');
insert into sys_user_role values ('2', '2');


-- ----------------------------
-- 7、角色和菜单关联表  角色1-N菜单
-- ----------------------------
drop table if exists sys_role_menu;
create table sys_role_menu (
  role_id   bigint(20) not null comment '角色ID',
  menu_id   bigint(20) not null comment '菜单ID',
  primary key(role_id, menu_id)
) engine=innodb comment = '角色和菜单关联表';

-- ----------------------------
-- 初始化-角色和菜单关联表数据
-- ----------------------------
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (2, 1);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (2, 2);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (2, 3);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (2, 4);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (2, 100);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (2, 101);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (2, 102);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (2, 103);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (2, 104);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (2, 105);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (2, 106);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (2, 107);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (2, 108);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (2, 109);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (2, 110);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (2, 111);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (2, 112);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (2, 113);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (2, 114);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (2, 115);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (2, 116);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (2, 500);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (2, 501);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (2, 1000);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (2, 1001);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (2, 1002);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (2, 1003);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (2, 1004);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (2, 1005);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (2, 1006);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (2, 1007);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (2, 1008);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (2, 1009);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (2, 1010);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (2, 1011);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (2, 1012);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (2, 1013);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (2, 1014);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (2, 1015);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (2, 1016);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (2, 1017);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (2, 1018);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (2, 1019);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (2, 1020);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (2, 1021);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (2, 1022);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (2, 1023);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (2, 1024);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (2, 1025);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (2, 1026);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (2, 1027);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (2, 1028);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (2, 1029);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (2, 1030);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (2, 1031);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (2, 1032);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (2, 1033);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (2, 1034);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (2, 1035);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (2, 1036);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (2, 1037);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (2, 1038);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (2, 1039);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (2, 1040);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (2, 1041);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (2, 1042);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (2, 1043);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (2, 1044);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (2, 1045);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (2, 1046);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (2, 1047);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (2, 1048);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (2, 1050);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (2, 1055);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (2, 1056);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (2, 1057);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (2, 1058);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (2, 1059);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (2, 1060);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (2, 1061);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (2, 1062);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (2, 1063);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (2, 1064);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (2, 1065);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (1765752906382327810, 1765748102306217985);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (1765752906382327810, 1765750471618215938);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (1765752906382327810, 1765751660271386625);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (1765752906382327810, 1765752069346050049);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (1765752906382327810, 1765752588034654210);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (1765752906382327810, 1769247292298739713);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (1765752906382327810, 1786683849485848578);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (1776257072934432769, 1);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (1776257072934432769, 100);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (1776257072934432769, 107);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (1776257072934432769, 1001);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (1776257072934432769, 1002);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (1776257072934432769, 1003);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (1776257072934432769, 1004);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (1776257072934432769, 1005);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (1776257072934432769, 1006);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (1776257072934432769, 1007);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (1776257072934432769, 1036);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (1776257072934432769, 1037);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (1776257072934432769, 1038);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (1776257072934432769, 1039);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (1776257072934432769, 1773553672308404225);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (1776257072934432769, 1776255093659152385);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (1776257072934432769, 1776255233673408514);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (1776257072934432769, 1778410600671047682);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (1776257072934432769, 1786729771934679042);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (1776257299984691201, 1);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (1776257299984691201, 2);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (1776257299984691201, 3);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (1776257299984691201, 100);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (1776257299984691201, 101);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (1776257299984691201, 102);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (1776257299984691201, 103);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (1776257299984691201, 104);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (1776257299984691201, 105);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (1776257299984691201, 107);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (1776257299984691201, 108);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (1776257299984691201, 109);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (1776257299984691201, 110);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (1776257299984691201, 111);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (1776257299984691201, 112);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (1776257299984691201, 113);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (1776257299984691201, 114);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (1776257299984691201, 115);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (1776257299984691201, 124);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (1776257299984691201, 500);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (1776257299984691201, 501);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (1776257299984691201, 1001);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (1776257299984691201, 1002);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (1776257299984691201, 1003);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (1776257299984691201, 1004);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (1776257299984691201, 1005);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (1776257299984691201, 1006);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (1776257299984691201, 1007);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (1776257299984691201, 1008);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (1776257299984691201, 1009);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (1776257299984691201, 1010);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (1776257299984691201, 1011);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (1776257299984691201, 1012);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (1776257299984691201, 1013);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (1776257299984691201, 1014);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (1776257299984691201, 1015);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (1776257299984691201, 1016);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (1776257299984691201, 1017);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (1776257299984691201, 1018);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (1776257299984691201, 1019);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (1776257299984691201, 1020);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (1776257299984691201, 1021);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (1776257299984691201, 1022);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (1776257299984691201, 1023);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (1776257299984691201, 1024);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (1776257299984691201, 1025);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (1776257299984691201, 1026);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (1776257299984691201, 1027);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (1776257299984691201, 1028);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (1776257299984691201, 1029);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (1776257299984691201, 1030);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (1776257299984691201, 1036);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (1776257299984691201, 1037);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (1776257299984691201, 1038);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (1776257299984691201, 1039);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (1776257299984691201, 1040);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (1776257299984691201, 1041);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (1776257299984691201, 1042);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (1776257299984691201, 1043);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (1776257299984691201, 1044);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (1776257299984691201, 1045);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (1776257299984691201, 1046);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (1776257299984691201, 1047);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (1776257299984691201, 1048);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (1776257299984691201, 1050);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (1776257299984691201, 1055);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (1776257299984691201, 1056);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (1776257299984691201, 1057);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (1776257299984691201, 1058);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (1776257299984691201, 1059);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (1776257299984691201, 1060);
INSERT INTO `ry-cloud`.sys_role_menu (role_id, menu_id) VALUES (1776257299984691201, 1774786262642323457);

-- ----------------------------
-- 8、角色和部门关联表  角色1-N部门
-- ----------------------------
drop table if exists sys_role_dept;
create table sys_role_dept (
  role_id   bigint(20) not null comment '角色ID',
  dept_id   bigint(20) not null comment '部门ID',
  primary key(role_id, dept_id)
) engine=innodb comment = '角色和部门关联表';

-- ----------------------------
-- 初始化-角色和部门关联表数据
-- ----------------------------
insert into sys_role_dept values ('2', '100');
insert into sys_role_dept values ('2', '101');
insert into sys_role_dept values ('2', '105');


-- ----------------------------
-- 9、用户与岗位关联表  用户1-N岗位
-- ----------------------------
drop table if exists sys_user_post;
create table sys_user_post
(
  user_id   bigint(20) not null comment '用户ID',
  post_id   bigint(20) not null comment '岗位ID',
  primary key (user_id, post_id)
) engine=innodb comment = '用户与岗位关联表';

-- ----------------------------
-- 初始化-用户与岗位关联表数据
-- ----------------------------
insert into sys_user_post values ('1', '1');
insert into sys_user_post values ('2', '2');


-- ----------------------------
-- 10、操作日志记录
-- ----------------------------
drop table if exists sys_oper_log;
create table sys_oper_log (
  oper_id           bigint(20)      not null                   comment '日志主键',
  tenant_id         varchar(20)     default '000000'           comment '租户编号',
  title             varchar(50)     default ''                 comment '模块标题',
  business_type     int(2)          default 0                  comment '业务类型（0其它 1新增 2修改 3删除）',
  method            varchar(100)    default ''                 comment '方法名称',
  request_method    varchar(10)     default ''                 comment '请求方式',
  operator_type     int(1)          default 0                  comment '操作类别（0其它 1后台用户 2手机端用户）',
  oper_name         varchar(50)     default ''                 comment '操作人员',
  dept_name         varchar(50)     default ''                 comment '部门名称',
  oper_url          varchar(255)    default ''                 comment '请求URL',
  oper_ip           varchar(128)    default ''                 comment '主机地址',
  oper_location     varchar(255)    default ''                 comment '操作地点',
  oper_param        varchar(2000)   default ''                 comment '请求参数',
  json_result       varchar(2000)   default ''                 comment '返回参数',
  status            int(1)          default 0                  comment '操作状态（0正常 1异常）',
  error_msg         varchar(2000)   default ''                 comment '错误消息',
  oper_time         datetime                                   comment '操作时间',
  cost_time         bigint(20)      default 0                  comment '消耗时间',
  primary key (oper_id),
  key idx_sys_oper_log_bt (business_type),
  key idx_sys_oper_log_s  (status),
  key idx_sys_oper_log_ot (oper_time)
) engine=innodb comment = '操作日志记录';


-- ----------------------------
-- 11、字典类型表
-- ----------------------------
drop table if exists sys_dict_type;
create table sys_dict_type
(
  dict_id          bigint(20)      not null                   comment '字典主键',
  tenant_id        varchar(20)     default '000000'           comment '租户编号',
  dict_name        varchar(100)    default ''                 comment '字典名称',
  dict_type        varchar(100)    default ''                 comment '字典类型',
  create_dept      bigint(20)      default null               comment '创建部门',
  create_by        bigint(20)      default null               comment '创建者',
  create_time      datetime                                   comment '创建时间',
  update_by        bigint(20)      default null               comment '更新者',
  update_time      datetime                                   comment '更新时间',
  remark           varchar(500)    default null               comment '备注',
  primary key (dict_id),
  unique (tenant_id, dict_type)
) engine=innodb comment = '字典类型表';

INSERT INTO `ry-cloud`.sys_dict_type (dict_id, tenant_id, dict_name, dict_type, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1, '000000', '用户性别', 'sys_user_sex', 103, 1, '2024-03-06 14:54:13', null, null, '用户性别列表');
INSERT INTO `ry-cloud`.sys_dict_type (dict_id, tenant_id, dict_name, dict_type, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (2, '000000', '菜单状态', 'sys_show_hide', 103, 1, '2024-03-06 14:54:13', null, null, '菜单状态列表');
INSERT INTO `ry-cloud`.sys_dict_type (dict_id, tenant_id, dict_name, dict_type, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (3, '000000', '系统开关', 'sys_normal_disable', 103, 1, '2024-03-06 14:54:13', null, null, '系统开关列表');
INSERT INTO `ry-cloud`.sys_dict_type (dict_id, tenant_id, dict_name, dict_type, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (6, '000000', '系统是否', 'sys_yes_no', 103, 1, '2024-03-06 14:54:13', null, null, '系统是否列表');
INSERT INTO `ry-cloud`.sys_dict_type (dict_id, tenant_id, dict_name, dict_type, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (7, '000000', '通知类型', 'sys_notice_type', 103, 1, '2024-03-06 14:54:13', null, null, '通知类型列表');
INSERT INTO `ry-cloud`.sys_dict_type (dict_id, tenant_id, dict_name, dict_type, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (8, '000000', '通知状态', 'sys_notice_status', 103, 1, '2024-03-06 14:54:13', null, null, '通知状态列表');
INSERT INTO `ry-cloud`.sys_dict_type (dict_id, tenant_id, dict_name, dict_type, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (9, '000000', '操作类型', 'sys_oper_type', 103, 1, '2024-03-06 14:54:13', null, null, '操作类型列表');
INSERT INTO `ry-cloud`.sys_dict_type (dict_id, tenant_id, dict_name, dict_type, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (10, '000000', '系统状态', 'sys_common_status', 103, 1, '2024-03-06 14:54:13', null, null, '登录状态列表');
INSERT INTO `ry-cloud`.sys_dict_type (dict_id, tenant_id, dict_name, dict_type, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (11, '000000', '授权类型', 'sys_grant_type', 103, 1, '2024-03-06 14:54:13', null, null, '认证授权类型');
INSERT INTO `ry-cloud`.sys_dict_type (dict_id, tenant_id, dict_name, dict_type, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (12, '000000', '设备类型', 'sys_device_type', 103, 1, '2024-03-06 14:54:13', null, null, '客户端设备类型');
INSERT INTO `ry-cloud`.sys_dict_type (dict_id, tenant_id, dict_name, dict_type, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1766784864277323778, '000000', '课程状态', 'course_status', 103, 1, '2024-03-10 19:15:12', 1, '2024-03-29 10:30:51', '课程状态列表');
INSERT INTO `ry-cloud`.sys_dict_type (dict_id, tenant_id, dict_name, dict_type, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1774763257094590466, '000000', '媒资状态', 'media_status', 103, 1, '2024-04-01 19:38:29', 1, '2024-04-01 19:38:29', '媒资状态列表');
INSERT INTO `ry-cloud`.sys_dict_type (dict_id, tenant_id, dict_name, dict_type, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1784501699839901697, '000000', '订单状态', 'order_status', 103, 1, '2024-04-28 16:35:34', 1, '2024-04-28 16:35:41', '订单状态列表');
INSERT INTO `ry-cloud`.sys_dict_type (dict_id, tenant_id, dict_name, dict_type, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1786377302427848705, '000000', '评论状态', 'discuss_status', 103, 1, '2024-05-03 20:48:33', 1, '2024-05-03 20:48:33', '评论状态列表');


-- ----------------------------
-- 12、字典数据表
-- ----------------------------
drop table if exists sys_dict_data;
create table sys_dict_data
(
  dict_code        bigint(20)      not null                   comment '字典编码',
  tenant_id        varchar(20)     default '000000'           comment '租户编号',
  dict_sort        int(4)          default 0                  comment '字典排序',
  dict_label       varchar(100)    default ''                 comment '字典标签',
  dict_value       varchar(100)    default ''                 comment '字典键值',
  dict_eng         varchar(100)    default ''  null           comment '枚举常量，用于生成enum',
  dict_type        varchar(100)    default ''                 comment '字典类型',
  css_class        varchar(100)    default null               comment '样式属性（其他样式扩展）',
  list_class       varchar(100)    default null               comment '表格回显样式',
  is_default       char(1)         default 'N'                comment '是否默认（Y是 N否）',
  create_dept      bigint(20)      default null               comment '创建部门',
  create_by        bigint(20)      default null               comment '创建者',
  create_time      datetime                                   comment '创建时间',
  update_by        bigint(20)      default null               comment '更新者',
  update_time      datetime                                   comment '更新时间',
  remark           varchar(500)    default null               comment '备注',
  primary key (dict_code)
) engine=innodb comment = '字典数据表';


INSERT INTO `ry-cloud`.sys_dict_data (dict_code, tenant_id, dict_sort, dict_label, dict_value, dict_eng, dict_type, css_class, list_class, is_default, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1, '000000', 1, '男', '0', '', 'sys_user_sex', '', '', 'Y', 103, 1, '2024-03-06 14:54:13', null, null, '性别男');
INSERT INTO `ry-cloud`.sys_dict_data (dict_code, tenant_id, dict_sort, dict_label, dict_value, dict_eng, dict_type, css_class, list_class, is_default, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (2, '000000', 2, '女', '1', '', 'sys_user_sex', '', '', 'N', 103, 1, '2024-03-06 14:54:13', null, null, '性别女');
INSERT INTO `ry-cloud`.sys_dict_data (dict_code, tenant_id, dict_sort, dict_label, dict_value, dict_eng, dict_type, css_class, list_class, is_default, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (3, '000000', 3, '未知', '2', '', 'sys_user_sex', '', '', 'N', 103, 1, '2024-03-06 14:54:13', null, null, '性别未知');
INSERT INTO `ry-cloud`.sys_dict_data (dict_code, tenant_id, dict_sort, dict_label, dict_value, dict_eng, dict_type, css_class, list_class, is_default, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (4, '000000', 1, '显示', '0', '', 'sys_show_hide', '', 'primary', 'Y', 103, 1, '2024-03-06 14:54:13', null, null, '显示菜单');
INSERT INTO `ry-cloud`.sys_dict_data (dict_code, tenant_id, dict_sort, dict_label, dict_value, dict_eng, dict_type, css_class, list_class, is_default, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (5, '000000', 2, '隐藏', '1', '', 'sys_show_hide', '', 'danger', 'N', 103, 1, '2024-03-06 14:54:13', null, null, '隐藏菜单');
INSERT INTO `ry-cloud`.sys_dict_data (dict_code, tenant_id, dict_sort, dict_label, dict_value, dict_eng, dict_type, css_class, list_class, is_default, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (6, '000000', 1, '正常', '0', '', 'sys_normal_disable', '', 'primary', 'Y', 103, 1, '2024-03-06 14:54:13', null, null, '正常状态');
INSERT INTO `ry-cloud`.sys_dict_data (dict_code, tenant_id, dict_sort, dict_label, dict_value, dict_eng, dict_type, css_class, list_class, is_default, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (7, '000000', 2, '停用', '1', '', 'sys_normal_disable', '', 'danger', 'N', 103, 1, '2024-03-06 14:54:13', null, null, '停用状态');
INSERT INTO `ry-cloud`.sys_dict_data (dict_code, tenant_id, dict_sort, dict_label, dict_value, dict_eng, dict_type, css_class, list_class, is_default, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (12, '000000', 1, '是', 'Y', '', 'sys_yes_no', '', 'primary', 'Y', 103, 1, '2024-03-06 14:54:13', null, null, '系统默认是');
INSERT INTO `ry-cloud`.sys_dict_data (dict_code, tenant_id, dict_sort, dict_label, dict_value, dict_eng, dict_type, css_class, list_class, is_default, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (13, '000000', 2, '否', 'N', '', 'sys_yes_no', '', 'danger', 'N', 103, 1, '2024-03-06 14:54:13', null, null, '系统默认否');
INSERT INTO `ry-cloud`.sys_dict_data (dict_code, tenant_id, dict_sort, dict_label, dict_value, dict_eng, dict_type, css_class, list_class, is_default, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (14, '000000', 1, '通知', '1', '', 'sys_notice_type', '', 'warning', 'Y', 103, 1, '2024-03-06 14:54:13', null, null, '通知');
INSERT INTO `ry-cloud`.sys_dict_data (dict_code, tenant_id, dict_sort, dict_label, dict_value, dict_eng, dict_type, css_class, list_class, is_default, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (15, '000000', 2, '公告', '2', '', 'sys_notice_type', '', 'success', 'N', 103, 1, '2024-03-06 14:54:13', null, null, '公告');
INSERT INTO `ry-cloud`.sys_dict_data (dict_code, tenant_id, dict_sort, dict_label, dict_value, dict_eng, dict_type, css_class, list_class, is_default, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (16, '000000', 1, '正常', '0', '', 'sys_notice_status', '', 'primary', 'Y', 103, 1, '2024-03-06 14:54:13', null, null, '正常状态');
INSERT INTO `ry-cloud`.sys_dict_data (dict_code, tenant_id, dict_sort, dict_label, dict_value, dict_eng, dict_type, css_class, list_class, is_default, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (17, '000000', 2, '关闭', '1', '', 'sys_notice_status', '', 'danger', 'N', 103, 1, '2024-03-06 14:54:13', null, null, '关闭状态');
INSERT INTO `ry-cloud`.sys_dict_data (dict_code, tenant_id, dict_sort, dict_label, dict_value, dict_eng, dict_type, css_class, list_class, is_default, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (18, '000000', 1, '新增', '1', '', 'sys_oper_type', '', 'info', 'N', 103, 1, '2024-03-06 14:54:13', null, null, '新增操作');
INSERT INTO `ry-cloud`.sys_dict_data (dict_code, tenant_id, dict_sort, dict_label, dict_value, dict_eng, dict_type, css_class, list_class, is_default, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (19, '000000', 2, '修改', '2', '', 'sys_oper_type', '', 'info', 'N', 103, 1, '2024-03-06 14:54:13', null, null, '修改操作');
INSERT INTO `ry-cloud`.sys_dict_data (dict_code, tenant_id, dict_sort, dict_label, dict_value, dict_eng, dict_type, css_class, list_class, is_default, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (20, '000000', 3, '删除', '3', '', 'sys_oper_type', '', 'danger', 'N', 103, 1, '2024-03-06 14:54:13', null, null, '删除操作');
INSERT INTO `ry-cloud`.sys_dict_data (dict_code, tenant_id, dict_sort, dict_label, dict_value, dict_eng, dict_type, css_class, list_class, is_default, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (21, '000000', 4, '授权', '4', '', 'sys_oper_type', '', 'primary', 'N', 103, 1, '2024-03-06 14:54:13', null, null, '授权操作');
INSERT INTO `ry-cloud`.sys_dict_data (dict_code, tenant_id, dict_sort, dict_label, dict_value, dict_eng, dict_type, css_class, list_class, is_default, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (22, '000000', 5, '导出', '5', '', 'sys_oper_type', '', 'warning', 'N', 103, 1, '2024-03-06 14:54:13', null, null, '导出操作');
INSERT INTO `ry-cloud`.sys_dict_data (dict_code, tenant_id, dict_sort, dict_label, dict_value, dict_eng, dict_type, css_class, list_class, is_default, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (23, '000000', 6, '导入', '6', '', 'sys_oper_type', '', 'warning', 'N', 103, 1, '2024-03-06 14:54:13', null, null, '导入操作');
INSERT INTO `ry-cloud`.sys_dict_data (dict_code, tenant_id, dict_sort, dict_label, dict_value, dict_eng, dict_type, css_class, list_class, is_default, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (24, '000000', 7, '强退', '7', '', 'sys_oper_type', '', 'danger', 'N', 103, 1, '2024-03-06 14:54:13', null, null, '强退操作');
INSERT INTO `ry-cloud`.sys_dict_data (dict_code, tenant_id, dict_sort, dict_label, dict_value, dict_eng, dict_type, css_class, list_class, is_default, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (25, '000000', 8, '生成代码', '8', '', 'sys_oper_type', '', 'warning', 'N', 103, 1, '2024-03-06 14:54:13', null, null, '生成操作');
INSERT INTO `ry-cloud`.sys_dict_data (dict_code, tenant_id, dict_sort, dict_label, dict_value, dict_eng, dict_type, css_class, list_class, is_default, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (26, '000000', 9, '清空数据', '9', '', 'sys_oper_type', '', 'danger', 'N', 103, 1, '2024-03-06 14:54:13', null, null, '清空操作');
INSERT INTO `ry-cloud`.sys_dict_data (dict_code, tenant_id, dict_sort, dict_label, dict_value, dict_eng, dict_type, css_class, list_class, is_default, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (27, '000000', 1, '成功', '0', '', 'sys_common_status', '', 'primary', 'N', 103, 1, '2024-03-06 14:54:13', null, null, '正常状态');
INSERT INTO `ry-cloud`.sys_dict_data (dict_code, tenant_id, dict_sort, dict_label, dict_value, dict_eng, dict_type, css_class, list_class, is_default, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (28, '000000', 2, '失败', '1', '', 'sys_common_status', '', 'danger', 'N', 103, 1, '2024-03-06 14:54:13', null, null, '停用状态');
INSERT INTO `ry-cloud`.sys_dict_data (dict_code, tenant_id, dict_sort, dict_label, dict_value, dict_eng, dict_type, css_class, list_class, is_default, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (29, '000000', 99, '其他', '0', '', 'sys_oper_type', '', 'info', 'N', 103, 1, '2024-03-06 14:54:13', null, null, '其他操作');
INSERT INTO `ry-cloud`.sys_dict_data (dict_code, tenant_id, dict_sort, dict_label, dict_value, dict_eng, dict_type, css_class, list_class, is_default, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (30, '000000', 0, '密码认证', 'password', '', 'sys_grant_type', 'el-check-tag', 'default', 'N', 103, 1, '2024-03-06 14:54:13', null, null, '密码认证');
INSERT INTO `ry-cloud`.sys_dict_data (dict_code, tenant_id, dict_sort, dict_label, dict_value, dict_eng, dict_type, css_class, list_class, is_default, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (31, '000000', 0, '短信认证', 'sms', '', 'sys_grant_type', 'el-check-tag', 'default', 'N', 103, 1, '2024-03-06 14:54:13', null, null, '短信认证');
INSERT INTO `ry-cloud`.sys_dict_data (dict_code, tenant_id, dict_sort, dict_label, dict_value, dict_eng, dict_type, css_class, list_class, is_default, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (32, '000000', 0, '邮件认证', 'email', '', 'sys_grant_type', 'el-check-tag', 'default', 'N', 103, 1, '2024-03-06 14:54:13', null, null, '邮件认证');
INSERT INTO `ry-cloud`.sys_dict_data (dict_code, tenant_id, dict_sort, dict_label, dict_value, dict_eng, dict_type, css_class, list_class, is_default, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (33, '000000', 0, '小程序认证', 'xcx', '', 'sys_grant_type', 'el-check-tag', 'default', 'N', 103, 1, '2024-03-06 14:54:13', null, null, '小程序认证');
INSERT INTO `ry-cloud`.sys_dict_data (dict_code, tenant_id, dict_sort, dict_label, dict_value, dict_eng, dict_type, css_class, list_class, is_default, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (34, '000000', 0, '三方登录认证', 'social', '', 'sys_grant_type', 'el-check-tag', 'default', 'N', 103, 1, '2024-03-06 14:54:13', null, null, '三方登录认证');
INSERT INTO `ry-cloud`.sys_dict_data (dict_code, tenant_id, dict_sort, dict_label, dict_value, dict_eng, dict_type, css_class, list_class, is_default, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (35, '000000', 0, 'PC', 'pc', '', 'sys_device_type', '', 'default', 'N', 103, 1, '2024-03-06 14:54:13', null, null, 'PC');
INSERT INTO `ry-cloud`.sys_dict_data (dict_code, tenant_id, dict_sort, dict_label, dict_value, dict_eng, dict_type, css_class, list_class, is_default, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (36, '000000', 0, '安卓', 'android', '', 'sys_device_type', '', 'default', 'N', 103, 1, '2024-03-06 14:54:13', null, null, '安卓');
INSERT INTO `ry-cloud`.sys_dict_data (dict_code, tenant_id, dict_sort, dict_label, dict_value, dict_eng, dict_type, css_class, list_class, is_default, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (37, '000000', 0, 'iOS', 'ios', '', 'sys_device_type', '', 'default', 'N', 103, 1, '2024-03-06 14:54:13', null, null, 'iOS');
INSERT INTO `ry-cloud`.sys_dict_data (dict_code, tenant_id, dict_sort, dict_label, dict_value, dict_eng, dict_type, css_class, list_class, is_default, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (38, '000000', 0, '小程序', 'xcx', '', 'sys_device_type', '', 'default', 'N', 103, 1, '2024-03-06 14:54:13', null, null, '小程序');
INSERT INTO `ry-cloud`.sys_dict_data (dict_code, tenant_id, dict_sort, dict_label, dict_value, dict_eng, dict_type, css_class, list_class, is_default, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1766784987799576578, '000000', 1, '未发布', '1', 'UNPUBLISHED', 'course_status', '', 'info', 'N', 103, 1, '2024-03-10 19:15:41', 1, '2024-04-04 10:34:00', '');
INSERT INTO `ry-cloud`.sys_dict_data (dict_code, tenant_id, dict_sort, dict_label, dict_value, dict_eng, dict_type, css_class, list_class, is_default, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1766785071924731905, '000000', 2, '审核中', '2', 'UNDER_REVIEW', 'course_status', '', 'warning', 'N', 103, 1, '2024-03-10 19:16:01', 1, '2024-04-04 10:34:08', '');
INSERT INTO `ry-cloud`.sys_dict_data (dict_code, tenant_id, dict_sort, dict_label, dict_value, dict_eng, dict_type, css_class, list_class, is_default, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1766785158545498113, '000000', 3, '审核不通过', '3', 'REVIEW_FAILED', 'course_status', '', 'danger', 'N', 103, 1, '2024-03-10 19:16:22', 1, '2024-04-04 10:34:14', '');
INSERT INTO `ry-cloud`.sys_dict_data (dict_code, tenant_id, dict_sort, dict_label, dict_value, dict_eng, dict_type, css_class, list_class, is_default, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1766785239264878593, '000000', 4, '审核通过', '4', 'REVIEW_PASSED', 'course_status', '', 'success', 'N', 103, 1, '2024-03-10 19:16:41', 1, '2024-04-04 10:34:20', '');
INSERT INTO `ry-cloud`.sys_dict_data (dict_code, tenant_id, dict_sort, dict_label, dict_value, dict_eng, dict_type, css_class, list_class, is_default, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1774763394680344577, '000000', 1, '未审核', '1', 'UNREVIEWED', 'media_status', '', 'info', 'N', 103, 1, '2024-04-01 19:39:01', 1, '2024-04-04 12:22:49', '');
INSERT INTO `ry-cloud`.sys_dict_data (dict_code, tenant_id, dict_sort, dict_label, dict_value, dict_eng, dict_type, css_class, list_class, is_default, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1774763472908308481, '000000', 3, '审核通过', '3', 'REVIEW_PASSED', 'media_status', '', 'success', 'N', 103, 1, '2024-04-01 19:39:20', 1, '2024-04-04 12:23:10', '');
INSERT INTO `ry-cloud`.sys_dict_data (dict_code, tenant_id, dict_sort, dict_label, dict_value, dict_eng, dict_type, css_class, list_class, is_default, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1774763520488493058, '000000', 4, '审核未通过', '4', 'REVIEW_FAILED', 'media_status', '', 'danger', 'N', 103, 1, '2024-04-01 19:39:31', 1, '2024-04-04 12:23:04', '');
INSERT INTO `ry-cloud`.sys_dict_data (dict_code, tenant_id, dict_sort, dict_label, dict_value, dict_eng, dict_type, css_class, list_class, is_default, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1774763586045464577, '000000', 2, '审核中', '2', 'UNDER_REVIEW', 'media_status', '', 'warning', 'N', 103, 1, '2024-04-01 19:39:47', 1, '2024-04-04 12:22:57', '');
INSERT INTO `ry-cloud`.sys_dict_data (dict_code, tenant_id, dict_sort, dict_label, dict_value, dict_eng, dict_type, css_class, list_class, is_default, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1784502178284158978, '000000', 0, '未支付', '1', 'UNPAID', 'order_status', '', 'info', 'N', 103, 1, '2024-04-28 16:37:28', 1, '2024-04-28 16:37:28', '');
INSERT INTO `ry-cloud`.sys_dict_data (dict_code, tenant_id, dict_sort, dict_label, dict_value, dict_eng, dict_type, css_class, list_class, is_default, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1784502253567721474, '000000', 1, '支付成功', '2', 'PAID_SUCCESS', 'order_status', '', 'success', 'N', 103, 1, '2024-04-28 16:37:46', 1, '2024-04-28 16:37:46', '');
INSERT INTO `ry-cloud`.sys_dict_data (dict_code, tenant_id, dict_sort, dict_label, dict_value, dict_eng, dict_type, css_class, list_class, is_default, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1784502314435461122, '000000', 2, '支付失败', '3', 'PAID_FAILED', 'order_status', '', 'danger', 'N', 103, 1, '2024-04-28 16:38:01', 1, '2024-05-01 20:36:02', '');
INSERT INTO `ry-cloud`.sys_dict_data (dict_code, tenant_id, dict_sort, dict_label, dict_value, dict_eng, dict_type, css_class, list_class, is_default, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1786377495109980162, '000000', 0, '正常', '0', 'SUCCESS', 'discuss_status', '', 'success', 'N', 103, 1, '2024-05-03 20:49:19', 1, '2024-05-03 20:49:19', '');
INSERT INTO `ry-cloud`.sys_dict_data (dict_code, tenant_id, dict_sort, dict_label, dict_value, dict_eng, dict_type, css_class, list_class, is_default, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1786377834966044673, '000000', 1, '申请屏蔽', '1', 'APPLYBLOCK', 'discuss_status', '', 'warning', 'N', 103, 1, '2024-05-03 20:50:40', 1, '2024-05-03 20:50:40', '');
INSERT INTO `ry-cloud`.sys_dict_data (dict_code, tenant_id, dict_sort, dict_label, dict_value, dict_eng, dict_type, css_class, list_class, is_default, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1786377908123095042, '000000', 2, '屏蔽', '2', 'BLOCK', 'discuss_status', '', 'danger', 'N', 103, 1, '2024-05-03 20:50:57', 1, '2024-05-03 20:50:57', '');


-- ----------------------------
-- 13、参数配置表
-- ----------------------------
drop table if exists sys_config;
create table sys_config (
  config_id         bigint(20)      not null                   comment '参数主键',
  tenant_id         varchar(20)     default '000000'           comment '租户编号',
  config_name       varchar(100)    default ''                 comment '参数名称',
  config_key        varchar(100)    default ''                 comment '参数键名',
  config_value      varchar(500)    default ''                 comment '参数键值',
  config_type       char(1)         default 'N'                comment '系统内置（Y是 N否）',
  create_dept       bigint(20)      default null               comment '创建部门',
  create_by         bigint(20)      default null               comment '创建者',
  create_time       datetime                                   comment '创建时间',
  update_by         bigint(20)      default null               comment '更新者',
  update_time       datetime                                   comment '更新时间',
  remark            varchar(500)    default null               comment '备注',
  primary key (config_id)
) engine=innodb comment = '参数配置表';

insert into sys_config values(1, '000000', '主框架页-默认皮肤样式名称',     'sys.index.skinName',            'skin-blue',     'Y', 103, 1, sysdate(), null, null, '蓝色 skin-blue、绿色 skin-green、紫色 skin-purple、红色 skin-red、黄色 skin-yellow' );
insert into sys_config values(2, '000000', '用户管理-账号初始密码',        'sys.user.initPassword',         '123456',        'Y', 103, 1, sysdate(), null, null, '初始化密码 123456' );
insert into sys_config values(3, '000000', '主框架页-侧边栏主题',          'sys.index.sideTheme',           'theme-dark',    'Y', 103, 1, sysdate(), null, null, '深色主题theme-dark，浅色主题theme-light' );
insert into sys_config values(5, '000000', '账号自助-是否开启用户注册功能',  'sys.account.registerUser',      'true',         'Y', 103, 1, sysdate(), null, null, '是否开启注册用户功能（true开启，false关闭）');
insert into sys_config values(11, '000000', 'OSS预览列表资源开关',         'sys.oss.previewListResource',   'true',          'Y', 103, 1, sysdate(), null, null, 'true:开启, false:关闭');


-- ----------------------------
-- 14、系统访问记录
-- ----------------------------
drop table if exists sys_logininfor;
create table sys_logininfor (
  info_id        bigint(20)     not null                  comment '访问ID',
  tenant_id      varchar(20)    default '000000'          comment '租户编号',
  user_name      varchar(50)    default ''                comment '用户账号',
  client_key     varchar(32)    default ''                comment '客户端',
  device_type    varchar(32)    default ''                comment '设备类型',
  ipaddr         varchar(128)   default ''                comment '登录IP地址',
  login_location varchar(255)   default ''                comment '登录地点',
  browser        varchar(50)    default ''                comment '浏览器类型',
  os             varchar(50)    default ''                comment '操作系统',
  status         char(1)        default '0'               comment '登录状态（0成功 1失败）',
  msg            varchar(255)   default ''                comment '提示消息',
  login_time     datetime                                 comment '访问时间',
  primary key (info_id),
  key idx_sys_logininfor_s  (status),
  key idx_sys_logininfor_lt (login_time)
) engine=innodb comment = '系统访问记录';


-- ----------------------------
-- 17、通知公告表
-- ----------------------------
drop table if exists sys_notice;
create table sys_notice (
  notice_id         bigint(20)      not null                   comment '公告ID',
  tenant_id         varchar(20)     default '000000'           comment '租户编号',
  notice_title      varchar(50)     not null                   comment '公告标题',
  notice_type       char(1)         not null                   comment '公告类型（1通知 2公告）',
  notice_content    longblob        default null               comment '公告内容',
  status            char(1)         default '0'                comment '公告状态（0正常 1关闭）',
  create_dept       bigint(20)      default null               comment '创建部门',
  create_by         bigint(20)      default null               comment '创建者',
  create_time       datetime                                   comment '创建时间',
  update_by         bigint(20)      default null               comment '更新者',
  update_time       datetime                                   comment '更新时间',
  remark            varchar(255)    default null               comment '备注',
  primary key (notice_id)
) engine=innodb comment = '通知公告表';

-- ----------------------------
-- 初始化-公告信息表数据
-- ----------------------------
INSERT INTO `ry-cloud`.sys_notice (notice_id, tenant_id, notice_title, notice_type, notice_content, status, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1, '000000', '温馨提醒：2018-07-01 新版本发布啦', '2', 0xE696B0E78988E69CACE58685E5AEB9, '0', 103, 1, '2024-03-06 14:54:13', null, null, '管理员');
INSERT INTO `ry-cloud`.sys_notice (notice_id, tenant_id, notice_title, notice_type, notice_content, status, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (2, '000000', '维护通知：2018-07-01 系统凌晨维护', '1', 0xE7BBB4E68AA4E58685E5AEB9, '0', 103, 1, '2024-03-06 14:54:13', null, null, '管理员');
INSERT INTO `ry-cloud`.sys_notice (notice_id, tenant_id, notice_title, notice_type, notice_content, status, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1786955168031031297, '000000', '测试公告', '1', 0x3C703EE6849FE8B0A2E59084E4BD8DE69CBAE69E84E585A5E9A9BBEFBC8CE9BE99E699BAE69599E882B2E380823C2F703E, '0', 103, 1, '2024-05-05 11:04:47', 1, '2024-05-05 11:04:47', '');
INSERT INTO `ry-cloud`.sys_notice (notice_id, tenant_id, notice_title, notice_type, notice_content, status, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1786955508717568002, '000000', '测试公告', '1', 0x3C703EE6849FE8B0A2E59084E4BD8DE69CBAE69E84E585A5E9A9BBEFBC8CE9BE99E699BAE69599E882B2E380823C2F703E, '0', 103, 1, '2024-05-05 11:06:08', 1, '2024-05-05 11:06:08', '');
INSERT INTO `ry-cloud`.sys_notice (notice_id, tenant_id, notice_title, notice_type, notice_content, status, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1786967342526767106, '000000', '复杂一点的公告测试', '2', 0x3C7020636C6173733D22716C2D616C69676E2D63656E746572223EE7BBB4E68AA4E58685E5AEB93C2F703E3C703E3C62723E3C2F703E3C68313EE4B880E38081E4BFAEE5A48DE4BA86E585ACE5918A3C7370616E207374796C653D22636F6C6F723A20726762283233302C20302C2030293B223EE5A4B1E695883C2F7370616E3E6275673C2F68313E3C703E3C62723E3C2F703E3C703EE7AE80E8808CE8A880E4B98BEFBC8CE8A880E8808CE7AE80E4B98BEFBC8CE5B0B1E698AFE5BE88E58E89E5AEB3E380823C2F703E3C703E3C62723E3C2F703E3C703E3C62723E3C2F703E3C68313EE4BA8CE38081E5A49AE4B8AAE585ACE5918AE5A682E4BD95E698BEE7A4BAEFBC9F3C2F68313E3C703E3C62723E3C2F703E3C703E3C7370616E207374796C653D226261636B67726F756E642D636F6C6F723A20726762283235352C203235352C20313032293B223EE5B09DE8AF95E8838CE699AFE9A29CE889B2E5A682E4BD95EFBC9F3C2F7370616E3E3C2F703E3C703E3C62723E3C2F703E3C703E3C7370616E207374796C653D226261636B67726F756E642D636F6C6F723A20726762283235352C203235352C20323034293B223EE5A682E4BD95EFBC9F3C2F7370616E3E3C2F703E3C703E3C7370616E207374796C653D226261636B67726F756E642D636F6C6F723A20726762283233302C20302C2030293B223EE5A682E4BD95EFBC9F3C2F7370616E3E3C2F703E3C703E3C62723E3C2F703E3C68313EE4B889E38081E58FAFE4BBA5E694BEE993BEE68EA5E59097EFBC9F3C2F68313E3C703E3C62723E3C2F703E3C703E3C6120687265663D227777772E62616964752E636F6D222072656C3D226E6F6F70656E6572206E6F726566657272657222207461726765743D225F626C616E6B223EE782B9E587BBE8BF99E4B8AAE58EBBE799BEE5BAA63C2F613E3C2F703E3C703E3C62723E3C2F703E3C68323EE59B9BE38081E8BF99E698AFE4BA8CE7BAA7E6A087E9A298EFBC8CE58FAFE4BBA5E694BEE59BBEE78987E59097EFBC9F3C2F68323E3C703E3C696D67207372633D22687474703A2F2F3139322E3136382E3130312E36353A393030302F72756F79692F323032342F30352F30352F66636266626133626566353334373637393033393762383031653032623730312E706E67223E3C2F703E3C703E3C62723E3C2F703E3C703E3C62723E3C2F703E3C703E3C753EE4B88BE58892E7BABF3C2F753E3C2F703E3C703E3C62723E3C2F703E3C703E3C733E3C753EE588A0E999A4E7BABF3C2F753E3C2F733E3C2F703E3C703E3C62723E3C2F703E3C626C6F636B71756F74653EE5BC95E794A83C2F626C6F636B71756F74653E3C626C6F636B71756F74653E3C62723E3C2F626C6F636B71756F74653E3C626C6F636B71756F74653E3C62723E3C2F626C6F636B71756F74653E3C626C6F636B71756F74653E3C62723E3C2F626C6F636B71756F74653E3C626C6F636B71756F74653E3C62723E3C2F626C6F636B71756F74653E3C703E3C62723E3C2F703E3C703E3C62723E3C2F703E3C7020636C6173733D22716C2D696E64656E742D33223EE7AD89E7AD893C2F703E3C703E3C62723E3C2F703E3C703EE4BBA3E7A081EFBC88E4B880E5AE9AE8A681E68F90E5898DE7A9BAE8A18CEFBC893C2F703E3C70726520636C6173733D22716C2D73796E74617822207370656C6C636865636B3D2266616C7365223E23696E636C756465266C743B73746164696F2E682667743B0A0A696E74206D61696E207965730A0A3C2F7072653E, '0', 103, 1, '2024-05-05 11:53:09', 1, '2024-05-05 11:54:27', '');
INSERT INTO `ry-cloud`.sys_notice (notice_id, tenant_id, notice_title, notice_type, notice_content, status, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1788841799965044738, '000000', '今天准备上云服务器', '1', 0x3C703EE8B4ADE4B9B0E4BA86E58D8EE4B8BAE4BA91E69C8DE58AA1E599A8EFBC8C3136637075202B2033324742EFBC8CE78BA0E78BA0E4B88AE680A7E883BDE380823C2F703E, '0', 107, 1776257575227502593, '2024-05-10 16:01:35', 1776257575227502593, '2024-05-10 16:01:35', '');


-- ----------------------------
-- 18、代码生成业务表
-- ----------------------------

drop table if exists gen_table;
create table gen_table (
  table_id          bigint(20)      not null                   comment '编号',
  data_name         varchar(200)    default ''                 comment '数据源名称',
  table_name        varchar(200)    default ''                 comment '表名称',
  table_comment     varchar(500)    default ''                 comment '表描述',
  sub_table_name    varchar(64)     default null               comment '关联子表的表名',
  sub_table_fk_name varchar(64)     default null               comment '子表关联的外键名',
  class_name        varchar(100)    default ''                 comment '实体类名称',
  tpl_category      varchar(200)    default 'crud'             comment '使用的模板（crud单表操作 tree树表操作）',
  package_name      varchar(100)                               comment '生成包路径',
  module_name       varchar(30)                                comment '生成模块名',
  business_name     varchar(30)                                comment '生成业务名',
  function_name     varchar(50)                                comment '生成功能名',
  function_author   varchar(50)                                comment '生成功能作者',
  gen_type          char(1)         default '0'                comment '生成代码方式（0zip压缩包 1自定义路径）',
  gen_path          varchar(200)    default '/'                comment '生成路径（不填默认项目路径）',
  options           varchar(1000)                              comment '其它生成选项',
  create_dept       bigint(20)      default null               comment '创建部门',
  create_by         bigint(20)      default null               comment '创建者',
  create_time       datetime                                   comment '创建时间',
  update_by         bigint(20)      default null               comment '更新者',
  update_time       datetime                                   comment '更新时间',
  remark            varchar(500)    default null               comment '备注',
  template_choose   tinyint(1)   default 0      null comment '模版选择，0表示默认，1表示自定义',
  primary key (table_id)
) engine=innodb comment = '代码生成业务表';


-- ----------------------------
-- 19、代码生成业务表字段
-- ----------------------------
drop table if exists gen_table_column;
create table gen_table_column (
  column_id         bigint(20)      not null                   comment '编号',
  table_id          bigint(20)                                 comment '归属表编号',
  column_name       varchar(200)                               comment '列名称',
  column_comment    varchar(500)                               comment '列描述',
  column_type       varchar(100)                               comment '列类型',
  java_type         varchar(500)                               comment 'JAVA类型',
  java_field        varchar(200)                               comment 'JAVA字段名',
  is_pk             char(1)                                    comment '是否主键（1是）',
  is_increment      char(1)                                    comment '是否自增（1是）',
  is_required       char(1)                                    comment '是否必填（1是）',
  is_insert         char(1)                                    comment '是否为插入字段（1是）',
  is_edit           char(1)                                    comment '是否编辑字段（1是）',
  is_list           char(1)                                    comment '是否列表字段（1是）',
  is_query          char(1)                                    comment '是否查询字段（1是）',
  query_type        varchar(200)    default 'EQ'               comment '查询方式（等于、不等于、大于、小于、范围）',
  html_type         varchar(200)                               comment '显示类型（文本框、文本域、下拉框、复选框、单选框、日期控件）',
  dict_type         varchar(200)    default ''                 comment '字典类型',
  sort              int                                        comment '排序',
  create_dept       bigint(20)      default null               comment '创建部门',
  create_by         bigint(20)      default null               comment '创建者',
  create_time       datetime                                   comment '创建时间',
  update_by         bigint(20)      default null               comment '更新者',
  update_time       datetime                                   comment '更新时间',
  primary key (column_id)
) engine=innodb comment = '代码生成业务表字段';

-- ----------------------------
-- OSS对象存储表
-- ----------------------------
drop table if exists sys_oss;
create table sys_oss (
  oss_id          bigint(20)   not null                   comment '对象存储主键',
  tenant_id       varchar(20)           default '000000'  comment '租户编号',
  file_name       varchar(255) not null default ''        comment '文件名',
  original_name   varchar(255) not null default ''        comment '原名',
  file_suffix     varchar(10)  not null default ''        comment '文件后缀名',
  url             varchar(500) not null                   comment 'URL地址',
  create_dept     bigint(20)            default null      comment '创建部门',
  create_time     datetime              default null      comment '创建时间',
  create_by       bigint(20)            default null      comment '上传人',
  update_time     datetime              default null      comment '更新时间',
  update_by       bigint(20)            default null      comment '更新人',
  service         varchar(20)  not null default 'minio'   comment '服务商',
  primary key (oss_id)
) engine=innodb comment ='OSS对象存储表';

-- ----------------------------
-- OSS对象存储动态配置表
-- ----------------------------
drop table if exists sys_oss_config;
create table sys_oss_config (
  oss_config_id   bigint(20)    not null                  comment '主建',
  tenant_id       varchar(20)             default '000000'comment '租户编号',
  config_key      varchar(20)   not null  default ''      comment '配置key',
  access_key      varchar(255)            default ''      comment 'accessKey',
  secret_key      varchar(255)            default ''      comment '秘钥',
  bucket_name     varchar(255)            default ''      comment '桶名称',
  prefix          varchar(255)            default ''      comment '前缀',
  endpoint        varchar(255)            default ''      comment '访问站点',
  domain          varchar(255)            default ''      comment '自定义域名',
  is_https        char(1)                 default 'N'     comment '是否https（Y=是,N=否）',
  region          varchar(255)            default ''      comment '域',
  access_policy   char(1)       not null  default '1'     comment '桶权限类型(0=private 1=public 2=custom)',
  status          char(1)                 default '1'     comment '是否默认（0=是,1=否）',
  ext1            varchar(255)            default ''      comment '扩展字段',
  create_dept     bigint(20)              default null    comment '创建部门',
  create_by       bigint(20)              default null    comment '创建者',
  create_time     datetime                default null    comment '创建时间',
  update_by       bigint(20)              default null    comment '更新者',
  update_time     datetime                default null    comment '更新时间',
  remark          varchar(500)            default null    comment '备注',
  primary key (oss_config_id)
) engine=innodb comment='对象存储配置表';

insert into sys_oss_config values (1, '000000', 'minio',  'ruoyi',            'ruoyi123',        'ruoyi',             '', '127.0.0.1:9000',                '','N', '',             '1' ,'0', '', 103, 1, sysdate(), 1, sysdate(), NULL);
insert into sys_oss_config values (2, '000000', 'qiniu',  'XXXXXXXXXXXXXXX',  'XXXXXXXXXXXXXXX', 'ruoyi',             '', 's3-cn-north-1.qiniucs.com',     '','N', '',             '1' ,'1', '', 103, 1, sysdate(), 1, sysdate(), NULL);
insert into sys_oss_config values (3, '000000', 'aliyun', 'XXXXXXXXXXXXXXX',  'XXXXXXXXXXXXXXX', 'ruoyi',             '', 'oss-cn-beijing.aliyuncs.com',   '','N', '',             '1' ,'1', '', 103, 1, sysdate(), 1, sysdate(), NULL);
insert into sys_oss_config values (4, '000000', 'qcloud', 'XXXXXXXXXXXXXXX',  'XXXXXXXXXXXXXXX', 'ruoyi-1250000000',  '', 'cos.ap-beijing.myqcloud.com',   '','N', 'ap-beijing',   '1' ,'1', '', 103, 1, sysdate(), 1, sysdate(), NULL);
insert into sys_oss_config values (5, '000000', 'image',  'ruoyi',            'ruoyi123',        'ruoyi',             'image', '127.0.0.1:9000',           '','N', '',             '1' ,'1', '', 103, 1, sysdate(), 1, sysdate(), NULL);


-- ----------------------------
-- 系统授权表
-- ----------------------------
drop table if exists sys_client;
create table sys_client (
    id                  bigint(20)    not null            comment 'id',
    client_id           varchar(64)   default null        comment '客户端id',
    client_key          varchar(32)   default null        comment '客户端key',
    client_secret       varchar(255)  default null        comment '客户端秘钥',
    grant_type          varchar(255)  default null        comment '授权类型',
    device_type         varchar(32)   default null        comment '设备类型',
    active_timeout      int(11)       default 1800        comment 'token活跃超时时间',
    timeout             int(11)       default 604800      comment 'token固定超时',
    status              char(1)       default '0'         comment '状态（0正常 1停用）',
    del_flag            char(1)       default '0'         comment '删除标志（0代表存在 2代表删除）',
    create_dept         bigint(20)    default null        comment '创建部门',
    create_by           bigint(20)    default null        comment '创建者',
    create_time         datetime      default null        comment '创建时间',
    update_by           bigint(20)    default null        comment '更新者',
    update_time         datetime      default null        comment '更新时间',
    primary key (id)
) engine=innodb comment='系统授权表';

insert into sys_client values (1, 'e5cd7e4891bf95d1d19206ce24a7b32e', 'pc', 'pc123', 'password,social', 'pc', 1800, 604800, 0, 0, 103, 1, sysdate(), 1, sysdate());
insert into sys_client values (2, '428a8310cd442757ae699df5d894f051', 'app', 'app123', 'password,sms,social', 'android', 1800, 604800, 0, 0, 103, 1, sysdate(), 1, sysdate());


-- for AT mode you must to init this sql for you business database. the seata server not need it.
CREATE TABLE IF NOT EXISTS undo_log
(
    branch_id     BIGINT(20)   NOT NULL COMMENT 'branch transaction id',
    xid           VARCHAR(100) NOT NULL COMMENT 'global transaction id',
    context       VARCHAR(128) NOT NULL COMMENT 'undo_log context,such as serialization',
    rollback_info LONGBLOB     NOT NULL COMMENT 'rollback info',
    log_status    INT(11)      NOT NULL COMMENT '0:normal status,1:defense status',
    log_created   DATETIME(6)  NOT NULL COMMENT 'create datetime',
    log_modified  DATETIME(6)  NOT NULL COMMENT 'modify datetime',
    UNIQUE KEY ux_undo_log (xid, branch_id)
) ENGINE = InnoDB COMMENT ='AT transaction mode undo table';
