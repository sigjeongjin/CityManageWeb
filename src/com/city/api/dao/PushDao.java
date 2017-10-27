package com.city.api.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.city.model.Push;
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
	 * @param conn
	 * @param Push
	 * @return
	 * @throws SQLException
	 */
	public int insertPushToken(Connection conn, Push push) {
		PreparedStatement pstmt = null;
		int resultCode = 0;
		
		try {
			pstmt = conn.prepareStatement("insert into push_info "
					+ "(push_token, member_id, member_phone) "
					+ "values (?, ?, ?)");
			pstmt.setString(1, push.getPushToken());
			pstmt.setString(2, push.getMemberId());
			pstmt.setString(3, push.getMemberPhone());
			
			resultCode = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			JdbcUtil.rollback(conn);
		}finally {
			JdbcUtil.close(pstmt);
		}
		return resultCode;
	}

	public int updatePushToken(Connection conn, Push push) {
		PreparedStatement pstmt = null;
		int resultCode = 0;
		
		try {
			pstmt = conn.prepareStatement("update push_info "
					+ "set push_token=?, member_phone=? where member_id=?");
			pstmt.setString(1, push.getPushToken());
			pstmt.setString(2, push.getMemberPhone());
			pstmt.setString(3, push.getMemberId());
			
			resultCode = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			JdbcUtil.rollback(conn);
		}finally {
			JdbcUtil.close(pstmt);
		}
		return resultCode;
	}
}
