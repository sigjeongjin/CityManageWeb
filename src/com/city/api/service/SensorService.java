package com.city.api.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.city.api.dao.ManagementDao;
import com.city.model.GmResultInfo;
import com.city.model.SensorInfo;
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
	
	private Connection conn = null;

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

	public GmResultInfo getGmInfo(String memberId, String manageId, GmResultInfo gmResultInfo) {

		try {
			conn = ConnectionProvider.getConnection(); // transaction
			conn.setAutoCommit(false);
			
			gmResultInfo = managementDao.selectGmInfobyManageId(conn, memberId, manageId,gmResultInfo);
			
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			JdbcUtil.rollback(conn);
		} finally {
			JdbcUtil.close(conn);
		}
		return gmResultInfo;
	}

	public SmResultInfo getSmInfo(String memberId, String manageId) {

		SmResultInfo smResultInfo = new SmResultInfo();

		try {
			conn = ConnectionProvider.getConnection(); // transaction
			conn.setAutoCommit(false);
			
			smResultInfo = managementDao.selectSmInfobyManageId(conn,memberId, manageId);
			
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
	public int modifyOperationStatus(String sensorId) {
		Connection conn = null;
		int resultCode = 0;
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);
			
			resultCode = managementDao.updateOperationStatus(conn, sensorId);	
			
			conn.commit();				
		} catch (SQLException e) {
			e.printStackTrace();
			JdbcUtil.rollback(conn);
		} finally {
			JdbcUtil.close(conn);
		}
		return resultCode;
	}
	
	/**
	 * @param sensorId
	 * @param status
	 * @return
	 */
	public int modifySensorStatus(String sensorId, String status) {
		Connection conn = null;
		int resultCode = 0;
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);
			
			resultCode = managementDao.updateSensorStatus(conn, sensorId, status);	
			System.out.println("result code : " + resultCode);
			conn.commit();				
		} catch (SQLException e) {
			e.printStackTrace();
			JdbcUtil.rollback(conn);
		} finally {
			JdbcUtil.close(conn);
		}
		return resultCode;
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
	
	/**
	 * 로그인한 사람의 관리 지역에 해당하는 센서 정보를 가져오기
	 * @param memberId : 회원 아이디
	 * @param manageId : 관리지역 아이디
	 * @return SensorInfo
	 */
	public List<SensorResultInfo> getSensorMapInfoList(String memberId, String manageType) {
		
		List<SensorResultInfo> sensorMapInfoList = new ArrayList<SensorResultInfo>();

		try {
			conn = ConnectionProvider.getConnection(); // transaction
			conn.setAutoCommit(false);
			
			sensorMapInfoList = managementDao.selectSensorMapInfoListByMemberIdAndManageId(conn, memberId, manageType);
			
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("센서 검색에 실패했습니다.");
			JdbcUtil.rollback(conn);
		} finally {
			JdbcUtil.close(conn);
		}
		return sensorMapInfoList;
	}
	
	/** 
	 * sensorId 로 센서 정보 조회 
	 * @param sensorId
	 * @return SensorInfo
	 */
	public SensorInfo getSensorInfo(String sensorId) {
		
		SensorInfo sensorInfo = new SensorInfo();
		
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);
			
			sensorInfo = managementDao.selectSensorInfo(conn, sensorId);
		
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			JdbcUtil.rollback(conn);
		} finally {
			JdbcUtil.close(conn);	
		} 
			return sensorInfo;		
	}
}