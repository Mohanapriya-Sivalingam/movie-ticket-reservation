create database movieticket;

use movieticket;

create table movie_table(movie_id int primary key auto_increment,movie_name varchar(60),movie_language  varchar(50));

create table admin_table(id int primary key auto_increment, admin_name varchar(60), admin_password varchar(50));

insert into admin_table (admin_name, admin_password)values("mohanapriya", "admin123"); //default admin

create table user_table(id int primary key auto_increment, user_name varchar(60), user_password varchar(50));

create table show_table(showid int, movie_id int, 
show_time varchar(50), 
show_date date, price int,
 foreign key (movie_id) references movie_table (movie_id));
 
 create table seat_table (seat_id int primary key auto_increment, total int, available int,
 booked int, show_id int, 
 foreign key (show_id) references show_table (show_id));
 
 create table booking_table (booking_id int auto_increment primary key,
 show_id int,
 seat_id int,
 cus_name varchar(50),
 foreign key (show_id) references show_table (show_id));
 
 