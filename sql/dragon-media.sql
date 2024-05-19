create table `dragon-media`.media_files
(
    id            varchar(64)       not null comment '文件id;如果是视频，则是md5值;如果是图片，则是md5+companyId'
        primary key,
    company_id    bigint            not null comment '机构ID',
    original_name varchar(255)      not null comment '原名,只真正的原来的，文件名',
    file_suffix   varchar(16)       not null comment '文件后缀',
    size          bigint            null comment '文件大小单位byte',
    path          varchar(255)      not null comment '在minio中的完整路径',
    create_time   datetime          null comment '上传时间',
    remark        varchar(32)       null comment '备注',
    audit_status  tinyint default 1 not null comment '审核状态',
    audit_mind    varchar(255)      null comment '审核意见'
);

create index media_files_company_id_index
    on `dragon-media`.media_files (company_id);

create index media_files_file_suffix_index
    on `dragon-media`.media_files (file_suffix);

