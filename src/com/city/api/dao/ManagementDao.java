package com.city.api.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.city.model.GmResultInfo;
import com.city.model.SensorInfo;
import com.city.model.SensorResultInfo;
import com.city.model.SmResultInfo;
import com.city.model.TmResultInfo;
import com.city.model.WmResultInfo;

import jdbc.JdbcUtil;

public class ManagementDao {


	/**
	 * 센서 상태 변경
	 * @param conn : 커넥션
	 * @param sensorId : 센서 아이디(primary key)
	 * @param senssorstatus : 센서 상태
	 * @return int
	 * @throws SQLException
	 */
	public int updateBySensorInfo(Connection conn, String sensorId, String senssorstatus)
			throws SQLException {
		PreparedStatement pstmt = null;
		int resultcode = 0;
		
		try {	
			pstmt = conn.prepareStatement("update sensor_info set sensor_status=? where sensor_id=?");
			pstmt.setString(1, senssorstatus);
			pstmt.setString(2, sensorId);

			resultcode = pstmt.executeUpdate();
		} finally {
			JdbcUtil.close(pstmt);
		}
		return resultcode;
	}

	/**
	 * 센서 정보 리스트 조회
	 * @param conn : 커넥션 
	 * @param memberId : 멤버 아이디
	 * @param manageType : 센서 타입
	 * @return List<SensorResultInfo>
	 * @throws SQLException
	 */
	public List<SensorResultInfo> selectSensorListByMemberIdAndManageType(Connection conn, 
			String memberId, String manageType)throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<SensorResultInfo> sensorResultInfoList = new ArrayList<SensorResultInfo>();
		try {
			pstmt = conn.prepareStatement("select "
						+" CONCAT((select city_name cityName from address_city where city_code=lm.city_code)"
						+ ",' ',(select state_name stateName from address_state where state_code=lm.state_code)) locationName,"
						+" lm.manage_id manageId "
						+" from location_management lm "
						+" join member m on lm.state_code = m.state_code "
						+" where lm.manage_type=? and m.member_id=?");
			
			pstmt.setString(1, manageType);
			pstmt.setString(2, memberId);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				SensorResultInfo sensorRsultInfo = new SensorResultInfo();
				sensorRsultInfo.setManageId(rs.getString("manageId"));
				sensorRsultInfo.setLocationName(rs.getString("locationName"));
				sensorResultInfoList.add(sensorRsultInfo);
			}
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
		return sensorResultInfoList;
	}
	
	public List<SensorResultInfo>selectSensorListByMemberIdAndManageTypeAndSearchText(Connection conn, 
			String memberId, String manageType, String searchText)throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<SensorResultInfo> sensorResultInfoList = new ArrayList<SensorResultInfo>();
		try {
			pstmt = conn.prepareStatement("select "
						+" CONCAT((select city_name cityName from address_city where city_code=lm.city_code)"
						+ ",' ',(select state_name stateName from address_state where state_code=lm.state_code)) locationName,"
						+" lm.manage_id manageId "
						+" from location_management lm "
						+" join member m on lm.state_code = m.state_code "
						+" where lm.manage_type=? and m.member_id=? and lm.manage_id like ?");
			pstmt.setString(1, manageType);
			pstmt.setString(2, memberId);
			pstmt.setString(3, "%" + searchText + "%");
			rs = pstmt.executeQuery();

			while (rs.next()) {
				SensorResultInfo sensorRsultInfo = new SensorResultInfo();
				sensorRsultInfo.setManageId(rs.getString("manageId"));
				sensorRsultInfo.setLocationName(rs.getString("locationName"));
				sensorResultInfoList.add(sensorRsultInfo);
			}
			
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
		return sensorResultInfoList;
	}
	
	
	/**
	 * @param conn
	 * @param manageId
	 * @param memberId
	 * @return WmResultInfo
	 * @throws SQLException
	 */
	public WmResultInfo selectWmInfobyManageId(Connection conn, 
			String manageId, String memberId)throws SQLException {
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		WmResultInfo wmResultInfo = new WmResultInfo();
		
		try {
			pstmt = conn.prepareStatement(this.commonQuery(
				"(select case sensor_status when 'Y' then '위험' when 'N' then '정상' end from sensor_info where manage_id=? and sensor_type='wq') waterQuality, "
				+"(select case sensor_status when 'Y' then '위험' when 'N' then '정상' end from sensor_info where manage_id=? and sensor_type='wl') waterLevel, "
				,"wm"));
			pstmt.setString(1, manageId);
			pstmt.setString(2, manageId);
			pstmt.setString(3, manageId);
			pstmt.setString(4, memberId);
			pstmt.setString(5, manageId);
			
			rs = pstmt.executeQuery();

			if (rs.next()) {
				wmResultInfo.setManageId(rs.getString("manageId"));
				wmResultInfo.setLocationName(rs.getString("locationName"));
				wmResultInfo.setWaterLevel(rs.getString("waterLevel"));
				wmResultInfo.setWaterQuality(rs.getString("waterQuality"));
				wmResultInfo.setInstallationDateTime(rs.getString("installationDateTime"));
				wmResultInfo.setBookmark(rs.getString("bookmark"));
			}
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
		return wmResultInfo;
	}
	
	public TmResultInfo selectTmInfobyManageId(Connection conn, 
			String manageId, String memberId)throws SQLException {
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		TmResultInfo tmResultInfo = new TmResultInfo();
		
		try {
			pstmt = conn.prepareStatement(this.commonQuery(
				"(select case sensor_status when 'Y' then '위험' when 'N' then '정상' end from sensor_info where manage_id=? and sensor_type='fd') flameDetection, "
				+"(select case sensor_status when 'Y' then '위험' when 'N' then '정상' end from sensor_info where manage_id=? and sensor_type='s') stink, "
				+"(select case sensor_status when 'Y' then '위험' when 'N' then '정상' end from sensor_info where manage_id=? and sensor_type='g') generous, "
				+"(select case sensor_status when 'Y' then '잠김' when 'N' then '열림' end from sensor_info where manage_id=? and sensor_type='l') lockStatus, "
				,"tm"));
			
			pstmt.setString(1, manageId);
			pstmt.setString(2, manageId);
			pstmt.setString(3, manageId);
			pstmt.setString(4, manageId);
			pstmt.setString(5, manageId);
			pstmt.setString(6, memberId);
			pstmt.setString(7, manageId);
			
			rs = pstmt.executeQuery();

			if (rs.next()) {
				tmResultInfo.setManageId(rs.getString("manageId"));
				tmResultInfo.setLocationName(rs.getString("locationName"));
				tmResultInfo.setFlameDetection(rs.getString("flameDetection"));
				tmResultInfo.setStink(rs.getString("stink"));
				tmResultInfo.setGenerous(rs.getString("generous"));
				tmResultInfo.setLockStatus(rs.getString("lockStatus"));
				tmResultInfo.setInstallationDateTime(rs.getString("installationDateTime"));
				tmResultInfo.setBookmark(rs.getString("bookmark"));
			}
			
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
		return tmResultInfo;
	}
	
	
	/**
	 * @param conn
	 * @param manageId
	 * @param gmResultInfo
	 * @return GmResultInfo
	 * @throws SQLException
	 */
	public GmResultInfo selectGmInfobyManageId(Connection conn, 
			 String memberId, String manageId,GmResultInfo gmResultInfo)throws SQLException {
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = conn.prepareStatement(this.commonQuery(
				"(select case sensor_status when 'Y' then '위험' when 'N' then '정상' end from sensor_info where manage_id=? and sensor_type='gd') gasDensity, "
				+"(select case sensor_status when 'Y' then '위험' when 'N' then '정상' end from sensor_info where manage_id=? and sensor_type='sd') shockDetection, "
				,"gm"));
			pstmt.setString(1, manageId);
			pstmt.setString(2, manageId);
			pstmt.setString(3, manageId);
			pstmt.setString(4, memberId);
			pstmt.setString(5, manageId);
			
			rs = pstmt.executeQuery();

			if (rs.next()) {
				gmResultInfo.setManageId(rs.getString("manageId"));
				gmResultInfo.setLocationName(rs.getString("locationName"));
				gmResultInfo.setGasDensity(rs.getString("gasDensity"));
				gmResultInfo.setShockDetection(rs.getString("shockDetection"));
				gmResultInfo.setInstallationDateTime(rs.getString("installationDateTime"));
				gmResultInfo.setBookmark(rs.getString("bookmark"));
			} else {
				gmResultInfo = null;
			}
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
		return gmResultInfo;
	}
	
	
	/**
	 * @param conn
	 * @param manageId
	 * @return SmResultInfo
	 * @throws SQLException
	 */
	public SmResultInfo selectSmInfobyManageId(Connection conn, 
			String memberId, String manageId)throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		SmResultInfo smResultInfo = new SmResultInfo();
		try {
			pstmt = conn.prepareStatement(this.commonQuery(
							"(select case sensor_status when 'Y' then '위험' when 'N' then '정상' end from sensor_info where manage_id=? and sensor_type='fd') flameDetection, "
							+"(select case sensor_status when 'Y' then '위험' when 'N' then '정상' end from sensor_info where manage_id=? and sensor_type='sm') smokeDetection, "
							,"sm"));
				
			pstmt.setString(1, manageId);
			pstmt.setString(2, manageId);
			pstmt.setString(3, manageId);
			pstmt.setString(4, memberId);
			pstmt.setString(5, manageId);
			
			rs = pstmt.executeQuery();

			if (rs.next()) {
				smResultInfo.setManageId(rs.getString("manageId"));
				smResultInfo.setLocationName(rs.getString("locationName"));
				smResultInfo.setFlameDetection(rs.getString("flameDetection"));
				smResultInfo.setSmokeDetection(rs.getString("smokeDetection"));
				smResultInfo.setInstallationDateTime(rs.getString("installationDatetime"));
				smResultInfo.setBookmark(rs.getString("bookmark"));
			}
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
		return smResultInfo;
	}
	
	
	/**
	 * 공통되는 sql문
	 * @param query
	 * @param manageType
	 * @return
	 */
	public String commonQuery(String query, String manageType) {
		StringBuilder sb = new StringBuilder();
		
		sb.append("select ");
		sb.append("lm.manage_id manageId, ");
		sb.append("CONCAT((select city_name from address_city where city_code=lm.city_code),' ',(select state_name from address_state where state_code=lm.state_code)) locationName, ");
		sb.append(query);
		sb.append("DATE_FORMAT(lm.create_datetime, '%Y-%m-%d %H:%i:%s') installationDatetime, ");
		sb.append("(select case count(*) when 1 then 'Y' when 0 then 'N' end from favorites_info where manage_id=? and member_id=?) bookmark ");
		sb.append("from location_management lm where manage_id=?  and lm.manage_type='");
		sb.append(manageType+ "'");
		return sb.toString();
	}

	/**
	 * @param conn
	 * @param sensorId
	 * @return
	 */
	public int updateOperationStatus(Connection conn, String sensorId) {
		PreparedStatement pstmt = null;
		int resultCode = 0;
		
		try {
			pstmt = conn.prepareStatement("update sensor_info set operation_status ='Y' where sensor_id=?");
			pstmt.setString(1, sensorId);
			
			resultCode = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			JdbcUtil.rollback(conn);
			e.printStackTrace();
		} finally {
			JdbcUtil.close(pstmt);
		}
		return resultCode;
	}
	
	/**
	 * @param conn
	 * @param sensorId
	 * @param Status
	 * @return
	 */
	public int updateSensorStatus(Connection conn, String sensorId, String Status) {
		PreparedStatement pstmt = null;
		
		int resultCode = 0;
		
		try {
			pstmt = conn.prepareStatement("update sensor_info set sensor_status =? where sensor_id=?");
			pstmt.setString(1, Status);
			pstmt.setString(2, sensorId);
			
			resultCode = pstmt.executeUpdate();
		} catch (SQLException e) {
			JdbcUtil.rollback(conn);
			e.printStackTrace();
		} finally {
			JdbcUtil.close(pstmt);
		}
		return resultCode;
	}
	
	/**
	 * @param conn
	 * @param sensorId
	 * @return
	 */
	public String selectSensorType(Connection conn, String sensorId) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sensorType = null;
		
		try {
			
			pstmt = conn.prepareStatement("select sensor_type from sensor_info where sensor_id=?");
			pstmt.setString(1, sensorId);
			rs = pstmt.executeQuery();
		
			while(rs.next()) {
				sensorType = rs.getString("sensor_type");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JdbcUtil.rollback(conn);
		} finally {
			JdbcUtil.close(rs);	
			JdbcUtil.close(pstmt);		
		}
		return sensorType;
	}
	
	/**
	 * 센서의 맵 정보를 가져온다
	 * @param memberId
	 * @param manageId
	 * @return SensorInfo
	 */
	public List<SensorResultInfo> selectSensorMapInfoListByMemberIdAndManageId (Connection conn, String memberId, String manageType) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		List<SensorResultInfo> sensorMapInfoList = new ArrayList<SensorResultInfo>();
		
		try {
			pstmt = conn.prepareStatement("SELECT lm.manage_id manageId, "
					+ " CONCAT((select city_name from address_city where city_code=lm.city_code),' ',(select state_name from address_state where state_code=lm.state_code)) locationName "
					+ " ,lm.latitude latitude "
					+ " ,lm.longitude longitude "
					+ " FROM location_management lm "
					+ " JOIN member m ON lm.state_code=m.state_code "
					+ " WHERE member_id=? AND lm.manage_type=?"
					);
			
			pstmt.setString(1, memberId);
			pstmt.setString(2, manageType);
			
			rs = pstmt.executeQuery();

			while (rs.next()) {
				SensorResultInfo sensorResultInfo = new SensorResultInfo();
				
				sensorResultInfo.setManageId(rs.getString("manageId"));
				sensorResultInfo.setLatitude(rs.getString("latitude"));
				sensorResultInfo.setLongitude(rs.getString("longitude"));
				
				sensorMapInfoList.add(sensorResultInfo);
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
			JdbcUtil.rollback(conn);
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
		return sensorMapInfoList;
	}
	
	/** 
	 * sensorId 로 sensorInfo 조회
	 * @param conn
	 * @param sensorId
	 * @return SensorInfo
	 * @throws SQLException
	 */
	public SensorInfo selectSensorInfo(Connection conn, String sensorId) {
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		SensorInfo sensorInfo = new SensorInfo();
		
		try {
			pstmt = conn.prepareStatement("SELECT lm.manage_id manageId" 
					+ ", si.sensor_id sensorId"
					+ ", si.sensor_status sensorStatus"
					+ ", si.operation_status operationStatus"
					+ ", si.sensor_notice_standard sensorNoticeStandard"
					+ ", si.sensor_compare sensorCompare"
					+ " FROM sensor_info si "
					+ " JOIN location_management lm ON si.manage_id=lm.manage_id "
					+ " WHERE si.sensor_id = ? ");
			pstmt.setString(1, sensorId);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				sensorInfo.setManageId(rs.getString("manageId"));
				sensorInfo.setSensorStatus(rs.getString("sensorId"));
				sensorInfo.setOperationStatus(rs.getString("operationStatus"));
				sensorInfo.setSensorNoticeStandard(rs.getString("sensorNoticeStandard"));
				sensorInfo.setSensorCompare(rs.getString("sensorCompare"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			JdbcUtil.rollback(conn);
		} finally {
			JdbcUtil.close(pstmt);
			JdbcUtil.close(rs);
		}		
		return sensorInfo;
	}
}
