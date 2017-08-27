package com.city.model;

import java.util.ArrayList;
import java.util.List;

public class FavoritesInfoJSON extends Result {
	
	private List<FavoritesInfo> favoritesInfo = new ArrayList<FavoritesInfo>();

	public List<FavoritesInfo> getFavoritesInfo() {
		return favoritesInfo;
	}

	public void setFavoritesInfo(List<FavoritesInfo> favoritesInfo) {
		this.favoritesInfo = favoritesInfo;
	}
	
	

}
