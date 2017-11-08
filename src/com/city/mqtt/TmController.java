package com.city.mqtt;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class TmController {
	
	CommonMqttUtil commonMqttUtil = new CommonMqttUtil(); 

	public void transferTopicAndMessag(String topic, MqttMessage message) {
				
		String topicMessage = message.toString();
		String sensorId = "";
		String sensorValue = "";
		int operationStatus = 0;
		int sensorStatus = 0;

		if(StringUtils.isNotEmpty(topicMessage)) {		
			sensorId = commonMqttUtil.getTokenizerMessage(message.toString(), 1);	  		 // message에서 sensorId 받아오기
			sensorValue = commonMqttUtil.getTokenizerMessage(message.toString(), 2); 		 // message에서 sensorValue 받아오기		
			operationStatus = commonMqttUtil.sensorService.modifyOperationStatus(sensorId); // 센서 동작을 N -> Y로 변경
		}	
		
		System.out.println("topic: " + topic + ", sensorId: " + sensorId + ", sensorValue: " + sensorValue);
		
		if(StringUtils.isNotEmpty(sensorId) && (operationStatus == 1)) {
			
			sensorStatus = commonMqttUtil.noticeAndValueCompare(sensorId, sensorValue);
			
			if (sensorStatus == 1) {
				String title = commonMqttUtil.getPsuhTitle(topic); 		 // topic(wm, tm, gm, sm) title 값 받아오기
				String contents = commonMqttUtil.getPsuhContents(sensorId); // sensorId로 contents 값 받아오기
				System.out.println("title: " + title + ", contenst: " + contents);
				
				commonMqttUtil.sendPushMessage(title, contents); // PUSH 보내기						
			}
		}
	}
}
