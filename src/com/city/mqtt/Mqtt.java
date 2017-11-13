package com.city.mqtt;

import java.io.IOException;
import java.io.StringReader;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class Mqtt implements ServletContextListener {
	
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
					mqttController.transferTopicAndMessag(topic, message);
				}
				
				@Override
				public void deliveryComplete(IMqttDeliveryToken token) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void connectionLost(Throwable throwable) {
					// TODO Auto-generated method stub
				}
			});
			
			// topic, qos
			client.subscribe("wm", 1);
			client.subscribe("tm", 1);
			client.subscribe("gm", 1);
			client.subscribe("sm", 1);
			
			String pubTopic = "민정이가 왕이다";
			MqttMessage message = new MqttMessage(pubTopic.getBytes());
	    	message.setQos(2);
	    	client.publish("CityManage", message);
			
			
		} catch (MqttException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}
}
