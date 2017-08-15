package com.city.api.service;

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

public class LoginServiceApp {
//	private MemberDao memberDao = new MemberDao();
//
//	public Member login(String memberId, String memberPwd) {
//		try (Connection conn = ConnectionProvider.getConnection()) {
//			Member member = memberDao.select(conn, memberId);
//			if (member == null) {
//				throw new LoginFailExceptionApp();
//			}
//			if (!member.matchPassword(memberId)) {
//				throw new LoginFailExceptionApp();
//			}
//			return new Member(member.getMemberId(), member.getMemberName());
//		} catch (SQLException e) {
//			throw new RuntimeException(e);
//		}
//	}
}
