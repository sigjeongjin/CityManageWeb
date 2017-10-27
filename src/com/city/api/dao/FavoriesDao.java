package com.city.api.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.city.model.Favorites;
import com.city.model.FavoritesResultInfo;

import jdbc.JdbcUtil;

public class FavoriesDao {

	public int insertFavories(Connection conn, String memberId , String manageId) throws SQLException {

		PreparedStatement pstmt = null;
		int resultcode = 0;

		try {
			pstmt = conn.prepareStatement("insert into favorites_info" +
											"(member_id, bookmark, manage_id)" + 
											"values (?, 'Y', ?)");
			pstmt.setString(1, memberId);
			pstmt.setString(2, manageId);
			
			resultcode = pstmt.executeUpdate();

			return resultcode;
		} finally {
			JdbcUtil.close(pstmt);
		}
	}
	
	public int updateFavories(Connection conn, String memberId , String manageId)
			throws SQLException {

		PreparedStatement pstmt = null;
		int resultcode = 0;

		try {
			pstmt = conn.prepareStatement("delete from favorites_info " +
											" where member_id=? and manage_id=? ");
			pstmt.setString(1, memberId);
			pstmt.setString(2, manageId);
			
			resultcode = pstmt.executeUpdate();

			return resultcode;
		} finally {
			JdbcUtil.close(pstmt);
		}
	}
	
	public Favorites selectFavoritesByMemberIdAndManageId(Connection conn, String memberId , String manageId)
			throws SQLException {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Favorites favorites = new Favorites();

		try {
			//출력 결과 : manageId, locationName(시티 + 스테이트)
			pstmt = conn.prepareStatement(
					"SELECT " + 
					" favorites_id favoritesId, " + 
					" manage_id manageId, " +
					" bookmark, " +
					" member_id memberId " + 
					" FROM favorites_info " + 
					" WHERE member_id=? and manage_id=?");
		
			pstmt.setString(1, memberId);
			pstmt.setString(2, manageId);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				favorites.setMemberId(rs.getString("member_id").toString());
				favorites.setManageId(rs.getString("manage_id"));
				favorites.setFavoritesId(rs.getString("favoritesId"));
				favorites.setBookmark(rs.getString("bookmark"));
			}
			return favorites;

		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
	}
	
	public List<FavoritesResultInfo> selectFavoritesInfoByMemberId(Connection conn, String memberId, 
			String manageType) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<FavoritesResultInfo> favoritesList = new ArrayList<FavoritesResultInfo>();

		try {
			//출력 결과 : manageId, locationName(시티 + 스테이트)
			pstmt = conn.prepareStatement(
					"SELECT " + 
					" fi.manage_id manageId, " + 
					" CONCAT((SELECT city_name cityName FROM address_city WHERE city_code=lm.city_code)" +
					",' ',(SELECT state_name stateName FROM address_state WHERE state_code=lm.state_code)) locationName" + 
					" FROM favorites_info fi JOIN location_management lm on fi.manage_id=lm.manage_id " + 
					" WHERE lm.manage_type=? and fi.bookmark='Y' and fi.member_id=? ");
		
			pstmt.setString(1, manageType);
			pstmt.setString(2, memberId);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				FavoritesResultInfo favoritesInfo = new FavoritesResultInfo();
				favoritesInfo.setManageId(rs.getString("manageId"));
				favoritesInfo.setLocationName(rs.getString("locationName"));
				favoritesList.add(favoritesInfo);
			}
			return favoritesList;

		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
	}
	
	
	public String selectFavoritesWhetherByMemberIdAndManageId(Connection conn, String memberId , String manageId)
			throws SQLException {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String result = "";

		try {
			//출력 결과 : manageId, locationName(시티 + 스테이트)
			pstmt = conn.prepareStatement(
					"SELECT " 
					+ " count(*) count "
					+ " FROM favorites_info " 
					+ " WHERE member_id=? and manage_id=?");
		
			pstmt.setString(1, memberId);
			pstmt.setString(2, manageId);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				result = rs.getString("count");
			}
			return result;

		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
	}
}
