package com.city.web.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.city.model.Push;

import jdbc.JdbcUtil;

public class PushDao {

	/* paging을 위한 member count */
	public int selectCount(Connection conn) throws SQLException {
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select count(*) from member");
			if (rs.next()) {
				return rs.getInt(1);
			}
			return 0;
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(stmt);
		}
	}

	/**
	 * push정보 및 페이징 관련 정보 조회
	 * @param conn
	 * @param startRow
	 * @param size
	 * @return
	 * @throws SQLException
	 */
	public List<Push> selectPushHistoryList(Connection conn, int startRow, int size) throws SQLException {

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		List<Push> pushHistoryList = new ArrayList<>();
		
		try {
			pstmt = conn.prepareStatement(
					"select phi.manage_id manageId, location_name locationName, "
					+"push_contents pushContents, DATE_FORMAT(push_send_time, '%Y-%m-%d %H:%i:%s') pushSendTime , "
					+"DATE_FORMAT(lm.create_datetime, '%Y-%m-%d %H:%i:%s') installationDateTime, "
					+"concat(lm.latitude,' ' ,lm.longitude) location " 
					+"from push_history_info phi join location_management lm on phi.manage_id = lm.manage_id limit ?, ?");
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, size);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				pushHistoryList.add(new Push(rs.getString("manageId"),
						rs.getString("locationName"), rs.getString("pushContents"),
						rs.getString("pushSendTime"), rs.getString("installationDateTime"), rs.getString("location")));
			}
			return pushHistoryList;
		} finally {
			JdbcUtil.close(pstmt);
			JdbcUtil.close(rs);
		}
	}
}
