package com.city.web.service;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;


import org.apache.commons.lang3.StringUtils;
import com.city.web.dao.MemberDao;

import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;

public class LoginService {

	private MemberDao memberDao = new MemberDao();
	private Connection conn = null;

	public HashMap<String, String> login(String memberId, String memberPwd) {
		
		HashMap<String, String> memberInfo = new HashMap<String, String>();
				
		try {
			conn = ConnectionProvider.getConnection(); // transaction
			conn.setAutoCommit(false);
			
			memberInfo = memberDao.selectByIdAndPwd(conn, memberId, memberPwd);
			
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
}
