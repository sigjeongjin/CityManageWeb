package com.city.api.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.city.model.Push;
import com.city.model.PushInfo;

import jdbc.JdbcUtil;

public class PushDao {

	/**
	 * @param conn
	 * @param memberId
	 * @return
	 * @throws SQLException
	 */
	public List<PushInfo> selectPushHistoryListByMemberId(Connection conn, String memberId, String manageType) 
			throws SQLException {

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		List<PushInfo> pushInfoList = new ArrayList<PushInfo>();
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
				PushInfo pushInfo = new PushInfo();
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
}
