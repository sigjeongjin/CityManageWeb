package com.city.web.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.city.model.LocationManagement;
import com.city.model.SensorRegister;
import com.city.model.GmManagementInfo;
import com.city.model.SmManagementInfo;
import com.city.model.TmManagementInfo;
import com.city.model.WmManagementInfo;

import jdbc.JdbcUtil;

public class ManagementDao {

	/** 
	 * 관리아이디 자동넘버링
	 * @param conn : 커넥션
	 * @return String
	 */
	public String selectManageId(Connection conn) {
		
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		String manageId = null;
		
		try {
			pstmt = conn.prepareStatement("select CONCAT('M', LPAD((select(select cast((select right((select max(manage_id) from location_management), 14)) as unsigned) as mInt) + 1 mSum), 14, '0')) manageId FROM DUAL");
			rs = pstmt.executeQuery();
			while (rs.next()) {
				manageId = rs.getString(1);
			} 
		}catch (SQLException e) {
			e.printStackTrace();
			JdbcUtil.close(conn);
		}finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
		return manageId;
	}
	
	/** 관리 정보 등록
	 * @param conn
	 * @param locationManagement
	 * @return int
	 * @throws SQLException
	 */
	public int insertManagementInfo(Connection conn, LocationManagement locationManagement) throws SQLException {
		
		PreparedStatement pstmt = null;
		int resultCode = 0;
		
		try {
			pstmt = conn.prepareStatement("insert into location_management"
					+ "(manage_id, latitude, longitude, manage_type, sensor_types, memo, city_code, state_code, create_datetime) "
					+ "values (?, ?, ?, ? ,? ,?, ?, ?, now())");
			pstmt.setString(1, locationManagement.getManageId());
			pstmt.setDouble(2, locationManagement.getLatitude());
			pstmt.setDouble(3, locationManagement.getLongitude());
			pstmt.setString(4, locationManagement.getManageType());
			pstmt.setString(5, locationManagement.getSensorTypes());
			pstmt.setString(6, locationManagement.getMemo());
			pstmt.setString(7, locationManagement.getCityCode());
			pstmt.setString(8, locationManagement.getStateCode());
			resultCode = pstmt.executeUpdate();
			
		} finally {
			JdbcUtil.close(pstmt);
		}
		return resultCode;
	}
	
	/**
	 * 관리 정보 수정
	 * @param conn : 커넥션
	 * @param locationManagement : 관리정보
	 * @return int
	 * @throws SQLException
	 */
	public int updateManagement(Connection conn, LocationManagement locationManagement) throws SQLException {
		
		PreparedStatement pstmt = null;
		int resultCode = 0;
		
		try {
			pstmt = conn.prepareStatement("update location_management "
					+ "set latitude = ?, longitude = ?, memo = ?, city_code = ?, state_code = ? , sensor_types = ? where manage_id = ?");
			pstmt.setDouble(1, locationManagement.getLatitude());
			pstmt.setDouble(2, locationManagement.getLongitude());
			pstmt.setString(3, locationManagement.getMemo());
			pstmt.setString(4, locationManagement.getCityCode());
			pstmt.setString(5, locationManagement.getStateCode());
			pstmt.setString(6, locationManagement.getSensorTypes());
			pstmt.setString(7, locationManagement.getManageId());
			
			resultCode = pstmt.executeUpdate();
		} finally {
			JdbcUtil.close(pstmt);
		}	
		return resultCode;
	}
	
	/**
	 * 관리아이디로 센서타입들 조회
	 * @param conn : 커넥션
	 * @param manageId : 관리 아이디
	 * @return String
	 * @throws SQLException
	 */
	public String selectSensorTypes(Connection conn, String manageId) throws SQLException {
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sensorTypes = null;
		
		try {
			pstmt = conn.prepareStatement("select sensor_types sensorTypes from location_management where manage_id = ?");
			pstmt.setString(1, manageId);	
			rs = pstmt.executeQuery();
			while (rs.next()) {
				sensorTypes =  rs.getString("sensorTypes");
			}
		} finally {
			JdbcUtil.close(pstmt);
			JdbcUtil.close(rs);
		}
		return sensorTypes;
	}
	

	
	/** LIST INFO manageId로 정보검색 SELECT문 */
	public LocationManagement selectManagementInfo(Connection conn, String manageId) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement("select manage_id manageId, "
					+ "(select city_name from address_city where city_code=lm.city_code) cityName, "
					+ "(select state_name from address_state where state_code=lm.state_code) stateName, "
					+ "city_code cityCode, state_code stateCode, "
					+ "latitude, longitude , memo, sensor_types sensorTypes, manage_type manageType "
					+ "from location_management lm where lm.manage_id = ?");
			pstmt.setString(1, manageId);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				LocationManagement manageInfo = new LocationManagement();
				manageInfo.setManageId(rs.getString("manageId"));
				manageInfo.setLatitude(rs.getDouble("latitude"));
				manageInfo.setLongitude(rs.getDouble("longitude"));
				manageInfo.setMemo(rs.getString("memo"));
				manageInfo.setCityName(rs.getString("cityName"));
				manageInfo.setCityCode(rs.getString("cityCode"));
				manageInfo.setStateName(rs.getString("stateName"));
				manageInfo.setStateCode(rs.getString("stateCode"));
				manageInfo.setSensorTypes(rs.getString("sensorTypes"));
				manageInfo.setManageType(rs.getString("manageType"));
				return manageInfo;
			}
		} finally {
			JdbcUtil.close(pstmt);
			JdbcUtil.close(rs);
		}
		return null;
	}




	/** LIST SELECT문 */
	// 쓰레기통관리 리스트
	public List<TmManagementInfo> tmSensorList(Connection conn, int startRow, int size, String manageType, String selectBox, String searchText) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			if (StringUtils.isEmpty(selectBox)) {
				pstmt = conn.prepareStatement("select lm.manage_id manageId, CONCAT((select city_name from address_city where city_code=lm.city_code),' ',(select state_name from address_state where state_code=lm.state_code)) locationName, "
						+ "(select case sensor_status when 'Y' then '위험' when 'N' then '정상' end from sensor_info where manage_id=lm.manage_id and sensor_type='g') generous, "				
						+ "(select case sensor_status when 'Y' then '위험' when 'N' then '정상' end from sensor_info where manage_id=lm.manage_id and sensor_type='s') stink, "
						+ "(select case sensor_status when 'Y' then '위험' when 'N' then '정상' end from sensor_info where manage_id=lm.manage_id and sensor_type='fd') flameDetection, "
						+ "(select case sensor_status when 'Y' then '위험' when 'N' then '정상' end from sensor_info where manage_id=lm.manage_id and sensor_type='l') lockStatus, "
						+ "case lm.operation_status when 'Y' then '동작' when 'N' then '동작안함' end operationStatus, "
						+ "CONCAT((latitude),', ',(longitude)) coordinate, memo "
						+ "from location_management lm where lm.manage_type= ? and manage_id=lm.manage_id  order by lm.create_datetime desc limit ?, ?");
				pstmt.setString(1, manageType);
				pstmt.setInt(2, startRow);
				pstmt.setInt(3, size);
			} else {		
				if (selectBox.equals("all")) {
					pstmt = conn.prepareStatement("select lm.manage_id manageId, CONCAT((select city_name from address_city where city_code=lm.city_code),' ',(select state_name from address_state where state_code=lm.state_code)) locationName, "
							+ "(select case sensor_status when 'Y' then '위험' when 'N' then '정상' end from sensor_info where manage_id=lm.manage_id and sensor_type='g') generous, "				
							+ "(select case sensor_status when 'Y' then '위험' when 'N' then '정상' end from sensor_info where manage_id=lm.manage_id and sensor_type='s') stink, "
							+ "(select case sensor_status when 'Y' then '위험' when 'N' then '정상' end from sensor_info where manage_id=lm.manage_id and sensor_type='fd') flameDetection, "
							+ "(select case sensor_status when 'Y' then '위험' when 'N' then '정상' end from sensor_info where manage_id=lm.manage_id and sensor_type='l') lockStatus, "
							+ "case lm.operation_status when 'Y' then '동작' when 'N' then '동작안함' end operationStatus, "
							+ "CONCAT((latitude),', ',(longitude)) coordinate, memo "
							+ "from location_management lm where lm.manage_type= ? and manage_id=lm.manage_id order by lm.create_datetime desc limit ?, ?");
					pstmt.setString(1, manageType);
					pstmt.setInt(2, startRow);
					pstmt.setInt(3, size);
				} else {
					pstmt = conn.prepareStatement("select * from (select lm.manage_id manageId, CONCAT((select city_name from address_city where city_code=lm.city_code),' ',(select state_name from address_state where state_code=lm.state_code)) locationName, "
							+ "(select case sensor_status when 'Y' then '위험' when 'N' then '정상' end from sensor_info where manage_id=lm.manage_id and sensor_type='g') generous, "				
							+ "(select case sensor_status when 'Y' then '위험' when 'N' then '정상' end from sensor_info where manage_id=lm.manage_id and sensor_type='s') stink, "
							+ "(select case sensor_status when 'Y' then '위험' when 'N' then '정상' end from sensor_info where manage_id=lm.manage_id and sensor_type='fd') flameDetection, "
							+ "(select case sensor_status when 'Y' then '위험' when 'N' then '정상' end from sensor_info where manage_id=lm.manage_id and sensor_type='l') lockStatus, "
							+ "case lm.operation_status when 'Y' then '동작' when 'N' then '동작안함' end operationStatus, "
							+ "CONCAT((latitude),', ',(longitude)) coordinate, memo, create_datetime "
							+ "from location_management lm where lm.manage_type= ? and manage_id=lm.manage_id)tbl_tm "
							+ "where tbl_tm." + selectBox + " like ? order by tbl_tm.create_datetime desc limit ?, ?");
					pstmt.setString(1, manageType);
					pstmt.setString(2, "%" + searchText + "%");
					pstmt.setInt(3, startRow);
					pstmt.setInt(4, size);
				}
			}
				
			rs = pstmt.executeQuery();

			List<TmManagementInfo> tmManagementInfoList = new ArrayList<>();
			while (rs.next()) {
				TmManagementInfo tmManagementInfo = new TmManagementInfo();
				tmManagementInfo.setManageId(rs.getString("manageId"));
				tmManagementInfo.setLocationName(rs.getString("locationName"));
				tmManagementInfo.setFlameDetection(rs.getString("flameDetection"));
				tmManagementInfo.setStink(rs.getString("stink"));
				tmManagementInfo.setGenerous(rs.getString("generous"));
				tmManagementInfo.setLockStatus(rs.getString("lockStatus"));
				tmManagementInfo.setOperationStatus(rs.getString("operationStatus"));
				tmManagementInfo.setCoordinate(rs.getString("coordinate"));
				tmManagementInfo.setMemo(rs.getString("memo"));
				tmManagementInfoList.add(tmManagementInfo);
			}
			return tmManagementInfoList;
		} finally {
			JdbcUtil.close(pstmt);
			JdbcUtil.close(rs);
		}
	}
	
	// 수질관리 리스트
	public List<WmManagementInfo> wmSensorList(Connection conn, int startRow, int size, String manageType, String selectBox,
			String searchText) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			if (StringUtils.isEmpty(selectBox)) {
				pstmt = conn.prepareStatement("select lm.manage_id manageId, CONCAT((select city_name from address_city where city_code=lm.city_code),' ',(select state_name from address_state where state_code=lm.state_code)) locationName, "
						+ "(select case sensor_status when 'Y' then '위험' when 'N' then '정상' end from sensor_info where manage_id=lm.manage_id and sensor_type='wq') waterQuality, "
						+ "(select case sensor_status when 'Y' then '위험' when 'N' then '정상' end from sensor_info where manage_id=lm.manage_id and sensor_type='wl') waterLevel, "
						+ "case lm.operation_status when 'Y' then '동작' when 'N' then '동작안함' end operationStatus, "
						+ "CONCAT((latitude),', ',(longitude)) coordinate, memo "
						+ "from location_management lm where lm.manage_type= ? and manage_id=lm.manage_id order by lm.create_datetime desc limit ?, ?");
				pstmt.setString(1, manageType);
				pstmt.setInt(2, startRow);
				pstmt.setInt(3, size);
			} else {
				if (selectBox.equals("all")) {
					pstmt = conn.prepareStatement("select lm.manage_id manageId, CONCAT((select city_name from address_city where city_code=lm.city_code),' ',(select state_name from address_state where state_code=lm.state_code)) locationName, "
							+ "(select case sensor_status when 'Y' then '위험' when 'N' then '정상' end from sensor_info where manage_id=lm.manage_id and sensor_type='wq') waterQuality, "
							+ "(select case sensor_status when 'Y' then '위험' when 'N' then '정상' end from sensor_info where manage_id=lm.manage_id and sensor_type='wl') waterLevel, "
							+ "case lm.operation_status when 'Y' then '동작' when 'N' then '동작안함' end operationStatus, "
							+ "CONCAT((latitude),', ',(longitude)) coordinate, memo "
							+ "from location_management lm where lm.manage_type= ? and manage_id=lm.manage_id order by lm.create_datetime desc limit ?, ?");
					pstmt.setString(1, manageType);
					pstmt.setInt(2, startRow);
					pstmt.setInt(3, size);
				}
				else {
					pstmt = conn.prepareStatement("select * from (select lm.manage_id manageId, CONCAT((select city_name from address_city where city_code=lm.city_code),' ',(select state_name from address_state where state_code=lm.state_code)) locationName, "
							+ "(select case sensor_status when 'Y' then '위험' when 'N' then '정상' end from sensor_info where manage_id=lm.manage_id and sensor_type='wq') waterQuality, "
							+ "(select case sensor_status when 'Y' then '위험' when 'N' then '정상' end from sensor_info where manage_id=lm.manage_id and sensor_type='wl') waterLevel, "
							+ "case lm.operation_status when 'Y' then '동작' when 'N' then '동작안함' end operationStatus, "
							+ "CONCAT((latitude),', ',(latitude)) coordinate, memo, create_time "
							+ "from location_management lm where lm.manage_type= ? and manage_id=lm.manage_id)tbl_wm "
							+ "where tbl_wm." + selectBox + " like ? order by tbl_wm.create_datetime desc limit ?, ?");
					pstmt.setString(1, manageType);
					pstmt.setString(2, "%" + searchText + "%");
					pstmt.setInt(3, startRow);
					pstmt.setInt(4, size);
				}
			}
			
			rs = pstmt.executeQuery();

			List<WmManagementInfo> wmManagementInfoList = new ArrayList<>();
			while (rs.next()) {
				WmManagementInfo wmManagementInfo = new WmManagementInfo();
				wmManagementInfo.setManageId(rs.getString("manageId"));
				wmManagementInfo.setLocationName(rs.getString("locationName"));
				wmManagementInfo.setWaterQuality(rs.getString("waterQuality"));
				wmManagementInfo.setWaterLevel(rs.getString("waterLevel"));
				wmManagementInfo.setOperationStatus(rs.getString("operationStatus"));
				wmManagementInfo.setCoordinate(rs.getString("coordinate"));
				wmManagementInfo.setMemo(rs.getString("memo"));
				wmManagementInfoList.add(wmManagementInfo);
			}
			return wmManagementInfoList;
		} finally {
			JdbcUtil.close(pstmt);
			JdbcUtil.close(rs);
		}
	}
	
	// 도시가스관리 리스트
	public List<GmManagementInfo> gmSensorList(Connection conn, int startRow, int size, String manageType, String selectBox, String searchText) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			if (StringUtils.isEmpty(selectBox)) {
				pstmt = conn.prepareStatement("select lm.manage_id manageId, CONCAT((select city_name from address_city where city_code=lm.city_code),' ',(select state_name from address_state where state_code=lm.state_code)) locationName, "
						+ "(select case sensor_status when 'Y' then '위험' when 'N' then '정상' end from sensor_info where manage_id=lm.manage_id and sensor_type='gd') gasDensity, "
						+ "(select case sensor_status when 'Y' then '위험' when 'N' then '정상' end from sensor_info where manage_id=lm.manage_id and sensor_type='sd') shockDetection, "
						+ "case lm.operation_status when 'Y' then '동작' when 'N' then '동작안함' end operationStatus, "
						+ "CONCAT((latitude),', ',(longitude)) coordinate, memo "
						+ "from location_management lm where lm.manage_type= ? and manage_id=lm.manage_id order by lm.create_datetime desc limit ?, ?");
				pstmt.setString(1, manageType);
				pstmt.setInt(2, startRow);
				pstmt.setInt(3, size);
			} else {
				if (selectBox.equals("all")) {
					pstmt = conn.prepareStatement("select lm.manage_id manageId, CONCAT((select city_name from address_city where city_code=lm.city_code),' ',(select state_name from address_state where state_code=lm.state_code)) locationName, "
							+ "(select case sensor_status when 'Y' then '위험' when 'N' then '정상' end from sensor_info where manage_id=lm.manage_id and sensor_type='gd') gasDensity, "
							+ "(select case sensor_status when 'Y' then '위험' when 'N' then '정상' end from sensor_info where manage_id=lm.manage_id and sensor_type='sd') shockDetection, "
							+ "case lm.operation_status when 'Y' then '동작' when 'N' then '동작안함' end operationStatus, "
							+ "CONCAT((latitude),', ',(longitude)) coordinate, memo "
							+ "from location_management lm where lm.manage_type= ? and manage_id=lm.manage_id order by lm.create_datetime desc limit ?, ?");
					pstmt.setString(1, manageType);
					pstmt.setInt(2, startRow);
					pstmt.setInt(3, size);
				}
				else {
					pstmt = conn.prepareStatement("select * from (select lm.manage_id manageId, CONCAT((select city_name from address_city where city_code=lm.city_code),' ',(select state_name from address_state where state_code=lm.state_code)) locationName, "
							+ "(select case sensor_status when 'Y' then '위험' when 'N' then '정상' end from sensor_info where manage_id=lm.manage_id and sensor_type='gd') gasDensity, "
							+ "(select case sensor_status when 'Y' then '위험' when 'N' then '정상' end from sensor_info where manage_id=lm.manage_id and sensor_type='sd') shockDetection, "
							+ "case lm.operation_status when 'Y' then '동작' when 'N' then '동작안함' end operationStatus, "
							+ "CONCAT((latitude),', ',(latitude)) coordinate, memo, create_time "
							+ "from location_management lm where lm.manage_type= ? and manage_id=lm.manage_id)tbl_gm "
							+ "where tbl_gm." + selectBox + " like ? order by tbl_gm.create_datetime desc limit ?, ?");
					pstmt.setString(1, manageType);
					pstmt.setString(2, "%" + searchText + "%");
					pstmt.setInt(3, startRow);
					pstmt.setInt(4, size);
				}
			}
			rs = pstmt.executeQuery();


			List<GmManagementInfo> gmManagementInfoList = new ArrayList<>();
			while (rs.next()) {
				GmManagementInfo gmManagementInfo = new GmManagementInfo();
				gmManagementInfo.setManageId(rs.getString("manageId"));
				gmManagementInfo.setLocationName(rs.getString("locationName"));
				gmManagementInfo.setGasDensity(rs.getString("gasDensity"));
				gmManagementInfo.setShockDetection(rs.getString("shockDetection"));
				gmManagementInfo.setOperationStatus(rs.getString("operationStatus"));
				gmManagementInfo.setCoordinate(rs.getString("coordinate"));
				gmManagementInfo.setMemo(rs.getString("memo"));
				gmManagementInfoList.add(gmManagementInfo);
			}
			return gmManagementInfoList;
		} finally {
			JdbcUtil.close(pstmt);
			JdbcUtil.close(rs);
		}
	}
	
	// 금연구역관리  리스트
	public List<SmManagementInfo> smSensorList(Connection conn, int startRow, int size, String manageType, String selectBox,
			String searchText) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			if (StringUtils.isEmpty(selectBox)) {
				pstmt = conn.prepareStatement("select lm.manage_id manageId, CONCAT((select city_name from address_city where city_code=lm.city_code),' ',(select state_name from address_state where state_code=lm.state_code)) locationName, "
						+ "(select case sensor_status when 'Y' then '위험' when 'N' then '정상' end from sensor_info where manage_id=lm.manage_id and sensor_type='fd') flameDetection, "
						+ "(select case sensor_status when 'Y' then '위험' when 'N' then '정상' end from sensor_info where manage_id=lm.manage_id and sensor_type='sd') smokeDetection, "
						+ "case lm.operation_status when 'Y' then '동작' when 'N' then '동작안함' end operationStatus, "
						+ "CONCAT((latitude),', ',(longitude)) coordinate, memo "
						+ "from location_management lm where lm.manage_type= ? and manage_id=lm.manage_id order by lm.create_datetime desc limit ?, ?");
				pstmt.setString(1, manageType);
				pstmt.setInt(2, startRow);
				pstmt.setInt(3, size);
			} else {
				if (selectBox.equals("all")) {
					pstmt = conn.prepareStatement("select lm.manage_id manageId, CONCAT((select city_name from address_city where city_code=lm.city_code),' ',(select state_name from address_state where state_code=lm.state_code)) locationName, "
							+ "(select case sensor_status when 'Y' then '위험' when 'N' then '정상' end from sensor_info where manage_id=lm.manage_id and sensor_type='fd') flameDetection, "
							+ "(select case sensor_status when 'Y' then '위험' when 'N' then '정상' end from sensor_info where manage_id=lm.manage_id and sensor_type='sd') smokeDetection, "
							+ "case lm.operation_status when 'Y' then '동작' when 'N' then '동작안함' end operationStatus, "
							+ "CONCAT((latitude),', ',(longitude)) coordinate, memo "
							+ "from location_management lm where lm.manage_type= ? and manage_id=lm.manage_id order by lm.create_datetime desc limit ?, ?");
					pstmt.setString(1, manageType);
					pstmt.setInt(2, startRow);
					pstmt.setInt(3, size);
				}
				else {
					pstmt = conn.prepareStatement("select * from (select lm.manage_id manageId, CONCAT((select city_name from address_city where city_code=lm.city_code),' ',(select state_name from address_state where state_code=lm.state_code)) locationName, "
							+ "(select case sensor_status when 'Y' then '위험' when 'N' then '정상' end from sensor_info where manage_id=lm.manage_id and sensor_type='fd') flameDetection, "
							+ "(select case sensor_status when 'Y' then '위험' when 'N' then '정상' end from sensor_info where manage_id=lm.manage_id and sensor_type='sd') smokeDetection, "
							+ "case lm.operation_status when 'Y' then '동작' when 'N' then '동작안함' end operationStatus, "
							+ "CONCAT((latitude),', ',(longitude)) coordinate, memo "
							+ "from location_management lm where lm.manage_type= ? and manage_id=lm.manage_id)tbl_sm "
							+ "where tbl_sm." + selectBox + " like ? order by tbl_sm.create_datetime desc limit ?, ?");
					pstmt.setString(1, manageType);
					pstmt.setString(2, "%" + searchText + "%");
					pstmt.setInt(3, startRow);
					pstmt.setInt(4, size);
				}
			}	
			rs = pstmt.executeQuery();

			List<SmManagementInfo> smManagementInfoList = new ArrayList<>();
			while (rs.next()) {
				SmManagementInfo smManagementInfo = new SmManagementInfo();
				smManagementInfo.setManageId(rs.getString("manageId"));
				smManagementInfo.setLocationName(rs.getString("locationName"));
				smManagementInfo.setFlameDetection(rs.getString("flameDetection"));
				smManagementInfo.setSmokeDetection(rs.getString("smokeDetection"));
				smManagementInfo.setOperationStatus(rs.getString("operationStatus"));
				smManagementInfo.setCoordinate(rs.getString("coordinate"));
				smManagementInfo.setMemo(rs.getString("memo"));
				smManagementInfoList.add(smManagementInfo);
			}
			return smManagementInfoList;
		} finally {
			JdbcUtil.close(pstmt);
			JdbcUtil.close(rs);
		}
	}

	/** management count를 위한 SELECT문 */
	public int selectCount(Connection conn, String manageType) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement("select count(*) from location_management where manage_type = ?");
			pstmt.setString(1, manageType);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				return rs.getInt(1);
			}
		} finally {
			JdbcUtil.close(pstmt);
			JdbcUtil.close(rs);
		}
		return 0;
	}
	
	/** 검색한 management count를 위한 SELECT문 */
	public int selectCount(Connection conn, String manageType, String selectBox, String searchText) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query = selectCountQuery(manageType);
		try {
			if (StringUtils.isEmpty(selectBox)) {
				pstmt = conn.prepareStatement("select count(*) from location_management where manage_type = ?");
				pstmt.setString(1, manageType);	
			} else {
				if (selectBox.equals("all")) {
					pstmt = conn.prepareStatement("select count(*) from location_management where manage_type = ?");
					pstmt.setString(1, manageType);
				} else {
					pstmt = conn.prepareStatement("select count(*) from (select lm.manage_id manageId, CONCAT((select city_name from address_city where city_code=lm.city_code),' ',(select state_name from address_state where state_code=lm.state_code)) locationName, "
					+ query
					+ "case lm.operation_status when 'Y' then '동작' when 'N' then '동작안함' end operationStatus, "
					+ "CONCAT((latitude),', ',(longitude)) coordinate, memo "
					+ "from location_management lm where lm.manage_type= ? and manage_id=lm.manage_id)tbl_iot "
					+ "where tbl_iot." + selectBox + " like ?");
					pstmt.setString(1, manageType);
					pstmt.setString(2, "%" + searchText + "%");
				}		
			}
			rs = pstmt.executeQuery();
			while (rs.next()) {
				return rs.getInt(1);
			}
		} finally {
			JdbcUtil.close(pstmt);
			JdbcUtil.close(rs);
		}
		return 0;
	}
	
	public String selectCountQuery(String manageType) {
		String query = "";
		if (manageType.equals("tm")) {
			query = "(select case sensor_status when 'Y' then '위험' when 'N' then '정상' end from sensor_info where manage_id=lm.manage_id and sensor_type='g') generous, "				
					+ "(select case sensor_status when 'Y' then '위험' when 'N' then '정상' end from sensor_info where manage_id=lm.manage_id and sensor_type='s') stink, "
					+ "(select case sensor_status when 'Y' then '위험' when 'N' then '정상' end from sensor_info where manage_id=lm.manage_id and sensor_type='fd') flameDetection, "
					+ "(select case sensor_status when 'Y' then '위험' when 'N' then '정상' end from sensor_info where manage_id=lm.manage_id and sensor_type='l') lockStatus, ";
		} else if (manageType.equals("wm")){
			query =	"(select case sensor_status when 'Y' then '위험' when 'N' then '정상' end from sensor_info where manage_id=lm.manage_id and sensor_type='wq') waterQuality, "
					+ "(select case sensor_status when 'Y' then '위험' when 'N' then '정상' end from sensor_info where manage_id=lm.manage_id and sensor_type='wl') waterLevel, ";
		} else if (manageType.equals("gm")){
			query = "(select case sensor_status when 'Y' then '위험' when 'N' then '정상' end from sensor_info where manage_id=lm.manage_id and sensor_type='gd') gasDensity, "
					+ "(select case sensor_status when 'Y' then '위험' when 'N' then '정상' end from sensor_info where manage_id=lm.manage_id and sensor_type='sd') shockDetection, ";
			
		} else if (manageType.equals("sm")){
			query = "(select case sensor_status when 'Y' then '위험' when 'N' then '정상' end from sensor_info where manage_id=lm.manage_id and sensor_type='fd') flameDetection, "
					+ "(select case sensor_status when 'Y' then '위험' when 'N' then '정상' end from sensor_info where manage_id=lm.manage_id and sensor_type='sd') smokeDetection, ";
		}
		return query;
	}

	/** 최근 등록 된 센서정보을 최근순으로 3개 조회
	 * @param conn
	 * @param manageType
	 * @return
	 */
	public List<SensorRegister> selectRegisterList(Connection conn, String manageType) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		List<SensorRegister> registerList = new ArrayList<>();
		
		try {
			pstmt = conn.prepareStatement("select CONCAT((select city_name from address_city where city_code=lm.city_code),' ',(select state_name from address_state where state_code=lm.state_code)) locationName, "
					+ "si.sensor_id sensorId from location_management lm  right join sensor_info si on si.manage_id=lm.manage_id where manage_type = ? limit 0,3;");
			pstmt.setString(1, manageType);
			rs = pstmt.executeQuery();			
							
			while (rs.next()) {
				SensorRegister manageList = new SensorRegister();
				manageList.setLocationName(rs.getString("locationName"));
				manageList.setSensorId(rs.getString("sensorId"));
				registerList.add(manageList);
			}	
		} catch (Exception e) {
			e.printStackTrace();
			JdbcUtil.rollback(conn);
		} finally {
			JdbcUtil.close(pstmt);
			JdbcUtil.close(rs);
		}
		return registerList;
	}
}
