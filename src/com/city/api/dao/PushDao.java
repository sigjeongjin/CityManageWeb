package com.city.api.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jdbc.JdbcUtil;

public class PushDao {
	
	public String selectByIdAndsensorId(Connection conn, String memberId, String sensorId) 
	throws SQLException { 
		
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String Resultcode = "200";
		
		try {
			pstmt = conn.prepareStatement(
					"select memberId, sensorId from member where memberId=? and sensorId= ?");
			pstmt.setString(1, memberId);
			pstmt.setString(2, sensorId);		
			rs = pstmt.executeQuery();
			if (rs.next()) {
				rs.getString("member_id");
				rs.getString("member_pwd");
				return Resultcode;
			} else {
				return null;
			}
		} finally {
			JdbcUtil.close(pstmt);
			JdbcUtil.close(rs);
		}
	}
	}

