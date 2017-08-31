package com.city.web.service;

/*
 * loginservice 로그인
 * logoutservice 로그아웃
 *  
 *  */

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

import com.city.web.dao.MemberDao;

import jdbc.connection.ConnectionProvider;

public class LoginService {

	private MemberDao memberDao = new MemberDao();

	public HashMap<String, String> login(String memberId, String memberPwd) {
		try (Connection conn = ConnectionProvider.getConnection()) {
			
			HashMap<String, String> idAndName = new HashMap<String, String>();
			
			idAndName = memberDao.selectByIdAndPwd(conn, memberId, memberPwd);
			
			if (idAndName == null) {
				System.out.println("LoginSFail-1");
				throw new NullPointerException();
			}

			return idAndName;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
