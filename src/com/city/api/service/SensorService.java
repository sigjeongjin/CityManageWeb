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

	public List<SensorResultInfo> getSensorListByState(String memberId, String manageType, String searchText) {
		List<SensorResultInfo> sensorRsultInfoList = new ArrayList<SensorResultInfo>();
		Connection conn = null;
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

		Connection conn = null;
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

		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection(); // transaction
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

	public GmResultInfo getGmInfo(String manageId) {

		GmResultInfo gmResultInfo = new GmResultInfo();

		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection(); // transaction
			conn.setAutoCommit(false);
			gmResultInfo = managementDao.selectGmInfobyManageId(conn, manageId);
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("센서 검색에 실패했습니다.");
			JdbcUtil.rollback(conn);
		} finally {
			JdbcUtil.close(conn);
		}
		return gmResultInfo;
	}

	public SmResultInfo getSmInfo(String manageId) {

		SmResultInfo smResultInfo = new SmResultInfo();

		Connection conn = null;
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

	/**
	 * @param sensorId
	 * @return
	 */
	public String modifyOperationStatus(String sensorId) {
		Connection conn = null;
		 String sensorStatus = null;
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);
			sensorStatus = managementDao.updateOperationStatus(conn, sensorId);	
			conn.commit();				
		} catch (SQLException e) {
			e.printStackTrace();
			JdbcUtil.rollback(conn);
		} finally {
			JdbcUtil.close(conn);
		}
		return sensorStatus;
	}
	
	/**
	 * @param sensorId
	 * @return
	 */
	public String getNoticeStandard(String sensorId) {
		Connection conn = null;
		String sensorNoticeStandard = null;
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);
			sensorNoticeStandard = managementDao.selectNoticeStandard(conn, sensorId);	
			conn.commit();		
		} catch (SQLException e) {
			e.printStackTrace();
			JdbcUtil.rollback(conn);
		} finally {
			JdbcUtil.close(conn);
		}
		return sensorNoticeStandard;
	}
	
	/**
	 * @param sensorId
	 * @param status
	 * @return
	 */
	public String modifySensorStatus(String sensorId, String status) {
		Connection conn = null;
		 String sensorStatus = null;
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);
			sensorStatus = managementDao.updateSensorStatus(conn, sensorId, status);	
			conn.commit();				
		} catch (SQLException e) {
			e.printStackTrace();
			JdbcUtil.rollback(conn);
		} finally {
			JdbcUtil.close(conn);
		}
		return sensorStatus;
	}
	
	/**
	 * @param sensorId
	 * @return
	 */
	public String getSensorType(String sensorId) {
		Connection conn = null;
		String sensorType = null;
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);
			sensorType = managementDao.selectSensorType(conn, sensorId);	
			conn.commit();		
		} catch (SQLException e) {
			e.printStackTrace();
			JdbcUtil.rollback(conn);
		} finally {
			JdbcUtil.close(conn);
		}
		return sensorType;
	}
}