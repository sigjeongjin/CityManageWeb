package com.city.model;


public class Member {

	private String memberId;
	private String memberPwd;
	private String memberName;
	private String memberPhone;	
	private String memberEmail;
	private String memberPhoto;
	private String memberAuthorization;
	private String memberDeleteCode;
	private String cityGeocode;
	private String stateGeocode;
	
	public Member() {

	}

	public Member(String memberId, String memberPwd, String memberName, String memberPhone, String memberEmail,
					String memberPhoto, String  memberAuthorization, String cityGeocode, String stateGeocode) {
		this.memberId = memberId;
		this.memberPwd = memberPwd;
		this.memberName = memberName;
		this.memberPhone = memberPhone;
		this.memberEmail = memberEmail;
		this.memberPhoto = memberPhoto;
		this.memberAuthorization = memberAuthorization;
		this.cityGeocode = cityGeocode;
		this.stateGeocode = stateGeocode;
	}

	public Member(String memberId, String memberName) {
		this.memberId = memberId;
		this.memberName = memberName;
	}

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

	public String getCityGeocode() {
		return cityGeocode;
	}

	public String getStateGeocode() {
		return stateGeocode;
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

	public void setCityGeocode(String cityGeocode) {
		this.cityGeocode = cityGeocode;
	}

	public void setStateGeocode(String stateGeocode) {
		this.stateGeocode = stateGeocode;
	}

	public boolean matchPassword(String Pwd) {
		return memberPwd.equals(Pwd);
	}

	public void changePassword(String newPwd) {
		this.memberPwd = newPwd;
	}
}
