package mqtt;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.city.api.command.CommandJsonHandler;
import com.city.api.service.SensorService;
import com.city.model.Result;
import com.google.gson.Gson;

public class MqttHendler implements CommandJsonHandler {
	
//	private PushService pushService = new PushService();
//	private SensorService sensorService = new SensorService();
	private MqttService mqttService = new MqttService();
	private SensorService sensorService = new SensorService();

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		if (request.getMethod().equalsIgnoreCase("GET")) {
			return processForm(request, response);
		} else if (request.getMethod().equalsIgnoreCase("POST")) {
			return processSubmit(request, response);
		} else {
			response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return null;
		}
	}
		private String processForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
			return this.processSubmit(request, response);
		}
		
		private String processSubmit(HttpServletRequest request, HttpServletResponse response) throws Exception {
			Gson gson = new Gson();
			Result result = new Result();
			
			//토픽 종류/센서ID/센서값
			String sensorId = request.getParameter("sensorId");
			String sensorValue  = request.getParameter("sensorValue");
			
			//센서 서비스에 있는 센서 ID의 기준값 가져오기 , gm, sm, wm, cm
			String sensorNoticeStandard = sensorService.readNoticeStandard(sensorId);
			System.out.println("sensorNoticeStandard : " + sensorNoticeStandard);
			
			/* 3. sensorNoticeStandard와  sensorValue 비교 (String to Integer) */
			int sensorValueInt = Integer.parseInt(sensorValue);
			int sensorNoticeStandardInt = Integer.parseInt(sensorNoticeStandard);
			
			//if문 센서 밸류값이 기준값보다 높을때 센서값을 Y로 변경, 아닐경우 N
			if(sensorValueInt > sensorNoticeStandardInt) {
				String sensorStatus = sensorService.changeSensorStatus(sensorId);
				System.out.println("sensorI-In : " + sensorId);
				System.out.println("sensorStatus-In : " + sensorStatus);
				
				
			} else {
				System.out.println("기준값 보다 낮습니다");
			}
				return gson.toJson(result);
			}
	}

	
