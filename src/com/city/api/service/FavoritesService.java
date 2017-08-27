package com.city.api.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.city.api.dao.FavoriesDao;
import com.city.model.FavoritesInfo;
import com.city.model.State;

import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;

public class FavoritesService {

	private FavoriesDao favoriesDao = new FavoriesDao();

	public String favoritesRegister(String memberId, String bookmark, String manageId) {

		int favoritesRegister = 0;
		String resultCode = "";
		Connection conn = null;

		try {
			conn = ConnectionProvider.getConnection(); // transaction
			conn.setAutoCommit(false);

			favoritesRegister = favoriesDao.insertFavories(conn, memberId, bookmark, manageId);

			if (favoritesRegister == 1) {
				resultCode = "Y";
			} else {
				throw new SQLException();
			}

			conn.commit();

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("등록 실패.");
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
	public List<FavoritesInfo> getFavoritesList(String memberId, String manageType) {

		List<FavoritesInfo> favoritesList = new ArrayList<FavoritesInfo>();
		Connection conn = null;

		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);

			favoritesList = favoriesDao.selectFavoritesInfoByMemberId(conn, memberId, manageType);
			conn.commit();

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("지역을 불러오지 못했습니다.");
			JdbcUtil.rollback(conn);
		} finally {
			JdbcUtil.close(conn);
		}
		return favoritesList;
	}
}