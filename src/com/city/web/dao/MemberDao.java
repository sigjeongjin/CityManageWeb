package com.city.web.dao;

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
	
	public String insertMember(Connection conn, Member member) throws SQLException {
		PreparedStatement pstmt = null;
		try 
		{ 
			pstmt = conn.prepareStatement(
					"insert into member " +
					"(member_id, member_pwd, member_name, member_phone, member_email, member_photo, member_authorization, city_geocode, state_geocode) " +
					"values (?, ?, ?, ?, ?, ?, ?, ?, ?)");
			pstmt.setString(1, member.getMemberId());
			pstmt.setString(2, member.getMemberPwd());
			pstmt.setString(3, member.getMemberName());
			pstmt.setString(4, member.getMemberPhone());
			pstmt.setString(5, member.getMemberEmail());
			pstmt.setString(6, member.getMemberPhoto());
			pstmt.setString(7, member.getMemberAuthorization());
			pstmt.setString(8, member.getCityGeocode());
			pstmt.setString(9, member.getStateGeocode());
			return Integer.toString(pstmt.executeUpdate());		
		} finally {
			JdbcUtil.close(pstmt);
		}
	}
	
	public HashMap<String, String> selectByIdAndPwd(Connection conn, String memberId, String memberPwd) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		HashMap<String, String> idAndName = new HashMap<String, String>();
		try {
			pstmt = conn.prepareStatement(
					"select member_id, member_name from member where member_id=? and member_pwd= ?");
			pstmt.setString(1, memberId);
			pstmt.setString(2, memberPwd);		
			rs = pstmt.executeQuery();
			if (rs.next()) {
				idAndName.put("memberId", rs.getString("member_id"));
				idAndName.put("memberName", rs.getString("member_name"));
				System.out.println("data 있음");
				return idAndName;
			} else {
				return null;
			}
		} finally {
			JdbcUtil.close(pstmt);
			JdbcUtil.close(rs);
		}
	}
	
	public Member selectById(Connection conn, String memberId) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(
					"select * from member where member_id=?");
			pstmt.setString(1, memberId);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return makeMemberFromResultSet(rs);
			} else {
				return null;
			}
		} finally {
			JdbcUtil.close(pstmt);
			JdbcUtil.close(rs);
		}
	}
	
	private Member makeMemberFromResultSet(ResultSet rs) throws SQLException {
		Member member = new Member();
		member.setMemberId(rs.getString("member_id"));
		member.setMemberPwd(rs.getString("member_pwd"));
		member.setMemberName(rs.getString("member_name"));
		member.setMemberPhone(rs.getString("member_phone"));
		member.setMemberEmail(rs.getString("member_email"));
		member.setMemberPhoto(rs.getString("member_photo"));
		member.setMemberAuthorization(rs.getString("member_authorization"));
		member.setMemberDeleteCode(rs.getString("member_delete_code"));
		member.setCityGeocode(rs.getString("city_geocode"));
		member.setStateGeocode(rs.getString("state_geocode"));

		return member;
	}
	
	public String update(Connection conn, Member member) throws SQLException {
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement("update member "
					+ "set member_pwd = ?, member_name = ?, member_phone = ?, member_email = ?, member_photo = ? where member_id = ?");
			pstmt.setString(1, member.getMemberPwd());
			pstmt.setString(2, member.getMemberName());
			pstmt.setString(3, member.getMemberPhone());
			pstmt.setString(4, member.getMemberEmail());
			pstmt.setString(5, member.getMemberPhoto());
			pstmt.setString(6, member.getMemberId());
			return Integer.toString(pstmt.executeUpdate());
		} finally {
			JdbcUtil.close(pstmt);
		}
		
	}
   
}
