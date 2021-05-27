create table user(					#用户表
username varchar(30) primary key,		#用户名				
password varchar(30) not null,			#密码
isLogin bit not null DEFAULT 0 	#是否重复登陆
);

create table college( #所属院系表
college_id varchar(30) primary key,	#院系编号
college_name varchar(50) unique not null 	#院系名
);

create table major(  #专业表
major_id varchar(30) primary key,	#专业编号
major_name varchar(50) unique not null,	#专业名称
college_id varchar(30) not null,	#所属院系ID
foreign key(college_id) references college(college_id)	#所属院系设置为院系表的外键
);


create table student(		#学生表
student_id varchar(30) primary key,	#学生学号
student_name varchar(50) not null,		#学生姓名
college_id varchar(30) not null,	#所属院系ID
college_name varchar(50)  not null,	#院系名称
foreign key(college_id) references college(college_id),	#所属院系编号设置为外键
foreign key(college_name) references college(college_name)	#所属院系名称设置为外键
);

create table course(								#课程表
course_id varchar(30) primary key,	#课程编号
course_name varchar(50) not null,			#课程名称
college_id varchar(30) not null						#所属学院编号
);

create table score( 									  #成绩表
student_id varchar(30) not null,						  #学生ID
student_name varchar(50) not null,		#学生姓名
course_id varchar(30) not null,							#课程名称
primary key(course_id,student_id),		  #学生ID和课程名称共同组成主键
foreign key(student_id) references student(student_id), #学生ID设置为学生表的外键
foreign key(course_id) references course(course_id),
score dec(4,1) default null  check(score between 0.0 and 100.0) #成绩在0到100之间
);

/*插入学院*/
insert into college values('210','通信与信息工程学院');
insert into college values('211','计算机科学与技术学院');
insert into college values('216','体育学院');
insert into college values('217','理学院');
insert into college values('218','外国语学院');

/*插入专业*/
insert into major values('01','通信工程','210');
insert into major values('02','电子信息工程','210');
insert into major values('03','计算机科学与技术','211');
insert into major values('04','智能科学与技术','211');
insert into major values('11','社会体育','216');
insert into major values('12','信息与计算科学','217');
insert into major values('13','数学与应用数学','217');
insert into major values('14','英语','218');


/*添加课程*/
insert into course values ('01','高等数学','217');
insert into course values ('02','线性代数','217');
insert into course values ('04','计算机组成原理','211');
insert into course values ('05','面向对象程序设计','211');
insert into course values ('06','大学英语','218');
insert into course values ('07','大学体育','216');
insert into course values ('09','计算机网络','210');
insert into course values ('12','数字电路','210');
insert into course values ('13','日语','218');
insert into course values ('14','游泳课','216');

