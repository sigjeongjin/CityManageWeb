package com.city.web.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.city.model.Member;

import jdbc.JdbcUtil;

public class MemberDao {

	/**
	 * 회원가입
	 * @param conn : 커넥션
	 * @param member : 멤버
	 * @return int
	 * @throws SQLException
	 */
	public int insertMember(Connection conn, Member member) throws SQLException {
		
		PreparedStatement pstmt = null;
		int resultCode = 0;
		
		try {
			pstmt = conn.prepareStatement("insert into member "
					+ "(member_id, member_pwd, member_name, member_phone, member_email, "
					+ "member_photo, member_authorization, city_code) "
					+ "values (?, ?, ?, ?, ?, ?, ?, ?)");
			pstmt.setString(1, member.getMemberId());
			pstmt.setString(2, member.getMemberPwd());
			pstmt.setString(3, member.getMemberName());
			pstmt.setString(4, member.getMemberPhone());
			pstmt.setString(5, member.getMemberEmail());
			pstmt.setString(6, member.getMemberPhoto());
			pstmt.setString(7, member.getMemberAuthorization());
			pstmt.setString(8, member.getCityCode());
			resultCode = pstmt.executeUpdate();
			
		}finally {
			JdbcUtil.close(pstmt);
		}	
		return resultCode;
	}

	/**  
	 * 로그인
	 * @param conn : 커넥션
	 * @param memberId : 아이디
	 * @param memberPwd : 비밀번호
	 * @return HashMap
	 * @throws SQLException
	 */
	public HashMap<String, String> selectIdAndName(Connection conn, String memberId, String memberPwd) throws SQLException {
		
		HashMap<String, String> memberInfo = new HashMap<String, String>();
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = conn
					.prepareStatement("select member_id, member_name, city_code from member "
							+ "where member_id=? and member_pwd= ?"
							+ " and member_authorization='admin'");
			pstmt.setString(1, memberId);
			pstmt.setString(2, memberPwd);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				memberInfo.put("memberId", rs.getString("member_id"));
				memberInfo.put("memberName", rs.getString("member_name"));
				memberInfo.put("cityCode", rs.getString("city_code"));
			}
			
		} finally {
			JdbcUtil.close(pstmt);
			JdbcUtil.close(rs);
		}		
		return memberInfo;
	}

	/** 
	 * 회원정보 수정
	 * @param conn : 커넥션
	 * @param member : 멤버
	 * @return int
	 * @throws SQLException
	 */
	public int updateMember(Connection conn, Member member) throws SQLException {
		
		PreparedStatement pstmt = null;
		int resultCode = 0;
		
		try {
			pstmt = conn.prepareStatement("update member "
					+ "set member_pwd = ?, member_name = ?, member_phone = ?, member_email = ?, member_photo = ? where member_id = ?");
			pstmt.setString(1, member.getMemberPwd());
			pstmt.setString(2, member.getMemberName());
			pstmt.setString(3, member.getMemberPhone());
			pstmt.setString(4, member.getMemberEmail());
			pstmt.setString(5, member.getMemberPhoto());
			pstmt.setString(6, member.getMemberId());
			resultCode = pstmt.executeUpdate();
			
		} finally {
			JdbcUtil.close(pstmt);
		}		
		return resultCode;
	}

	/**
	 * paging을 위한 member count
	 * @param conn
	 * @param memberSelect
	 * @param memberInput
	 * @param cityCode
	 * @return
	 * @throws SQLException
	 */
	public int selectMemberCount(Connection conn, String memberSelect, String memberInput, String cityCode) throws SQLException { 
		
		if(StringUtils.isNotEmpty(memberSelect)) {
			if( memberSelect.equals("city_code")) {
				memberSelect = "city_name";
			} else if ( memberSelect.equals("state_code")) {
				memberSelect = "state_name";
			}
		}
			
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			if(StringUtils.isEmpty(memberSelect)){
				pstmt = conn.prepareStatement("select count(*) from member mb "
						+ "left join address_city city on mb.city_code = city.city_code "
						+ "left join address_state state on mb.state_code= state.state_code where mb.city_code=?");
			} else {
				if (memberSelect.equals("all")) {
					pstmt = conn.prepareStatement("select count(*) from member mb "
							+ "left join address_city city on mb.city_code = city.city_code "
							+ "left join address_state state on mb.state_code= state.state_code where mb.city_code=?");
				} else {
					pstmt = conn.prepareStatement("select count(*) from member mb "
							+ "left join address_city city on mb.city_code = city.city_code "
							+ "left join address_state state on mb.state_code= state.state_code where mb.city_code=?"
							+ "and " + memberSelect + " like " + "'%" + memberInput + "%'");
				}
			}	
			pstmt.setString(1, cityCode);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				return rs.getInt(1);
			}		
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}		
		return 0;
	}
	
	/**
	 * 회원정보 리스트, 검색한 후 리스트
	 * @param conn : 커넥션
	 * @param startRow
	 * @param size
	 * @param selectBox  : View Page 콤보 받스
	 * @param searchText : View Page 검색어
	 * @param cityCode : 씨티코드
	 * @return
	 * @throws SQLException
	 */
	public List<Member> selectMemberList(Connection conn, int startRow, int size, String selectBox,
			String searchText, String cityCode) throws SQLException {

		if(StringUtils.isNotEmpty(selectBox)) {
			if( selectBox.equals("city_code")) {
				selectBox = "city_name";
			} else if ( selectBox.equals("state_code")) {
				selectBox = "state_name";
			}
		}
		
		List<Member> memberList = new ArrayList<>();
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			
			if(StringUtils.isEmpty(selectBox)){
				pstmt = conn.prepareStatement("select * from member mb "
						+ "left join address_city city on mb.city_code = city.city_code "
						+ "left join address_state state on mb.state_code= state.state_code where mb.city_code=? limit ?, ?");
				pstmt.setString(1, cityCode);
				pstmt.setInt(2, startRow);
				pstmt.setInt(3, size);
			} else {
				if (selectBox.equals("all")) {
					pstmt = conn.prepareStatement("select * from member mb "
							+ "left join address_city city on mb.city_code = city.city_code "
							+ "left join address_state state on mb.state_code= state.state_code where mb.city_code=? limit ?, ?");
					pstmt.setString(1, cityCode);
					pstmt.setInt(2, startRow);
					pstmt.setInt(3, size);
				} else {
					pstmt = conn.prepareStatement("select * from member mb "
							+ "left join address_city city on mb.city_code = city.city_code "
							+ "left join address_state state on mb.state_code= state.state_code "
							+ "where " + selectBox + " like '%" + searchText + "%' and mb.city_code=?  limit ?, ?");
					pstmt.setString(1, cityCode);
					pstmt.setInt(2, startRow);
					pstmt.setInt(3, size);
				}
			}

			rs = pstmt.executeQuery();

			while (rs.next()) {
				memberList.add(joinMemberFromResultSet(rs));
			}			
		} finally {
			JdbcUtil.close(pstmt);
			JdbcUtil.close(rs);
		}
		return memberList;
	}
	
	/**
	 * 회원가입 목록
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private Member joinMemberFromResultSet(ResultSet rs) throws SQLException {
		Member member = new Member();
		member.setMemberId(rs.getString("member_id"));
		member.setMemberPwd(rs.getString("member_pwd"));
		member.setMemberName(rs.getString("member_name"));
		member.setMemberPhone(rs.getString("member_phone"));
		member.setMemberEmail(rs.getString("member_email"));
		member.setMemberPhoto(rs.getString("member_photo"));
		member.setMemberAuthorization(rs.getString("member_authorization"));
		member.setCityCode(rs.getString("city_name"));	// code로 name 가져오기
		member.setStateCode(rs.getString("state_name"));// code로 name 가져오기
		return member;
	}

	/**
	 * 멤버 상세 정보 조회
	 * @param conn : 커넥션
	 * @param memberId : 아이디
	 * @return
	 * @throws SQLException
	 */
	public Member selectMemberInfo(Connection conn, String memberId) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		Member member = new Member();
		
		try {
			pstmt = conn.prepareStatement("SELECT * FROM member WHERE member_id=?");
			pstmt.setString(1, memberId);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				member.setMemberId(rs.getString("member_id"));
				member.setMemberPwd(rs.getString("member_pwd"));
				member.setMemberName(rs.getString("member_name"));
				member.setMemberPhone(rs.getString("member_phone"));
				member.setMemberEmail(rs.getString("member_email"));
				member.setMemberPhoto(rs.getString("member_photo"));
				member.setMemberAuthorization(rs.getString("member_authorization"));
				member.setCityCode(rs.getString("city_code"));
				member.setStateCode(rs.getString("state_code"));
			} 
			
		} finally {
			JdbcUtil.close(pstmt);
			JdbcUtil.close(rs);
		}	
		return member;
	}
	
	/** 
	 * 최근가입자 조회
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public List<Member> selectMemberNameList(Connection conn) throws SQLException{
		
		List<Member> memberNameList = new ArrayList<>();
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
				pstmt = conn.prepareStatement("select member_name memberName from member limit 0 ,3");
				rs = pstmt.executeQuery();
				
			while (rs.next()) {
				Member member = new Member();
				member.setMemberName(rs.getString("memberName"));
				memberNameList.add(member);
			}
			
		} finally {
			JdbcUtil.close(pstmt);
			JdbcUtil.close(rs);
		}
		return memberNameList;
	}
}