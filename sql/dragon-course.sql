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

INSERT INTO `dragon-course`.course_category (id, name, parentid, orderby, description, is_leaf) VALUES (1773616090480902145, 'IT·互联网', 0, 1, '', 0);
INSERT INTO `dragon-course`.course_category (id, name, parentid, orderby, description, is_leaf) VALUES (1773616415820480513, 'Java开发', 1773616090480902145, 3, 'Java零基础、Java进阶', 1);
INSERT INTO `dragon-course`.course_category (id, name, parentid, orderby, description, is_leaf) VALUES (1773720439068565506, '前端开发', 1773616090480902145, 7, '前端零基础、前端进阶、其他', 1);
INSERT INTO `dragon-course`.course_category (id, name, parentid, orderby, description, is_leaf) VALUES (1773720529564868609, '设计·创作', 0, 2, '', 0);
INSERT INTO `dragon-course`.course_category (id, name, parentid, orderby, description, is_leaf) VALUES (1773720589971234818, '平面设计', 1773720529564868609, 1, '电商设计、平面视觉、设计软件、其他', 1);
INSERT INTO `dragon-course`.course_category (id, name, parentid, orderby, description, is_leaf) VALUES (1773720694413598721, '游戏美术设计', 1773720529564868609, 6, '游戏角色设计、场景概念设计、游戏模型设计、游戏特效设计、游戏美宣、设计软件、其他', 1);
INSERT INTO `dragon-course`.course_category (id, name, parentid, orderby, description, is_leaf) VALUES (1773720753297432577, '移动开发', 1773616090480902145, 8, 'iOS、Android、鸿蒙OS、其他', 1);
INSERT INTO `dragon-course`.course_category (id, name, parentid, orderby, description, is_leaf) VALUES (1773720814374887426, '语言·留学', 0, 6, '', 0);
INSERT INTO `dragon-course`.course_category (id, name, parentid, orderby, description, is_leaf) VALUES (1773720906918010882, '英语', 1773720814374887426, 1, '英语口语、学术英语、新概念英语、外语翻译、词汇语法、职场英语、英语四六级', 1);
INSERT INTO `dragon-course`.course_category (id, name, parentid, orderby, description, is_leaf) VALUES (1773720940686352385, '日语', 1773720814374887426, 3, '日语入门、考研日语、日语能力考、实用日语', 1);
INSERT INTO `dragon-course`.course_category (id, name, parentid, orderby, description, is_leaf) VALUES (1773721004989227010, 'Python', 1773616090480902145, 6, '数据爬虫、办公自动化、Python开发、Python其他应用', 1);
INSERT INTO `dragon-course`.course_category (id, name, parentid, orderby, description, is_leaf) VALUES (1773721068293857282, '大数据与AI', 1773616090480902145, 9, '数据分析、大数据、机器学习、计算机视觉、自然语言处理(NLP)、人工智能、其他', 1);
INSERT INTO `dragon-course`.course_category (id, name, parentid, orderby, description, is_leaf) VALUES (1781266294801707010, '职业·职场', 0, 4, '', 0);
INSERT INTO `dragon-course`.course_category (id, name, parentid, orderby, description, is_leaf) VALUES (1781266375307177986, '办公软件', 1781266294801707010, 4, 'PPT、Excel、Word、思维导图、其他', 1);
INSERT INTO `dragon-course`.course_category (id, name, parentid, orderby, description, is_leaf) VALUES (1781944508184281090, '考试·考证', 0, 3, '', 0);
INSERT INTO `dragon-course`.course_category (id, name, parentid, orderby, description, is_leaf) VALUES (1781944625708679169, '兴趣·生活', 0, 5, '', 0);
INSERT INTO `dragon-course`.course_category (id, name, parentid, orderby, description, is_leaf) VALUES (1781944680624701441, '企业·培训', 0, 7, '', 0);
INSERT INTO `dragon-course`.course_category (id, name, parentid, orderby, description, is_leaf) VALUES (1781944861457924097, '环境艺术设计', 1773720529564868609, 2, '室内设计、建筑设计、景观设计、设计软件、其他', 1);
INSERT INTO `dragon-course`.course_category (id, name, parentid, orderby, description, is_leaf) VALUES (1781945078689316866, '大学学历', 1781944508184281090, 1, '自考、专升本、成考高起专/高起本、大学学科、公共课', 1);
INSERT INTO `dragon-course`.course_category (id, name, parentid, orderby, description, is_leaf) VALUES (1781945126969950210, '考研', 1781944508184281090, 2, '规划指导、考研英语、考研政治、考研数学、管综、专业课、其他', 1);
INSERT INTO `dragon-course`.course_category (id, name, parentid, orderby, description, is_leaf) VALUES (1781945346386575361, '技工技能', 1781266294801707010, 1, '汽车维修、电工电气、美容美发、学术科研、其他', 1);
INSERT INTO `dragon-course`.course_category (id, name, parentid, orderby, description, is_leaf) VALUES (1781945401025773570, '面试求职', 1781266294801707010, 3, '职业规划、面试简历', 1);
INSERT INTO `dragon-course`.course_category (id, name, parentid, orderby, description, is_leaf) VALUES (1781945505950482434, '出国留学', 1773720814374887426, 2, '雅思、托福、留学指导', 1);
INSERT INTO `dragon-course`.course_category (id, name, parentid, orderby, description, is_leaf) VALUES (1781945599336660994, '组织管理', 1781944680624701441, 1, '企业管理、人才培养', 1);
INSERT INTO `dragon-course`.course_category (id, name, parentid, orderby, description, is_leaf) VALUES (1781945636837933057, '岗位技能', 1781944680624701441, 2, '研发岗位、数据岗位、产品岗位、供应链岗位、销售岗位、营销岗位、客服岗位、设计岗位', 1);
INSERT INTO `dragon-course`.course_category (id, name, parentid, orderby, description, is_leaf) VALUES (1781945678051164162, '职场通用', 1781944680624701441, 3, '办公效率、人脉社交、其他', 1);
INSERT INTO `dragon-course`.course_category (id, name, parentid, orderby, description, is_leaf) VALUES (1781952873304178689, '播音主播', 1781944625708679169, 1, '朗诵主持、普通话训练、配音、口才、其他', 1);
INSERT INTO `dragon-course`.course_category (id, name, parentid, orderby, description, is_leaf) VALUES (1781952915993804802, '投资理财', 1781944625708679169, 2, '股票、基金、期权期货、外汇衍生、其他', 1);
INSERT INTO `dragon-course`.course_category (id, name, parentid, orderby, description, is_leaf) VALUES (1781952955122466818, '生活百科', 1781944625708679169, 3, '心理情感、时尚美妆、母婴亲子、美食烹饪、书法、动植育养、游戏电竞、手工手艺、益智、棋牌、编剧写作、家居家装、其他', 1);
INSERT INTO `dragon-course`.course_category (id, name, parentid, orderby, description, is_leaf) VALUES (1782022285340291074, '前沿技术', 1773616090480902145, 1, '云计算、云原生、区块链、物联网、5G、VR/AR、量子计算', 1);
INSERT INTO `dragon-course`.course_category (id, name, parentid, orderby, description, is_leaf) VALUES (1782022483093336065, '互联网运营', 1773616090480902145, 2, '新媒体运营、产品运营、游戏运营、SEO、SEM、网络营销、社群营销、其他', 1);
INSERT INTO `dragon-course`.course_category (id, name, parentid, orderby, description, is_leaf) VALUES (1782022627020877825, '互联网产品', 1773616090480902145, 4, '产品经理、游戏策划', 1);
INSERT INTO `dragon-course`.course_category (id, name, parentid, orderby, description, is_leaf) VALUES (1782022728447537154, '后台开发', 1773616090480902145, 5, 'C/C++、Go、C#/.Net、PHP、其他', 1);
INSERT INTO `dragon-course`.course_category (id, name, parentid, orderby, description, is_leaf) VALUES (1782023243008946178, '软件测试', 1773616090480902145, 10, '功能测试、自动化测试、性能测试、安全测试、接口测试、测试开发、其他', 1);
INSERT INTO `dragon-course`.course_category (id, name, parentid, orderby, description, is_leaf) VALUES (1782023342904684546, '运维', 1773616090480902145, 11, 'Linux运维、自动化运维、DevOps、K8S、安全、其他', 1);
INSERT INTO `dragon-course`.course_category (id, name, parentid, orderby, description, is_leaf) VALUES (1782023419496869890, '游戏开发', 1773616090480902145, 12, 'Unity、Cocos、服务器开发、H5游戏、UE4、其他', 1);
INSERT INTO `dragon-course`.course_category (id, name, parentid, orderby, description, is_leaf) VALUES (1782023521724641282, '认证考试', 1773616090480902145, 13, '华为认证、思科认证、软考、腾讯云认证、计算机等级考试、PMP、微软认证、其他', 1);
INSERT INTO `dragon-course`.course_category (id, name, parentid, orderby, description, is_leaf) VALUES (1782023600539807745, '计算机通识课', 1773616090480902145, 14, '计算机网络、操作系统、数据库原理、其他', 1);
INSERT INTO `dragon-course`.course_category (id, name, parentid, orderby, description, is_leaf) VALUES (1782023657641062401, '硬件开发', 1773616090480902145, 15, '嵌入式开发、IC设计、IC验证、其他', 1);
INSERT INTO `dragon-course`.course_category (id, name, parentid, orderby, description, is_leaf) VALUES (1782024070855503873, '绘画创作', 1773720529564868609, 3, '插画、漫画绘本、国画油画、水彩水粉画、素描、彩铅手绘、设计软件、其他', 1);
INSERT INTO `dragon-course`.course_category (id, name, parentid, orderby, description, is_leaf) VALUES (1782024180792406018, '工业产品设计', 1773720529564868609, 4, '模具设计、机械设计、产品设计、智能制造、包装设计、设计软件、其他', 1);
INSERT INTO `dragon-course`.course_category (id, name, parentid, orderby, description, is_leaf) VALUES (1782024269158002689, 'UI/UX', 1773720529564868609, 5, '用户体验设计、交互设计、游戏UI设计、设计软件、其他', 1);
INSERT INTO `dragon-course`.course_category (id, name, parentid, orderby, description, is_leaf) VALUES (1782024450792337410, '影视设计', 1773720529564868609, 7, '动画设计、短视频制作、包装与剪辑、特效与后期、设计软件、其他', 1);
INSERT INTO `dragon-course`.course_category (id, name, parentid, orderby, description, is_leaf) VALUES (1782024526495330305, '服装设计', 1773720529564868609, 8, '服装设计、服装打版、配饰设计、设计软件、其他', 1);
INSERT INTO `dragon-course`.course_category (id, name, parentid, orderby, description, is_leaf) VALUES (1782024603309813761, '摄影', 1773720529564868609, 9, '专业摄影、手机摄影、摄影后期、其他', 1);
INSERT INTO `dragon-course`.course_category (id, name, parentid, orderby, description, is_leaf) VALUES (1782024659748368385, '其他', 1773720529564868609, 10, '其他', 1);
INSERT INTO `dragon-course`.course_category (id, name, parentid, orderby, description, is_leaf) VALUES (1782025074875412481, '公考教资', 1781944508184281090, 3, '公务员、教资教招、事业单位、三支一扶、社区工作者、公安招、警军队文职、金融银行、其他招考', 1);
INSERT INTO `dragon-course`.course_category (id, name, parentid, orderby, description, is_leaf) VALUES (1782025150041534466, '法学院', 1781944508184281090, 4, '法律硕士、法律职业资格、法律实务、其他', 1);
INSERT INTO `dragon-course`.course_category (id, name, parentid, orderby, description, is_leaf) VALUES (1782025258627870721, '财会金融', 1781944508184281090, 5, '会计职称、注册会计师、国际证书、金融类从业、实务就业、其他财经类考试', 1);
INSERT INTO `dragon-course`.course_category (id, name, parentid, orderby, description, is_leaf) VALUES (1782025417671684098, '医疗卫生', 1781944508184281090, 6, '执业药师、执业医师、执业护士、健康管理师、中医技能、卫生资格、医学升学、执业兽医、妇幼课堂、其他', 1);
INSERT INTO `dragon-course`.course_category (id, name, parentid, orderby, description, is_leaf) VALUES (1782025527914770433, '建造工程', 1781944508184281090, 7, '一级建造师、二级建造师、消防工程师、工程造价（实操）、工程施工（技能）、注册工程师、其他', 1);
INSERT INTO `dragon-course`.course_category (id, name, parentid, orderby, description, is_leaf) VALUES (1782025801563746306, '职业素养', 1781266294801707010, 2, '人际沟通、领导力', 1);
INSERT INTO `dragon-course`.course_category (id, name, parentid, orderby, description, is_leaf) VALUES (1782025979054108674, '农业生产', 1781266294801707010, 5, '种植、畜牧养殖', 1);
INSERT INTO `dragon-course`.course_category (id, name, parentid, orderby, description, is_leaf) VALUES (1782026422069080066, '音乐乐器', 1781944625708679169, 4, '音乐基础、唱歌发声、乐器演奏、音乐制作、其他', 1);
INSERT INTO `dragon-course`.course_category (id, name, parentid, orderby, description, is_leaf) VALUES (1782026503128199170, '运动健康', 1781944625708679169, 5, '减肥健身、球类运动、舞蹈健美、瑜伽、武术格斗、其他', 1);
INSERT INTO `dragon-course`.course_category (id, name, parentid, orderby, description, is_leaf) VALUES (1782026559491256321, '人文社科', 1781944625708679169, 6, '文学、其他', 1);
INSERT INTO `dragon-course`.course_category (id, name, parentid, orderby, description, is_leaf) VALUES (1782026951457353730, '韩语', 1773720814374887426, 4, '韩语入门、韩语能力考、实用韩语', 1);
INSERT INTO `dragon-course`.course_category (id, name, parentid, orderby, description, is_leaf) VALUES (1782027024593432578, '小语种', 1773720814374887426, 5, '法语、德语、西班牙语、多语种、粤语', 1);


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

