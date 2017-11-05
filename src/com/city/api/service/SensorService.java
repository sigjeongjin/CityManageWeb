package com.city.api.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.city.api.dao.ManagementDao;
import com.city.model.GmResultInfo;
import com.city.model.SensorResultInfo;
import com.city.model.SmResultInfo;
import com.city.model.TmResultInfo;
import com.city.model.WmResultInfo;

import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;

//String sensorId, String sensorInfo, String manageId, Date installationDatetime
//, String sensorType, String operationStatus, String sensorNoticeStandard

public class SensorService {

	private ManagementDao managementDao = new ManagementDao();
	Connection conn = null;

	public List<SensorResultInfo> getSensorList(String memberId, String manageType) {
		List<SensorResultInfo> sensorRsultInfoList = new ArrayList<SensorResultInfo>();

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

	public List<SensorResultInfo> getSensorListByState(String memberId, String manageType, String searchText) {
		List<SensorResultInfo> sensorRsultInfoList = new ArrayList<SensorResultInfo>();

		try {
			conn = ConnectionProvider.getConnection(); // transaction
			conn.setAutoCommit(false);
			sensorRsultInfoList = managementDao.selectSensorListByMemberIdAndManageTypeAndSearchText(conn, memberId,
					manageType, searchText);
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("센서 검색에 실패했습니다.");
			JdbcUtil.rollback(conn);
		} finally {
			JdbcUtil.close(conn);
		}
		return sensorRsultInfoList;
	}

	public WmResultInfo getWmInfo(String manageId, String memberId) {

		WmResultInfo wmResultInfo = new WmResultInfo();

		try {
			conn = ConnectionProvider.getConnection(); // transaction
			conn.setAutoCommit(false);
			wmResultInfo = managementDao.selectWmInfobyManageId(conn, manageId, memberId);
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("센서 검색에 실패했습니다.");
			JdbcUtil.rollback(conn);
		} finally {
			JdbcUtil.close(conn);
		}
		return wmResultInfo;
	}

	public TmResultInfo getTmInfo(String manageId, String memberId) {
		TmResultInfo tmResultInfo = new TmResultInfo();

		try {
			conn = ConnectionProvider.getConnection(); 
			conn.setAutoCommit(false);
			tmResultInfo = managementDao.selectTmInfobyManageId(conn, manageId, memberId);
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("센서 검색에 실패했습니다.");
			JdbcUtil.rollback(conn);
		} finally {
			JdbcUtil.close(conn);
		}
		return tmResultInfo;
	}

	public GmResultInfo getGmInfo(String manageId, GmResultInfo gmResultInfo) {

		try {
			conn = ConnectionProvider.getConnection(); // transaction
			conn.setAutoCommit(false);
			
			gmResultInfo = managementDao.selectGmInfobyManageId(conn, manageId, gmResultInfo);
			
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			JdbcUtil.rollback(conn);
		} finally {
			JdbcUtil.close(conn);
		}
		return gmResultInfo;
	}

	public SmResultInfo getSmInfo(String manageId) {

		SmResultInfo smResultInfo = new SmResultInfo();

		try {
			conn = ConnectionProvider.getConnection(); // transaction
			conn.setAutoCommit(false);
			smResultInfo = managementDao.selectSmInfobyManageId(conn, manageId);
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("센서 검색에 실패했습니다.");
			JdbcUtil.rollback(conn);
		} finally {
			JdbcUtil.close(conn);
		}
		return smResultInfo;
	}

	/* 센서 알림 값 가져오기 */
	public String readNoticeStandard(String sensorId) {

		String sensorNoticeStandard = null;
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);
			sensorNoticeStandard = managementDao.selectByNoticeStandard(conn, sensorId);
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			JdbcUtil.rollback(conn);
		} finally {
			JdbcUtil.close(conn);
		}
		return sensorNoticeStandard;
	}

	/* 센서 이상 상태 변경 */
	public String sensorStatusChange(String sensorId, String status) {

		String sensorStatus = null;
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);
			sensorStatus = managementDao.updateBySensorStatus(conn, sensorId, status);
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			JdbcUtil.rollback(conn);
		} finally {
			JdbcUtil.close(conn);
		}
		return sensorStatus;
	}

	/* 센서 종류 가져오기 */
	public String readSensorType(String sensorId) {

		String sensorType = null;
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);
			sensorType = managementDao.selectBySensorType(conn, sensorId);
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			JdbcUtil.rollback(conn);
		} finally {
			JdbcUtil.close(conn);
		}
		return sensorType;
	}

	public String operationStatusCahnge(String sensorId) {
		
		String sensorStatus = "";
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);
			sensorStatus = managementDao.updateByOperationStatus(conn, sensorId);
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			JdbcUtil.rollback(conn);
		} finally {
			JdbcUtil.close(conn);
		}
		return sensorStatus;
	}

}