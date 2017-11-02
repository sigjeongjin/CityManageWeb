package mqtt;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class MqttMain implements ServletContextListener {
	
	private String sensorId;
	private String sensorValue;
//	
//	
//	public MqttMain(String sensorId, String sensorValue) { 
//		this.sensorId = sensorId;
//		this.sensorValue = sensorValue;
//	}
//	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		final String MQTT_BROKER_IP = "tcp://192.168.0.155:1883";
		// 연결
		try
		{
			MqttClient client = new MqttClient(MQTT_BROKER_IP, MqttClient.generateClientId(), new MemoryPersistence());
			client.connect();
			// 연결

			client.setCallback(new MqttCallback() {

				@Override
				public void connectionLost(Throwable acuse) {

				}

				@Override
				public void deliveryComplete(IMqttDeliveryToken arg0) {

				}

				@Override
				public void messageArrived(String arg0, MqttMessage arg1) throws Exception {
					System.out.println(arg1.toString());
					

					//저장 및 처리 부분
					
				}

			});
//			client.subscribe("sensorId/sensorValue", 1);
			client.subscribe("sm", 1); // 구독
			
		
			// sub 구독 , pub 보내기
			//

		}catch(
		MqttException e)
		{
			e.printStackTrace();
		}

	}
}

//select X form X where X = "";
//update X set X = Y where X "";
//update X set X = N where X "";