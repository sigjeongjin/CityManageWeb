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
import com.city.model.MemberAPI;
import com.city.model.State;

import jdbc.JdbcUtil;

public class MemberDao {

	public int updatePwdChange(Connection conn, String memberChangePwd , String memberId, String memberPwd) throws SQLException {
		PreparedStatement pstmt = null;
		int resultcode = 0;

		try {
			pstmt = conn.prepareStatement("update member set member_pwd=? where member_id=? and member_pwd=? ");
		
			pstmt.setString(1, memberChangePwd);
			pstmt.setString(2, memberId);
			pstmt.setString(3, memberPwd);
			resultcode = pstmt.executeUpdate();
			
			return resultcode;

		} finally {
			
			JdbcUtil.close(pstmt);
		}
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

	public MemberAPI selectPwdConfirm(Connection conn, String memberId, String memberPwd) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MemberAPI member = new MemberAPI();
		String Resultcode = "200";
		try {
			pstmt = conn.prepareStatement("select count(*) from member where member_id=? and member_pwd=? ");
			pstmt.setString(1, memberId);
			pstmt.setString(2, memberPwd);
			rs = pstmt.executeQuery();

			return member;

		} finally {

			JdbcUtil.close(pstmt);
		}
	}

	public List<City> selectCityInfo(Connection conn) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<City> city = new ArrayList<City>();
		String Resultcode = "200";
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

		String Resultcode = "200";

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

	public String selectByIdAndPwd(Connection conn, String memberId, String memberPwd) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String Resultcode = "200";
		try {
			pstmt = conn
					.prepareStatement("select member_id, member_pwd from member where member_id=? and member_pwd= ?");
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
		String Resultcode = "200";

		try {

			pstmt = conn.prepareStatement("insert into member "
					+ "(member_id, member_pwd, member_name, member_phone, member_email, member_photo, member_authorization, city_code, state_code) "
					+ "values (?, ?, ?, ?, ?, ?, ?, ?, ?)");
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
	public List<State> selectStateListByCityCode(Connection conn, String cityCode) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<State> state = new ArrayList<State>();

		String Resultcode = "200";
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

}
