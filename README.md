# 数据库表

```sql
create table doctor_sign
(
    username varchar(20) not null
        primary key,
    password varchar(20) not null
);

create table time
(
    doctor varchar(20) not null,
    time   varchar(20) null,
    state  varchar(10) null
);

create table users_sign
(
    username varchar(20) not null
        primary key,
    password varchar(20) not null
);

create table reserve
(
    num      int auto_increment
        primary key,
    username varchar(20)                  not null,
    name     varchar(20)                  not null,
    sex      varchar(5)                   not null,
    phone    varchar(20)                  not null,
    time     varchar(20)                  not null,
    doctor   varchar(10)                  not null,
    state    varchar(20) default '待诊断' null,
    result   varchar(20)                  null,
    constraint 外键_name
        foreign key (username) references users_sign (username)
);
```

```sql
INSERT INTO doctor_sign (username, password) VALUES ('123456', '123456');
INSERT INTO doctor_sign (username, password) VALUES ('孙医生', '123456sun');
INSERT INTO doctor_sign (username, password) VALUES ('李医生', '123456li');
INSERT INTO doctor_sign (username, password) VALUES ('王医生', '123456wang');
INSERT INTO doctor_sign (username, password) VALUES ('赵医生', '123456zhao');
```

```sql
INSERT INTO time (doctor, time, state) VALUES ('赵医生', '8:00-10:00', '已预约');
INSERT INTO time (doctor, time, state) VALUES ('赵医生', '10:00-12:00', '已预约');
INSERT INTO time (doctor, time, state) VALUES ('赵医生', '14:00-16:00', '已预约');
INSERT INTO time (doctor, time, state) VALUES ('赵医生', '16:00-18:00', '未预约');
INSERT INTO time (doctor, time, state) VALUES ('王医生', '8:00-10:00', '已预约');
INSERT INTO time (doctor, time, state) VALUES ('王医生', '10:00-12:00', '未预约');
INSERT INTO time (doctor, time, state) VALUES ('王医生', '14:00-16:00', '已预约');
INSERT INTO time (doctor, time, state) VALUES ('王医生', '16:00-18:00', '未预约');
INSERT INTO time (doctor, time, state) VALUES ('李医生', '8:00-10:00', '已预约');
INSERT INTO time (doctor, time, state) VALUES ('李医生', '10:00-12:00', '未预约');
```

```sql
INSERT INTO users_sign (username, password) VALUES ('1', '1');
INSERT INTO users_sign (username, password) VALUES ('12', '12');
INSERT INTO users_sign (username, password) VALUES ('123', '123');
INSERT INTO users_sign (username, password) VALUES ('1234', '1234');
INSERT INTO users_sign (username, password) VALUES ('12345', '12345');
INSERT INTO users_sign (username, password) VALUES ('123456', '123456');
INSERT INTO users_sign (username, password) VALUES ('2', '2');
INSERT INTO users_sign (username, password) VALUES ('3', '3');
INSERT INTO users_sign (username, password) VALUES ('4', '4');
INSERT INTO users_sign (username, password) VALUES ('5', '5');
```

```sql
INSERT INTO reserve (num, username, name, sex, phone, time, doctor, state, result) VALUES (1, '2', '李四', '男', '18658474785', '8:00-10:00', '李医生', '已诊断', '正常');
INSERT INTO reserve (num, username, name, sex, phone, time, doctor, state, result) VALUES (2, '4', '小明', '男', '14596571254', '14:00-16:00', '王医生', '待诊断', null);
INSERT INTO reserve (num, username, name, sex, phone, time, doctor, state, result) VALUES (3, '3', '小红', '女', '17484526578', '10:00-12:00', '赵医生', '已诊断', '轻度焦虑症');
INSERT INTO reserve (num, username, name, sex, phone, time, doctor, state, result) VALUES (8, '1', '张三', '男', '17548540159', '8:00-10:00', '孙医生', '待诊断', null);
INSERT INTO reserve (num, username, name, sex, phone, time, doctor, state, result) VALUES (9, '12', '钱五', '男', '14795447458', '14:00-16:00', '赵医生', '已诊断', '重度抑郁症');
INSERT INTO reserve (num, username, name, sex, phone, time, doctor, state, result) VALUES (10, '123', '小芳', '女', '18654795482', '8:00-10:00', '王医生', '待诊断', null);
INSERT INTO reserve (num, username, name, sex, phone, time, doctor, state, result) VALUES (11, '5', '王晶晶', '女', '14854269547', '8:00-10:00', '赵医生', '待诊断', null);
INSERT INTO reserve (num, username, name, sex, phone, time, doctor, state, result) VALUES (12, '1234', '张杰', '男', '19658742365', '16:00-18:00', '孙医生', '待诊断', null);
INSERT INTO reserve (num, username, name, sex, phone, time, doctor, state, result) VALUES (13, '6', '孙丽', '女', '17584865488', '14:00-16:00', '李医生', '待诊断', null);
```
