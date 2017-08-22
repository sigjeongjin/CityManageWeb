package com.city.web.service;

import java.sql.Connection;
import java.sql.SQLException;
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
	
	// 전체 페이지
	public MemberListPage getMemberListPage(int pageNum) {
		try (Connection conn = ConnectionProvider.getConnection()) {
			int total = memberDao.selectCount(conn);
			List<Member> content = memberDao.selectMemberList(
					conn, (pageNum - 1) * size, size);
			return new MemberListPage(total, pageNum, size, content);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	// 검색 페이지
	public MemberListPage getMemberListPage(int pageNum, String memberSelect, String memberInput) {
		try (Connection conn = ConnectionProvider.getConnection()) {
			int total = memberDao.selectCount(conn, memberSelect, memberInput);
			List<Member> content = memberDao.searchMemberList(
					conn, (pageNum - 1) * size, size, memberSelect, memberInput);	
			return new MemberListPage(total, pageNum, size, content);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}


	public Member search(String memberSelect, String memberInput) {
		try (Connection conn = ConnectionProvider.getConnection()) {

			Member mS = memberDao.selectSearchrList(conn, memberSelect, memberInput);

			if (mS == null) {
				System.out.println("memberList null");
				throw new NullPointerException();
			}
			return mS;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	
}
