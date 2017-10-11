package com.city.api.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.city.model.City;
import com.city.model.Member;
import com.city.model.State;

import jdbc.JdbcUtil;

public class MemberDao {

	// 로그인
	public String selectByIdAndPwd(Connection conn, String memberId, String memberPwd) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String resultCode = null;
		
		try {
			pstmt = conn
					.prepareStatement("select member_id, member_pwd from member "
							+ "where member_id=? and member_pwd=? "
							+ "and member_authorization='app_user'"
							+ "and member_delete_code='N'");
			pstmt.setString(1, memberId);
			pstmt.setString(2, memberPwd);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				resultCode = "200";
				return resultCode;
			} 					
		} catch (SQLException e) {
			e.printStackTrace();
			JdbcUtil.rollback(conn);
		}finally {
			JdbcUtil.close(pstmt);
		}
		return resultCode;
	}
	
	// 아이디 조회
	public String selectById(Connection conn, String memberId) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String resultCode = null;
		
		try {
			pstmt = conn.prepareStatement(
					"select member_id memberId from member where member_id=?");
			pstmt.setString(1, memberId);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Member member = new Member();
				member.setMemberId(rs.getString("memberId"));
				resultCode = "200";
				return resultCode;
			}		
		} catch (SQLException e) {
			e.printStackTrace();
			JdbcUtil.rollback(conn);
		}finally {
			JdbcUtil.close(pstmt);
		}
		return resultCode;
	}
	
	// 회원가입
	public int insertMember(Connection conn, Member member) throws SQLException {
		PreparedStatement pstmt = null;
		int resultCode = 0;
		
		try {
			pstmt = conn.prepareStatement("insert into member "
					+ "(member_id, member_pwd, member_name, member_phone, member_email, "
					+ "member_photo, member_authorization) "
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
			JdbcUtil.rollback(conn);
		}finally {
			JdbcUtil.close(pstmt);
		}
		return resultCode;
	}

	// 맴버 비밀번호 확인
	public String selectPwdConfirm(Connection conn, String memberId, String memberPwd) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String resultCode = null;
		
		try {
			pstmt = conn.prepareStatement("select count(*) as count from member where member_id=? and member_pwd=?");
			pstmt.setString(1, memberId);
			pstmt.setString(2, memberPwd);
			rs = pstmt.executeQuery();
			if(rs.next()) { 
				String count = rs.getString("count");
				if(count.equals("1")) {
					resultCode = "200";
					return resultCode;
				} else {
					return resultCode;
				}
			}		
		} catch (SQLException e) {
			e.printStackTrace();
			JdbcUtil.rollback(conn);
		}finally {
			JdbcUtil.close(pstmt);
		}
		return resultCode;
	}
			
	// 맴버 비밀번호 변경
	public int updatePwdChange(Connection conn, String memberId , String memberNewPwd) throws SQLException {
		PreparedStatement pstmt = null;
		int resultCode = 0;

		try {
			pstmt = conn.prepareStatement("update member set member_pwd=? where member_id=? ");
			
			pstmt.setString(1, memberNewPwd);
			pstmt.setString(2, memberId);

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
	
	// 맴버 사진 변경
	public int memberPhotoChange(Connection conn, String memberId, String memberPhoto) {
		PreparedStatement pstmt = null;
		int resultCode = 0;

		try {
			pstmt = conn.prepareStatement("update member set member_Photo=? where member_id=? ");
			
			pstmt.setString(1, memberPhoto);
			pstmt.setString(2, memberId);

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

	
	public int updateCityStateInfoRegiste(Connection conn, String cityCode, String stateCode, String memberId, String memberPwd) throws SQLException {
		PreparedStatement pstmt = null;
		int resultcode = 0;
	
		try {
			pstmt = conn.prepareStatement("update member set city_code=?, state_code=? where member_id=? and member_pwd=?");
			pstmt.setString(1, cityCode);
			pstmt.setString(2, stateCode);
			pstmt.setString(3, memberId);
			pstmt.setString(4, memberPwd);
			resultcode = pstmt.executeUpdate();

			return resultcode;
		} finally {
			JdbcUtil.close(pstmt);
		}
	}
	
	public List<State> selectStateListByCityCode(Connection conn, String cityCode) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<State> state = new ArrayList<State>();

		try {
			pstmt = conn.prepareStatement(
					"select state_code as stateCode, state_name as stateName from address_state where city_code=?");
			pstmt.setString(1, cityCode);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				state.add(new State(rs.getString("stateCode"), rs.getString("stateName")));
			}
			return state;

		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
	}

	public List<City> selectCityInfo(Connection conn) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<City> city = new ArrayList<City>();

		try {
			pstmt = conn.prepareStatement("select city_code as cityCode,city_name as cityName from address_city");
			rs = pstmt.executeQuery();

			while (rs.next()) {
				city.add(new City(rs.getString("cityCode"), rs.getString("cityName")));

			}

			return city;
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
	}

	public String updateBymemberIdAndmemberPhoto(Connection conn, String memberId, String memberPhoto)
			throws SQLException {
		PreparedStatement pstmt = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			pstmt = conn.prepareStatement(
					"update citymanage.member set member_Photo=? where member_id=? and member_Photo=? ");
			pstmt.setString(1, memberId);
			pstmt.setString(2, memberPhoto);
			rs = pstmt.executeQuery();
			return Integer.toString(pstmt.executeUpdate());

		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(stmt);
			JdbcUtil.close(pstmt);
		}
	}
}