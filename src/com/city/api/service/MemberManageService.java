package com.city.api.service;

import java.sql.Connection;
import java.sql.SQLException;

import com.city.model.Member;
import com.city.api.dao.MemberDao;

import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;

/* 
 *  memberLogin	       	로그인 			mL
 *  memberIdCheck  		아이디 조회		mIC
 *  memberRegister 		회원가입 			mR
 *  memberPwdConfirm	맴버 비밀번호 확인 	mPC
 * 	memberPwdChange		맴버 비밀번호 변경 	mPC
 * 	memberPhotoChange	맴버 사진 변경 		mPC
 */

public class MemberManageService {

	private MemberDao memberDao = new MemberDao();

	// memberLogin 로그인 mL
	public String memberLogin(String memberId, String memberPwd) {

		Connection conn = null;
		String mL = "";

		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);

			String resultCode = memberDao.selectByIdAndPwd(conn, memberId, memberPwd);

			if (resultCode == "200") {
				mL = "Y";
				return mL;
			} else {
				mL = "N";
				return mL;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			JdbcUtil.rollback(conn);
		} finally {
			JdbcUtil.close(conn);
		}
		return null;
	}

	// memberLogin 아이디 조회 mIC
	public String memberIdCheck(String memberId) {

		Connection conn = null;
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

	// RegisterRegister 회원가입 mR
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
	
	// memberPwdConfirm	맴버 비밀번호 확인 mPC
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
				return mPC;
			} else {
				mPC = "N";
				return mPC;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JdbcUtil.rollback(conn);
		} finally {
			JdbcUtil.close(conn);
		}
		return null;
	}

	// memberPwdChange 맴버 비밀번호 변경 mPC
	public String memberPwdChange(String memberId, String memberNewPwd) {
		
		Connection conn = null;
		String mPC = "";
		
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);

			int resultCode = memberDao.updatePwdChange(conn, memberId, memberNewPwd);
			conn.commit();

			if (resultCode == 1) {
				mPC = "Y";
				return mPC;
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
			return null;
	}
	
	// memberPhotoChange 맴버 사진 변경 mPC
	public String memberPhotoChange(String memberId, String memberPhoto) {

		Connection conn = null;
		String mPC = "";
		
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);

			int resultCode = memberDao.memberPhotoChange(conn, memberId, memberPhoto);
			conn.commit();

			if (resultCode == 1) {
				mPC = "Y";
				return mPC;
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
			return null;
	}
}
