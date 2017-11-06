package com.city.web.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.city.model.Push;
import com.city.web.dao.PushDao;

import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;



/**
 * push 서비스
 * @author com
 *
 */
public class PushService {
	
	private Connection conn;
	
	private PushDao pushDao = new PushDao();
	
	private int size = 10;
	
	/**
	 * push 테이블 정보 및 페이징 관련 정보
	 * @param pageNum
	 * @return
	 */
	public PushHistoryListPage getPushHistoryListPage(int pageNum, String selectBox, String searchText) {
		
		int total = 0;
		
		List<Push> content = new ArrayList<>();
		
		try {
			conn  = ConnectionProvider.getConnection();
			
			total =  pushDao.selectPushCount(conn, selectBox, searchText);
			
			content = pushDao.selectPushHistoryList(conn, (pageNum - 1) * size, size, selectBox, searchText);
		}  catch (SQLException e) {
			e.printStackTrace();
			JdbcUtil.rollback(conn);
		}
		return new PushHistoryListPage(total, pageNum, size, content);
	}
}