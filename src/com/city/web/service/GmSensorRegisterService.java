package com.city.web.service;

import java.sql.Connection;
import java.sql.SQLException;

import com.city.model.GmSensor;
import com.city.model.SensorInfo;
import com.city.web.dao.SensorManageDao;

import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;

public class GmSensorRegisterService {

	private SensorManageDao sensorManageDao = new SensorManageDao();

	public String gmSensorRegister(SensorInfo sensor, GmSensor gmSensor) {

		Connection conn = null;
		String gmSensorStr = null;
		String sensorStr = null;

		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);
			gmSensorStr = sensorManageDao.insertGmSensorRegiste(conn, gmSensor);
			sensorStr = sensorManageDao.insertSensorRegiste(conn, sensor);
			conn.commit();
			if (gmSensorStr != null && sensorStr != null) {
				return gmSensorStr; 
			} else {
				throw new SQLException();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("GmSensor register fail");
			JdbcUtil.rollback(conn);
		} finally {
			JdbcUtil.close(conn);
		}
		return null;
	}
}
