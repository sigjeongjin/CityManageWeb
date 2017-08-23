package com.city.api.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.city.model.Member;

import jdbc.JdbcUtil;

public class MemberDao {

	public String selectByIdAndPwd(Connection conn, String memberId, String memberPwd) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String Resultcode = "200";
		try {
			pstmt = conn.prepareStatement(
					"select member_id, member_pwd from member where member_id=? and member_pwd= ?");
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
	
	public String insertMember(Connection conn, Member member) throws SQLException {
		PreparedStatement pstmt = null;
		try 
		{ 
			String Resultcode = "200";
			pstmt = conn.prepareStatement(
					"insert into member " +
					"(member_id, member_pwd, member_name, member_phone, member_email, member_photo, member_authorization, city_code, state_code) " +
					"values (?, ?, ?, ?, ?, ?, ?, ?, ?)");
			pstmt.setString(1, member.getMemberId());
			pstmt.setString(2, member.getMemberPwd());
			pstmt.setString(3, member.getMemberName());
			pstmt.setString(4, member.getMemberPhone());
			pstmt.setString(5, member.getMemberEmail());
			pstmt.setString(6, member.getMemberPhoto());
			pstmt.setString(7, member.getMemberAuthorization());
			pstmt.setString(8, member.getCityCode());
			pstmt.setString(9, member.getStateCode());
			pstmt.executeUpdate();	
			return Resultcode;
		} finally {
			JdbcUtil.close(pstmt);
		}
	}
}
