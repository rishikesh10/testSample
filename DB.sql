create schema employeetest;

use employeetest;

create table dept (
dept_id int(6),
dept_name varchar(50),
constraint Dept_id_pk primary key(Dept_id)
);


create table emp (
emp_id int(6),
emp_name varchar(50),
dept_id int(6), 
constraint emp_id_pk primary key(emp_id),
constraint emp_Dept_fk foreign key(dept_id) REFERENCES dept(dept_id)
);

insert into employeetest.dept (dept_id, dept_name) values (1, "HR");

insert into employeetest.emp (emp_id, emp_name, dept_id) values (1,"Rishi",1);