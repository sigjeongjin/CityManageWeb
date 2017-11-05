package com.city.api.service;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.city.api.dao.PushDao;
import com.city.model.PushInfo;
import com.city.model.PushResultInfo;
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;

import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;

public class PushService {

	Connection conn = null;
	
	private PushDao pushDao = new PushDao();
	private String MESSAGE_ID = String.valueOf(Math.random() % 100 + 1);
	private boolean SHOW_ON_IDLE = true;
	private int LIVE_TIME = 1;
	private int RETRY = 2;
	
	//push를 보내기 위한 API KEY 저장 변수(web-inf/apiKey.properties 파일에 저장되어있음)
	private String APIKEY ="";
	
	public PushService() {
		super();
		
		try {
			String apiKeyProperties = "/WEB-INF/apiKey.properties";  
			Properties prop = new Properties();	
			FileInputStream fis = new FileInputStream(apiKeyProperties); 
			
			prop.load(new BufferedInputStream(fis));
			
			APIKEY = prop.getProperty("apiKey");
			
			System.out.println("api key : " + APIKEY);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}

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

			pushInfoList = pushDao.selectPushHistoryListByMemberId(conn, memberId, manageType);
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

	/**
	 * @param PushInfo
	 * @return
	 */
	public String pushTokenRegister(PushInfo pushInfo) throws SQLException {
		int resultCode = 0;
		String ptr = "";

		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);

			resultCode = pushDao.insertPushToken(conn, pushInfo);

			conn.commit();

			if (resultCode == 1) {
				ptr = "Y";
			} else {
				throw new SQLException();
			}

		} catch (SQLException e) {
			e.printStackTrace();
			JdbcUtil.rollback(conn);
		} finally {
			JdbcUtil.close(conn);
		}
		return ptr;
	}

	/**
	 * @param PushInfo
	 * @return
	 */
	public String pushTokenUpdate(PushInfo pushInfo) {
		String ptu = "";
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);

			int resultCode = pushDao.updatePushToken(conn, pushInfo);

			conn.commit();

			if (resultCode == 1) {
				ptu = "Y";
			} else {
				throw new SQLException();
			}

		} catch (SQLException e) {
			e.printStackTrace();
			JdbcUtil.rollback(conn);
		} finally {
			JdbcUtil.close(conn);
		}
		return ptu;
	}

	public ArrayList<String> sendTokenList() throws SQLException {
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

	/*
	 * ApiKey → FireBase에서 가져온 서버 키 MESSAGE_ID → 메세지 고유 ID SHOW_ON_IDLE → 앱이 비활성화
	 * 상태일때 PUSH를 보여줄 것인지 LIVE_TIME → 비활성화 상태일때 FCM가 메시지를 유효화하는 시간 RETRY → 메시지 전송실패시
	 * 재시도 횟수
	 */

	public void sendPush(ArrayList<String> tokenList, String title, String content) {
		Sender sender = new Sender(APIKEY);
		Message message = new Message.Builder().collapseKey(MESSAGE_ID).delayWhileIdle(SHOW_ON_IDLE)
				.timeToLive(LIVE_TIME).addData("title", title).addData("contents", content).build();

		MulticastResult result;
		try {
			result = sender.send(message, tokenList, RETRY);
			
			//뭐하는소스지...필요가 없는데.....................어쩌겠다는거지......
			if (result != null) {
				List<Result> resultList = result.getResults();

				for (Result result2 : resultList) {
					// System.out.println(result2.getErrorCodeName());
				}
			}
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}
}
