package com.city.web.command;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.city.model.GmSensor;
import com.city.model.SensorInfo;
import com.city.web.service.GmSensorRegisterService;

public class GmSensorRegisterHandler implements CommandHandler {

	private GmSensorRegisterService gmSensorRegisterService = new GmSensorRegisterService();

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (request.getMethod().equalsIgnoreCase("GET")) {
			return processForm(request, response);
		} else if (request.getMethod().equalsIgnoreCase("POST")) {
			return processSubmit(request, response);
		} else {
			response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return null;
		}
	}

	private String processForm(HttpServletRequest request, HttpServletResponse response) {
		return null;
	}

	private String processSubmit(HttpServletRequest request, HttpServletResponse response) {

		SensorInfo sensor = new SensorInfo();
		GmSensor gmSensor = new GmSensor();

		sensor.setSensorId(request.getParameter("sensorId"));
		sensor.setLatitude(Double.parseDouble(request.getParameter("latitude")));
		sensor.setLongitude(Double.parseDouble(request.getParameter("longitude")));
		sensor.setMemo(request.getParameter("memo"));
		sensor.setCityGeocode(request.getParameter("cityGeocode"));
		sensor.setStateGeocode(request.getParameter("stateGeocode"));

		gmSensor.setSensorId(request.getParameter("sensorId"));
		gmSensor.setGasDensity(request.getParameter("gasDensity"));
		gmSensor.setShockDetection(request.getParameter("shockDetection"));
		gmSensor.setOperationStatus(request.getParameter("operationStatus"));
		gmSensor.setSensorNoticeStandard(Integer.parseInt(request.getParameter("sensorNoticeStandard")));

		gmSensorRegisterService.gmSensorRegister(sensor, gmSensor);
		return null;
	}

}