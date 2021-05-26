drop table  if exists sys_employee;
create table sys_employee(
    id bigint(20)  not null auto_increment primary key ,
    name varchar(50) not null ,
    email varchar(50) ,
    phone varchar(13) ,
    sex char(1) default 0 ,
    birthday date,
    entry_date date
)engine = innodb ;

insert into sys_employee values (1,'admin','','',1,'2020-01-12','2021-05-26');