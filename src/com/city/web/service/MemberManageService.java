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
 *  memberSearch 멤버 정보 검색			mS
 * 
 */

public class MemberManageService {

	private MemberDao memberDao = new MemberDao();
	private int size = 10;

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


	/**
	 * @param pageNum
	 * @param memberSelect
	 * @param memberInput
	 * @return
	 */
	public MemberListPage getMemberListPage(int pageNum, String memberSelect, String memberInput, String cityCode) {
		try (Connection conn = ConnectionProvider.getConnection()) {
			int total = memberDao.selectCount(conn, memberSelect, memberInput, cityCode);
			List<Member> content = memberDao.searchMemberList(conn, (pageNum - 1) * size, size, memberSelect, memberInput, cityCode);
			return new MemberListPage(total, pageNum, size, content);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public Member memberSelect(String memberId) {
		try (Connection conn = ConnectionProvider.getConnection()) {

			Member member = new Member();

			member = memberDao.selectById(conn, memberId);

			if (member == null) {
				throw new NullPointerException();
			}

			return member;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
