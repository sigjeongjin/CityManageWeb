package com.city.api.service;

import java.sql.Connection;
import java.sql.SQLException;

import com.city.model.Member;
import com.city.api.dao.MemberDao;

import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;

/* 
 *  memberLogin	      로그인 			mL
 *  memberIdCheck  아이디 조회			mIC
 *  memberRegister 회원가입 			mR
 * 	memberInfo 멤버 상세 정보 조회 		mI
 * 	memberList 멤버 리스트  			mL
 *  memberUpdate 멤버 정보 업데이트		mU
 *  memberDelete 멤버 정보 삭제			mD
 *  memberSearch 멤버 정보 검색			mS
 */

public class MemberManageService {

	private MemberDao memberDao = new MemberDao();

	// memberLogin 로그인 mL
	public String login(String memberId, String memberPwd) {

		Connection conn = null;
		String mL = "";

		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);

			String resultCode = memberDao.selectByIdAndPwd(conn, memberId, memberPwd);
			conn.commit();

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
			conn = ConnectionProvider.getConnection(); // transaction
			conn.setAutoCommit(false);

			int resultCode = memberDao.insertMember(conn, member);
			conn.commit();

			if (resultCode == 1) {
				mR = "Y";
				return mR;
			} else {
				mR = "N";
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

	public String pwdChange(String memberChangePwd, String memberId) {
		int pwdmember = 0;
		String resultCode = "";
		Connection conn = null;

		try {
			conn = ConnectionProvider.getConnection(); // transaction
			conn.setAutoCommit(false);

			pwdmember = memberDao.updatePwdChange(conn, memberChangePwd, memberId);

			if (pwdmember == 1) {
				resultCode = "Y";
			} else {
				throw new SQLException();
			}

			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("변경실패");
			JdbcUtil.rollback(conn);
		} finally {
			JdbcUtil.close(conn);
		}
		return resultCode;
	}

	public String pwdConfirm(String memberId, String memberPwd) {

		String resultCode = "";
		Connection conn = null;

		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);

			resultCode = memberDao.selectPwdConfirm(conn, memberId, memberPwd);
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("다른 비밀번호 입니다.");
			JdbcUtil.rollback(conn);
		} finally {
			JdbcUtil.close(conn);
		}
		return resultCode;
	}

	public String memberPhotoChange(String memberId, String memberPhoto) {

		String rs = null;
		Connection conn = null;

		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);

			String strId = memberDao.updateBymemberIdAndmemberPhoto(conn, memberId, memberPhoto);
			conn.commit();

			if (strId != null) {
				rs = "200";
				return rs;
			} else {
				rs = "400";
				throw new SQLException();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("변경 실패");
			JdbcUtil.rollback(conn);
		} finally {
			JdbcUtil.close(conn);
		}
		return null;
	}

}
