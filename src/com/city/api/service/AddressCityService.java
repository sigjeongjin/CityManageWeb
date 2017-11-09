package com.city.api.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.city.api.dao.MemberDao;
import com.city.model.City;
import com.city.model.State;

import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;

public class AddressCityService {

	private Connection conn = null;
	private MemberDao memberDao = new MemberDao();

	public int setCityStateInfo(String cityCode, String stateCode, String memberId, String memberPwd) {
		int resultCode = 0;

		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);

			resultCode = memberDao.updateCityStateInfoRegiste(conn, cityCode, stateCode, memberId, memberPwd);
			
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			JdbcUtil.rollback(conn);
		} finally {
			JdbcUtil.close(conn);
		}
		return resultCode;
	}

	public List<City> getAddressCityCode() {

		List<City> city = new ArrayList<City>();
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);

			city = memberDao.selectCityInfo(conn);
			
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			JdbcUtil.rollback(conn);
		} finally {
			JdbcUtil.close(conn);
		}
		return city;
	}

	public List<State> getStateList(String cityCode) {
		List<State> state = new ArrayList<State>();
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);

			state = memberDao.selectStateListByCityCode(conn, cityCode);
			conn.commit();

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("지역을 불러오지 못했습니다.");
			JdbcUtil.rollback(conn);
		} finally {
			JdbcUtil.close(conn);
		}
		return state;
	}

}