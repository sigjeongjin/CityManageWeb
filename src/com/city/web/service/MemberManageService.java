package com.city.web.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.city.model.Member;
import com.city.web.dao.MemberDao;

import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;

public class MemberManageService {

	private Connection conn = null;
	
	private MemberDao memberDao = new MemberDao();
	
	private int size = 10;
	
	/** 
	 * 아이디와 비밀번호로 아이디와 이름 가져오기
	 * @param memberId
	 * @param memberPwd
	 * @return
	 */
	public HashMap<String, String> getIdAndName(String memberId, String memberPwd) {
		
		HashMap<String, String> memberInfo = new HashMap<String, String>();
				
		try {
			conn = ConnectionProvider.getConnection(); // transaction
			conn.setAutoCommit(false);
			
			memberInfo = memberDao.selectIdAndName(conn, memberId, memberPwd);
			
			if (StringUtils.isEmpty(memberInfo.get("memberId"))) {
				memberInfo.put("error", "등록 되지 않은 회원 이거나 로그인 정보를 잘못 입력하셨습니다.");
			}
			conn.commit();
		}  catch (SQLException e) {
			e.printStackTrace();
			JdbcUtil.rollback(conn);
		} finally {
			JdbcUtil.close(conn);
		}
		return memberInfo;
	}

	/**
	 * @param member
	 * @return
	 */
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
	 * @param selectBox
	 * @param searchText
	 * @param cityCode
	 * @return
	 */
	public MemberListPage getMemberListPage(int pageNum, String selectBox, String searchText, String cityCode) {
		int total = 0;
		List<Member> content = new ArrayList<>();
		
		try {		
			conn  = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);
			
			total = memberDao.selectCount(conn, selectBox, searchText, cityCode);
			conn.commit();
			
			content = memberDao.searchMemberList(conn, (pageNum - 1) * size, size, selectBox, searchText, cityCode);			
		} catch (SQLException e) {
			e.printStackTrace();
			JdbcUtil.rollback(conn);
		} finally {
			JdbcUtil.close(conn);
		}
		return new MemberListPage(total, pageNum, size, content);
	}

	/**
	 * @param memberId
	 * @return
	 */
	public Member getMemberInfo(String memberId) {
		Member member = new Member();
		
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);

			member = memberDao.selectById(conn, memberId);	
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			JdbcUtil.rollback(conn);
		} finally {
			JdbcUtil.close(conn);
		}
		return member;
	}

	/** 최근 등록 된 센서정보을 가져오기
	 * @return
	 */
	public List<Member> getMemberNameList() {	
		List<Member> memberName = new ArrayList<>();
		
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);
			
			memberName = memberDao.selecMemberNameList(conn);
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			JdbcUtil.rollback(conn);
		} finally {
			JdbcUtil.close(conn);
		}
		return memberName;
	}
}