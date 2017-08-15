package com.city.web.service;

import java.sql.Connection;
import java.sql.SQLException;

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

	public void MemberUpdate(Member memberReq) {
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);

			System.out.println("memberId" + memberReq.getMemberId());
			
			Member member = memberDao.selectById(conn, memberReq.getMemberId());
			if (member == null) {
				throw new NullPointerException();
			}
			
			System.out.println("없니??" + memberReq.getMemberId());
			System.out.println("없니??" + memberReq.getMemberPwd());
			System.out.println("없니??" + memberReq.getMemberName());
//			memberDao.update(conn, new Member(
//					memberReq.getMemberId(),
//					memberReq.getMemberPwd(),
//					memberReq.getMemberName(),
//					memberReq.getMemberPhone(),
//					memberReq.getMemberEmail(),
//					memberReq.getMemberPhoto(),
//					memberReq.getMemberAuthorization(),
//					memberReq.getCityGeocode(),
//					memberReq.getStateGeocode())
//			);
			
			memberDao.update(conn, memberReq);
			conn.commit();
		} catch (SQLException e) {
			JdbcUtil.rollback(conn);
			throw new RuntimeException(e);
		} finally {
			JdbcUtil.close(conn);
		}
	}
}
