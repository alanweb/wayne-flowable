drop table  if exists sys_position;
create table sys_position(
    id bigint(20)  not null auto_increment primary key ,
    employee_id bigint(20) not null ,
    salary float ,
    rank integer ,
    leader_id bigint not null
)engine = innodb ;

insert into sys_position(employee_id,salary,`rank`,leader_id) values (1,15000,16,1);