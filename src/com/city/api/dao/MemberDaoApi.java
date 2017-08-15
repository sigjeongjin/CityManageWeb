package com.city.api.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import com.city.model.Member;

import jdbc.JdbcUtil;

public class MemberDaoApi {
    	
//	public int insert(Connection conn, Member member) throws SQLException {
//		PreparedStatement pstmt = null;
//		try 
//		{ 
//			pstmt = conn.prepareStatement(
//					"insert into member " +
//					"(memberId, memberPwd, memberName, memberPhone, memberEmail, regDate) " +
//					"values (?, ?, ?, ?, ?)");
//			pstmt.setString(1, member.getMemberId());
//			pstmt.setString(2, member.getMemberPwd());
//			pstmt.setString(3, member.getMemberName());
//			pstmt.setString(4, member.getMemberPhone());
//			pstmt.setString(5, member.getMemberEmail());
//			pstmt.setTimestamp(6, new Timestamp(member.getRegDate().getTime()));
//			return pstmt.executeUpdate();
//		} finally {
//			JdbcUtil.close(pstmt);
//		}
//	}
//	
//	public Member select(Connection conn, String userId) throws SQLException {
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		try {
//			pstmt = conn.prepareStatement(
//					"select * from member where userId=?");
//			pstmt.setString(1, userId);
//			rs = pstmt.executeQuery();
//			if (rs.next()) {
//				return makeMemberFromResultSet(rs);
//			} else {
//				return null;
//			}
//		} finally {
//			JdbcUtil.close(pstmt);
//			JdbcUtil.close(rs);
//		}
//	}
//	
//	private Member makeMemberFromResultSet(ResultSet rs) throws SQLException {
//		Member member = new Member();
//		member.setMemberId(rs.getString("memberId"));
//		member.setMemberPwd(rs.getString("memberPwd"));
//		member.setMemberName(rs.getString("memberName"));
//		member.setMemberPhone(rs.getString("memberPhone"));
//		member.setMemberEmail(rs.getString("memberEmail"));
//		member.setRegDate(rs.getTimestamp("regDate"));
//
//		return member;
//	}
//	
//	private Date toDate(Timestamp date) {
//		return date == null ? null : new Date(date.getTime());
//	}
//
//	public Member selectById(Connection conn, String id) {
//		
//		return null;
//	}
   
}
