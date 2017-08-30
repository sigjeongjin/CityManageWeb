package com.city.api.service;

import java.sql.Connection;

import com.city.api.dao.MemberDao;
import com.city.api.dao.PushDao;
import com.city.model.Push;

import jdbc.connection.ConnectionProvider;

/* PushActionRegisterService 위험 알림 푸쉬 		pars
 * PushRecordListService 푸시 이력 리스트 조화     	prls
 * */

public class PushService {
	
	private PushDao pushDao = new PushDao();
	
	public String push(Push push) { 

	String re = null;
	Connection conn = null;
	
//	
//	try { 
//		
//		conn = ConnectionProvider.getConnection(); // transaction
//		conn.setAutoCommit(false);
//	
//		String strId = pushDao.selectByIdAndsensorId(conn, memberId, sensorId);
//		conn.commit();
//	
	return null;
	


	}
	
}
	
