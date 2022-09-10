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
