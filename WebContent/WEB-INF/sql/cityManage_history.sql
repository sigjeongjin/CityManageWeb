CREATE TABLE member(
member_id		 		VARCHAR(20)		NOT NULL 	PRIMARY KEY 	COMMENT '회원 아이디' ,
member_pwd				VARCHAR(20)		NOT NULL 					COMMENT '회원 비밀번호' ,
member_name				VARCHAR(10)		NOT NULL 					COMMENT '회원 이름' ,	
member_phone	 		VARCHAR(15)		NOT NULL 					COMMENT '회원 휴대폰',	
member_email	 		VARCHAR(30)		DEFAULT NULL  				COMMENT '회원 E-mail',
member_photo	 		VARCHAR(300)	DEFAULT NULL  				COMMENT '회원 아이디',
member_authorization	VARCHAR(15)		NOT NULL 					COMMENT '회원 권한 구분',
member_delete_code		VARCHAR(1)		NOT NULL 	DEFAULT 'N' 	COMMENT '회원 삭제 코드' ,
city_geocode			VARCHAR(10)		NOT NULL					COMMENT '시/도 코드',
state_geocode			VARCHAR(10)		NOT NULL 					COMMENT '시/군/구 코드'
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE address_city(
city_geocode			VARCHAR(10)		NOT NULL 	PRIMARY KEY		COMMENT '시/도 코드',
city_name				VARCHAR(20)		NOT NULL 					COMMENT '시/도 명'
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE address_state(
state_geocode			VARCHAR(10)		NOT NULL 	PRIMARY KEY		COMMENT '시/군/구 코드',
state_name				VARCHAR(20)		NOT NULL 					COMMENT '시/군/구 명',
city_geocode			VARCHAR(10)		NOT NULL 					COMMENT '시/도 코드'
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- 2017.08.16 테이블 분리로 스키마 정보 변경
CREATE TABLE sensor_info (
sensor_id 				VARCHAR(15) 	NOT NULL	PRIMARY KEY		COMMENT '센서 아이디, w:수질, t:쓰레기통, s: 금연구역, g:도시가스',
latitude 				DOUBLE			NOT NULL 	DEFAULT '0' 	COMMENT '위도',
longitude 				DOUBLE 			NOT NULL 	DEFAULT '0' 	COMMENT '경도',
create_datetime 		datetime 		NOT NULL 					COMMENT '등록 날짜',
installation_datetime 	datetime 		NOT NULL 					COMMENT '센서 설치 날짜',
memo 					VARCHAR(500)   	DEFAULT NULL 				COMMENT '비고',
city_geocode 			VARCHAR(10) 	DEFAULT NULL 				COMMENT '시/도 코드',
state_geocode			VARCHAR(10) 	DEFAULT NULL 				COMMENT '시/군/구 코드'
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE favorites_info (
favorites_id 	INT 			NOT NULL PRIMARY KEY AUTO_INCREMENT 	COMMENT '아이디',
sensor_id 		VARCHAR(15) 	NOT NULL 								COMMENT '센서 아이디',
bookmark 		VARCHAR(1) 		NOT NULL 								COMMENT '즐겨찾기 여부, Y : 즐겨찾기 설정 N : 즐겨찾기 비설정',
member_id 		VARCHAR(20) 	NOT NULL 								COMMENT '회원 아이디'
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE push_history_info(
push_id 		INT 			NOT NULL PRIMARY KEY AUTO_INCREMENT 	COMMENT 'PUSH 아이디',
sensor_id		VARCHAR(15)		NOT NULL 	  							COMMENT '센서 아이디',
push_contents	VARCHAR(200)	NOT NULL 								COMMENT 'PUSH내용',
push_send_tiem	DATETIME		NOT NULL 								COMMENT 'PUSH발송 시간'
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 2017.08.16 테이블 분리로 인한 tm,wm,sm,gm 테이블 생성
CREATE TABLE tm_sensor_info (
  sensor_id 			VARCHAR(15) 	NOT NULL	PRIMARY KEY		COMMENT '센서 아이디, t:쓰레기통',
  locking 				VARCHAR(1)		NOT NULL 	DEFAULT 'N' 	COMMENT '잠김 정보, Y : 닫힘, N : 열림',
  generous		 		VARCHAR(1) 		NOT NULL	DEFAULT 'N' 	COMMENT '만적 정보, Y : 만적 상태 N : 비어 없음',
  flame_detection		VARCHAR(1) 		NOT NULL 	DEFAULT 'N' 	COMMENT '불꽃감지 정보, Y : 불꽃 감지 N : 불꽃 없음',
  stink 				VARCHAR(1) 		NOT NULL 	DEFAULT 'N' 	COMMENT '악취 정보, Y : 악취 감지 N : 악취 없음',
  operation_status 		VARCHAR(1) 		NOT NULL 	DEFAULT 'N' 	COMMENT '센서 동작 상태, Y : 동작중 N : 고장'
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE wm_sensor_info (
  sensor_id 			varchar(15) 	NOT NULL	PRIMARY KEY		COMMENT '센서 아이디, w:수질',
  water_level 			varchar(1) 		NOT NULL 	DEFAULT 'N'		COMMENT '수위 정보, Y : 수위 경고 N : 수위 안전',
  water_quality 		varchar(1) 		NOT NULL 	DEFAULT 'N' 	COMMENT '수질 정보, Y : 수질 경고 N : 수질 안전',
  operation_status 		varchar(1) 		NOT NULL 	DEFAULT 'N' 	COMMENT '센서 동작 상태, Y : 동작중 N : 고장'
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE sm_sensor_info (
  sensor_id 			VARCHAR(15) 	NOT NULL	PRIMARY KEY		COMMENT '센서 아이디, s: 금연구역',
  flame_detection		VARCHAR(1) 		NOT NULL 	DEFAULT 'N' 	COMMENT '불꽃감지 정보, Y : 불꽃 감지 N : 불꽃 없음',
  smoke_detection		VARCHAR(1)		NOT NULL 	DEFAULT 'N' 	COMMENT '연기감지 정보, Y : 연기 감지 N : 연기 없음',
  operation_status 		VARCHAR(1) 		NOT NULL 	DEFAULT 'N' 	COMMENT '센서 동작 상태, Y : 동작중 N : 고장'
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE gm_sensor_info (
  sensor_id 			VARCHAR(15) 	NOT NULL	PRIMARY KEY		COMMENT '센서 아이디, g:도시가스',
  gas_density 			VARCHAR(1) 		NOT NULL	DEFAULT 'N' 	COMMENT '압력(농도) 정보, Y : 압력 경고 N : 압력 안전',
  shock_detection 		VARCHAR(1) 		NOT NULL 	DEFAULT 'N' 	COMMENT '충격감지 정보, Y : 충격 경고 N : 충격 없음',
  operation_status 		VARCHAR(1) 		NOT NULL 	DEFAULT 'N' 	COMMENT '센서 동작 상태, Y : 동작중 N : 고장'
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 2017.08.16 push 발송을 위한 push_token 값과 회원 id 매핑하는 클래스 생성
CREATE TABLE push_info(
push_token				VARCHAR(200)	NOT NULL 	PRIMARY KEY		COMMENT '푸쉬 토큰키, firebase token key' ,
member_id				VARCHAR(20)		NOT NULL					COMMENT '회원 아이디',
member_phone			VARCHAR(15)		NOT NULL 					COMMENT '회원 휴대폰'	
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 2017.08.16 push_id 컬럼 생성
ALTER TABLE push_history_info ADD (push_id INT);

-- 2017.08.17 city_geocode, state_geocode option 변경(NULL)
alter table member modify city_geocode VARCHAR(10) NULL;
alter table member modify state_geocode VARCHAR(10) NULL;

-- 2017.08.17 tm,wm,sm,gm colums 추가
alter table tm_sensor_info add sensor_notice_standard	INT(5)	Not Null COMMENT '기준 초과시 Push 알림'
alter table wm_sensor_info add sensor_notice_standard	INT(5)	Not Null COMMENT '기준 초과시 Push 알림'
alter table sm_sensor_info add sensor_notice_standard	INT(5)	Not Null COMMENT '기준 초과시 Push 알림'
alter table gm_sensor_info add sensor_notice_standard	INT(5)	Not Null COMMENT '기준 초과시 Push 알림'

-- 2017.08.17 tm,wm,sm,gm 센서리스트 논의에 따른 table drop
DROP TABLE `citymanage`.`sm_sensor_info`;
DROP TABLE `citymanage`.`gm_sensor_info`;
DROP TABLE `citymanage`.`wm_sensor_info`;

-- 2017.08.17 table 수정
ALTER TABLE `citymanage`.`sensor_info` 
CHANGE COLUMN `sensor_id` `manage_id` VARCHAR(15) NOT NULL COMMENT '센서 아이디, w:수질, t:쓰레기통, s: 금연구역, g:도시가스' ,
CHANGE COLUMN `installation_datetime` `sensor_types` VARCHAR(5) NOT NULL COMMENT '센서 타입들' , 
RENAME TO  `citymanage`.`location_management` ;

-- 2017.08.17 table 수정
ALTER TABLE `citymanage`.`tm_sensor_info` 
CHANGE COLUMN `locking` `manage_id` VARCHAR(15) NOT NULL DEFAULT 'N' ,
CHANGE COLUMN `generous` `sensor_info` VARCHAR(1) NOT NULL DEFAULT 'N' COMMENT 'Y:이상 상태 , N :정상 상태' ,
CHANGE COLUMN `flame_detection` `installation_datetime` TIMESTAMP NOT NULL ,
CHANGE COLUMN `stink` `sensor_type` VARCHAR(2) NOT NULL COMMENT 'wl:수위센서, wq:수질센서, g:만적센서, fd:불꽃감지센서, s:악취센서' ,
ADD COLUMN `sensor_notice_standard` INT(5) NULL COMMENT 'PUSH알림 기준값' AFTER `operation_status`;
ALTER TABLE `citymanage`.`tm_sensor_info` 
RENAME TO  `citymanage`.`sensor_info` ;

ALTER TABLE `citymanage`.`sensor_info` 
CHANGE COLUMN `sensor_type` `sensor_type` VARCHAR(2) NOT NULL COMMENT 'wl:수위센서, wq:수질센서, g:만적센서, fd:불꽃감지센서, s:악취센서, sm:연기감지센서, sd:충격감지센서, gd:압력농도센서, lr:잠금센서' ;

-- 2017.08.21 colum 추가
ALTER TABLE `citymanage`.`location_management` 
ADD COLUMN `manage_type` VARCHAR(2) NULL DEFAULT NULL AFTER `state_geocode`;

-- 2017.08.21 colum 변경
ALTER TABLE `citymanage`.`favorites_info` 
CHANGE COLUMN `sensor_id` `manage_id` VARCHAR(15) NOT NULL COMMENT '센서 아이디' ;

ALTER TABLE `citymanage`.`favorites_info` 
CHANGE COLUMN `sensor_id` `manage_id` VARCHAR(15) NOT NULL COMMENT '센서 아이디' ;
ALTER TABLE `citymanage`.`push_history_info` 
CHANGE COLUMN `sensor_id` `manage_id` VARCHAR(15) NOT NULL COMMENT '센서 아이디' ;

-- 2017.08.22 type 변경
ALTER TABLE `citymanage`.`location_management` 
CHANGE COLUMN `sensor_types` modify VARCHAR(50) NOT NULL 
COMMENT '센서 타입들, "wl:수위센서, wq:수질센서, g:만적센서, fd:불꽃감지센서, s:악취센서, sm:연기감지센서,sd:충격감지센서, gd:압력농도센서, lr:잠금센서"';

-- 2017.08.23 colum 변경
ALTER TABLE `citymanage`.`address_city` 
CHANGE COLUMN `city_geocode` `city_code` VARCHAR(10) NOT NULL COMMENT '시/도 코드' ;
ALTER TABLE `citymanage`.`address_state` 
CHANGE COLUMN `state_geocode` `state_code` VARCHAR(10) NOT NULL COMMENT '시/군/구 코드' ;
ALTER TABLE `citymanage`.`location_management` 
CHANGE COLUMN `city_geocode` `city_code` VARCHAR(10) NULL DEFAULT NULL COMMENT '시/도 코드' ,
CHANGE COLUMN `state_geocode` `state_code` VARCHAR(10) NULL DEFAULT NULL COMMENT '시/군/구 코드' ;
ALTER TABLE `citymanage`.`member` 
CHANGE COLUMN `city_geocode` `city_code` VARCHAR(10) NOT NULL COMMENT '시/도 코드' ,
CHANGE COLUMN `state_geocode` `state_code` VARCHAR(10) NOT NULL COMMENT '시/군/구 코드' ;
ALTER TABLE `citymanage`.`address_state` 
CHANGE COLUMN `city_geocode` `city_code` VARCHAR(10) NOT NULL COMMENT '시/도 코드' ;

-- 2017.08.25 colum 추가
ALTER TABLE `citymanage`.`location_management` 
ADD COLUMN `operation_status` VARCHAR(1) NULL AFTER `manage_type`;

-- 2017.08.28 colum 추가
ALTER TABLE `citymanage`.`push_history_info` 
ADD COLUMN `manage_id` VARCHAR(15) NOT NULL AFTER `push_send_time`;

-- 2017.09.11 colum 변경
ALTER TABLE `citymanage`.`member` 
CHANGE COLUMN `city_code` `city_code` VARCHAR(10) NULL DEFAULT NULL COMMENT '시/도 코드' ;

-- 2017.09.11 colum 추가
ALTER TABLE `citymanage`.`member` 
ADD COLUMN `member_delete_code` VARCHAR(1) NOT NULL DEFAULT 'N' AFTER `state_code`;

-- 2017.10.26 primary key 변경
ALTER TABLE `citymanage`.`push_info` 
DROP PRIMARY KEY,
ADD PRIMARY KEY (`member_id`);

-- 2017.10.30 colum 변경
ALTER TABLE `citymanage`.`sensor_info` 
CHANGE COLUMN `sensor_info` `sensor_status` VARCHAR(1) NOT NULL DEFAULT 'N' COMMENT 'Y:이상 상태 , N :정상 상태' ;

-- 2017.11.06 colum 추가
ALTER TABLE `citymanage`.`member` 
ADD COLUMN `member_register_complete` VARCHAR(1) NULL DEFAULT 'N' AFTER `member_photo_original`;
