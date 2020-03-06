create table movie.sys_user
(
    user_id      bigint auto_increment
        primary key,
    username     varchar(50)                        not null comment '用户名',
    password     varchar(50)                        not null comment '密码',
    device_id    varchar(50)                        null comment '设备ID',
    create_id    bigint                             null comment '创建人id',
    channel_name varchar(100)                       null comment '渠道商名称',
    status       tinyint                            null comment '状态  0：禁用   1：正常',
    level        tinyint                            null comment '用户等级-0级管理员、1级代理商、2级经销商、3级用户',
    up_level     tinyint                            null comment '上级',
    create_time  datetime default CURRENT_TIMESTAMP null comment '创建时间',
    expired_time datetime                           null comment '过期时间',
    constraint username
        unique (username)
)
    comment '系统用户' charset = utf8;

