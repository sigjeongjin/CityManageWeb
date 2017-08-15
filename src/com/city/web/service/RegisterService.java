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

	public void register(Member memberReq) {
		Connection conn = null;

		try {
			conn = ConnectionProvider.getConnection(); // transaction
			conn.setAutoCommit(false);

			Member member = memberDao.selectById(conn, memberReq.getMemberId());
			
			if (member != null) {
				JdbcUtil.rollback(conn);
				throw new RuntimeException();
			}
			
			memberDao.insertMember(conn, new Member(
					memberReq.getMemberId(),
					memberReq.getMemberPwd(),
					memberReq.getMemberName(),
					memberReq.getMemberPhone(),
					memberReq.getMemberEmail(),
					memberReq.getMemberPhoto(),
					memberReq.getMemberAuthorization(),
					memberReq.getCityGeocode(),
					memberReq.getStateGeocode())
			);
			
			System.out.println("회원가입 OK");
			conn.commit();
		} catch (SQLException e) {
			JdbcUtil.rollback(conn);
		} finally {
			JdbcUtil.close(conn);
		}
	}
}
