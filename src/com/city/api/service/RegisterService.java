package com.city.api.service;

import java.sql.Connection;
import java.sql.SQLException;

import com.city.model.Member;
import com.city.api.dao.MemberDao;

import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;

/* 
 * RegisterService 회원가입				  rs
 * PwdConfirmService 비밀번호 확인			  pcs
 * PwdChangService 비밀번호 변경			  pcs
 * ProfileImageChangeService 프로필 사진 변경   pics
 * 
 * 
 * */


public class RegisterService {
	private MemberDao memberDao = new MemberDao();

	public String register(Member member) {

		String rs = null;
		Connection conn = null;

		try {
			conn = ConnectionProvider.getConnection(); // transaction
			conn.setAutoCommit(false);

			String strId = memberDao.insertMember(conn, member);
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
			System.out.println("Register fail");
			JdbcUtil.rollback(conn);
		} finally {
			JdbcUtil.close(conn);
		}
		return null;
	}
}
