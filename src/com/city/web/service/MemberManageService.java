package com.city.web.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.city.model.Member;
import com.city.web.dao.MemberDao;

import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;

/*   
 * 	memberInfo 멤버 상세 정보 조회 		mI
 * 	memberList 멤버 리스트  			mL
 *  memberUpdate 멤버 정보 업데이트		mU
 *  memberDelete 멤버 정보 삭제			mD
 *  
 * 
 */

public class MemberManageService {

	private MemberDao memberDao = new MemberDao();

	public String MemberUpdate(Member member) {

		Connection conn = null;
		String mU = null;

		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);

			String strId = memberDao.update(conn, member);
			conn.commit();
			if (strId != null) {
				mU = "Y";
				return mU;
			} else {
				mU = "N";
				throw new SQLException();
			}
		} catch (SQLException e) {
			JdbcUtil.rollback(conn);
			throw new RuntimeException(e);
		} finally {
			JdbcUtil.close(conn);
		}
	}

	public List<Member> MemberList() {
		try (Connection conn = ConnectionProvider.getConnection()) {

			List<Member> mL = new ArrayList<Member>();

			mL = memberDao.selectMemberList(conn);

			if (mL == null) {
				System.out.println("memberList null");
				throw new NullPointerException();
			}
			return mL;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
