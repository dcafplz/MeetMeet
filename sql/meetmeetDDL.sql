CREATE DATABASE 데이터베이스이름;

use playdata;

CREATE TABLE account(
	account_id varchar(20) PRIMARY KEY,
	pw varchar(100) NOT NULL,
	hash_salt varchar(10) NOT NULL,
	pw_question varchar(20) NOT NULL,
	nick_name varchar(20) NOT NULL
);

CREATE TABLE place(
	place_id int AUTO_INCREMENT PRIMARY KEY,
	account_id varchar(20) NOT NULL,
	place_name varchar(20) NOT NULL,
	lat double NOT NULL,
	lng double NOT NULL
);

CREATE TABLE hot_place(
	hot_place_id int AUTO_INCREMENT PRIMARY KEY,
	place_name varchar(20) NOT NULL,
	lat double  NOT NULL,
	lng double  NOT NULL
);

CREATE TABLE friend_list(
	id int primary key,
	id1 varchar(20) NOT NULL,
	id2 varchar(20) NOT null
);

CREATE TABLE friend_request(
	id int primary key,
	request_id varchar(20) NOT NULL,
	requested_id varchar(20) NOT null
);

CREATE TABLE preference(
	preference_id int AUTO_INCREMENT PRIMARY KEY,
	account_id varchar(20) NOT NULL,
	category varchar(20) NOT NULL
);

CREATE TABLE meeting(
	meeting_id bigint AUTO_INCREMENT PRIMARY KEY,
	category varchar(255) NOT NULL,
    filename varchar(255) NOT NULL,
    filepath varchar(255) NOT NULL,
	max_participant int NOT NULL,
	meeting_detail varchar(50) NOT NULL,
	master_id varchar(20),
	meeting_name varchar(20) NOT NULL,
	meeting_place varchar(20) NOT NULL,
	meeting_place_lat varchar(255) NOT NULL,
	meeting_place_lng varchar(255) NOT NULL,
	meeting_start_date date NOT NULL,
	meeting_end_date date NOT NULL
);

CREATE TABLE meeting_participant(
	meeting_participant_id int AUTO_INCREMENT PRIMARY KEY,
	meeting_id bigint NOT NULL,
	participant_id varchar(20) NOT NULL
);
	
ALTER TABLE place ADD FOREIGN KEY (account_id) REFERENCES account  (account_id) on delete cascade;
ALTER TABLE friend_list ADD FOREIGN KEY (id1) REFERENCES account  (account_id) on delete cascade;
ALTER TABLE friend_list ADD FOREIGN KEY (id2) REFERENCES account  (account_id) on delete cascade;
alter table friend_list add constraint ck_friendList check (id1 != id2);
ALTER TABLE friend_request ADD FOREIGN KEY (request_id) REFERENCES account  (account_id) on delete cascade;
ALTER TABLE friend_request ADD FOREIGN KEY (requested_id) REFERENCES account  (account_id) on delete cascade;
ALTER TABLE preference ADD FOREIGN KEY (account_id) REFERENCES account  (account_id) on delete cascade;
ALTER TABLE meeting ADD FOREIGN KEY (master_id) REFERENCES account  (account_id) on delete cascade;
ALTER TABLE meeting_participant ADD FOREIGN KEY (participant_id) REFERENCES account  (account_id) on delete cascade;
ALTER TABLE meeting_participant ADD FOREIGN KEY (meeting_id) REFERENCES meeting  (meeting_id) on delete cascade;

drop table if exists meeting_participant; 
drop table if exists meeting; 
drop table if exists preference; 
drop table if exists friend_request; 
drop table if exists friend_list; 
drop table if exists hot_place; 
drop table if exists place; 
drop table if exists account; 