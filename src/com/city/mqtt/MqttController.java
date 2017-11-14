package com.city.mqtt;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import com.city.model.SensorInfo;

/**
 * @author minjeong
 *
 */
public class MqttController {
	
	CommonMqttUtil commonMqttUtil = new CommonMqttUtil(); 

	/**
	 * mqtt broker receiver ("wm","W00000000000001:xxx")
	 * @param topic
	 * @param message
	 * @throws Exception 
	 */
	public void transferTopicAndMessag(String topic, MqttMessage message) throws Exception {
				
		System.out.println("Receive - topic : " + topic + " message : " + message);
		
		SensorInfo sensorInfo = new SensorInfo();
		
		String topicMessage = message.toString();
		
		String arduinoSensorId = "";
		int arduinoSensorValue = 0;
		
		int sensorStatus = 0;
		
		int sensorStatusUpdateResultCode = 0;
		
		// message에서 sensorId 받아오기
		arduinoSensorId = commonMqttUtil.getTokenizerMessage(message.toString(), 1);	  		 
		
		// message에서 sensorValue 받아오기
		arduinoSensorValue = Integer.parseInt(commonMqttUtil.getTokenizerMessage(message.toString(), 2)); 		 
		
		//정확한 값을 가져오면 sensor 정보를 조회
		if(StringUtils.isEmpty(arduinoSensorId) && StringUtils.isEmpty(topicMessage)) {
			throw new Exception("센서 정보 값 오류");
		} else {
			sensorInfo = commonMqttUtil.getSensorInfo(arduinoSensorId, sensorInfo);
		}
		
		//센서의 동작 상태를 변경
		if(sensorInfo != null) {
			sensorStatusUpdateResultCode = commonMqttUtil.sensorService.modifyOperationStatus(arduinoSensorId); // 센서 동작을 N -> Y로 변경
		}
		
		if(sensorStatusUpdateResultCode == 1) {
			//DB의 센서 기준값과 아두이노에서 보내준 센서 값을 비교
			sensorStatus = commonMqttUtil.noticeAndValueCompare(sensorInfo, arduinoSensorValue );
			
			if (sensorStatus == 1) {
				sensorInfo = commonMqttUtil.getSensorInfo(arduinoSensorId, sensorInfo);
				                  
				if(sensorInfo.getSensorStatus().equals("Y")) {
					String title = commonMqttUtil.getPsuhTitle(topic); 		 // topic(wm, tm, gm, sm) title 값 받아오기
					String contents = commonMqttUtil.getPsuhContents(arduinoSensorId); // sensorId로 contents 값 받아오기
					
					commonMqttUtil.sendPushMessage(title, contents, arduinoSensorId); // PUSH 보내기		
				}
			}
		} else {
			throw new Exception("센서 상태 변경 오류");
		}
	}
}