create table member(
	id varchar2(30) primary key not null,
	password varchar2(30) not null,
	name varchar2(30) not null,
	nickname varchar2(20),
	tel varchar2(20),
	attachId number(10),
	age number(3),
	address varchar2(40),
	gender char(1),
	birthday varchar2(10),
	interest varchar2(20),
	category varchar2(10),
	grade number(1),
	createdAt timestamp(6) default sysdate not null,
	modifiedAt timestamp(6) default sysdate not null
);

drop table member;

select * from member;

delete from member;
delete from member where id='mhbae@gmail.com';

alter table member add (category varchar2(10));
alter table member add (grade number(1));
alter table member add (createdAt date not null);
alter table member add (modifiedAt date);

desc member;

update member set category='admin' where id='ksseo63@naver.com';
update member set category='customer' where id='kdhong@gmail.com';
update member set category='business' where id='doradora@gmail.com';
update member set category='customer' where id='mhbae@gmail.com';
update member set nickname='관리자' where id='ksseo63@naver.com';

insert into member values ('mhbae@gmail.com', '!qoangh0', '배무호', '거북', '010-3333-4444', 1, 34, '강원도', 'M', '2013-07-04', '정치', 'customer', 4, '2015-09-16', '2015-09-16');
insert into member values ('kdhong@gmail.com', '!rlfehd0', '홍길동', '바람잡이', '010-3333-5555', 1, 29, '경기도', 'M', '2014-04-14', '싸움', 'customer', 4, '2015-09-16', '2015-09-16');
insert into member values ('doradora@gmail.com', '!ehfkehfk0', '도라도라', '방구쟁이', '010-3333-6666', 1, 37, '서울시', 'M', '2015-07-24', '꿈꾸기', 'business', 4, '2015-09-16', '2015-09-16');
insert into member values ('ksseo63@naver.com', '!rudtjq0', '서경섭', '관리자', '010-3333-7777', 1, 44, '서울시', 'M', '2014-04-06', '자바', 'admin', 4, '2015-09-16', '2015-09-16');

create table manager(
	id varchar2(30) primary key not null,
	password varchar2(30) not null,
	role varchar2(30)
);

select * from manager;

/* for user_grade primary key generation */
create sequence grade_sequence
	increment by 1
	start with 1
	nomaxvalue
	nocycle
	cache 10;

/* seq : sequences 사용
 * category : 관리자/상인/일반/손님
 * level : 1(VIP), 2(Gold), 3(Silver), 4(Bronze)
 * 첨부 파일 수 : 0, 1, 3, 5
 * 첨부 파일 용량 : 0, 1(50KB), 2(200KB), 3(1MB), 4(10MB), 5(100MB)
 *  */
create table user_grade(
	id number(3) primary key not null,
	category varchar2(10) not null,
	grade number(1),
	read char(1),
	write char(1),
	reply char(1),
	liking char(1),
	download char(1),
	upload char(1),
	aNum number(1),
	aSize varchar2(5)
);

drop sequence grade_sequence;

drop table user_grade;
	
select * from user_sequences where sequence_name=upper('grade_sequence');

select * from user_grade;

create sequence board_sequence
	increment by 1
	start with 1
	maxvalue 100
	nocycle
	cache 10;

/* board type
 *   기본 : 제목, 작성자, 비밀번호, 내용, 첨부 1
 *   갤러리 : 이미지 첨부만 가능, 첨부를 thumbnail로 처리
 *   자료실 : 첨부 5개까지 가능
 *   Q&A : 비밀글 표시 및 비밀번호 검사 후 내용 표시
 *   공지사항 : 글쓰기 버튼 비활성화, 관리자만 쓰기 가능
 * secret : 비밀글 표시 사용 여부
 * nAttach : 첨부 파일 수
 * aSize : 첨부파일 하나의 크기 제약
 * tSize : 첨부파일 전체 크기의 제약
 */
create table board (
	board_id number(2) not null primary key,
	board_name varchar2(40) not null,
	board_type varchar2(20) not null,
	url varchar2(100),
	secret char(1) default 'F',
	read_allow varchar2(10),
	write_allow varchar2(10),
	reply varchar2(10),
	modify varchar2(10),
	remove varchar2(10),
	download varchar2(10),
	upload varchar2(10),
	nAttach number(1),
	aSize varchar2(5),
	tSize varchar2(5),
	creating_date date not null,
	board_desc varchar2(100)
);

drop sequence board_sequence;

drop table board;

select board_sequence.currval from board;

select * from board;

create sequence article_sequence
	increment by 1
	start with 1
	nomaxvalue
	nocycle
	cache 10;

/* 게시글
 * secret : 비밀글 표시 사용 여부
 * 
 */
create table article (
	article_id number(12) not null primary key,
	boardId number(2) default 0,
	writer_name varchar2(20) not null,
	email varchar2(30) not null,
	title varchar2(100) not null,
	password varchar2(10) not null,
	read_count number(12) default 0 not null,
	comment_count number(4) default 0 not null,
	like_count number(4) default 0 not null,
	ref number(10) default 0 not null,
	step number(3) default 0 not null,
	depth number(3) default 0 not null,
	createdAt timestamp(6) default sysdate not null,
	modifiedAt timestamp(6) default sysdate not null,
	content varchar2(4000) not null,
	ip varchar2(20) not null,
	secret char(1) default 'F'
);

drop sequence article_sequence;

drop table article;

select * from article;

delete from article where article_id = 1;

desc article;

create sequence pds_sequence
	increment by 1
	start with 1
	nomaxvalue
	nocycle
	cache 10;
	
create table pds_item (
	pds_item_id number(12) not null,
	filename varchar2(200) not null,
	realpath varchar2(200) not null,
	filesize number(10),
	downloadcount number(5) default 0,
	description varchar2(255),
	articleId number(12),
	primary key (pds_item_id)
);

select * from pds_item;

drop sequence pds_sequence;

drop table pds_item;

delete from pds_item where pds_item_id = 22;

create sequence message_sequence
	increment by 1
	start with 1
	nomaxvalue
	nocycle
	cache 10;
	
create table guestbook_message (
	message_id number(12) primary key not null,
	guest_name varchar2(30) not null,
	password varchar2(20) not null,
	creating_date timestamp(6) default sysdate not null,
	message varchar2(4000) not null
);

select * from guestbook_message;

select message_sequence.currval from guestbook_message;

drop table guestbook_message;
drop sequence message_sequence;

create sequence qna_sequence
	increment by 1
	start with 1
	nomaxvalue
	nocycle
	nocache;
	
create table qna_msg (
	article_id number(12) not null primary key,
	boardId number(2) default 0,
	writer_name varchar2(20) not null,
	email varchar2(30) not null,
	title varchar2(100) not null,
	password varchar2(10) not null,
	read_count number(5) default 0 not null,
	comment_count number(4) default 0,
	like_count number(4) default 0,
	ref number(10) default 0 not null,
	step number(3) default 0 not null,
	depth number(3) default 0 not null,
	regDate timestamp(6) default sysdate not null,
	content varchar2(4000) not null,
	ip varchar2(20) not null,
	secret char(1) default 'F'
);

select * from qna_msg;

drop table qna_msg;
drop sequence qna_sequence;