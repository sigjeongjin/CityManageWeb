package com.city.mqtt;

import java.util.StringTokenizer;

import org.eclipse.paho.client.mqttv3.MqttMessage;

import com.city.api.service.PushService;
import com.city.api.service.SensorService;

public class MqttController {
	
	private PushService pushService = new PushService();
	private SensorService sensorService = new SensorService();

	public void transferTopicAndMessag(String topic, MqttMessage message) {
		
		String sensorId = tokenizerMessage(message.toString(), 1);
		String sensorValue = tokenizerMessage(message.toString(), 2);
		String sensorStatus = "";
		
		if(sensorId != null) {
			
			sensorStatus = NoticeAndValueCompare(sensorId, sensorValue);
			
			if (sensorStatus == "Y") {
				
			}
		}
		
		
		System.out.println("sensorId: " + sensorId + ", sensorValue: " + sensorValue);
		
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
	
	
	private String NoticeAndValueCompare(String sensorId, String sensorValue) {
		
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
}


