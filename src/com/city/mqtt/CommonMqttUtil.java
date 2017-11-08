package com.city.mqtt;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import com.city.api.service.PushService;
import com.city.api.service.SensorService;

public class CommonMqttUtil {
	
	public PushService pushService = new PushService();
	public SensorService sensorService = new SensorService();
	
	/** 
	 * message에서 값을 받아오는 메소드
	 * @param message
	 * @param data
	 * @return
	 */
	public String getTokenizerMessage(String message, int data) {
		
		 StringTokenizer tokens = new StringTokenizer(message.toString());						
		 String tokenData = null;
		
		 for (int i = 0; i < data;i++) {
			 tokenData = tokens.nextToken(":");
		 }
		return tokenData;
	}
	
	/** 
	 * DB에 있는 기준 알림값과 MQTT로 받은 센서값과 비교하는 메소드
	 * @param sensorId
	 * @param sensorValue
	 * @return
	 */
	public int noticeAndValueCompare(String sensorId, String sensorValue) {
		
		int sensorStatus = 0;
		// 센서 알림 기준값 가져오기
		String sensorNoticeStandard = sensorService.getNoticeStandard(sensorId);
		if (sensorNoticeStandard != null) {
			
			int iSensorValue = Integer.parseInt(sensorValue);
			int iSensorNoticeStandard = Integer.parseInt(sensorNoticeStandard);
			
			if(iSensorValue > iSensorNoticeStandard) {
				// mqtt로 받은 센서 값이 센서 알림보다 높으면 이상 상태를 Y로 해줌(이상)
				sensorStatus = sensorService.modifySensorStatus(sensorId, "Y");
		
			} else {
				// mqtt로 받은 센서 값이 센서 알림보다 낮으면 이상 상태를 N로 해줌(정상)
				sensorStatus = sensorService.modifySensorStatus(sensorId, "N");
			}
		}	
		return sensorStatus;
	}
	
	/** 
	 * topic(wm, tm, gm, sm) title 값 받아오기
	 * @param topic
	 * @return
	 */
	public String getPsuhTitle(String topic) {
		
		String tilte = "";
		
		if (topic.equals("wm")) {
			tilte = "수질 관리";
		} else if (topic.equals("tm")) {
			tilte = "쓰레기통 관리";
		} else if (topic.equals("gm")) {
			tilte = "도시가스 관리";
		} else if (topic.equals("sm")) {
			tilte = "금연구역 관리";
		}
		return tilte;
	}
	
	/** 
	 * sensorId으로 sensorType 조회하여 contents 값 가져오기
	 * @param sensorId
	 * @return
	 */
	public String getPsuhContents(String sensorId) {
		
		String sensorType = sensorService.getSensorType(sensorId);
		String contents = "";
		
		if (sensorType.equals("wl")) {
			contents = sensorId + ":수위 이상";
		} else if (sensorType.equals("wq")) {
			contents = sensorId + ":수질 이상";
		} else if (sensorType.equals("g")) {
			contents = sensorId + ":쓰레기량 이상";
		} else if (sensorType.equals("l")) {
			contents = sensorId + ":자동 잠금";
		} else if (sensorType.equals("fd")) {
			contents = sensorId + ":불꽃 감지";
		} else if (sensorType.equals("s")) {
			contents = sensorId + ":악취 이상";
		} else if (sensorType.equals("sm")) {
			contents = sensorId + ":연기 감지";
		} else if (sensorType.equals("sd")) {
			contents = sensorId + ";충격 감지";
		} else if (sensorType.equals("gd")) {
			contents = sensorId + ":압력 이상";
		}
		return contents;
	}	
	
	/** 
	 * PUSH 메시지 전송
	 * @param title
	 * @param contents
	 */
	public void sendPushMessage(String title, String contents) {
		
		ArrayList<String> tokenList; // tokenList 불러오기
		try {
			tokenList = pushService.sendTokenList();
			pushService.sendPush(tokenList, title, contents);
		} catch (SQLException e) {
			System.out.println("PUSH Exception");
			e.printStackTrace();
		}
	}
}
