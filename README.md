# 心理咨询预约系统

## 一、所需环境配置

1.下载IDEA

2、下载MySQL[mysql8.0.25安装配置教程(windows 64位)最详细_聚精会神搞学习的博客-CSDN博客_mysql安装教程8.0.25](https://blog.csdn.net/weixin_43579015/article/details/117228159?ops_request_misc=%7B%22request%5Fid%22%3A%22166286237716782395352317%22%2C%22scm%22%3A%2220140713.130102334..%22%7D&request_id=166286237716782395352317&biz_id=0&utm_medium=distribute.pc_search_result.none-task-blog-2~all~top_positive~default-2-117228159-null-null.142^v47^pc_rank_34_ecpm25,201^v3^add_ask&utm_term=mysql安装配置教程&spm=1018.2226.3001.4187)

3、下载jdbc[ IDEA使用JDBC连接MySQL数据库详细教程_末尾带空格的bearBaby的博客-CSDN博客](https://blog.csdn.net/qq_36172505/article/details/84102468?ops_request_misc=%7B%22request%5Fid%22%3A%22166282023616782248567094%22%2C%22scm%22%3A%2220140713.130102334..%22%7D&request_id=166282023616782248567094&biz_id=0&utm_medium=distribute.pc_search_result.none-task-blog-2~all~top_positive~default-2-84102468-null-null.142^v47^pc_rank_34_ecpm25,201^v3^add_ask&utm_term=idea连接mysql数据库&spm=1018.2226.3001.4187)

4、idea连接数据库[idea：使用idea连接mysql数据库_m0_67401920的博客-CSDN博客_用idea连接mysql](https://blog.csdn.net/m0_67401920/article/details/126384835?ops_request_misc=&request_id=&biz_id=102&utm_term=idea连接mysql数据库&utm_medium=distribute.pc_search_result.none-task-blog-2~all~sobaiduweb~default-0-126384835.142^v47^pc_rank_34_ecpm25,201^v3^add_ask&spm=1018.2226.3001.4187)

5、这个是项目里DbProcess2的代码，注意注释，改成自己的

```Java
public class DbProcess2 {
    public Connection con = null;public Statement sta=null;
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    //注意！：下行3306后面的q1为我建立的数据库名，要改成自己建立的数据库名！！！
    static final String DB_URL = "jdbc:mysql://localhost:3306/xl?&useSSL=false&serverTimezone=UTC";
    //数据库账号密码，也要改为自己的
    static final String USER = "root";
    static final String PASS = "201749zao";
```

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
