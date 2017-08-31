package com.city.api.service;

/*
 * loginservice 로그인
 * logoutservice 로그아웃
 *  
 *  */

import java.sql.Connection;
import java.sql.SQLException;

import com.city.api.dao.MemberDao;

import jdbc.connection.ConnectionProvider;

public class LoginService {
	private MemberDao memberDao = new MemberDao();

	public String login(String memberId, String memberPwd) {
		try (Connection conn = ConnectionProvider.getConnection()) {
			String resultCode = memberDao.selectByIdAndPwd(conn, memberId, memberPwd);	
			return resultCode;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
