create table `dragon-learn`.class_schedule
(
    id            bigint        not null
        primary key,
    user_id       bigint        not null comment '用户id',
    course_id     bigint        not null comment '课程id',
    course_name   varchar(100)  not null,
    learning_time int default 0 not null comment '学习时长（分钟）'
)
    comment '课程表';

create index class_schedule_user_id_index
    on `dragon-learn`.class_schedule (user_id);

