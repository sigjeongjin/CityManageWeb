package mqtt;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class MqttMain {
	
	public static void main(String[] args) {  
	final String MQTT_BROKER_IP = "tcp://192.168.0.35:1883";
	//연결
try { 
	MqttClient client = new MqttClient(
			MQTT_BROKER_IP,
			MqttClient.generateClientId(),
			new MemoryPersistence());
	client.connect();
	//연결
	
	client.setCallback(new MqttCallback() { 
		
		@Override
		public void connectionLost(Throwable acuse) { 
			
		}
		
		@Override
		public void deliveryComplete(IMqttDeliveryToken arg0) { 
			
		}
		
		@Override
		public void messageArrived(String arg0, MqttMessage arg1) throws Exception { 
			System.out.println(arg0 + ";" + arg1.toString());
		}
		
	});
	client.subscribe("oneTopic", 1); //구독
	// sub 구독 , pub 보내기
	//
	
	
}
catch(MqttException e) { 
	e.printStackTrace();
		}
	}

}
