package com.city.web.service;

import java.sql.Connection;
import java.sql.SQLException;

import com.city.model.Member;
import com.city.web.dao.MemberDao;

import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;

/* 
 * RegisterService 회원가입
 * 
 * 
 * */

public class RegisterService {

	private MemberDao memberDao = new MemberDao();

	public String register(Member member) {

		String flag = null;
		Connection conn = null;

		try {
			conn = ConnectionProvider.getConnection(); // transaction
			conn.setAutoCommit(false);

			String strId = memberDao.insertMember(conn, member);
			conn.commit();

			if (strId != null) {
				flag = "Y";
				return flag;
			} else {
				flag = "N";
				throw new SQLException();
			}
		} catch (SQLException e) {
			JdbcUtil.rollback(conn);
		} finally {
			JdbcUtil.close(conn);
		}
		return null;
	}
}
