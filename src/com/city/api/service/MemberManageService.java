package com.city.api.service;

import java.sql.Connection;
import java.sql.SQLException;

import com.city.api.dao.MemberDao;
import com.city.model.Member;
import com.city.model.MemberAPI;

import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;

public class MemberManageService {

	private MemberDao memberDao = new MemberDao();
	private Connection conn = null;
	
	public MemberAPI memberLogin(String memberId, String memberPwd) {

		MemberAPI member = new MemberAPI();

		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);

			member = memberDao.selectByIdAndPwd(conn, memberId, memberPwd);
		} catch (SQLException e) {
			e.printStackTrace();
			JdbcUtil.rollback(conn);
		} finally {
			JdbcUtil.close(conn);
		}
		return member;
	}

	public String memberIdCheck(String memberId) {

		String mIC = "";

		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);

			String resultCode = memberDao.selectById(conn, memberId);
			conn.commit();

			if (resultCode == "200") {
				mIC = "Y";
				return mIC;
			} else {
				mIC = "N";
				throw new SQLException();
			}

		} catch (SQLException e) {
			e.printStackTrace();
			JdbcUtil.rollback(conn);
		} finally {
			JdbcUtil.close(conn);
		}
		return null;
	}

	public String memberRegister(Member member) {

		Connection conn = null;
		String mR = "";

		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);

			int resultCode = memberDao.insertMember(conn, member);

			if (resultCode == 1) {
				mR = "Y";
			} else {
				mR = "N";
				throw new SQLException();
			}
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			JdbcUtil.rollback(conn);
		} finally {
			JdbcUtil.close(conn);
		}
		return mR;
	}

	public String memberPwdConfirm(String memberId, String memberPwd) {

		Connection conn = null;
		String mPC = "";

		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);

			String resultCode = memberDao.selectPwdConfirm(conn, memberId, memberPwd);
			conn.commit();

			if (resultCode == "200") {
				mPC = "Y";
			} else {
				mPC = "N";
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JdbcUtil.rollback(conn);
		} finally {
			JdbcUtil.close(conn);
		}
		return mPC;
	}

	// memberPwdChange 맴버 비밀번호 변경 mPC
	public String memberPwdChange(String memberId, String memberNewPwd) {

		String mPC = "";

		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);

			int resultCode = memberDao.updatePwdChange(conn, memberId, memberNewPwd);
			conn.commit();

			if (resultCode == 1) {
				mPC = "Y";
			} else {
				mPC = "N";
				throw new SQLException();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JdbcUtil.rollback(conn);
		} finally {
			JdbcUtil.close(conn);
		}
		return mPC;
	}

	// memberPhotoChange 맴버 사진 변경 mPC
	public String memberPhotoChange(String memberId, String memberPhoto) {

		String mPC = "";

		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);

			int resultCode = memberDao.memberPhotoChange(conn, memberId, memberPhoto);
			conn.commit();

			if (resultCode == 1) {
				mPC = "Y";
			} else {
				mPC = "N";
				throw new SQLException();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JdbcUtil.rollback(conn);
		} finally {
			JdbcUtil.close(conn);
		}
		return mPC;
	}

	public String memberPhoneSelect(String memberId) {
		String memberPhone = "";
		
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);

			memberPhone = memberDao.selectPhone(conn, memberId);
			conn.commit();

			if (memberPhone == null) {
				memberPhone = "N";
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JdbcUtil.rollback(conn);
		} finally {
			JdbcUtil.close(conn);
		}
		return memberPhone;
	}
}
