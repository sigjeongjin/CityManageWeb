package com.city.web.service;

import java.sql.Connection;
import java.sql.SQLException;

import com.city.model.Member;
import com.city.web.dao.MemberDao;

import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;

/* 
 *  memberRegister 회원가입 			mR
 * 	memberInfo 멤버 상세 정보 조회 		mI
 * 	memberList 멤버 리스트  			mL
 *  memberUpdate 멤버 정보 업데이트		mU
 *  memberDelete 멤버 정보 삭제			mD
 *  memberSearch 멤버 정보 검색			mS
 * 
 */

public class RegisterService {

	private MemberDao memberDao = new MemberDao();

	public String register(Member member) {

		Connection conn = null;
		String rs = null;

		try {
			conn = ConnectionProvider.getConnection(); // transaction
			conn.setAutoCommit(false);

			int resultCode = memberDao.insertMember(conn, member);
			conn.commit();
			if (resultCode == 1) {
				rs = "Y";
				return rs;
			} else {
				rs = "N";
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
