package com.city.mqtt;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import com.city.api.service.PushService;
import com.city.api.service.SensorService;

/**
 * @author minjeong
 *
 */
public class MqttController {
	
	final String WM = "wm";
	final String TM = "tm";
	final String GM = "gm";
	final String SM = "sm";
	
	
	public TmController tmController = new TmController();
	
	public void transferTopicAndMessag(String topic, MqttMessage message) {
		
		if(topic.equals("WM")) {
			
		} else if(topic.equals("TM")) {
			tmController.transferTopicAndMessag(topic, message);
		} else if(topic.equals("GM")) {
			
		} else if(topic.equals("SM")) {
			
		}
	}
	
}