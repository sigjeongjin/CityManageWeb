package com.city.web.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.city.model.Member;

import jdbc.JdbcUtil;

public class MemberDao {

	/*Register을 위한 정보 입력(회원가입)*/
	public int insertMember(Connection conn, Member member) throws SQLException {
		PreparedStatement pstmt = null;
		int resultCode=0;
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
			
			return resultCode;
		} catch (SQLException e) {
			e.printStackTrace();
			JdbcUtil.rollback(conn);
		}finally {
			JdbcUtil.close(pstmt);
		}
		return resultCode;
	}

	/*login을 위한 ID, PASSWORD matching*/
	public HashMap<String, String> selectByIdAndPwd(Connection conn, String memberId, String memberPwd)
			throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		HashMap<String, String> memberInfo = new HashMap<String, String>();
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

	/*정보 수정 및 상세정보 클릭 시 member 업데이트*/
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

	/*paging을 위한 member count*/
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

	/*paging을 위한 member count*/
	public int selectCount(Connection conn, String memberSelect, String memberInput, String cityCode) throws SQLException {
		
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
			return 0;
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
	}
	
	// /memberSearch.do
	public List<Member> searchMemberList(Connection conn, int startRow, int size, String memberSelect,
			String memberInput, String cityCode) throws SQLException {

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
				pstmt = conn.prepareStatement("select * from member mb "
						+ "left join address_city city on mb.city_code = city.city_code "
						+ "left join address_state state on mb.state_code= state.state_code where mb.city_code=? limit ?, ?");
				pstmt.setString(1, cityCode);
				pstmt.setInt(2, startRow);
				pstmt.setInt(3, size);
			} else {
				if (memberSelect.equals("all")) {
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
							+ "where " + memberSelect + " like '%" + memberInput + "%' and mb.city_code=?  limit ?, ?");
					pstmt.setString(1, cityCode);
					pstmt.setInt(2, startRow);
					pstmt.setInt(3, size);
				}
			}

			rs = pstmt.executeQuery();

			List<Member> memberList = new ArrayList<>();
			while (rs.next()) {
				memberList.add(joinMemberFromResultSet(rs));
			}
			return memberList;
		} finally {
			JdbcUtil.close(pstmt);
			JdbcUtil.close(rs);
		}
	}
	
	/*member join을 위한 member result*/
	private Member joinMemberFromResultSet(ResultSet rs) throws SQLException {
		Member member = new Member();
		member.setMemberId(rs.getString("member_id"));
		member.setMemberPwd(rs.getString("member_pwd"));
		member.setMemberName(rs.getString("member_name"));
		member.setMemberPhone(rs.getString("member_phone"));
		member.setMemberEmail(rs.getString("member_email"));
		member.setMemberPhoto(rs.getString("member_photo"));
		member.setMemberAuthorization(rs.getString("member_authorization"));
		member.setCityCode(rs.getString("city_name"));		// code로 name 가져오기
		member.setStateCode(rs.getString("state_name"));	// code로 name 가져오기
		return member;
	}

	/*member 조회 및 업데이트*/
	public Member selectById(Connection conn, String memberId) throws SQLException {
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
	
	public List<Member> selecMemberNameList(Connection conn) throws SQLException{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
				pstmt = conn.prepareStatement("select member_name memberName from member limit 0 ,3");
				rs = pstmt.executeQuery();
				List<Member> memberNameList = new ArrayList<>();
			while (rs.next()) {
				Member member = new Member();
				member.setMemberName(rs.getString("memberName"));
				memberNameList.add(member);
			}
			return memberNameList;
		} finally {
			JdbcUtil.close(pstmt);
			JdbcUtil.close(rs);
		}
	}
}
