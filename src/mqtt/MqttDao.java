package mqtt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jdbc.JdbcUtil;

public class MqttDao  {
	
	public int updateMqttStateInfoRegiste(Connection conn, String sensorInfo, String oneTopic) throws SQLException { 
		// updateMqttStateInfoRegiste메소드의 입력값으로 conn,sensorInfo, oneTopic를 입력값으로 줌
		PreparedStatement pstmt = null;		//null 로 초기화
		int resultcode = 0;				
		
		
		
		try {
			pstmt = conn.prepareStatement("update sensor_info set sensor_info=? where oneTopic =?"); 
			//pstmt를 conn을 사용하여  객체 생성 및 쿼리문 작성 , 토픽값을 기준으로 센서 인포의 값을 변경
			pstmt.setString(1, sensorInfo);													   //1번값
			pstmt.setString(2, oneTopic);													   //2번값
			resultcode = pstmt.executeUpdate(); //쿼리문으로 데이터를 다루는 insert, update, delete 문사용을 위해 executeUpdate();을 사용

		
			return resultcode; //코드값으로 리턴
		} catch (SQLException e) {	 
			e.printStackTrace();
			JdbcUtil.rollback(conn);	//롤백
		}finally {
			JdbcUtil.close(pstmt);	//닫음
		}
		return resultcode; 		//리턴, 없는경우 에러 뜸
	}
}
