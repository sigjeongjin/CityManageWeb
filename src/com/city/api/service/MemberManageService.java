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
	
	public MemberAPI memberLogin(String memberId, String memberPwd, MemberAPI memberAPI) {

		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);

			memberAPI = memberDao.selectByIdAndPwd(conn, memberId, memberPwd, memberAPI);
		} catch (SQLException e) {
			e.printStackTrace();
			JdbcUtil.rollback(conn);
		} finally {
			JdbcUtil.close(conn);
		}
		return memberAPI;
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

	public int memberRegister(Member member) {

		int resultCode = 0;

		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);

			resultCode = memberDao.insertMember(conn, member);
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			JdbcUtil.rollback(conn);
		} finally {
			JdbcUtil.close(conn);
		}
		return resultCode;
	}

	public int getMemberPwdConfirm(String memberId, String memberPwd) {

		int resultCode = 0;
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);

			resultCode = memberDao.selectPwdConfirm(conn, memberId, memberPwd);
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			JdbcUtil.rollback(conn);
		} finally {
			JdbcUtil.close(conn);
		}
		return resultCode;
	}

	/**
	 * 회원 패스 워드 변경
	 * @param memberId
	 * @param memberPwd
	 * @param memberNewPwd
	 * @return
	 */
	public int setMemberPwdChange(String memberId, String memberPwd, String memberNewPwd) {

		int resultCode = 0;

		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);

			resultCode = memberDao.updatePwdChange(conn, memberId, memberPwd, memberNewPwd);
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			JdbcUtil.rollback(conn);
		} finally {
			JdbcUtil.close(conn);
		}
		return resultCode;
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
