package com.city.api.service;

/*
 * loginservice 로그인
 * logoutservice 로그아웃
 *  
 *  */

import java.sql.Connection;
import java.sql.SQLException;

import com.city.api.dao.MemberDao;
import com.city.model.Member;

import jdbc.connection.ConnectionProvider;

public class LoginService {
	private MemberDao memberDao = new MemberDao();

	public User login(String id, String password) {
		try (Connection conn = ConnectionProvider.getConnection()) {
			Member member = memberDao.selectById(conn, id);
			if (member == null) {
				throw new LoginFailException();
			}
			if (!member.matchPassword(password)) {
				throw new LoginFailException();
			}
			return new User(member.getMemberId(), member.getMemberName());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

		
	}





