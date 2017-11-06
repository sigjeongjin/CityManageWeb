package com.city.api.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.city.api.dao.FavoriesDao;
import com.city.model.FavoritesResultInfo;

import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;

public class FavoritesService {

	private FavoriesDao favoriesDao = new FavoriesDao();
	Connection conn = null;

	public int setFavoritesRegister(String memberId, String manageId) {

		int resultCode = 0;

		try {
			conn = ConnectionProvider.getConnection(); // transaction
			conn.setAutoCommit(false);

			resultCode = favoriesDao.insertFavories(conn, memberId, manageId);
			conn.commit();

		} catch (SQLException e) {
			e.printStackTrace();
			JdbcUtil.rollback(conn);
		} finally {
			JdbcUtil.close(conn);
		}
		return resultCode;
	}
	
	public int setFavoritesRelease(String memberId, String manageId) {
		int resultCode = 0;

		try {
			conn = ConnectionProvider.getConnection(); // transaction
			conn.setAutoCommit(false);

			resultCode = favoriesDao.updateFavories(conn, memberId, manageId);

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
	 * @param memberId
	 * @param manageType
	 * @return
	 */
	public List<FavoritesResultInfo> getFavoritesList(String memberId, String manageType) {

		List<FavoritesResultInfo> favoritesList = new ArrayList<FavoritesResultInfo>();

		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);

			favoritesList = favoriesDao.selectFavoritesInfoByMemberId(conn, memberId, manageType);
			conn.commit();

		} catch (SQLException e) {
			e.printStackTrace();
			JdbcUtil.rollback(conn);
		} finally {
			JdbcUtil.close(conn);
		}
		return favoritesList;
	}
}