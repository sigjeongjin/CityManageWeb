package com.city.web.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.city.model.Push;
import com.city.web.dao.PushDao;

import jdbc.connection.ConnectionProvider;



/**
 * push 서비스
 * @author com
 *
 */
public class PushService {
	
	PushDao pushDao = new PushDao();
	private int size = 10;
	
	/**push 테이블 정보 및 페이징 관련 정보
	 * @param pageNum
	 * @return
	 */
	public PushHistoryListPage getPushHistoryListPage(int pageNum, String searchText, String searchSelect) {
		try (Connection conn = ConnectionProvider.getConnection()) {
			int total = pushDao.selectCount(conn,searchText,searchSelect);
			List<Push> content = pushDao.selectPushHistoryList(conn, (pageNum - 1) * size, size, searchText, searchSelect);
			return new PushHistoryListPage(total, pageNum, size, content);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
