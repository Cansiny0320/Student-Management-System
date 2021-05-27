create table user(					#用户表
username varchar(30) primary key,		#用户名				
password varchar(30) not null,			#密码
isLogin bit not null DEFAULT 0 	#是否重复登陆
);

create table college( #所属院系表
college_id varchar(30) primary key,	#院系编号
college_name varchar(50) unique not null 	#院系名
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
course_name varchar(50) primary key,			#课程名称
college_id varchar(30) not null						#所属学院编号
);

create table score( 									  #成绩表
student_id varchar(30) not null,						  #学生ID
student_name varchar(50) not null,		#学生姓名
course_name varchar(50) not null,							#课程名称
primary key(course_name,student_id),		  #学生ID和课程名称共同组成主键
foreign key(student_id) references student(student_id), #学生ID设置为学生表的外键
foreign key(course_name) references course(course_name),
score dec(4,1) default null  check(score between 0.0 and 100.0) #成绩在0到100之间
);

/*插入学院*/
insert into college values('210','通信与信息工程学院');
insert into college values('211','计算机科学与技术学院');
insert into college values('216','体育学院');
insert into college values('217','理学院');
insert into college values('218','外国语学院');


/*添加课程*/
insert into course values ('高等数学','217');
insert into course values ('线性代数','217');
insert into course values ('计算机组成原理','211');
insert into course values ('面向对象程序设计','211');
insert into course values ('大学英语','218');
insert into course values ('大学体育','216');
insert into course values ('计算机网络','210');
insert into course values ('数字电路','210');
insert into course values ('日语','218');
insert into course values ('游泳课','216');

