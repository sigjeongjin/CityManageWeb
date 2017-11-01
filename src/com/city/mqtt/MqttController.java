package com.city.mqtt;

import java.util.ArrayList;
import java.util.StringTokenizer;

import org.eclipse.paho.client.mqttv3.MqttMessage;

import com.city.api.service.PushService;
import com.city.api.service.SensorService;

public class MqttController {
	
	private PushService pushService = new PushService();
	private SensorService sensorService = new SensorService();

	public void transferTopicAndMessag(String topic, MqttMessage message) {
		
		// message에서 sensorId 값과 sensorValue 값 받아오기
		String sensorId = tokenizerMessage(message.toString(), 1);
		String sensorValue = tokenizerMessage(message.toString(), 2);
		String sensorStatus = ""; // 전역변수에 따른
		
		System.out.println("sensorId: " + sensorId + ", sensorValue: " + sensorValue);
		
		if(sensorId != null) {
			
			// 아두이노로 받은 센서 데이터와 데이터베이스에 있는 기준알림 비교하기
			sensorStatus = noticeAndValueCompare(sensorId, sensorValue);
			
			if (sensorStatus == "Y") {
				String title = psuhTitle(topic); 		  // topic으로 title 정하기
				//String contents = psuhContents(sensorId); // sensorId으로 contents 정하기
				String contents = "ㅎㅎㅎ";
				
				System.out.println("topic : " + topic);
				System.out.println("title : " + title + ", contenst : " + contents);

							
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
				sensorStatus = sensorService.SensorStatuschangeY(sensorId);
		
			} else {
				// mqtt로 받은 센서 값이 센서 알림보다 낮으면 이상 상태를 N로 해줌(정상)
				sensorStatus = sensorService.SensorStatuschangeN(sensorId);
			}
		}	
		return sensorStatus;
	}
	
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
	
	private String psuhContents(String sensorId) {
		String contents = sensorService.readSensorType(sensorId);
		
		
		
		return contents;
	}
}


