package com.city.api.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.city.model.FavoritesInfo;
import com.city.model.State;
import com.mysql.jdbc.Statement;

import jdbc.JdbcUtil;

public class FavoriesDao {

	public int insertFavories(Connection conn, String memberId ,String bookmark, String manageId)

			throws SQLException {

		PreparedStatement pstmt = null;
		int resultcode = 0;

		
		try {

			pstmt = conn.prepareStatement("insert into favorites_info" +
											"(member_id, bookmark, manage_id)" + 
											"values (?, ?, ?)");
			pstmt.setString(1, memberId);
			pstmt.setString(2, bookmark);
			pstmt.setString(3, manageId);
			
			resultcode = pstmt.executeUpdate();

			return resultcode;

			

		} finally {
		
			JdbcUtil.close(pstmt);
		}
	}
	
	public List<FavoritesInfo> selectFavoritesInfo(Connection conn, String memberId, String manageType) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<FavoritesInfo> favoritesInfo = new ArrayList<FavoritesInfo>();

		String Resultcode = "200";
		try {
			pstmt = conn.prepareStatement(
					"select member_id as sensorId, manage_type as manageType from favorites_info where member_id=? and manage_type=?");
			pstmt.setString(1, memberId);
			pstmt.setString(2, manageType);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				favoritesInfo.add(new FavoritesInfo(rs.getString("memberId"), rs.getString("manageType")));
			}
			return favoritesInfo;

		} finally {
			JdbcUtil.close(rs);	
			JdbcUtil.close(pstmt);
		}
	}

}
