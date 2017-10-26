package com.city.api.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.city.api.dao.FavoriesDao;
import com.city.model.Favorites;
import com.city.model.FavoritesResultInfo;

import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;

/* 
 * FavoritesListService 즐겨찾기 리스트 조회 			fls
 * FavoritesRegisterService 즐겨찾기 등록			frs
 */

public class FavoritesService {

	private FavoriesDao favoriesDao = new FavoriesDao();

	public String favoritesRegister(String memberId, String manageId) {

		int favoritesRegister = 0;
		String resultCode = "";
		Connection conn = null;

		try {
			conn = ConnectionProvider.getConnection(); // transaction
			conn.setAutoCommit(false);

			favoritesRegister = favoriesDao.insertFavories(conn, memberId, manageId);

			if (favoritesRegister == 1) {
				resultCode = "Y";
			} else {
				resultCode = "N";
				throw new SQLException();
			}
			conn.commit();

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("즐겨찾기 등록 실패.");
			JdbcUtil.rollback(conn);
		} finally {
			JdbcUtil.close(conn);
		}
		return resultCode;
	}
	
	public String favoritesRelease(String memberId, String bookmark, String manageId) {

		int favoritesRegister = 0;
		String resultCode = "";
		Connection conn = null;

		try {
			conn = ConnectionProvider.getConnection(); // transaction
			conn.setAutoCommit(false);

			favoritesRegister = favoriesDao.updateFavories(conn, memberId, manageId);

			if (favoritesRegister == 1) {
				resultCode = "Y";
			} else {
				resultCode = "N";
				throw new SQLException();
			}
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("즐겨찾기 해제 실패.");
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
	
	
	/**
	 * @param memberId
	 * @param manageId
	 * @return
	 */
	public String getFavoritesWhether(String memberId, String manageId) {

		String result = "";
		Connection conn = null;

		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);

			result = favoriesDao.selectFavoritesWhetherByMemberIdAndManageId(conn, memberId, manageId);

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("즐겨찾기 여부를 조회 하지 못했습니다.");
			JdbcUtil.rollback(conn);
		} finally {
			JdbcUtil.close(conn);
		}
		return result;
	}
}