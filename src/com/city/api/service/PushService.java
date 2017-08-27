package com.city.api.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.city.api.dao.MemberDao;
import com.city.api.dao.PushDao;
import com.city.model.FavoritesInfo;
import com.city.model.Push;
import com.city.model.PushInfo;

import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;


/**
 * @author park
 * PushList조회	
 *
 */

public class PushService {

	private PushDao pushDao = new PushDao();

	/**
	 * @param memberId
	 * @param manageType
	 * @return
	 */
	public List<PushInfo> getPushHistoryList(String memberId, String manageType) {
		List<PushInfo> pushInfoList = new ArrayList<PushInfo>();
		Connection conn = null;

		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);

			pushInfoList = pushDao.selectPushHistoryListByMemberId(conn, memberId,manageType);
			conn.commit();

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("PUSH 이력 조회 실패");
			JdbcUtil.rollback(conn);
		} finally {
			JdbcUtil.close(conn);
		}
		return pushInfoList;
	}
}
