package com.city.web.service;

/*
 * loginservice 로그인
 * logoutservice 로그아웃
 *  
 *  */

import java.sql.Connection;
import java.sql.SQLException;

import com.city.model.Member;
import com.city.web.dao.MemberDao;

import jdbc.connection.ConnectionProvider;

public class LoginService {

	private MemberDao memberDao = new MemberDao();

	public Member login(String memberId, String memberPwd) {
		try (Connection conn = ConnectionProvider.getConnection()) {
			Member member = memberDao.selectById(conn, memberId);
			if (member == null) {
				System.out.println("LoginSFail-1");
				throw new RuntimeException();
			}
			if (!member.matchPassword(memberPwd)) {
				System.out.println("LoginSFail-2");
				throw new RuntimeException();
			}
			return new Member(member.getMemberId(), member.getMemberName());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
