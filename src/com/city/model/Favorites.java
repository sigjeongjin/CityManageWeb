package com.city.model;

public class Favorites extends Result{
	
	public String favoriteId;
	public String manageId;
	public String bookmark;
	public String memberId;
	

	
	public Favorites(String favoriteId, String manageId, String bookmark, String memberId) { 
		this.favoriteId = favoriteId;
		this.manageId = manageId;
		this.bookmark = bookmark;
		this.memberId = memberId;
	}

	public String getFavoriteId() {
		return favoriteId;
	}

	public String getManageId() {
		return manageId;
	}

	public String getBookmark() {
		return bookmark;
	}
	public String getmemberId() { 
		return memberId;
	}

	public void setFavoriteId(String favoriteId) {
		this.favoriteId = favoriteId;
	}

	public void setManageId(String manageId) {
		this.manageId = manageId;
	}

	public void setBookmark(String bookmark) {
		this.bookmark = bookmark;
	}
	public void setmemberId(String memberId) { 
		this.memberId = memberId;
	}
	
	

}
