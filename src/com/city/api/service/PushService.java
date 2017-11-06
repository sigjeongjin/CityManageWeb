package com.city.api.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.city.api.dao.PushDao;
import com.city.model.PushInfo;
import com.city.model.PushResultInfo;
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Sender;

import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;

public class PushService {

	private PushDao pushDao = new PushDao();
	
	private static final String ApiKey = "AAAA1ij4yDw:APA91bFGrAK0yYTqjiwkeevKztPKSTbqqYM45_Mm2m1Uhgk-9JyFEEmaeJP-JgHVd5ueXfQlctRM6dcqEoPrb4-YqdywJ3lLw6GOgm3lNP34QLrcQdAWIMJAJ25pLK6BOStbOUq4Gfl-";
	
	private String MESSAGE_ID = String.valueOf(Math.random() % 100 + 1);
	private boolean SHOW_ON_IDLE = true;
	private int LIVE_TIME = 1;
	private int RETRY = 2;
	
	private Connection conn = null;
	
	/**
	 * PUSH 히스토리 리스트 조회
	 * @param memberId
	 * @param manageType
	 * @return List<PushResultInfo>
	 */
	public List<PushResultInfo> getPushHistoryList(String memberId, String manageType) {
		List<PushResultInfo> pushInfoList = new ArrayList<PushResultInfo>();

		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);

			pushInfoList = pushDao.selectPushHistoryListByMemberId(conn, memberId,manageType);
			conn.commit();

		} catch (SQLException e) {
			e.printStackTrace();
			JdbcUtil.rollback(conn);
		} finally {
			JdbcUtil.close(conn);
		}
		return pushInfoList;
	}

	/**
	 * PUSH 토큰 저장
	 * @param PushInfo
	 * @return resultCode
	 */
	public int pushTokenRegister(PushInfo pushInfo) throws SQLException{
		int resultCode = 0;

		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);

			resultCode = pushDao.insertPushToken(conn, pushInfo);
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
	 * PUSH 토큰 업데이트
	 * @param PushInfo
	 * @return resultCode
	 */
	public int pushTokenUpdate(PushInfo pushInfo) {
		
		int resultCode = 0;
		
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);

			resultCode = pushDao.updatePushToken(conn, pushInfo);
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			JdbcUtil.rollback(conn);
		} finally {
			JdbcUtil.close(conn);
		}
		return resultCode;
	}

	public ArrayList<String> sendTokenList() throws SQLException{
		Connection conn = null;
		ArrayList<String> pushTokenList = null;

		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);

			pushTokenList = pushDao.selectPushList(conn);
			
			conn.commit();
			
		} catch (SQLException e) {
			e.printStackTrace();
			JdbcUtil.rollback(conn);
		} finally {
			JdbcUtil.close(conn);
		}
		return pushTokenList;
	}

	
	/**
	 * PUSH 보내기
	 * @param tokenList
	 * @param title
	 * @param content
	 */
	public void senJdPush(ArrayList<String> tokenList, String title, String content) {
		// ApiKey → FireBase에서 가져온 서버 키
		// MESSAGE_ID → 메세지 고유 ID
		// SHOW_ON_IDLE → 앱이 비활성화 상태일때 PUSH를 보여줄 것인지 
		// LIVE_TIME → 비활성화 상태일때 FCM가 메시지를 유효화하는 시간
		// RETRY → 메시지 전송실패시 재시도 횟수
		 
		Sender sender = new Sender(ApiKey);
		
		Message message = new Message.Builder()
				.collapseKey(MESSAGE_ID)
				.delayWhileIdle(SHOW_ON_IDLE)
				.timeToLive(LIVE_TIME)
				.addData("title", title)
				.addData("contents", content)
				.build();
		
		try {
			MulticastResult multicastResult = sender.send(message, tokenList, RETRY);
			
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
}
