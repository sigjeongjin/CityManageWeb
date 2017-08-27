package com.city.model;

import java.util.ArrayList;
import java.util.List;

public class FavoritesInfoJSON extends Result {
	
	private List<FavoritesInfo> favoritesList = new ArrayList<FavoritesInfo>();

	public List<FavoritesInfo> getFavoritesList() {
		return favoritesList;
	}

	public void setFavoritesList(List<FavoritesInfo> favoritesList) {
		this.favoritesList = favoritesList;
	}
}
