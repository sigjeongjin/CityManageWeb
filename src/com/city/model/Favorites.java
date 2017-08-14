package com.city.model;

public class Favorites {
	
	private String favoriteId;
	private String sensorId;
	private String bookmark;
	private String memberId;
	
	public Favorites() {

	}
	
	public Favorites(String favoriteId, String sensorId, String bookmark, String memberId) { 
		this.favoriteId = favoriteId;
		this.sensorId = sensorId;
		this.bookmark = bookmark;
		this.memberId = memberId;
	}

	public String getFavoriteId() {
		return favoriteId;
	}

	public String getSensorId() {
		return sensorId;
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

	public void setSensorId(String sensorId) {
		this.sensorId = sensorId;
	}

	public void setBookmark(String bookmark) {
		this.bookmark = bookmark;
	}
	public void setmemberId(String memberId) { 
		this.memberId = memberId;
	}
	
	

}
