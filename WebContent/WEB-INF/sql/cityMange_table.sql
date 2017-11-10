CREATE TABLE `address_city` (
  `city_code` varchar(10) NOT NULL COMMENT '시/도 코드',
  `city_name` varchar(20) NOT NULL COMMENT '시/도 명',
  PRIMARY KEY (`city_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `address_state` (
  `state_code` varchar(10) NOT NULL COMMENT '시/군/구 코드',
  `state_name` varchar(20) NOT NULL COMMENT '시/군/구 명',
  `city_code`  varchar(10) NOT NULL COMMENT '시/도 코드',
  PRIMARY KEY (`state_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `favorites_info` (
  `favorites_id` int(11) 	 NOT NULL AUTO_INCREMENT COMMENT '아이디',
  `manage_id` 	 varchar(15) NOT NULL 				 COMMENT '센서 아이디',
  `bookmark`  	 varchar(1)  NOT NULL 				 COMMENT '즐겨찾기 여부, Y : 즐겨찾기 설정 N : 즐겨찾기 비설정',
  `member_id` 	 varchar(20) NOT NULL 				 COMMENT '회원 아이디',
  PRIMARY KEY (`favorites_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `location_management` (
  `manage_id` 		 varchar(15)  NOT NULL DEFAULT 'M000000' COMMENT '센서 아이디, w:수질, t:쓰레기통, s: 금연구역, g:도시가스',
  `latitude` 		 double 	  NOT NULL DEFAULT '0' 		 COMMENT '위도',
  `longitude` 		 double 	  NOT NULL DEFAULT '0' 		 COMMENT '경도',
  `create_datetime`  datetime	  NOT NULL 					 COMMENT '등록 날짜',
  `sensor_types` 	 varchar(50)  NOT NULL 					 COMMENT '센서 타입들, "wl:수위센서, wq:수질센서, g:만적센서, fd:불꽃감지센서, s:악취센서, sm:연기감지센서,sd:충격감지센서, gd:압력농도센서, lr:잠금센서"',
  `memo` 			 varchar(500) DEFAULT NULL 				 COMMENT '비고',
  `city_code` 		 varchar(10)  DEFAULT NULL 				 COMMENT '시/도 코드',
  `state_code` 		 varchar(10)  DEFAULT NULL 				 COMMENT '시/군/구 코드',
  `manage_type` 	 varchar(2)	  DEFAULT NULL,
  `operation_status` varchar(1)   DEFAULT NULL,
  PRIMARY KEY (`manage_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `member` (
  `member_id` 			 varchar(20)  NOT NULL 	   			COMMENT '회원 아이디',
  `member_pwd` 			 varchar(20)  NOT NULL 	   			COMMENT '회원 비밀번호',
  `member_name` 		 varchar(10)  NOT NULL 	   			COMMENT '회원 이름',
  `member_phone` 		 varchar(15)  NOT NULL 	   			COMMENT '회원 휴대폰',
  `member_email` 		 varchar(30)  DEFAULT NULL			COMMENT '회원 E-mail',
  `member_photo` 		 varchar(300) DEFAULT NULL 			COMMENT '회원 아이디',
  `member_authorization` varchar(15)  NOT NULL 	   			COMMENT '회원 권한 구분',
  `member_delete_code` 	 varchar(1)   NOT NULL DEFAULT 'N'  COMMENT '회원 삭제 코드',
  `city_code` 			 varchar(10)  DEFAULT NULL 			COMMENT '시/도 코드',
  `state_code` 			 varchar(10)  DEFAULT NULL 			COMMENT '시/군/구 코드',
  PRIMARY KEY (`member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `push_history_info` (
  `push_id` 	   int(11) 		NOT NULL AUTO_INCREMENT COMMENT 'PUSH 아이디',
  `manage_id` 	   varchar(15) 	NOT NULL 				COMMENT '센서 아이디',
  `push_contents`  varchar(200) NOT NULL 				COMMENT 'PUSH내용',
  `push_send_time` datetime 	NOT NULL 				COMMENT 'PUSH발송 시간',
  `location_name`  varchar(100) NOT NULL,
  PRIMARY KEY (`push_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `push_history_info` (
  `push_id` 		int(11) 	 NOT NULL AUTO_INCREMENT COMMENT 'PUSH 아이디',
  `manage_id` 		varchar(15)  NOT NULL 				 COMMENT '센서 아이디',
  `push_contents` 	varchar(200) NOT NULL 				 COMMENT 'PUSH내용',
  `push_send_time`  datetime 	 NOT NULL 				 COMMENT 'PUSH발송 시간',
  `location_name` 	varchar(100) NOT NULL,
  `push_message_id` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`push_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `sensor_info` (
  `sensor_id` 			   varchar(15) NOT NULL 			COMMENT '센서 아이디, t:쓰레기통',
  `manage_id` 			   varchar(15) NOT NULL DEFAULT 'N',
  `sensor_info` 		   varchar(1)  NOT NULL DEFAULT 'N' COMMENT 'Y:이상 상태 , N :정상 상태',
  `installation_datetime`  timestamp   NOT NULL DEFAULT 	CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `sensor_type` 		   varchar(2)  NOT NULL 			COMMENT 'wl:수위센서, wq:수질센서, g:만적센서, fd:불꽃감지센서, s:악취센서, sm:연기감지센서,\nsd:충격감지센서, gd:압력농도센서, l:잠금센서',
  `operation_status` 	   varchar(1)  NOT NULL DEFAULT 'N' COMMENT '센서 동작 상태, Y : 동작중 N : 고장',
  `sensor_notice_standard` int(5) 	   DEFAULT NULL 		COMMENT 'PUSH알림 기준값',
  PRIMARY KEY (`sensor_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;