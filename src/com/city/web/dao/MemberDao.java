package com.city.web.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.city.model.Member;

import jdbc.JdbcUtil;

public class MemberDao {

	/*Register을 위한 정보 입력(회원가입)*/
	public int insertMember(Connection conn, Member member) throws SQLException {
		PreparedStatement pstmt = null;
		int resultCode=0;
		try {
			pstmt = conn.prepareStatement("insert into member "
					+ "(member_id, member_pwd, member_name, member_phone, member_email, member_photo, member_authorization) "
					+ "values (?, ?, ?, ?, ?, ?, ?)");
			pstmt.setString(1, member.getMemberId());
			pstmt.setString(2, member.getMemberPwd());
			pstmt.setString(3, member.getMemberName());
			pstmt.setString(4, member.getMemberPhone());
			pstmt.setString(5, member.getMemberEmail());
			pstmt.setString(6, member.getMemberPhoto());
			pstmt.setString(7, member.getMemberAuthorization());
			resultCode = pstmt.executeUpdate();
			
			return resultCode;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e);
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

		HashMap<String, String> idAndName = new HashMap<String, String>();
		try {
			pstmt = conn
					.prepareStatement("select member_id, member_name from member where member_id=? and member_pwd= ?");
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
	public int selectCount(Connection conn, String memberSelect, String memberInput) throws SQLException {
		
		if( memberSelect.equals("city_code")) {
			memberSelect = "city_name";
		} else if ( memberSelect.equals("state_code")) {
			memberSelect = "state_name";
		}
			
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();

			if (memberSelect.equals("all")) {
				rs = stmt.executeQuery("select count(*) from member mb "
						+ "left join address_city city on mb.city_code = city.city_code "
						+ "left join address_state state on mb.state_code= state.state_code");
			} else {
				rs = stmt.executeQuery("select count(*) from member mb "
						+ "left join address_city city on mb.city_code = city.city_code "
						+ "left join address_state state on mb.state_code= state.state_code "
						+ "where " + memberSelect + "=" + "'" + memberInput + "'");
				
			}
			if (rs.next()) {
				return rs.getInt(1);
			}
			return 0;
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(stmt);
		}
	}
	
	// /memberList.do
	public List<Member> selectMemberList(Connection conn, int startRow, int size) throws SQLException {

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			pstmt = conn.prepareStatement("select * from member mb "
					+ "left join address_city city on mb.city_code = city.city_code "
					+ "left join address_state state on mb.state_code= state.state_code " + "limit ?, ?");
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, size);
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

	// /memberSearch.do
	public List<Member> searchMemberList(Connection conn, int startRow, int size, String memberSelect,
			String memberInput) throws SQLException {

		if( memberSelect.equals("city_code")) {
			memberSelect = "city_name";
		} else if ( memberSelect.equals("state_code")) {
			memberSelect = "state_name";
		}
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			if (memberSelect.equals("all")) {
				pstmt = conn.prepareStatement("select * from member mb "
						+ "left join address_city city on mb.city_code = city.city_code "
						+ "left join address_state state on mb.state_code= state.state_code " + "limit ?, ?");
				pstmt.setInt(1, startRow);
				pstmt.setInt(2, size);
			} else {
				pstmt = conn.prepareStatement("select * from member mb "
						+ "left join address_city city on mb.city_code = city.city_code "
						+ "left join address_state state on mb.state_code= state.state_code "
						+ "where " + memberSelect + "=?" + "limit ?, ?");
				pstmt.setString(1, memberInput);
				pstmt.setInt(2, startRow);
				pstmt.setInt(3, size);
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
		member.setMemberDeleteCode(rs.getString("member_delete_code"));
		member.setCityCode(rs.getString("city_name"));		// code로 name 가져오기
		member.setStateCode(rs.getString("state_name"));	// code로 name 가져오기
		return member;
	}

	/*member 조회 및 업데이트*/
	public Member selectById(Connection conn, String memberId) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement("select * from member where member_id=?");
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
		member.setCityCode(rs.getString("city_code"));
		member.setStateCode(rs.getString("state_code"));
		return member;
	}
}
