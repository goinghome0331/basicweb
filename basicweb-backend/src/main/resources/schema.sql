
create table users (
	id int auto_increment,
	username varchar(30) not null,
	password varchar(60) not null,
	first_name varchar(30) not null,
	last_name varchar(30) not null,	
	birth datetime not null,
	gender char(1) not null,
	image_path varchar(100),
	constraint users_pk primary key(id),
	constraint users_uk unique(username)
);

create table roles (
	id int auto_increment,
	name varchar(30) not null,
	constraint roles_pk primary key(id),
	constraint roles_uk unique(name)
);

create table user_role (
	id int auto_increment,
	user_id int not null,
	role_id int not null,
	constraint user_role_pk primary key(id),
	constraint user_role_fk1 foreign key(user_id) references users(id),
	constraint user_role_fk2 foreign key(role_id) references roles(id)
);

create table posts (
	id int auto_increment,
	title varchar(50) not null,
	content tinytext not null,
	hit int not null default 0,
	reg_date datetime not null,
	user_id int not null,
	constraint posts_pk primary key(id),
	constraint posts_fk foreign key(user_id) references users(id)
);

create table comments (
	id int auto_increment,
	content tinytext not null,
	reg_date datetime not null,
	user_id int not null,
	post_id int not null,
	constraint comments_pk primary key(id),
	constraint comments_fk1 foreign key(user_id) references users(id),
	constraint comments_fk2 foreign key(post_id) references posts(id)
);