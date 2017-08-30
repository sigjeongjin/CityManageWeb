package com.city.api.service;

import java.sql.Connection;
import java.sql.SQLException;

import com.city.api.dao.ManagementDao;
import com.city.model.SensorInfo;

import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;

//String sensorId, String sensorInfo, String manageId, Date installationDatetime
//, String sensorType, String operationStatus, String sensorNoticeStandard

public class SensorRegisterService {

	private ManagementDao managementDao = new ManagementDao();

	public String sensorRegister(String sensorId, String sensorInfo, String sensorType) {
	
		int sensorRegister = 0;
		String resultCode ="";
		Connection conn = null;

		try {
			conn = ConnectionProvider.getConnection(); 
			conn.setAutoCommit(false);

			sensorRegister = managementDao.sensorRegister(conn, sensorId, sensorInfo, sensorType);
			conn.commit();

			
			if(sensorRegister == 1) {
				resultCode = "Y";
			} else {
				throw new SQLException();
			}	conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("정보를 불러오지 못했습니다.");
			JdbcUtil.rollback(conn);
		} finally {
			JdbcUtil.close(conn);
		}	
			return resultCode; 
		
	}
	public String operationStatus(String sensorId, String operationStatus) {
		String rs = null;
		Connection conn = null;

		try {
			conn = ConnectionProvider.getConnection(); // transaction
			conn.setAutoCommit(false);

			String strId = managementDao.insertOperationStatus(conn, sensorId, operationStatus);
			conn.commit();

			if (strId != null) {
				rs = "200";
				return rs;
			} else {
				rs = "400";
				throw new SQLException();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("저장에 실패했습니다.");
			JdbcUtil.rollback(conn);
		} finally {
			JdbcUtil.close(conn);
		}
		return rs;
	}


}