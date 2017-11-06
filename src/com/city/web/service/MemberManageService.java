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
	
	private int size = 10; // memberListPage
	
	/** 회원가입
	 * @param member
	 * @return
	 */
	public int setMember(Member member) {
		
		int resultCode = 0;

		try {
			conn = ConnectionProvider.getConnection(); // transaction
			conn.setAutoCommit(false);

			resultCode = memberDao.insertMember(conn, member);
			
			conn.commit();	
			
		} catch (SQLException e) {
			e.printStackTrace();
			JdbcUtil.rollback(conn);
		} finally {
			JdbcUtil.close(conn);
		}
		return resultCode;
	}
	
	/** 
	 * 로그인
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
	 * 회원정보 수정
	 * @param member
	 * @return
	 */
	public int modifyMember(Member member) {

		Connection conn = null;
		int resultCode = 0;

		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);

			resultCode = memberDao.updateMember(conn, member);
			
			conn.commit();
			
		} catch (SQLException e) {
			JdbcUtil.rollback(conn);
		} finally {
			JdbcUtil.close(conn);
		}
		return resultCode;
	}

	/**
	 * 회원정보 리스트
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
			
			total = memberDao.selectMemberCount(conn, selectBox, searchText, cityCode);
			
			content = memberDao.selectMemberList(conn, (pageNum - 1) * size, size, selectBox, searchText, cityCode);			
		} catch (SQLException e) {
			e.printStackTrace();
			JdbcUtil.rollback(conn);
		} finally {
			JdbcUtil.close(conn);
		}
		return new MemberListPage(total, pageNum, size, content);
	}

	/**
	 * 멤버 상세정보
	 * @param memberId
	 * @return
	 */
	public Member getMemberInfo(String memberId) {
		Member member = new Member();
		
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);

			member = memberDao.selectMemberInfo(conn, memberId);	
			
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
			
			memberName = memberDao.selectMemberNameList(conn);
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