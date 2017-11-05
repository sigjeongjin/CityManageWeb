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
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;

import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;


/**
 * @author park
 * PushList조회	
 *
 */

public class PushService {

	private PushDao pushDao = new PushDao();
	
	private static final String ApiKey = "AAAA1ij4yDw:APA91bFGrAK0yYTqjiwkeevKztPKSTbqqYM45_Mm2m1Uhgk-9JyFEEmaeJP-JgHVd5ueXfQlctRM6dcqEoPrb4-YqdywJ3lLw6GOgm3lNP34QLrcQdAWIMJAJ25pLK6BOStbOUq4Gfl-";
	
	private String MESSAGE_ID = String.valueOf(Math.random() % 100 + 1);
	private boolean SHOW_ON_IDLE = true;
	private int LIVE_TIME = 1;
	private int RETRY = 2;
	
	private Connection conn = null;
	
	/**
	 * @param memberId
	 * @param manageType
	 * @return
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
	 * @param PushInfo
	 * @return
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
	 * @param PushInfo
	 * @return
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


	/* ApiKey → FireBase에서 가져온 서버 키
	 * MESSAGE_ID → 메세지 고유 ID
	 * SHOW_ON_IDLE → 앱이 비활성화 상태일때 PUSH를 보여줄 것인지 
	 * LIVE_TIME → 비활성화 상태일때 FCM가 메시지를 유효화하는 시간
	 * RETRY → 메시지 전송실패시 재시도 횟수
	 * */
	
	public void sendPush(ArrayList<String> tokenList, String title, String content) {
		Sender sender = new Sender(ApiKey);
		Message message = new Message.Builder()
				.collapseKey(MESSAGE_ID)
				.delayWhileIdle(SHOW_ON_IDLE)
				.timeToLive(LIVE_TIME)
				.addData("title", title)
				.addData("contents", content)
				.build();
		
		MulticastResult result;
		try {
			result = sender.send(message, tokenList, RETRY);
			if(result != null){
				List<Result> resultList = result.getResults();
				
				//이것은 뭔 해괴 망측한 소스코드일까요.....
				for(Result result2: resultList){
					//System.out.println(result2.getErrorCodeName());
				}
			}
		} catch (Exception e) {
			throw new RuntimeException();
		}	
	}
}
