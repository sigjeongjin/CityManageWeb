package com.city.web.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.city.model.Push;

import jdbc.JdbcUtil;

public class PushDao {


	/**
	 * push 정보 수 조회
	 * @param conn
	 * @param selectBox
	 * @param searchText
	 * @return
	 * @throws SQLException
	 */
	public int selectPushCount(Connection conn, String selectBox, String searchText) throws SQLException {
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			StringBuilder sb = new StringBuilder();
			sb.append("select count(*) from (select phi.manage_id manageId, location_name locationName, "
					+"push_contents pushContents, DATE_FORMAT(phi.push_send_time, '%Y-%m-%d %H:%i:%s') pushSendTime , "
					+"DATE_FORMAT(lm.create_datetime, '%Y-%m-%d %H:%i:%s') installationDateTime, "
					+"concat(lm.latitude,' ' ,lm.longitude) location " 
					+"from push_history_info phi join location_management lm on phi.manage_id = lm.manage_id) tbl ");
			
			if(StringUtils.isNotEmpty(selectBox))
			{
				if(selectBox.equals("manageId")) {
					sb.append("where tbl.manageId like ?");
				} else if(selectBox.equals("locationName")) {
					sb.append("where tbl.locationName like ?");
				} else if(selectBox.equals("pushContents")) {
					sb.append("where tbl.pushContents like ?");
				}
			}
			
			pstmt = conn.prepareStatement(sb.toString());
			
			if(StringUtils.isNotEmpty(selectBox))
			{
				if(selectBox.equals("manageId")) {
					pstmt.setString(1, "'%" + searchText + "%'");
				} else if(selectBox.equals("locationName")) {
					pstmt.setString(1, "'%" + searchText + "%'");
				} else if(selectBox.equals("pushContents")) {
					pstmt.setString(1, "'%" + searchText + "%'");
				}
			}
			
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
			return 0;
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
	}

	/**
	 * push정보 및 페이징 관련 정보 조회
	 * @param conn
	 * @param startRow
	 * @param size
	 * @param selectBox
	 * @param searchText
	 * @return
	 * @throws SQLException
	 */
	public List<Push> selectPushHistoryList(Connection conn, int startRow, int size, String selectBox, String searchText) throws SQLException {

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		List<Push> pushHistoryList = new ArrayList<>();
		
		try {
			
			StringBuilder sb = new StringBuilder();
			
			sb.append("select * from (select phi.manage_id manageId, location_name locationName, "
					+"push_contents pushContents, DATE_FORMAT(phi.push_send_time, '%Y-%m-%d %H:%i:%s') pushSendTime , "
					+"DATE_FORMAT(lm.create_datetime, '%Y-%m-%d %H:%i:%s') installationDateTime, "
					+"concat(lm.latitude,' ' ,lm.longitude) location " 
					+"from push_history_info phi join location_management lm on phi.manage_id = lm.manage_id limit ?, ?) tbl ");
			
			pstmt = conn.prepareStatement(sb.toString());
			
			if(StringUtils.isNotEmpty(selectBox))
			{
				if(selectBox.equals("manageId")) {
					sb.append("where tbl.manageId like ?");
				} else if(selectBox.equals("locationName")) {
					sb.append("where tbl.locationName like ?");
				} else if(selectBox.equals("pushContents")) {
					sb.append("where tbl.pushContents like ?");
				}
			}
			
			if(StringUtils.isNotEmpty(selectBox))
			{
				if(selectBox.equals("manageId")) {
					pstmt.setString(3, "'%" + searchText + "%'");
				} else if(selectBox.equals("locationName")) {
					pstmt.setString(3, "'%" + searchText + "%'");
				} else if(selectBox.equals("pushContents")) {
					pstmt.setString(3, "'%" + searchText + "%'");
				}
			}
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				pushHistoryList.add(new Push(rs.getString("manageId")
						, rs.getString("locationName")
						, rs.getString("pushContents")
						, rs.getString("pushSendTime")
						, rs.getString("installationDateTime")
						, rs.getString("location")));
			}	
			
		} finally {
			JdbcUtil.close(pstmt);
			JdbcUtil.close(rs);
		}
		return pushHistoryList;
	}
}