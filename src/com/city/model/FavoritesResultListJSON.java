package com.city.model;

import java.util.ArrayList;
import java.util.List;

public class FavoritesResultListJSON extends Result {
	
	private List<FavoritesResultInfo> favoritesList = new ArrayList<FavoritesResultInfo>();

	public List<FavoritesResultInfo> getFavoritesList() {
		return favoritesList;
	}

	public void setFavoritesList(List<FavoritesResultInfo> favoritesList) {
		this.favoritesList = favoritesList;
	}
}
