CREATE TABLE member(
member_id		 		VARCHAR(20)		NOT NULL 	PRIMARY KEY 	COMMENT '회원 아이디' ,
member_pwd				VARCHAR(20)		NOT NULL 					COMMENT '회원 비밀번호' ,
member_name				VARCHAR(10)		NOT NULL 					COMMENT '회원 이름' ,	
member_phone	 		VARCHAR(15)		NOT NULL 					COMMENT '회원 휴대폰',	
member_email	 		VARCHAR(30)		DEFAULT NULL  				COMMENT '회원 E-mail',
member_photo	 		VARCHAR(300)	DEFAULT NULL  				COMMENT '회원 아이디',
member_authorization	VARCHAR(15)		NOT NULL 					COMMENT '회원 권한 구분',
member_delete_code		VARCHAR(1)		NOT NULL 	default 'N' 	COMMENT '회원 삭제 코드' ,
city_geocode			VARCHAR(10)		NOT NULL					COMMENT '시/도 코드',
state_geocode			VARCHAR(10)		NOT NULL 					COMMENT '시/군/구 코드'
)ENGINE=InnoDB DEFAULT CHARCTER SET=utf8;

CREATE TABLE address_city(
city_geocode			VARCHAR(10)		NOT NULL 	PRIMARY KEY		COMMENT '시/도 코드',
city_name				VARCHAR(20)		NOT NULL 					COMMENT '시/도 명'
)ENGINE=InnoDB DEFAULT CHARCTER SET=utf8;

CREATE TABLE address_state(
state_geocode			VARCHAR(10)		NOT NULL 	PRIMARY KEY		COMMENT '시/군/구 코드',
state_name				VARCHAR(20)		NOT NULL 					COMMENT '시/군/구 명',
city_geocode			VARCHAR(10)		NOT NULL 					COMMENT '시/도 코드'
)ENGINE=InnoDB DEFAULT CHARCTER SET=utf8;

CREATE TABLE sensor_info (
  sensor_id 			varchar(15) 	NOT NULL	PRIMARY KEY		COMMENT '센서 아이디\nw:수질, t:쓰레기통, s: 금연구역, g:도시가스',
  locking 				varchar(1)		NOT NULL 	DEFAULT 'N' 	COMMENT '잠김 정보\nY : 닫힘, N : 열림',
  generous		 		varchar(1) 		NOT NULL	DEFAULT 'N' 	COMMENT '만적 정보\nY : 만적 상태 N : 비어 없음\n',
  flame_detection		varchar(1) 		NOT NULL 	DEFAULT 'N' 	COMMENT '불꽃감지 정보\nY : 불꽃 감지 N : 불꽃 없음',
  smoke_detection		varchar(1)		NOT NULL 	DEFAULT 'N' 	COMMENT '연기감지 정보\nY : 연기 감지 N : 연기 없음',
  stink 				varchar(1) 		NOT NULL 	DEFAULT 'N' 	COMMENT '악취 정보\nY : 악취 감지 N : 악취 없음',
  water_level 			varchar(1) 		NOT NULL 	DEFAULT 'N'		COMMENT '수위 정보\nY : 수위 경고 N : 수위 안전',
  water_quality 		varchar(1) 		NOT NULL 	DEFAULT 'N' 	COMMENT '수질 정보\nY : 수질 경고 N : 수질 안전',
  gas_density 			varchar(1) 		NOT NULL	DEFAULT 'N' 	COMMENT '압력(농도) 정보\nY : 압력 경고 N : 압력 안전',
  shock_detection 		varchar(1) 		NOT NULL 	DEFAULT 'N' 	COMMENT '충격감지 정보\nY : 충격 경고 N : 충격 없음',
  operation_status 		varchar(1) 		NOT NULL 	DEFAULT 'N' 	COMMENT '센서 동작 상태\nY : 동작중 N : 고장',
  latitude 				double			NOT NULL 	DEFAULT '0' 	COMMENT '위도',
  longitude 			double 			NOT NULL 	DEFAULT '0' 	COMMENT '경도',
  create_datetime 		datetime 		NOT NULL 					COMMENT '등록 날짜',
  installation_datetime datetime 		NOT NULL 					COMMENT '센서 설치 날짜',
  memo 					varchar(500)   	DEFAULT NULL 				COMMENT '비고',
  city_geocode 			varchar(10) 	DEFAULT NULL 				COMMENT '시/도 코드',
  state_geocode			varchar(10) 	DEFAULT NULL 				COMMENT '시/군/구 코드',
) ENGINE=InnoDB DEFAULT CHARCTER SET=utf8;


CREATE TABLE favorite_info (
  favorite_id 	int(15) 	NOT NULL PRIMARY AUTO_INCREMENT 		COMMENT '아이디',
  sensor_id 	varchar(15) NOT NULL 								COMMENT '센서 아이디',
  bookmark 		varchar(1) 	NOT NULL 								COMMENT '즐겨찾기 여부\nY : 즐겨찾기 설정\nN : 즐겨찾기 비설정',
  member_id 	varchar(20) NOT NULL 								COMMENT '회원 아이디',
) ENGINE=InnoDB DEFAULT CHARCTER SET=utf8;


CREATE TABLE push_history_info(
sensor_id				VARCHAR(15)		NOT NULL 	PRIMARY KEY 	COMMENT '센서 아이디',
push_contents			VARCHAR(200)	NOT NULL 					COMMENT 'PUSH내용',
push_send_tiem			DATETIME		NOT NULL 					COMMENT 'PUSH발송 시간'
)ENGINE=InnoDB DEFAULT CHARCTER SET=utf8;