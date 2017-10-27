package com.city.model;

public class Favorites {
	
	private String favoritesId;
	private String manageId;
	private String bookmark;
	private String memberId;
	
	public Favorites() {

	}
	
	public Favorites(String favoriteId, String manageId, String bookmark, String memberId) { 
		this.favoritesId = favoriteId;
		this.manageId = manageId;
		this.bookmark = bookmark;
		this.memberId = memberId;
	}

	public String getFavoritesId() {
		return favoritesId;
	}

	public String getManageId() {
		return manageId;
	}

	public String getBookmark() {
		return bookmark;
	}
	public String getMemberId() { 
		return memberId;
	}

	public void setFavoritesId(String favoriteId) {
		this.favoritesId = favoriteId;
	}

	public void setManageId(String manageId) {
		this.manageId = manageId;
	}

	public void setBookmark(String bookmark) {
		this.bookmark = bookmark;
	}
	public void setMemberId(String memberId) { 
		this.memberId = memberId;
	}
}
