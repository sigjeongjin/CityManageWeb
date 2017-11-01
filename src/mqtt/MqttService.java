package mqtt;

import java.sql.Connection;
import java.sql.SQLException;


import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;
import mqtt.MqttDao;

public class MqttService {
	
	private MqttDao mqttDao = new MqttDao(); // mqttdao 객체 생성 private로 외부 접근 금지


	public String mqttData(String sensorInfo, String oneTopic, String sensorNoticeStandard)  { //사용할 값을 설정 , 센서 정보값과 받아올 아웃 토픽 값 
		int mqttData = 0;		
		String resultCode = "";
		Connection conn = null;		//값을 null 로 초기화 한다 
		
		try { 
			conn = ConnectionProvider.getConnection();  //커넥션 객체 생성
			conn.setAutoCommit(false);		//오토 커밋의 자동 작동 방지( 기본은 True )
			
			mqttData = mqttDao.updateMqttStateInfoRegiste(conn, sensorInfo, oneTopic); //mqttdata 를 정의 
			
		
			
			
			
			if(mqttData == 1) { //mqttdata 1이랑 같을때 true 1이 아닌경우 false 비교식
				resultCode = "Y"; //수행문 결과값은 Y
				 
			} else { 		//아닐경우 
				throw new SQLException(); //작업중 발생 SQLException을 예외 처리 한다.	
			}
			conn.commit(); // 실행된 결과를 커밋 한다
			//커밋 - 마무리된 작업에 작업 이력을 저장소로 보내는 행위
		} catch (SQLException e) {  // 예외 상황 잡음
			e.printStackTrace();	// 예외 상황 메시지 출력
			System.out.println("정보를 불러오지 못했습니다");		// 출력문
			
			JdbcUtil.rollback(conn); // 익셉션 발생시 롤백한다
		} finally { 
			JdbcUtil.close(conn);  //닫음
		}
		return resultCode;		//resultCode 로 돌아간다
	}
	
}

//if ((oneTopic) >= (sensorNoticeStandard)) {// 받은값이 기준값보다 크면 
//	sensorInfo ="Y";				   // "Y"
//	return resultCode;
//} else { 
//	sensorInfo = "N";				   //아닌경우 "N"
//	return resultCode;
//	
//}

