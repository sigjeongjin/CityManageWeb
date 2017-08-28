package com.city.api.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.city.api.dao.ManagementDao;
import com.city.model.SensorResultInfo;

import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;

//String sensorId, String sensorInfo, String manageId, Date installationDatetime
//, String sensorType, String operationStatus, String sensorNoticeStandard

public class SensorService {

	private ManagementDao managementDao = new ManagementDao();

	public List<SensorResultInfo> getSensorList(String memberId, String manageType) {
		List<SensorResultInfo> sensorRsultInfoList = new ArrayList<SensorResultInfo>();
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection(); // transaction
			conn.setAutoCommit(false);

			sensorRsultInfoList = managementDao.selectSensorListByMemberIdAndManageType(conn, memberId, manageType);
			conn.commit();
		
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("등록에 실패했습니다.");
			JdbcUtil.rollback(conn);
		} finally {
			JdbcUtil.close(conn);
		}
		return sensorRsultInfoList; 
	}
}