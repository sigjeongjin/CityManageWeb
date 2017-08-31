package com.city.web.service;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;


import org.apache.commons.lang3.StringUtils;
import com.city.web.dao.MemberDao;


import jdbc.connection.ConnectionProvider;

public class LoginService {

	private MemberDao memberDao = new MemberDao();

	public HashMap<String, String> login(String memberId, String memberPwd) {
		
		HashMap<String, String> idAndName = new HashMap<String, String>();
		try (Connection conn = ConnectionProvider.getConnection()) {
			
			idAndName = memberDao.selectByIdAndPwd(conn, memberId, memberPwd);
			
			if (StringUtils.isEmpty(idAndName.get("memberId"))) {
				idAndName.put("error", "등록 되지 않은 회원 이거나 로그인 정보를 잘못 입력하셨습니다.");
			}

			return idAndName;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
