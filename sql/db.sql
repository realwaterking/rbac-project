create database if not exists rbac_project;

use rbac_project;

create table if not exists user(
    id BIGINT not null auto_increment comment '用户主键' primary key ,
    username varchar(256) not null comment '用户名称',
    userAccount varchar(256) not null comment '用户账号',
    userPassword varchar(256) not null comment '加密密码',
    phoneNumber varchar(128) null comment '用户手机号码',
    location varchar(256) not null comment '用户地址(社区)',
    accessKey varchar(512) not null comment 'accessKey',
    secretKey varchar(512) not null comment 'secretKey',
    createTime DATETIME DEFAULT current_timestamp not null comment '创建时间',
    updateTime DATETIME DEFAULT current_timestamp on update current_timestamp not null comment '更新时间',
    isDelete TINYINT DEFAULT 0 not null comment '是否删除（0-未删除， 1-已删除）'
)comment '用户信息表';

create table if not exists role(
    id BIGINT not null auto_increment comment '角色主键' primary key ,
    roleName varchar(256) not null comment '角色名称',
    roleDesc varchar(256) comment '角色描述',
    createTime DATETIME default current_timestamp not null comment '创建时间',
    updateTime DATETIME default current_timestamp on update current_timestamp not null comment '更新时间',
    isDelete TINYINT default 0 not null comment '是否删除（0-未删除， 1-已删除）'
)comment '角色信息表';

create table if not exists permission(
    id BIGINT not null auto_increment comment '权限主键' primary key ,
    permissionName varchar(256) not null comment '权限名称',
    permissionDesc varchar(256) comment '权限描述',
    createTime DATETIME default current_timestamp not null comment '创建时间',
    updateTime DATETIME default current_timestamp on update current_timestamp not null comment '更新时间',
    isDelete TINYINT default 0 not null comment '是否删除（0-未删除， 1-已删除）'
)comment '权限信息表';

create table if not exists user_role(
    id bigint not null auto_increment comment '用户角色关系表主键' primary key,
    userId bigint not null comment '用户id',
    roleId bigint not null comment '角色id',
    foreign key (userId) references user(id),
    foreign key (roleId) references role(id),
    createTime DATETIME default current_timestamp not null comment '创建时间',
    updateTime DATETIME default current_timestamp on update current_timestamp not null comment '更新时间',
    isDelete TINYINT default 0 not null comment '是否删除（0-未删除， 1-已删除）'
)comment '用户角色关系表';

create table if not exists role_permission(
    id bigint not null auto_increment comment '角色权限关系表主键' primary key ,
    roleId bigint not null comment '角色id',
    permissionId bigint not null comment '权限id',
    foreign key (roleId) references role(id),
    foreign key (permissionId) references permission(id),
    createTime DATETIME default current_timestamp not null comment '创建时间',
    updateTime DATETIME default current_timestamp on update current_timestamp not null comment '更新时间',
    isDelete TINYINT default 0 not null comment '是否删除（0-未删除， 1-已删除）'
)comment '角色权限关系表';

create table if not exists fire_facility(
    id bigint not null auto_increment comment '消防设备主键' primary key ,
    facilityName varchar(256) not null comment '消防设备名称',
    location varchar(512) not null comment '消防设备所在地',
    type varchar(128) not null comment '消防设备类型',
    manufacturer varchar(256) comment '消防设备制造商',
    model varchar(256) comment '消防设备型号',
    capacity int comment '消防设备容量',
    installationDate datetime default current_timestamp not null comment '消防设备安装日期',
    lastInspectionDate DATETIME default current_timestamp on update current_timestamp not null comment '上次巡检日期',
    isActive boolean default true comment '消防设施启用状态',
    createTime DATETIME default current_timestamp not null comment '创建时间',
    updateTime DATETIME default current_timestamp on update current_timestamp not null comment '更新时间',
    isDelete TINYINT default 0 not null comment '是否删除（0-未删除， 1-已删除）'
)comment '消防设备信息表';

create table if not exists fire_data(
    id bigint not null auto_increment comment '报警数据表主键' primary key ,
    temperature float comment '环境温度',
    humidity float comment '湿度',
    smokeConcentration float comment '烟雾浓度',
    gasConcentration float comment '气体浓度',
    illumination int comment '光照强度',
    deviceStatus varchar(256) comment '设备状态',
    deviceId bigint not null comment '产生数据的设备id',
    batteryLever int comment '电池电量',
    connectionStatus int comment '连接状态',#(0-连接正常, 1-连接不稳定, 2-连接失败)
    alarmType varchar(256) comment '报警类型',
    alarmTime DATETIME default current_timestamp not null comment '报警时间',
    alarmLocation varchar(256) comment '报警位置',
    isDelete tinyint default 0 not null comment '逻辑删除',
    foreign key (deviceId) references fire_facility(id)
)comment '消防设备产生报警数据记录表';

create table if not exists fire_maintenance(
    id bigint not null auto_increment comment '日常维护记录表主键' primary key ,
    deviceId bigint not null comment '设备ID',
    maintenanceType varchar(256) comment '维护类型',
    maintenanceDesc varchar(512) comment '维护描述',
    maintenancePerson varchar(256) not null comment '维护人员',
    maintenanceResult varchar(128) comment '记录维护的结果 (正常, 异常, 修复完成)',
    maintenanceDate DATETIME default current_timestamp not null comment '维护日期',
    isDelete tinyint default 0 not null comment '逻辑删除',
    foreign key (deviceId) references fire_facility(id)
)comment '消防设备日常维护记录表';

create table if not exists dispatch_data(
    id bigint not null  auto_increment comment '出警信息表主键' primary key ,
    fireDataId bigint not null  comment '报警信息表主键',
    userId bigint not null  comment '出警人id',
    result varchar(256) not null comment '处理结果',
    createTime DATETIME default current_timestamp not null comment '出警时间',
    isDelete tinyint default 0 not null comment '逻辑删除',
    foreign key (fireDataId) references fire_data(id),
    foreign key (userId) references user(id)
)comment '出警记录表';
