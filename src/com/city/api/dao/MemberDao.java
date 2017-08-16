package com.city.api.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;

import com.city.model.Member;

import jdbc.JdbcUtil;

public class MemberDao {

	public String selectByIdAndPwd(Connection conn, String memberId, String memberPwd) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String Resultcode = "200";
		try {
			pstmt = conn.prepareStatement(
					"select member_id, member_name from member where member_id=? and member_pwd= ?");
			pstmt.setString(1, memberId);
			pstmt.setString(2, memberPwd);		
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
