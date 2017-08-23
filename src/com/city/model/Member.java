package com.city.model;


public class Member{

	private String memberId;
	private String memberPwd;
	private String memberName;
	private String memberPhone;	
	private String memberEmail;
	private String memberPhoto;
	private String memberAuthorization;
	private String memberDeleteCode;
	private String cityCode;
	private String stateCode;
	
	public String getMemberId() {
		return memberId;
	}

	public String getMemberPwd() {
		return memberPwd;
	}

	public String getMemberName() {
		return memberName;
	}

	public String getMemberPhone() {
		return memberPhone;
	}

	public String getMemberEmail() {
		return memberEmail;
	}

	public String getMemberPhoto() {
		return memberPhoto;
	}

	public String getMemberAuthorization() {
		return memberAuthorization;
	}

	public String getMemberDeleteCode() {
		return memberDeleteCode;
	}

	public String getCityCode() {
		return cityCode;
	}

	public String getStateCode() {
		return stateCode;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public void setMemberPwd(String memberPwd) {
		this.memberPwd = memberPwd;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public void setMemberPhone(String memberPhone) {
		this.memberPhone = memberPhone;
	}

	public void setMemberEmail(String memberEmail) {
		this.memberEmail = memberEmail;
	}

	public void setMemberPhoto(String memberPhoto) {
		this.memberPhoto = memberPhoto;
	}

	public void setMemberAuthorization(String memberAuthorization) {
		this.memberAuthorization = memberAuthorization;
	}

	public void setMemberDeleteCode(String memberDeleteCode) {
		this.memberDeleteCode = memberDeleteCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	public boolean matchPassword(String Pwd) {
		return memberPwd.equals(Pwd);
	}

	public void changePassword(String newPwd) {
		this.memberPwd = newPwd;
	}
}
