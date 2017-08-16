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
  sensor_id 			VARCHAR(15) 	NOT NULL	PRIMARY KEY		COMMENT '센서 아이디, w:수질, t:쓰레기통, s: 금연구역, g:도시가스',
  latitude 				DOUBLE			NOT NULL 	DEFAULT '0' 	COMMENT '위도',
  longitude 			DOUBLE 			NOT NULL 	DEFAULT '0' 	COMMENT '경도',
  create_datetime 		datetime 		NOT NULL 					COMMENT '등록 날짜',
  installation_datetime datetime 		NOT NULL 					COMMENT '센서 설치 날짜',
  memo 					VARCHAR(500)   	DEFAULT NULL 				COMMENT '비고',
  city_geocode 			VARCHAR(10) 	DEFAULT NULL 				COMMENT '시/도 코드',
  state_geocode			VARCHAR(10) 	DEFAULT NULL 				COMMENT '시/군/구 코드'
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE favorites_info (
  favorites_id 	INT 			NOT NULL PRIMARY KEY AUTO_INCREMENT 	COMMENT '아이디',
  sensor_id 	VARCHAR(15) 	NOT NULL 								COMMENT '센서 아이디',
  bookmark 		VARCHAR(1) 		NOT NULL 								COMMENT '즐겨찾기 여부, Y : 즐겨찾기 설정 N : 즐겨찾기 비설정',
  member_id 	VARCHAR(20) 	NOT NULL 								COMMENT '회원 아이디'
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
