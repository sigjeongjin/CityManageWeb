package com.city.mqtt;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import org.eclipse.paho.client.mqttv3.MqttMessage;

import com.city.api.service.PushService;
import com.city.api.service.SensorService;

public class MqttController {
	
	private PushService pushService = new PushService();
	private SensorService sensorService = new SensorService();

	public void transferTopicAndMessag(String topic, MqttMessage message) {
				
		String sensorId = "";
		String sensorValue = "";
		String operationStatus = "";
		String sensorStatus = "";
		
		// data가 오면 message 동작상태 N -> Y
		if(message.toString() != null && message.toString() != "") {
			
			// message에서 sensorId 값과 sensorValue 값 받아오기
			sensorId = tokenizerMessage(message.toString(), 1);
			sensorValue = tokenizerMessage(message.toString(), 2);		
			operationStatus = sensorService.operationStatusCahnge(sensorId);	
		}	
		
		System.out.println("topic: " + topic);
		System.out.println("sensorId: " + sensorId + ", sensorValue: " + sensorValue);
		
		if(operationStatus == "Y" && sensorId != null) {
			// 아두이노로 받은 센서 데이터와 데이터베이스에 있는 기준알림 비교하기
			sensorStatus = noticeAndValueCompare(sensorId, sensorValue);
			
			if (sensorStatus == "Y") {
				String title = psuhTitle(topic); 		  // topic으로 title 정하기
				String contents = psuhContents(sensorId); // sensorId으로 contents 정하기
	
				System.out.println("title: " + title + ", contenst: " + contents);
				
				sendPushMessage(title, contents); // PUSH
							
			} else {
				return;
			}	
		}
	}
	
	/* message를 받아서 tokenizer (message -> mqtt message / data -> 메세지 순서) */ 	
	private String tokenizerMessage(String message, int data) {
		
		 StringTokenizer tokens = new StringTokenizer(message.toString());						
		 String tokenData = null;
		
		 for (int i = 0; i < data;i++) {
			 tokenData = tokens.nextToken(":");
		 }
		return tokenData;
	}
	
	/* sensorValue을 받아 sensorNoticeStandard를 비교하여 이상 상태 업데이트 */
	private String noticeAndValueCompare(String sensorId, String sensorValue) {
		
		String sensorStatus = "";
		// 센서 알림 기준값 가져오기
		String sensorNoticeStandard = sensorService.readNoticeStandard(sensorId);
		if (sensorNoticeStandard != null) {
			
			int sensorValueInt = Integer.parseInt(sensorValue);
			int sensorNoticeStandardInt = Integer.parseInt(sensorNoticeStandard);
			
			if(sensorValueInt > sensorNoticeStandardInt) {
				// mqtt로 받은 센서 값이 센서 알림보다 높으면 이상 상태를 Y로 해줌(이상)
				sensorStatus = sensorService.sensorStatusChange(sensorId, "Y");
		
			} else {
				// mqtt로 받은 센서 값이 센서 알림보다 낮으면 이상 상태를 N로 해줌(정상)
				sensorStatus = sensorService.sensorStatusChange(sensorId, "N");
			}
		}	
		return sensorStatus;
	}
	
	/* topic으로 title message 정하기  */
	private String psuhTitle(String topic) {
		
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
	
	/* sensorId으로 sensorType 조회하여 contents message 정하기  */
	private String psuhContents(String sensorId) {
		
		String sensorType = sensorService.readSensorType(sensorId);
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
	
	/* title, contents를 이용해 push message 전송  */
	private void sendPushMessage(String title, String contents) {
		
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