package com.city.model;

public class FavoritesInfo {
	
	private String memberId;
	private String memberType;
	
	
	public FavoritesInfo(String memberId, String memberType) {
		this.memberId = memberId;
		this.memberType = memberType;
	}
	public String getMemberId() {
		return memberId;
	}
	public String getMemberType() {
		return memberType;
	}


}
