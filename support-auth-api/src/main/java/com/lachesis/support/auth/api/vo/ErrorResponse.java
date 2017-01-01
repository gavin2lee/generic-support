package com.lachesis.support.auth.api.vo;

public interface ErrorResponse extends AuthResponse {
	String getSubcode();
	void setSubcode(String subcode);
	String getSubmsg();
	void setSubmsg(String submsg);
}
