package com.city.api.service;

import java.sql.Connection;
import java.sql.SQLException;

import com.city.api.dao.ManagementDao;

import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;

//String sensorId, String sensorInfo, String manageId, Date installationDatetime
//, String sensorType, String operationStatus, String sensorNoticeStandard

public class SensorRegisterService {

	private ManagementDao managementDao = new ManagementDao();

	 
	public String sensorRegister(String sensorId, String sensorInfo) {
		int sensorRegister = 0;
		String resultcode = "";
		Connection conn = null;

		try {
			conn = ConnectionProvider.getConnection(); // transaction
			conn.setAutoCommit(false);

			sensorRegister = managementDao.updateBySensorInfo(conn, sensorInfo, sensorId);
			conn.commit();

			if(sensorRegister == 1) {
				resultcode = "Y";
			} else {
				throw new SQLException();
			}	
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("등록에 실패했습니다.");
			JdbcUtil.rollback(conn);
		} finally {
			JdbcUtil.close(conn);
		}
			return resultcode; 
	}
	public String operationStatus(String sensorId, String operationStatus) {
		String rs = null;
		Connection conn = null;

		try {
			conn = ConnectionProvider.getConnection(); // transaction
			conn.setAutoCommit(false);

			String strId = managementDao.updateBySensorIdAndOperationStatus(conn, sensorId, operationStatus);
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