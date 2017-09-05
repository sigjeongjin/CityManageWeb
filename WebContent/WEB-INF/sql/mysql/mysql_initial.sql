/*
 * member : 사용자 정보 테이블
 *   id : 이메일 형식의 사용자 구별 ID
 *   password : 특수문자, 영뮨자, 숫자 조합으로 8자 이상
 *   name : 실명 (어떻게 실명 확인을 할 것인가?)
 *   nickname : 가명(별명, 애칭)
 *   tel : 연락할 수 있는 휴대폰 전화번호
 *   attachID : 프로필 사진을 포인팅하는 ID
 *   age : 나이
 *   address : 주소 (우편번호 연동 입력방법)
 *   gender : 성별 (남, 여)
 *   birthday : 생년월일 (저장형식 2016-06-08)
 *   interest : 취미, 관심분야
 *   category : 회원 분류 (고객: customer, 협력사: business, 관리자: admin)
 *   grade : 회원 등급 (관리자가 내부적으로 운영하면서 설정하는 값)
 */
create table member(
	id varchar(30) primary key not null,
	password varchar(30) not null,
	name varchar(50) not null,
	nickname varchar(30) not null,
	tel varchar(20),
	attachId int,
	age int,
	address varchar(40),
	gender char(1),
	birthday varchar(10),
	interest varchar(20),
	category varchar(10) default 'customer' not null,
	grade int,
	createdAt timestamp default current_timestamp,
	modifiedAt timestamp default current_timestamp on update current_timestamp
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

insert into member values ('mhbae@gmail.com', '!qoangh0', '배무호', '거북', '010-3333-4444', 1, 34, '강원도', 'M', '2013-07-04', '정치', 'customer', 4);
insert into member values ('kdhong@gmail.com', '!rlfehd0', '홍길동', '바람잡이', '010-3333-5555', 1, 29, '경기도', 'M', '2014-04-14', '싸움', 'customer', 4);
insert into member values ('doradora@gmail.com', '!ehfkehfk0', '도라도라', '방구쟁이', '010-3333-6666', 1, 37, '서울시', 'M', '2015-07-24', '꿈꾸기', 'business', 4);
insert into member values ('ksseo63@naver.com', '!rudtjq0', '서경섭', '관리자', '010-9530-3135', 1, 53, '서울시 강남구 삼성로 212 6-906 06284', 'M', '1963-07-11', '자바', 'admin', 4, '2016-11-20', '2016-11-20');

insert into member values ('mhbae@gmail.com', '!qoangh0', '배무호', '거북', '010-3333-4444', 1, 34, '강원도', 'M', '2013-07-04', '정치', 'customer', 4, '2015-09-16', '2015-09-16');
insert into member values ('kdhong@gmail.com', '!rlfehd0', '홍길동', '바람잡이', '010-3333-5555', 1, 29, '경기도', 'M', '2014-04-14', '싸움', 'customer', 4, '2015-09-16', '2015-09-16');
insert into member values ('doradora@gmail.com', '!ehfkehfk0', '도라도라', '방구쟁이', '010-3333-6666', 1, 37, '서울시', 'M', '2015-07-24', '꿈꾸기', 'business', 4, '2015-09-16', '2015-09-16');
insert into member values ('ksseo63@naver.com', '!rudtjq0', '서경섭', '관리자', '010-9530-3135', 1, 53, '서울시 강남구 삼성로 212 6-906 06284', 'M', '1963-07-11', '자바', 'admin', 4);

create table manager(
	id varchar(30) primary key not null,
	password varchar(30) not null,
	role varchar(30)
);

select * from manager;

/* seq : sequences 사용
 * category : 관리자/상인/일반/손님
 * level : 1(VIP), 2(Gold), 3(Silver), 4(Bronze)
 * 첨부 파일 수 : 0, 1, 3, 5
 * 첨부 파일 용량 : 0, 1(50KB), 2(200KB), 3(1MB), 4(10MB), 5(100MB)
 *  */
create table user_grade(
	id int primary key not null autoincrement,
	category varchar(10) not null,
	grade int not null,
	read char(1),
	write char(1),
	reply char(1),
	liking char(1),
	download char(1),
	upload char(1),
	aNum int,
	aSize varchar(5)
);

drop table user_grade;

select * from user_grade;

/* board type
 *   기본 : 제목, 작성자, 비밀번호, 내용, 첨부 1
 *   갤러리 : 이미지 첨부만 가능, 첨부를 thumbnail로 처리
 *   자료실 : 첨부 5개까지 가능
 *   Q&A : 비밀글 표시 및 비밀번호 검사 후 내용 표시
 *   공지사항 : 글쓰기 버튼 비활성화, 관리자만 쓰기 가능
 *   방명록 : 쓰기, 삭제, 목록보기 기능
 * url    : 링크를 누르면 이동할 페이지의 주소
 * secret : 비밀글 표시 사용 여부
 * read_allow  : 읽기 권한 (all, login, customer, business, admin)
 * write_allow : 쓰기 권한 (all, login, customer, business, admin)
 * reply  : 댓글 권한 (all, login, customer, business, admin)
 * modify : 수정 권한 (writer, admin)
 * remove : 삭제 권한 (writer, admin)
 * download : 첨부물 다운로드 권한 (all, login, customer, business, admin)
 * upload : 첨부물 업로드 권한 (all, login, customer, business, admin)
 * nAttach : 첨부 파일 수
 * aSize : 첨부파일 하나의 크기 제약
 */
create table board (
	board_id int not null primary key auto_increment,
	board_name varchar(40) not null,
	board_type varchar(20) not null,
	url varchar(100),
	secret char(1) default 'F',
	read_allow varchar(10),
	write_allow varchar(10),
	reply varchar(10),
	modify varchar(10),
	remove varchar(10),
	download varchar(10),
	upload varchar(10),
	nAttach int,
	aSize varchar(5),
	creating_date timestamp default current_timestamp,
	board_desc varchar(100)
);

drop table board;

select * from board;

update board set board_type='기본' where board_id='1';
update board set modify='admin' where board_id='4';
update board set modify='writer' where board_id='6';
update board set download='not', upload='not' where board_id='7';

insert into board (board_name, board_type, url, secret, read_allow, write_allow, reply, modify, remove, download, upload, nAttach, aSize, board_desc)
			values ('시 모음', '기본', 'board/list.jsp?boardId=1&p=1', 'F', 'all', 'admin', 'not', 'admin', 'admin', 'login', 'admin', 5, '10MB', '시를  모아놓은 곳');
insert into board (board_name, board_type, url, secret, read_allow, write_allow, reply, modify, remove, download, upload, nAttach, aSize, board_desc)
			values ('육아 정보', '기본', 'board/list.jsp?boardId=2&p=1', 'F', 'all', 'admin', 'not', 'admin', 'admin', 'login', 'admin', 5, '10MB', '육아 정보를 모아놓은 곳');
insert into board (board_name, board_type, url, secret, read_allow, write_allow, reply, modify, remove, download, upload, nAttach, aSize, board_desc)
			values ('도시농업', '기본', 'board/list.jsp?boardId=3&p=1', 'F', 'all', 'admin', 'not', 'admin', 'admin', 'login', 'admin', 5, '10MB', '도시농업에 대한 정보를 공유하는 곳');
insert into board (board_name, board_type, url, secret, read_allow, write_allow, reply, modify, remove, download, upload, nAttach, aSize, board_desc)
			values ('Android', '기본', 'board/list.jsp?boardId=4&p=1', 'F', 'all', 'admin', 'not', 'admin', 'admin', 'login', 'admin', 5, '10MB', 'Android 앱을 개발하기 위하여 필요한 정보를 공유하는 게시판');
insert into board (board_name, board_type, url, secret, read_allow, write_allow, reply, modify, remove, download, upload, nAttach, aSize, board_desc)
			values ('Web(자바기반)', '기본', 'board/list.jsp?boardId=5&p=1', 'F', 'all', 'admin', 'not', 'admin', 'admin', 'login', 'admin', 5, '10MB', '자바기반의 Web을 개발하기 위하여 필요한 정보를 공유하는 곳');
insert into board (board_name, board_type, url, secret, read_allow, write_allow, reply, modify, remove, download, upload, nAttach, aSize, board_desc)
			values ('임베디드', '기본', 'board/list.jsp?boardId=6&p=1', 'F', 'all', 'admin', 'not', 'admin', 'admin', 'login', 'admin', 5, '10MB', '임베디드 기술에 대한 정보를 공유하는 곳');
insert into board (board_name, board_type, url, secret, read_allow, write_allow, reply, modify, remove, download, upload, nAttach, aSize, board_desc)
			values ('함께 만들어요', '기본', 'board/list.jsp?boardId=7&p=1', 'F', 'all', 'login', 'not', 'admin', 'admin', 'login', 'login', 5, '10MB', '회원과 운영자가 함께 정보를 만들어 가는 곳');
insert into board (board_name, board_type, url, secret, read_allow, write_allow, reply, modify, remove, download, upload, nAttach, aSize, board_desc)
			values ('공지사항', '공지사항', 'board/list.jsp?boardId=8&p=1', 'F', 'all', 'admin', 'not', 'admin', 'admin', 'login', 'admin', 5, '10MB', '관리자가 회원들에게 알리는 사항을 게시하는 곳');
insert into board (board_name, board_type, url, secret, read_allow, write_allow, reply, modify, remove, download, upload, nAttach, aSize, board_desc)
			values ('Q&A', 'Q&A', 'qna/list.jsp?boardId=9&p=1', 'T', 'all', 'login', 'admin', 'writer', 'writer', 'login', 'login', 3, '1MB', '회원과 관리자간에 질문과 답변을 주고 받는 곳');
insert into board (board_name, board_type, url, secret, read_allow, write_allow, reply, modify, remove, download, upload, nAttach, aSize, board_desc)
			values ('방명록', '방명록', 'guestbook/list.jsp?boardId=10&p=1', 'F', 'all', 'login', 'not', 'writer', 'writer', 'not', 'not', 0, '0', '사이트의 방문자가 방문록을 남기는 곳');
			
/* 게시글
 * secret : 비밀글 표시 사용 여부
 * 
 */
create table article (
	article_id int not null primary key auto_increment,
	boardId int default 0,
	writer_name varchar(20) not null,
	email varchar(30) not null,
	title varchar(100) not null,
	password varchar(10) not null,
	read_count int default 0 not null,
	comment_count int default 0 not null,
	like_count int default 0 not null,
	ref int default 0 not null,
	step int default 0 not null,
	depth int default 0 not null,
	createdAt timestamp default current_timestamp,
	modifiedAt timestamp default current_timestamp on update current_timestamp,
	content varchar(4000) not null,
	ip varchar(20) not null,
	secret char(1) default 'F'
);

drop table article;

select * from article;

select count(*) from ( select article_id from article where boardId = 2 and title LIKE '%가나다%') as A;

delete from article where article_id = 1;

desc article;
	
create table pds_item (
	pds_item_id int not null auto_increment,
	filename varchar(200) not null,
	realpath varchar(200) not null,
	filesize int,
	downloadcount int default 0,
	description varchar(255),
	articleId int,
	primary key (pds_item_id)
);

select * from pds_item;

drop table pds_item;

delete from pds_item where pds_item_id = 22;

create table guestbook_message (
	message_id int primary key not null auto_increment,
	guest_name varchar(30) not null,
	password varchar(20) not null,
	creating_date timestamp default current_timestamp,
	message varchar(4000) not null
);

select * from guestbook_message;

drop table guestbook_message;
	
create table qna_msg (
	article_id int not null primary key auto_increment,
	boardId int default 0,
	writer_name varchar(20) not null,
	email varchar(30) not null,
	title varchar(100) not null,
	password varchar(10) not null,
	read_count int default 0 not null,
	comment_count int default 0,
	like_count int default 0,
	ref int default 0 not null,
	step int default 0 not null,
	depth int default 0 not null,
	regDate timestamp default current_timestamp,
	content varchar(4000) not null,
	ip varchar(20) not null,
	secret char(1) default 'F'
);

select * from qna_msg;

drop table qna_msg;