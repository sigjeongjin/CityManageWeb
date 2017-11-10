package com.city.api.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.city.model.PushInfo;
import com.city.model.PushResultInfo;

import jdbc.JdbcUtil;

public class PushDao {

	/**
	 * @param conn
	 * @param memberId
	 * @return
	 * @throws SQLException
	 */
	public List<PushResultInfo> selectPushHistoryListByMemberId(Connection conn, String memberId, String manageType) 
			throws SQLException {

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		List<PushResultInfo> pushInfoList = new ArrayList<PushResultInfo>();
		try {
			pstmt = conn.prepareStatement("select " + 
					" phi.manage_id manageId,DATE_FORMAT(phi.push_send_time, '%Y-%m-%d %H:%i:%s') pushSendTime, " +
					" CONCAT((select city_name cityNmae from address_city where city_code = lm.city_code),' '," +
					" (select state_name from address_state where state_code = lm.state_code)) locationName" + 
					" from push_history_info phi " + 
					" join location_management lm on phi.manage_id = lm.manage_id" + 
					" join member m on m.city_code=lm.city_code and m.state_code=lm.state_code" +
					" where m.member_id=? and lm.manage_type=?");
			pstmt.setString(1, memberId);
			pstmt.setString(2, manageType);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				PushResultInfo pushInfo = new PushResultInfo();
				pushInfo.setManageId(rs.getString("manageId"));
				pushInfo.setPushSendTime(rs.getString("pushSendTime"));
				pushInfo.setLocationName(rs.getString("locationName"));
				pushInfoList.add(pushInfo);
			}
			return pushInfoList;
		} finally {
			JdbcUtil.close(pstmt);
			JdbcUtil.close(rs);
		}
	}

	/**
	 * 토큰 등록
	 * @param conn
	 * @param PushInfo
	 * @return
	 * @throws SQLException
	 */
	public int insertPushToken(Connection conn, PushInfo pushInfo) {
		PreparedStatement pstmt = null;
		int resultCode = 0;
		
		try {
			pstmt = conn.prepareStatement("insert into push_info "
					+ "(push_token, member_id, member_phone) "
					+ "values (?, ?, ?)");
			pstmt.setString(1, pushInfo.getPushToken());
			pstmt.setString(2, pushInfo.getMemberId());
			pstmt.setString(3, pushInfo.getMemberPhone());
			
			resultCode = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			JdbcUtil.rollback(conn);
		}finally {
			JdbcUtil.close(pstmt);
		}
		return resultCode;
	}

	/**
	 * 토큰 업데이트
	 * @param conn
	 * @param PushInfo
	 * @return
	 * @throws SQLException
	 */
	public int updatePushToken(Connection conn, PushInfo pushInfo) {
		PreparedStatement pstmt = null;
		int resultCode = 0;
		
		try {
			pstmt = conn.prepareStatement("update push_info "
					+ "set push_token=?, member_phone=? where member_id=?");
			pstmt.setString(1, pushInfo.getPushToken());
			pstmt.setString(2, pushInfo.getMemberPhone());
			pstmt.setString(3, pushInfo.getMemberId());
			
			resultCode = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			JdbcUtil.rollback(conn);
		}finally {
			JdbcUtil.close(pstmt);
		}
		return resultCode;
	}

	/* 조건에 따른 토큰 리스트 생성 */
	/* 조건 */
	public ArrayList<String> selectPushList(Connection conn) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		ArrayList<String> pushList = new ArrayList<>();
		
		try {
			// Test 용  select문
			pstmt = conn.prepareStatement("select push_token from push_info");
			
			// 즐겨찾기 추가한 사람들만 push 전송되게 쿼리문 작성
			// pstmt = conn.prepareStatement("select push_token from push_info p inner join favorites_info f on p.member_id = f.member_id where f.bookmark='Y'");

			rs = pstmt.executeQuery();

			while (rs.next()) {
				pushList.add(rs.getString(1));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			JdbcUtil.rollback(conn);
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
		return pushList;
	}

	public int insertPushHistory(Connection conn, String contents, String arduinoSensorId, String pushMessageId) {

		PreparedStatement pstmt = null;
		int resultCode = 0;
		
		try {
			pstmt = conn.prepareStatement("INSERT INTO push_history_info "
					+ "(manage_id, location_name, push_contents, push_message_id, push_send_time) "
					+ "values "
					+ "((SELECT manage_id from sensor_info where sensor_id=?), "
					+ "(SELECT CONCAT((select city_name from address_city where city_code=lm.city_code),' ',(select state_name from address_state where state_code=lm.state_code)) locationName " 
					+ "FROM location_management lm JOIN sensor_info s ON lm.manage_id=s.manage_id where sensor_id=?), "
					+ "?, "
					+ "?, "
					+ "now())");
	
			pstmt.setString(1, arduinoSensorId);
			pstmt.setString(2, arduinoSensorId);
			pstmt.setString(3, contents);
			pstmt.setString(4, pushMessageId);
			
			resultCode = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(pstmt);
		}
		return resultCode;
	}
}
