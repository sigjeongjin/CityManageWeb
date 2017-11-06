package com.city.web.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.city.model.Member;
import com.city.web.dao.MemberDao;

import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;


public class MemberManageService {

	private MemberDao memberDao = new MemberDao();
	
	private int size = 10;
	
	Connection conn = null;

	public String MemberUpdate(Member member) {

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

	public Member getMemberInfo(String memberId) {
		Member member = new Member();
		
		try {

			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);

			member = memberDao.selectById(conn, memberId);
			
			conn.commit();
		} catch (SQLException e) {
			JdbcUtil.rollback(conn);
			throw new RuntimeException(e);
		} finally {
			JdbcUtil.close(conn);
		}
		return member;
	}


	public List<Member> memberNameList() {
		
		List<Member> memberName = new ArrayList<>();
		try (Connection conn = ConnectionProvider.getConnection()) {
			
			memberName = memberDao.selecMemberNameList(conn);
			
			return memberName;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
