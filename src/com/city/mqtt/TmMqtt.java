package com.city.mqtt;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import com.city.api.service.PushService;
import com.city.api.service.SensorService;


public class TmMqtt implements ServletContextListener {
	
	MqttController mqttController = new MqttController();
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
			
		String mqttConfig = sce.getServletContext().getInitParameter("mqttConfig");
		Properties prop = new Properties();
		
		try {
			prop.load(new StringReader(mqttConfig));
		} catch (IOException e) {
			throw new RuntimeException("config load fail", e);
		}
		
		try {
			MqttClient client = new MqttClient(
					prop.getProperty("mqttBrokerServer"), 
					MqttClient.generateClientId(),
					new MemoryPersistence());
			
			client.connect();
			
			client.setCallback(new MqttCallback() {
				
				@Override
				public void messageArrived(String topic, MqttMessage message) throws Exception {
					// System.out.println(topic + ": " + message.toString());
					mqttController.transferTopicAndMessag(topic, message);
				}
				
				@Override
				public void deliveryComplete(IMqttDeliveryToken arg0) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void connectionLost(Throwable arg0) {
					// TODO Auto-generated method stub
				}
			});
			
			client.subscribe("tm", 1); // topic, qos						

			
			String pubTopic = "민정이가 왕이다";
			MqttMessage message = new MqttMessage(pubTopic.getBytes());
	    	message.setQos(2);
	    	client.publish("inTopic", message);
			
			
		} catch (MqttException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}
}
